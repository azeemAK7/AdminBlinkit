package com.example.adminblinkit.Models

data class Products(
    val productId : String ?= null,
    var productTitle : String ?= null,
    var productQuantity : Int ?= null,
    var productUnit : String ?= null,
    var productPrice : Double ?= null,
    var productStock : Int ?= null,
    var productCategory : String ?= null,
    var productType : String ?= null,
    val itemCount : Int ?= null,
    val adminUid : String ?= null,
    var productImageUris : ArrayList<String?> = arrayListOf()

)
