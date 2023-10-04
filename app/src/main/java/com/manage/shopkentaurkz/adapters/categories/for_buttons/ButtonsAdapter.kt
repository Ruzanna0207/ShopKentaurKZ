package com.manage.shopkentaurkz.adapters.categories.for_buttons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.manage.shopkentaurkz.R
import com.manage.shopkentaurkz.databinding.ForAdapterCategoriesButtonsBinding

class ButtonsAdapter(private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<ButtonsAdapter.ViewHolder>() {

    var item = listOf<String>()

    private var selectedItemPosition = 0 // нач-е знач-е для позиции первого элемента

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ForAdapterCategoriesButtonsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = item[position]
        holder.bind(currentItem)

        // Условия для создания кнопок если они нажаты и не нажаты
        val context = holder.itemView.context
        val textColor = when (selectedItemPosition) {
            position -> ContextCompat.getColor(context, R.color.orange)
            else -> ContextCompat.getColor(context, R.color.white)
        }
        holder.binding.textButton.setTextColor(textColor)

        when (selectedItemPosition == position) {
            true -> holder.itemView.setBackgroundResource(R.drawable.ic_rectangle)
            else -> holder.itemView.setBackgroundResource(R.color.dark_blue)
        }

        // Слушатель нажатий для кнопок
        holder.binding.textButton.setOnClickListener {
            onItemClick(currentItem)
            setSelectedItem(position)
        }
    }

    override fun getItemCount(): Int = item.size

    inner class ViewHolder(val binding: ForAdapterCategoriesButtonsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: String) {
            binding.textButton.text = product
        }
    }

    // Условия для выбранной позиции
    private fun setSelectedItem(position: Int) {
        val previousSelectedItem = selectedItemPosition
        selectedItemPosition = position

        if (previousSelectedItem != RecyclerView.NO_POSITION) {
            notifyItemChanged(previousSelectedItem)
        }
        if (selectedItemPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(selectedItemPosition)
        }
    }
}