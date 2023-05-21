package com.example.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubuser.data.remote.GithubResponse
import com.example.githubuser.databinding.ItemListBinding

class UserAdapter(private val data:MutableList<GithubResponse.ItemsItem> = mutableListOf(),
                  private val listener:(GithubResponse.ItemsItem)-> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    fun setData(data: MutableList<GithubResponse.ItemsItem>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val v: ItemListBinding):RecyclerView.ViewHolder(v.root){
        fun bind(item: GithubResponse.ItemsItem){
            v.profileImage.load(item.avatarUrl)
            v.userName.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener(){
            listener(item)
        }
    }

}