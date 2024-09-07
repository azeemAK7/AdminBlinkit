package com.example.adminblinkit.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.adminblinkit.Models.Categories
import com.example.adminblinkit.Models.Products
import com.example.adminblinkit.databinding.CategoryItemviewBinding
import com.example.adminblinkit.databinding.FragmentHomeBinding
import com.example.adminblinkit.databinding.ProductItemviewBinding

class categoryAdapter(val categories: ArrayList<Categories>) : RecyclerView.Adapter<categoryAdapter.categoryViewHolder>() {
    class categoryViewHolder(val binding: CategoryItemviewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryViewHolder {
        return categoryViewHolder(CategoryItemviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: categoryViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.apply {
            icon.setImageResource(category.icon)
            title.text = category.title
        }
    }
}











