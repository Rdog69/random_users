package com.random.example.usergenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import com.random.example.usergenerator.databinding.ActivityMainBinding
import com.random.example.usergenerator.network.RandomUserService
import com.random.example.usergenerator.network.response.PersonInfo
import com.random.example.usergenerator.network.response.RandomUserResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityMainBinding
    private val binding get() = _binding
    override  fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        _binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED

        binding.bottomNavigation.setOnItemSelectedListener { item ->
                     when(item.itemId) {
                         R.id.users -> {
                             findNavController(R.id.fragmentContainerView).navigate(R.id.usersFragment)
                             true
                         }
                         R.id.saved -> {
                             findNavController(R.id.fragmentContainerView).navigate(R.id.savedFragment)
                             true
                         }
                         R.id.setting -> {
                             findNavController(R.id.fragmentContainerView).navigate(R.id.settingFragment)
                             true
                         }

                         else -> false
                     }
        }}

}


