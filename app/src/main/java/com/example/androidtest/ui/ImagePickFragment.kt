package com.example.androidtest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidtest.R

class ImagePickFragment: Fragment(R.layout.fragment_image_pick) {

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // To bind the view model to ensure that it is retained by the fragment when it is destroyed
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
    }


}