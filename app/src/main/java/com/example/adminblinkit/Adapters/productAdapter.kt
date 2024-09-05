package com.example.adminblinkit.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.models.SlideModel
import com.example.adminblinkit.Models.Products
import com.example.adminblinkit.databinding.CategoryItemviewBinding
import com.example.adminblinkit.databinding.ProductItemviewBinding

class productAdapter :  RecyclerView.Adapter<productAdapter.productViewHolder>() {


    class productViewHolder(val binding: ProductItemviewBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    val diffUtil = object  : DiffUtil.ItemCallback<Products>(){
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
        return productViewHolder(ProductItemviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: productViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            val imageList = ArrayList<SlideModel>()

            for(i in 0 until product.productImageUris.size){
                imageList.add(SlideModel(product.productImageUris[i].toString()))
            }
            imageSlider.setImageList(imageList)
            productTitle.text = product.productTitle
            productPrice.text = "₹${product.productPrice.toString()}"
            val quantity = product.productQuantity.toString() + product.productUnit
            productQuantity.text = quantity
        }
    }
}