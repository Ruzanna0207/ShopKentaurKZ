package com.manage.shopkentaurkz.presentation.categories.for_feeding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.manage.shopkentaurkz.R
import com.manage.shopkentaurkz.adapters.categories.for_buttons.ButtonsAdapter
import com.manage.shopkentaurkz.adapters.categories.for_feeding.CategoriesAdapterForFeedingHorse
import com.manage.shopkentaurkz.core.data_classes.FeedingCategories
import com.manage.shopkentaurkz.databinding.FragmentCategoriesBinding
import com.manage.shopkentaurkz.viewModel.for_feeding.ForFeedingViewModel


class CategoriesFeedingFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var buttonsAdapter: ButtonsAdapter
    private var tags = mutableSetOf<String>()

    private var listCategoryFeeding = emptyList<FeedingCategories?>()
    private val viewModel: ForFeedingViewModel by viewModels()

    private val categoriesAdapterForFeedingHorse: CategoriesAdapterForFeedingHorse by lazy {
        CategoriesAdapterForFeedingHorse { data ->
            val secondFragment = FragmentFeedingHorseDetails()
            secondFragment.arguments = bundleOf("detail" to data)

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.frame, secondFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        viewModel.getFeeding()
        viewModel.getTagsHorse()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showUi()
        fetchButtons()
        fetchCategoriesRider()
        setupOnBackClickListeners()
    }

    private fun showUi() {
        binding.info.text = "Подкормки для лошади"
    }

    //фун-я определяет адаптер для категорий
    private fun fetchCategoriesRider() = with(binding) {

        cathegoriesRecView.adapter = categoriesAdapterForFeedingHorse

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        cathegoriesRecView.layoutManager = layoutManager

        viewModel.forFeeding.observe(viewLifecycleOwner) { forFeeding ->
            categoriesAdapterForFeedingHorse.products = forFeeding
            listCategoryFeeding = forFeeding
            categoriesAdapterForFeedingHorse.notifyDataSetChanged()

            stopAnimation()
        }
    }

    //фун-я определяет адаптер для кнопок-категорий
    private fun fetchButtons() = with(binding) {

        buttonsAdapter = ButtonsAdapter { tag ->
            searchForTag(tag)
        }
        adapterButtons.adapter = buttonsAdapter
        val layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        adapterButtons.layoutManager = layoutManager

        viewModel.tagsFeeding.observe(viewLifecycleOwner) { forHorse ->
            tags = forHorse.toMutableSet()
            buttonsAdapter.item = tags.toList()
            buttonsAdapter.notifyDataSetChanged()
        }
    }

    //обработка нажатий для кнопки назад из категорий
    private fun setupOnBackClickListeners() {
        binding.pic.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    ////фун-я определяет поиск по тегу при помощи кнопок
    private fun searchForTag(tag: String) {

        val newList = listCategoryFeeding
        categoriesAdapterForFeedingHorse.products = newList
        categoriesAdapterForFeedingHorse.notifyDataSetChanged()
    }

    private fun stopAnimation() = with(binding) {
        animationViewMain.cancelAnimation()
        animationViewMain.visibility = View.GONE
    }
}

