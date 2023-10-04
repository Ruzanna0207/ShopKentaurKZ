package com.manage.shopkentaurkz.adapters.categories.for_horse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manage.shopkentaurkz.core.data_classes.ForHorseProduct
import com.manage.shopkentaurkz.databinding.ForAdapterCategoriesProductsBinding

class CategoriesAdapterForHorse(private val onItemClick: (ForHorseProduct) -> Unit) :
    RecyclerView.Adapter<CategoriesAdapterForHorse.CategoriesViewHolder>() {

    var products = listOf<ForHorseProduct?>()

    class CategoriesViewHolder(private val binding: ForAdapterCategoriesProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(property: ForHorseProduct?) = with(binding) {
            val firstPhoto = property?.image?.first()

            categoriesText.text = property?.name
            cathegoriesPrice.text = "${property?.price?.toInt()}тг"

            val random = listOf(900).random()

            Glide.with(itemView.context)
                .load(firstPhoto)
                .override(random, random)
                .centerCrop()
                .centerInside()
                .into(cathegoriesImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding = ForAdapterCategoriesProductsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(products[position])
        holder.itemView.setOnClickListener { products[position]?.let { it1 -> onItemClick(it1) } } //слушатель нажатий
    }
}