package com.manage.shopkentaurkz.adapters.main_page


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manage.shopkentaurkz.core.data_classes.ProductCategories
import com.manage.shopkentaurkz.databinding.ForAdapterMainPageBinding

class MainPageAdapter(
    private val categoryProduct: List<ProductCategories?>,
    private val listener: Clickable
) :
    RecyclerView.Adapter<MainPageAdapter.MainPageViewHolder>() {

    class MainPageViewHolder(private val binding: ForAdapterMainPageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(property: ProductCategories, listener: Clickable) = with(binding) {

            text.text = property.name

            Glide.with(itemView.context)
                .load(property.imageUrl)
                .fitCenter()
                .into(image)

            itemView.setOnClickListener {
                listener.onClick(property.description)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageViewHolder {
        val binding = ForAdapterMainPageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainPageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryProduct.size
    }

    override fun onBindViewHolder(holder: MainPageViewHolder, position: Int) {
        categoryProduct[position]?.let { holder.bind(it, listener) }
    }
}

// интерфейс для обработки слушателя нажатий
interface Clickable {
    fun onClick(tag: String)
}