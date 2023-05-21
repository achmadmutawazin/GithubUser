package com.example.githubuser.ui.favourite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.UserAdapter
import com.example.githubuser.data.local.DbConfig
import com.example.githubuser.databinding.ActivityFavouriteBinding
import com.example.githubuser.ui.detail.Detail

class Favourite : AppCompatActivity() {
    private lateinit var binding:ActivityFavouriteBinding

    private val adapter by lazy {
        UserAdapter{user->
            Intent(this, Detail::class.java).apply {
                putExtra("item",user)
                startActivity(this)
            }
        }
    }


    private val viewModel by viewModels<FavViewModel> {
        FavViewModel.Factory(DbConfig(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvfav.layoutManager = LinearLayoutManager(this)
        binding.rvfav.adapter = adapter

        viewModel.getUserFav().observe(this){
            adapter.setData(it)
        }
}

    override fun onResume() {
        super.onResume()
        viewModel.getUserFav().observe(this){
            adapter.setData(it)
        }
    }
}
