package com.mgm.fallingwords.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.mgm.fallingwords.R
import com.mgm.fallingwords.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityMainBinding

    //Other
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InitViews
        binding.apply {
            //Navigation
            navController = findNavController(R.id.navHost)
        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.homeFragment -> {
                finish()
            }
            R.id.gameResultSheet , R.id.gameFragment -> {
                navController.navigate(R.id.homeFragment)
            }
            else -> super.onBackPressed()
        }
    }
}