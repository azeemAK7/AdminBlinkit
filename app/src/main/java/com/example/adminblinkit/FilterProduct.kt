package com.example.adminblinkit

import android.util.Log
import android.widget.Filter
import com.example.adminblinkit.Adapters.productAdapter
import com.example.adminblinkit.Models.Products
import java.util.Locale

class FilterProduct(
    val productAdapter: productAdapter,
    val productList: ArrayList<Products>
) : Filter() {
    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val result = FilterResults()
        val filterProdList = ArrayList<Products>()

        val query = constraint.toString().trim().uppercase(Locale.getDefault()).split(" ")

        if(query != null){
            Log.e("q", query.toString() )
            for(product in productList){
                if(query.any {
                        product.productTitle!!.uppercase(Locale.getDefault()).contains(it) == true || product.productCategory!!.uppercase(Locale.getDefault()).contains(it) == true || product.productPrice!!.toString().uppercase(Locale.getDefault()).contains(it) == true || product.productType!!.uppercase(Locale.getDefault()).contains(it) == true
                    }){
                    filterProdList.add(product)
                }
            }
            result.values = filterProdList
            result.count = filterProdList.size
        }else{
            result.values = productList
            result.count = productList.size
        }
        Log.e("filter", filterProdList.toString() )

        return  result
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        productAdapter.differ.submitList(results?.values as ArrayList<Products>)
    }


}