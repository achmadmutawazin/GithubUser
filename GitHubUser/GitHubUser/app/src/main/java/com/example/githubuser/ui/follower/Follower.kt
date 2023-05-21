 package com.example.githubuser.ui.follower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.model.Result
import com.example.githubuser.UserAdapter
import com.example.githubuser.data.remote.GithubResponse
import com.example.githubuser.databinding.FragmentFollowerBinding
import com.example.githubuser.ui.detail.DetailViewModel

 class Follower : Fragment() {

    private var binding:FragmentFollowerBinding? = null
     private val adapter by lazy {
         UserAdapter{

         }
     }
     private val viewModel by activityViewModels<DetailViewModel>()
     var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(layoutInflater)
        return binding?.root
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

         binding?.rvFollower?.apply {
             layoutManager = LinearLayoutManager(requireActivity())
             setHasFixedSize(true)
             adapter = this@Follower.adapter
         }
         when (type) {
             FOLLOWERS -> {
                 viewModel.resultFollower.observe(viewLifecycleOwner, this::manageResultFollowers)
             }

             FOLLOWING -> {
                 viewModel.resultFollowing.observe(viewLifecycleOwner, this::manageResultFollowers)
             }
         }
     }

     private fun manageResultFollowers(state: Result){
         when(state){
             is Result.Success<*> ->{
                 adapter.setData(state.data as MutableList<GithubResponse.ItemsItem>)
             }
             is Result.Error ->{
                 Toast.makeText(requireActivity(),state.exception.message.toString(), Toast.LENGTH_SHORT).show()
             }
             is Result.Loading ->{
                 binding?.progressBar?.isVisible = state.isLoading
             }
         }
     }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101
        fun newInstance(type:Int) = Follower()
            .apply {
                this.type = type
            }
}
 }