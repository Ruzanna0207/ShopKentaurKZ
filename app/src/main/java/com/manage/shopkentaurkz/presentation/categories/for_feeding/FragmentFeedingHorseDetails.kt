package com.manage.shopkentaurkz.presentation.categories.for_feeding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.manage.shopkentaurkz.core.data_classes.FeedingCategories
import com.manage.shopkentaurkz.databinding.FragmentProductsDetailsFeedingBinding

class FragmentFeedingHorseDetails : Fragment() {

    private lateinit var binding: FragmentProductsDetailsFeedingBinding
    private var details: FeedingCategories? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsDetailsFeedingBinding.inflate(layoutInflater)
        arguments?.let {
            details = it.getParcelable("detail") // Получаем репоз-й из аргументов
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupClickListeners()
    }

    private fun setupViews() = with(binding) {
        Glide.with(requireContext())
            .load(details?.image)
            .optionalCenterCrop()
            .into(imageProductFeeding)

        nameFeeding.text = details?.name
        "${details?.price?.toInt()}тг".also { priceFeeding.text = it }

        descriptionFeeding.text = details?.description
        compoundFeeding.text = details?.compound
    }

    private fun setupClickListeners() = with(binding) {
        //при нажатии на задний фон или крестик - фрагмент закроется
        clickInterceptor.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        pic.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}