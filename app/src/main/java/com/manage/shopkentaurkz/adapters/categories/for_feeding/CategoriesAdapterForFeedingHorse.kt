package com.manage.shopkentaurkz.adapters.categories.for_feeding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manage.shopkentaurkz.core.data_classes.FeedingCategories
import com.manage.shopkentaurkz.databinding.ForAdapterCategoriesProductsBinding

//установлен слушатель нажа-й в виде лямбда выраж-я
class CategoriesAdapterForFeedingHorse(private val onItemClick: (FeedingCategories) -> Unit) :
    RecyclerView.Adapter<CategoriesAdapterForFeedingHorse.CategoriesViewHolder>() {

    var products = listOf<FeedingCategories?>()

    class CategoriesViewHolder(private val binding: ForAdapterCategoriesProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(property: FeedingCategories) {

            binding.categoriesText.text = property.name
            binding.cathegoriesPrice.text = "${property.price.toInt()}тг"

            Glide.with(itemView.context)
                .load(property.image)
                .centerCrop()
                .centerInside()
                .into(binding.cathegoriesImage)
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
        products[position]?.let { holder.bind(it) }
        holder.itemView.setOnClickListener { products[position]?.let { it1 -> onItemClick(it1) } } //слушатель нажатий
    }
}