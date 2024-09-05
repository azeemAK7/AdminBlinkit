package com.example.adminblinkit.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.adminblinkit.Adapters.categoryAdapter
import com.example.adminblinkit.Adapters.productAdapter
import com.example.adminblinkit.Constants
import com.example.adminblinkit.Models.Categories

import com.example.adminblinkit.R
import com.example.adminblinkit.ViewModels.AdminViewModel
import com.example.adminblinkit.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    val adminViewModel : AdminViewModel by viewModels()
    private lateinit var binding : FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        showCategory()
        getAllProducts()
        return binding.root
    }

    private fun getAllProducts() {
        lifecycleScope.launch {
            adminViewModel.getAllProducts().collect{
                val adapter : productAdapter = productAdapter()
                binding.rvProducts.adapter = adapter
                adapter.differ.submitList(it)
            }
        }

    }

    private fun showCategory() {
        val categories = ArrayList<Categories>()
        for(i in 0 until Constants.allProductsCategory.size){
            categories.add(Categories(Constants.productIcon[i],Constants.allProductsCategory[i]))
        }

        binding.rvCategories.adapter = categoryAdapter(categories)
    }


}