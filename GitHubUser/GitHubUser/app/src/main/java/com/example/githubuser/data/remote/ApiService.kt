package com.example.githubuser.data.remote

import retrofit2.http.*

interface ApiService {
    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUsers(
    ): MutableList<GithubResponse.ItemsItem>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUsers(
        @Path("username")
        username:String
    ): GithubUserResponse

    @JvmSuppressWildcards
    @GET("/users/{username}/followers")
    suspend fun getFollowers(
        @Path("username")
        username:String
    ):MutableList<GithubResponse.ItemsItem>

    @JvmSuppressWildcards
    @GET("/users/{username}/following")
    suspend fun getFollowing(
        @Path("username")
        username:String
    ):MutableList<GithubResponse.ItemsItem>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun getSearch(
        @QueryMap
        params:Map<String, Any>
    ): GithubResponse
}