@file:Suppress("DEPRECATION")

package com.example.githubuser.ui.detail

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.example.githubuser.*
import com.example.githubuser.data.local.DbConfig
import com.example.githubuser.data.model.Result
import com.example.githubuser.data.remote.GithubResponse
import com.example.githubuser.data.remote.GithubUserResponse
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.ui.follower.Follower
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Detail : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>(){
        DetailViewModel.Factory(DbConfig(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<GithubResponse.ItemsItem>("item")
        val username = item?.login?:""

        viewModel.resultDetailUser.observe(this){
            when(it){
                is Result.Success<*> ->{
                    val user = it.data as GithubUserResponse
                    binding.detailImage.load(user.avatarUrl)
                    binding.detailName.text = user.name
                    binding.followcnt.text = "${user.followers} Followers"
                    binding.followingcnt.text = "${user.following} Following"
                        binding.useraliases.text = user.login
                }
                is Result.Error ->{
                    Toast.makeText(this,it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading ->{
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
        viewModel.getDetailUser(username)

        val fragments = mutableListOf<Fragment>(
            Follower.newInstance(Follower.FOLLOWERS),
            Follower.newInstance(Follower.FOLLOWING)
        )
        val titleFragment = mutableListOf(
            getString(R.string.follower),
            getString(R.string.following)
        )
        val adapter = DetailAdapter(this,fragments)
        binding.viewpage.adapter = adapter

        TabLayoutMediator(binding.tab,binding.viewpage){ tab, position->
            tab.text = titleFragment[position]
        }.attach()

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    viewModel.getFollowers(username)
                }else{
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewModel.getFollowers(username)

        viewModel.resultisfav.observe(this){
            binding.Favourite.changeIconColor(R.color.red)
        }

        viewModel.resultdelfav.observe(this){
            binding.Favourite.changeIconColor(R.color.white)
        }

        binding.Favourite.setOnClickListener{
            viewModel.setFav(item)
        }
        viewModel.findFav(item?.id?:0){
            binding.Favourite.changeIconColor(R.color.red)
        }

    }
    fun FloatingActionButton.changeIconColor(@ColorRes color :Int){
        imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context,color))
    }

}