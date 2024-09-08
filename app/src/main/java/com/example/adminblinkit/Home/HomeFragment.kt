package com.example.adminblinkit.Home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.adminblinkit.Adapters.categoryAdapter
import com.example.adminblinkit.Adapters.productAdapter
import com.example.adminblinkit.Constants
import com.example.adminblinkit.Models.Categories
import com.example.adminblinkit.Models.Products
import com.example.adminblinkit.R
import com.example.adminblinkit.Utils

import com.example.adminblinkit.ViewModels.AdminViewModel
import com.example.adminblinkit.databinding.EditDeleteProductBinding
import com.example.adminblinkit.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    val adminViewModel : AdminViewModel by viewModels()
    private lateinit var binding : FragmentHomeBinding
    private lateinit var adapter : productAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        showCategory()
        getAllProducts("all")
        searchProduct()
        return binding.root
    }

    private fun searchProduct() {
        binding.etSearch.addTextChangedListener(object  : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                val query = text.toString().trim()
                Log.e("a", query )
                adapter.getFilter().filter(query)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun onEditBtnClicked(product : Products) {
        val editLayout = EditDeleteProductBinding.inflate(LayoutInflater.from(requireContext()))
        editLayout.apply {
            ETProductTitle.setText(product.productTitle)
            ETProductUnit.setText(product.productUnit)
            ETProductPrice.setText(product.productPrice.toString())
            ETProductCategory.setText(product.productCategory)
            ETProductQuantity.setText(product.productQuantity.toString())
            ETProductType.setText(product.productType)
            ETProductStock.setText(product.productStock.toString())
        }
        val alertDialog = AlertDialog.Builder(requireContext()).setView(editLayout.root).create()
        alertDialog.show()
        setAutoCompleteTextView(editLayout)

        editLayout.saveBtn.setOnClickListener {
            lifecycleScope.launch {
                product.productTitle = editLayout.ETProductTitle.text.toString().trim()
                product.productUnit = editLayout.ETProductUnit.text.toString().trim()
                product.productPrice = editLayout.ETProductPrice.text.toString().trim().toDouble()
                product.productCategory = editLayout.ETProductCategory.text.toString().trim()
                product.productQuantity = editLayout.ETProductQuantity.text.toString().trim().toInt()
                product.productType = editLayout.ETProductType.text.toString()
                product.productStock = editLayout.ETProductStock.text.toString().trim().toInt()
                adminViewModel.saveUpdatedProduct(product)
                alertDialog.dismiss()
            }
        }
        editLayout.deleteBtn.setOnClickListener {
            lifecycleScope.launch {
                adminViewModel.deleteProduct(product)
                alertDialog.dismiss()
            }
        }
        Utils.ToastMes(requireContext(),"product updated")
    }


    private fun getAllProducts(category: String) {
        binding.productShimer.visibility = View.VISIBLE
        lifecycleScope.launch {
            adminViewModel.getAllProducts(category).collect{listOfProduct->
                if(listOfProduct.isEmpty()){
                    binding.tvNoProduct.visibility = View.VISIBLE
                    binding.rvProducts.visibility = View.GONE
                }else{
                    binding.tvNoProduct.visibility = View.GONE
                    binding.rvProducts.visibility = View.VISIBLE
                }
                binding.productShimer.visibility = View.GONE
                adapter  = productAdapter(::onEditBtnClicked)
                binding.rvProducts.adapter = adapter
                adapter.differ.submitList(listOfProduct)
                adapter.originalList = listOfProduct as ArrayList<Products>
            }
        }
    }

    fun onSelectedCategory(category : String){
        getAllProducts(category)
    }


    private fun showCategory() {
        val categories = ArrayList<Categories>()
        for(i in 0 until Constants.allProductsCategory.size){
            categories.add(Categories(Constants.productIcon[i],Constants.allProductsCategory[i]))
        }

        binding.rvCategories.adapter = categoryAdapter(categories,::onSelectedCategory)
    }

    private fun setAutoCompleteTextView(editLayout : EditDeleteProductBinding) {
        val units = ArrayAdapter(requireContext(), R.layout.constant_list_itemview,Constants.allProductsUnits)
        val category = ArrayAdapter(requireContext(), R.layout.constant_list_itemview,Constants.allProductsCategory)
        val type = ArrayAdapter(requireContext(), R.layout.constant_list_itemview,Constants.allProductsType)

        editLayout.apply {
            ETProductUnit.setAdapter(units)
            ETProductCategory.setAdapter(category)
            ETProductType.setAdapter(type)
        }

    }


}