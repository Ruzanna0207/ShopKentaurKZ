package com.manage.shopkentaurkz.presentation.main_page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.manage.shopkentaurkz.R
import com.manage.shopkentaurkz.adapters.main_page.Clickable
import com.manage.shopkentaurkz.adapters.main_page.MainPageAdapter
import com.manage.shopkentaurkz.databinding.FragmentMainPageBinding
import com.manage.shopkentaurkz.presentation.categories.for_feeding.CategoriesFeedingFragment
import com.manage.shopkentaurkz.presentation.categories.for_horse.CategoriesHorseFragment
import com.manage.shopkentaurkz.presentation.categories.for_rider.CategoriesRiderFragment
import com.manage.shopkentaurkz.viewModel.main_page.MainPageViewModel

class MainPageFragment : androidx.fragment.app.Fragment(), Clickable {

    private lateinit var binding: FragmentMainPageBinding
    private lateinit var adapter: MainPageAdapter
    private val viewModel: MainPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        binding.animationViewMain.playAnimation()
        viewModel.getCategories()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        getAllCategories()
    }

    //опред-ие основного ui, загрузка значений
    private fun setupViews() = with(binding) {

        Glide.with(requireActivity())
            .load("https://www.dropbox.com/scl/fi/xz7eytszbt94yuo5ar4uj/1695312960921.png?rlkey=0tgpv6s8fc7nlitgpgftquyfs&raw=1")
            .optionalCenterCrop()
            .into(imageWelcome)

        Glide.with(requireActivity())
            .load("https://www.dropbox.com/scl/fi/6erti07skz813s886lm25/kentaurLogo.jpg?rlkey=7jxah2x5h9rzpww3c8jwr1j9d&raw=1")
            .fitCenter()
            .into(logo)

        //открытие инстаграм
        inst.setOnClickListener {
            openInstagramProfile()
        }
    }

    private fun getAllCategories() {

        viewModel.currentCategories.observe(viewLifecycleOwner) { allCats ->
            adapter = MainPageAdapter(allCats, this)
            binding.mainRecView.adapter = adapter
            adapter.notifyDataSetChanged()

            stopAnimation()
        }
    }

    private fun stopAnimation() = with(binding) {

        animationViewMain.cancelAnimation()
        animationViewMain.visibility = View.GONE
    }

    override fun onClick(tag: String) {

        when {
            (tag == "Все для лошади") -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, CategoriesHorseFragment())
                    .addToBackStack(null)
                    .commit()
            }

            (tag == "Все для всадника") -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, CategoriesRiderFragment())
                    .addToBackStack(null)
                    .commit()
            }

            (tag == "Кормовые добавки") -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, CategoriesFeedingFragment())
                    .addToBackStack(null)
                    .commit()
            }

            else -> {}
        }
    }

    // Открывается Instagram
    private fun openInstagramProfile() {

        val instagramUri = Uri.parse("https://www.instagram.com/kentaur.kz/")

        // интент для открытия Instagram
        val intent = Intent(Intent.ACTION_VIEW, instagramUri)

        intent.setPackage("com.instagram.android")

        // проверка наличия приложения Instagram на устройстве
        when (intent.resolveActivity(requireActivity().packageManager) != null) {
            true -> startActivity(intent)
            else -> startActivity(Intent(Intent.ACTION_VIEW, instagramUri)) // Если Instagram нет, открыть профиль в браузере
        }
    }
}