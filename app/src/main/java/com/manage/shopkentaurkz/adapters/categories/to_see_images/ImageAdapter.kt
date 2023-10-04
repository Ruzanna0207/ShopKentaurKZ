package com.manage.shopkentaurkz.adapters.categories.to_see_images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manage.shopkentaurkz.databinding.ForAdapterDetailsImagesBinding

//установлен слушатель нажа-й в виде лямбда выраж-я
class ImageAdapter(private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<ImageAdapter.CategoriesViewHolder>() {

    var products = listOf<String?>()

    class CategoriesViewHolder(private val binding: ForAdapterDetailsImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(property: String) = with(binding) {

            Glide.with(itemView.context)
                .load(property)
                .fitCenter()
                .centerInside()
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding = ForAdapterDetailsImagesBinding.inflate(
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