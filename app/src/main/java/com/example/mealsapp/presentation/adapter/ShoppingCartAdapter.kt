package com.example.mealsapp.presentation.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealsapp.R
import com.example.mealsapp.databinding.ItemShoppingCartBinding
import com.example.mealsapp.databinding.MealItemLayoutBinding
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.model.ShoppingCartItem

class ShoppingCartAdapter : ListAdapter<ShoppingCartItem, ShoppingCartAdapter.ShoppingCartViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShoppingCartBinding.inflate(inflater, parent, false)
        return ShoppingCartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ShoppingCartViewHolder(private val binding: ItemShoppingCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShoppingCartItem) {
            binding.txtTitle.text = item.name
            binding.txtDes.text = item.description
            Glide.with(binding.imageView.context).load(item.imageUri).into(binding.imageView)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ShoppingCartItem>() {
        override fun areItemsTheSame(oldItem: ShoppingCartItem, newItem: ShoppingCartItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingCartItem, newItem: ShoppingCartItem): Boolean {
            return oldItem == newItem
        }
    }
}
