package com.example.adminblinkit.Adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminblinkit.databinding.ItemViewImageSelectionBinding

class selectImageAdapter(val imageUri : ArrayList<Uri>) : RecyclerView.Adapter<selectImageAdapter.selectImgViewHolder>() {
    class selectImgViewHolder(val binding: ItemViewImageSelectionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): selectImgViewHolder {
        return selectImgViewHolder(ItemViewImageSelectionBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return imageUri.size
    }

    override fun onBindViewHolder(holder: selectImgViewHolder, position: Int) {
        val currentImg = imageUri[position]
        holder.binding.apply {
            ivImg.setImageURI(currentImg)
        }
        holder.binding.closeButton.setOnClickListener {
            imageUri.remove(currentImg)
            notifyItemRemoved(position)
        }
    }
}