package com.example.githubuser.data.remote

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
){
	@Parcelize
	@Entity(tableName = "User")
	data class ItemsItem(
	@ColumnInfo(name = "login")
	@field:SerializedName("login")
	val login: String,

	@ColumnInfo(name = "avatar_url")
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	@field:SerializedName("id")
	val id: Int
):Parcelable }
