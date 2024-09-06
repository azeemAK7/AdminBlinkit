package com.example.adminblinkit.ViewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.adminblinkit.Models.Products
import com.example.adminblinkit.Utils
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AdminViewModel : ViewModel() {
    private val _imgUploadSuccess =  MutableStateFlow(false)
    val imgUploadSuccess = _imgUploadSuccess

    private val _productUploadSuccess =  MutableStateFlow(false)
    val productUploadSuccess = _productUploadSuccess

    val downloadedUri = MutableStateFlow<ArrayList<String?>?>(null)

     suspend fun saveImg(imageUri : ArrayList<Uri>){
        val downloadUri = ArrayList<String?>( )

        imageUri.forEach { Uri ->
            val imageRef =  FirebaseStorage.getInstance().reference.child(Utils.getCurrentUserId()).child("Images").child(Utils.getRandomId())
            imageRef.putFile(Uri).continueWithTask {
        imageRef.downloadUrl

            }.addOnCompleteListener {task ->
                val uri = task.result.toString()
                downloadUri.add(uri)
            }
        }
        if(downloadUri.size == imageUri.size) {
            _imgUploadSuccess.value = true
            downloadedUri.value = downloadUri
        }
    }

    fun saveProduct(product: Products){
        FirebaseDatabase.getInstance().reference.child("Admins").child("AllProducts/${product.productId}").setValue(product).addOnSuccessListener {
            FirebaseDatabase.getInstance().reference.child("Admins").child("ProductCategory/${product.productCategory}").setValue(product).addOnSuccessListener{
                FirebaseDatabase.getInstance().reference.child("Admins").child("ProductType/${product.productType}").setValue(product).addOnSuccessListener{
                    _productUploadSuccess.value = true
                }
            }
        }
    }

    fun getAllProducts(category : String) : Flow<ArrayList<Products>> = callbackFlow{
        val db = FirebaseDatabase.getInstance().reference.child("Admins").child("AllProducts")
        val productList = ArrayList<Products>()

        val eventListner = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(product in snapshot.children) {
                    val prod = product.getValue(Products::class.java)
                    if (category == "all" || category == prod?.productCategory) {
                        productList.add(prod!!)
                    }
                }
                trySend(productList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        db.addValueEventListener(eventListner)
        awaitClose { db.removeEventListener(eventListner) }
    }
}