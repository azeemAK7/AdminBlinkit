package com.example.adminblinkit.Models

data class Products(
    val productId : String ?= null,
    val productTitle : String ?= null,
    val productQuantity : Int ?= null,
    val productUnit : String ?= null,
    val productPrice : Double ?= null,
    val productStock : Int ?= null,
    val productCategory : String ?= null,
    val productType : String ?= null,
    val itemCount : Int ?= null,
    val adminUid : String ?= null,
    var productImageUris : ArrayList<String?> = arrayListOf()

)
