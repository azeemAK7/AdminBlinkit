package com.example.adminblinkit.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.adminblinkit.R
import com.example.adminblinkit.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NavigationUI.setupWithNavController(binding.bottomMenu,Navigation.findNavController(this,R.id.fragmentContainerView2))
    }
}