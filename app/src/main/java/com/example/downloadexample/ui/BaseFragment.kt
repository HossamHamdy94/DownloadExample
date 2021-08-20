package com.example.downloadexample.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment()
{



    var callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */)
    {
        override fun handleOnBackPressed()
        {

        }
    }


    var callbackFinish: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */)
    {
        override fun handleOnBackPressed()
        {
            requireActivity().finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)


    }






    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callbackFinish)

    }





}