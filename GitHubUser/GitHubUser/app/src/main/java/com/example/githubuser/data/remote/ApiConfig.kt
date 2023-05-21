package com.example.githubuser.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        private var token: String? = null

        fun setToken(token: String) {
            this.token = token
        }

        fun getApiService(token:String): ApiService {
            val authInterceptor = Interceptor{chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
                chain.proceed(requestHeaders)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(com.example.githubuser.BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}