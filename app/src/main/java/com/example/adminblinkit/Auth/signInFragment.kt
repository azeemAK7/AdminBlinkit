package com.example.adminblinkit.Auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.adminblinkit.R
import com.example.adminblinkit.Utils
import com.example.adminblinkit.databinding.FragmentSignInBinding


class signInFragment : Fragment() {


    private lateinit var binding : FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(layoutInflater)
        getUserNumber()
        onContinueBtnClicked()
        return binding.root
    }

    private fun onContinueBtnClicked() {
        binding.btnContinue.setOnClickListener {
            val number = binding.ETUserMobileNumber.text.toString()
            if(number.isEmpty() || number.length != 10){
                Utils.ToastMes(requireContext(),"Enter Correct Number")
            }else{
                val bundle =  Bundle()
                bundle.putString("number",number)
                findNavController().navigate(R.id.action_signInFragment_to_OTPFragment,bundle)
            }
        }
    }

    private fun getUserNumber() {
        binding.ETUserMobileNumber.addTextChangedListener(object  : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(number: CharSequence?, start: Int, before: Int, count: Int) {
                val len = number?.length
                if(len == 10){
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.Green))
                }else{
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey))
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

}