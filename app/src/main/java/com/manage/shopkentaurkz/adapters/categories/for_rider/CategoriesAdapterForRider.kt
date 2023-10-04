package com.manage.shopkentaurkz.adapters.categories.for_rider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manage.shopkentaurkz.core.data_classes.ForRiderProduct
import com.manage.shopkentaurkz.databinding.ForAdapterCategoriesProductsBinding

//установлен слушатель нажа-й в виде лямбда выраж-я
class CategoriesAdapterForRider(private val onItemClick: (ForRiderProduct) -> Unit) :
    RecyclerView.Adapter<CategoriesAdapterForRider.CategoriesViewHolder>() {

    var products = listOf<ForRiderProduct?>()

    class CategoriesViewHolder(private val binding: ForAdapterCategoriesProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(property: ForRiderProduct) = with(binding) {
            val firstPhoto = property?.image?.first()

            categoriesText.text = property?.name
            cathegoriesPrice.text = "${property.price.toInt()}тг"


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
        products[position]?.let { holder.bind(it) }
        holder.itemView.setOnClickListener { products[position]?.let { it1 -> onItemClick(it1) } } //слушатель нажатий
    }
}