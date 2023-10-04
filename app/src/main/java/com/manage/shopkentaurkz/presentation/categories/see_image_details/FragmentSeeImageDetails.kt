package com.manage.shopkentaurkz.presentation.categories.see_image_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.manage.shopkentaurkz.databinding.FragmentSeeImageDetailsBinding

class FragmentSeeImageDetails : Fragment() {

    private lateinit var binding: FragmentSeeImageDetailsBinding
    private var details: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeeImageDetailsBinding.inflate(layoutInflater)
        arguments?.let {
            details = it.getString("detail") // Получаем репоз-й из аргументов
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() = with(binding) {

        Glide.with(requireContext())
            .load(details)
            .optionalCenterCrop()
            .into(image)
    }
}