package com.example.adminblinkit.Home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.adminblinkit.Adapters.selectImageAdapter
import com.example.adminblinkit.Constants
import com.example.adminblinkit.Models.Products
import com.example.adminblinkit.R
import com.example.adminblinkit.Utils
import com.example.adminblinkit.ViewModels.AdminViewModel
import com.example.adminblinkit.ViewModels.AuthViewModel
import com.example.adminblinkit.activity.AdminActivity
import com.example.adminblinkit.databinding.FragmentAddProductBinding
import kotlinx.coroutines.launch


class addProductFragment : Fragment() {

    private val adminViewModel : AdminViewModel by viewModels()
    private lateinit var binding : FragmentAddProductBinding

    private val imageUri : ArrayList<Uri> = ArrayList()
    val getimages = registerForActivityResult(ActivityResultContracts.GetMultipleContents()){listOfUri ->
        val selectedImg = listOfUri.take(5)
        imageUri.clear()
        imageUri.addAll(selectedImg)
        binding.rvProductImages.adapter = selectImageAdapter(imageUri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(layoutInflater)

        setAutoCompleteTextView()
        onSelectImageBtnClicked()
        onAddProductsBtnClicked()

        return binding.root
    }

    private fun onAddProductsBtnClicked() {
        binding.btnAddProduct.setOnClickListener {
            Utils.showDialog(requireContext(),"Uploading Product")

            val productTitle = binding.ETProductTitle.text.toString().trim()
            val productQuantity = binding.ETProductQuantity.text.toString().trim()
            val productCategory = binding.ETProductCategory.text.toString().trim()
            val productUnit = binding.ETProductUnit.text.toString().trim()
            val productPrice = binding.ETProductPrice .text.toString().trim()
            val productStock = binding.ETProductStock.text.toString().trim()
            val productType = binding.ETProductType.text.toString().trim()
            if(productTitle.isEmpty() || productQuantity.isEmpty() || productCategory.isEmpty() || productUnit.isEmpty() || productPrice.isEmpty() || productStock.isEmpty() || productType.isEmpty() || imageUri.isEmpty()){
                Utils.closeDialog()
                Utils.ToastMes(requireContext(),"Enter all fields")
                }
            else{
                val product = Products(
                    productId = Utils.getRandomId(),
                    productTitle = productTitle,
                    productQuantity = productQuantity.toInt(),
                    productUnit = productUnit,
                    productPrice = productPrice.toDouble(),
                    productStock = productStock.toInt(),
                    productCategory = productCategory,
                    productType = productType,
                    itemCount = 0,
                    adminUid = Utils.getCurrentUserId()
                )
                saveProductInDb(product)
            }
        }
    }

    private fun saveProductInDb(product: Products) {
        adminViewModel.saveImg(imageUri)
        lifecycleScope.launch {
            adminViewModel.imgUploadSuccess.collect{
                if(it){
                    adminViewModel.downloadedUri.collect{ imgUri ->
                        val Uri = imgUri
                        product.productImageUris = Uri!!
                    }
                }
            }
        }
        adminViewModel.saveProduct(product)
        lifecycleScope.launch {
            adminViewModel.productUploadSuccess.collect{
                if(it){
                    Utils.closeDialog()
                    startActivity(Intent(requireActivity(),AdminActivity::class.java))
                    Utils.ToastMes(requireContext(),"product uploaded")
                }
            }
        }
    }

    private fun onSelectImageBtnClicked() {
        binding.btnSelectImg.setOnClickListener {
            getimages.launch("image/*")
        }
    }

    private fun setAutoCompleteTextView() {
        val units = ArrayAdapter(requireContext(),R.layout.constant_list_itemview,Constants.allProductsUnits)
        val category = ArrayAdapter(requireContext(),R.layout.constant_list_itemview,Constants.allProductsCategory)
        val type = ArrayAdapter(requireContext(),R.layout.constant_list_itemview,Constants.allProductsType)

        binding.apply {
            ETProductUnit.setAdapter(units)
            ETProductCategory.setAdapter(category)
            ETProductType.setAdapter(type)
        }

    }


}