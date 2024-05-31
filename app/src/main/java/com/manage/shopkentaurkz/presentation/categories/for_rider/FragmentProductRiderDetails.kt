package com.manage.shopkentaurkz.presentation.categories.for_rider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.manage.shopkentaurkz.R
import com.manage.shopkentaurkz.adapters.categories.to_see_images.ImageAdapter
import com.manage.shopkentaurkz.core.data_classes.ForRiderProduct
import com.manage.shopkentaurkz.databinding.FragmentProductsDetailsBinding
import com.manage.shopkentaurkz.presentation.categories.see_image_details.FragmentSeeImageDetails

class FragmentProductRiderDetails : Fragment() {

    private lateinit var binding: FragmentProductsDetailsBinding
    private var details: ForRiderProduct? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsDetailsBinding.inflate(layoutInflater)
        arguments?.let {
            details = it.getParcelable("detail") // Получ-е инфы из аргументов
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupClickListeners()
    }

    private fun setupViews() = with(binding) {
        addImages()

        nameProduct.text = details?.name
        descriptionProduct.text = details?.description
        priceProduct.text = "${details?.price?.toInt()}тг"
        compoundProduct.text = "Размеры: ${details?.sizes?.joinToString()}"
    }
    private fun setupClickListeners() = with(binding) {
        //при нажатии на задний фон - фрагмент закроется
        clickInterceptor.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        card.setOnClickListener {}

        imageProduct.setOnClickListener {
            val secondFragment = FragmentSeeImageDetails()
            secondFragment.arguments = bundleOf("detail" to details?.image)

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.frame, secondFragment)
                .addToBackStack(null)
                .commit()
        }

        pic.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    //просмотр списка изображений
    private fun addImages() {
        val adapterImage = ImageAdapter {
            val secondFragment = FragmentSeeImageDetails()
            secondFragment.arguments = bundleOf("detail" to it)

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.frame, secondFragment)
                .addToBackStack(null)
                .commit()
        }
        adapterImage.products = details?.image?.toList() ?: emptyList()
        binding.imageProduct.adapter = adapterImage
        adapterImage.notifyDataSetChanged()
    }
}