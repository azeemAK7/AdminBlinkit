package com.example.adminblinkit.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.adminblinkit.R
import com.example.adminblinkit.databinding.FragmentAddProductBinding


class addProductFragment : Fragment() {

    private lateinit var binding : FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(layoutInflater)



        return binding.root
    }

}
