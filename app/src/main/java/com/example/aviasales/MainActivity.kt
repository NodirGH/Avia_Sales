package com.example.aviasales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.aviasales.databinding.ActivityMainBinding
import extension.hideAnimWithSlideDown
import extension.showAnimWithSlideUp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
        binding.bottomNavigationView.itemIconTintList = null
        navController = navHostFragment.navController
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.hotelsFragment,
                R.id.inShortsFragment,
                R.id.subscribeFragment,
                R.id.profileFragment -> {
                    binding.bottomNavigationView.showAnimWithSlideUp()
                }
                else -> {
                    binding.bottomNavigationView.hideAnimWithSlideDown()
                }
            }
        }
    }
}