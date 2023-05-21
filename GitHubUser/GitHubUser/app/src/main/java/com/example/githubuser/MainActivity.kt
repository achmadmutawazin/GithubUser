package com.example.githubuser


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.model.Result
import com.example.githubuser.data.preference.Setting
import com.example.githubuser.data.preference.SettingPref
import com.example.githubuser.data.remote.GithubResponse
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.ui.currencyconverter.CurrencyConverter
import com.example.githubuser.ui.detail.Detail
import com.example.githubuser.ui.favourite.Favourite
import com.google.android.material.snackbar.Snackbar

sealed class MainActivityEvent {
    data class ShowMessage(val message: String) : MainActivityEvent()
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy {
        UserAdapter{user->
            Intent(this, Detail::class.java).apply {
                putExtra("item",user)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<MainViewModel>{
        MainViewModel.Factory(SettingPref(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // Enable the home button
            setHomeAsUpIndicator(R.drawable.baseline_refresh_24) // Set the left arrow icon
        }

        viewModel.getTheme().observe(this){
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        viewModel.resultUser.observe(this){
            when(it){
                is Result.Success<*>->{
                    adapter.setData(it.data as MutableList<GithubResponse.ItemsItem>)
                }
                is Result.Error->{
                    val message = it.exception.message.toString()
                    Snackbar.make(binding.root,message,Snackbar.LENGTH_SHORT).show()
                }
                is Result.Loading->{
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
        viewModel.event.observe(this) { event ->
            when (event) {
                is MainActivityEvent.ShowMessage -> {
                    Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getUser()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu,menu)

        val searchManager=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView= menu.findItem(R.id.search_opt).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity,query,Toast.LENGTH_SHORT).show()
                viewModel.Search(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_opt -> {
                Intent(this, Favourite::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.setting_opt -> {
                Intent(this, Setting::class.java).apply {
                    startActivity(this)
                }
            }
            android.R.id.home ->{
                viewModel.getUser() // Call getUser() to refresh the user list
                return true
            }
            R.id.currency -> {
                val intent = Intent(this, CurrencyConverter::class.java)
                startActivity(intent)
                true
            }
        }
        return super.onOptionsItemSelected(item)}
}


