package com.manage.shopkentaurkz.presentation.categories.for_horse

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
import com.manage.shopkentaurkz.adapters.categories.for_horse.CategoriesAdapterForHorse
import com.manage.shopkentaurkz.core.data_classes.ForHorseProduct
import com.manage.shopkentaurkz.databinding.FragmentCategoriesBinding
import com.manage.shopkentaurkz.viewModel.for_horse.ForHorseViewModel

class CategoriesHorseFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var buttonsAdapter: ButtonsAdapter
    private var tags = mutableSetOf<String>()
    private var listCategoryHorse: List<ForHorseProduct?> = emptyList()
    private val viewModel: ForHorseViewModel by viewModels()

    private val categoriesAdapterForHorse: CategoriesAdapterForHorse by lazy {
        CategoriesAdapterForHorse { data ->
            val secondFragment = FragmentProductHorseDetails()
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
        binding.animationViewMain.playAnimation()
        viewModel.onCreate()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupOnBackClickListeners()
    }

    private fun setupUi() {
        fetchButtons()
        fetchCategoriesHorse()
    }

    //фун-я определяет адаптер для загрузки списка категории
    private fun fetchCategoriesHorse() = with(binding) {
        cathegoriesRecView.adapter = categoriesAdapterForHorse

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        cathegoriesRecView.layoutManager = layoutManager

        viewModel.forHorse.observe(viewLifecycleOwner) { forHorse ->
            categoriesAdapterForHorse.products = forHorse
            listCategoryHorse = forHorse
            categoriesAdapterForHorse.notifyDataSetChanged()
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

        viewModel.tagsHorse.observe(viewLifecycleOwner) { forHorse ->
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

    //фун-я определяет поиск по тегу при помощи кнопок
    private fun searchForTag(tag: String) {
        val newList = listCategoryHorse.filter { it?.categories!!.contains(tag) }
        categoriesAdapterForHorse.products = newList
        categoriesAdapterForHorse.notifyDataSetChanged()
    }

    private fun stopAnimation() = with(binding) {
        animationViewMain.cancelAnimation()
        animationViewMain.visibility = View.GONE
    }
}