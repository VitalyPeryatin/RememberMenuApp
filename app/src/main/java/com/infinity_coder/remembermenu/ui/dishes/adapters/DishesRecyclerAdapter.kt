package com.infinity_coder.remembermenu.ui.dishes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.infinity_coder.remembermenu.R
import com.infinity_coder.remembermenu.domain.entities.dishes.DishEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_dish.*

class DishesRecyclerAdapter(private val onItemClickListener: (dish: DishEntity) -> Unit) : RecyclerView.Adapter<DishesRecyclerAdapter.DishesViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CONTENT = 0
        private const val VIEW_TYPE_SEPARATOR = 1
    }

    private var dishes: List<DishEntity?> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return if (dishes[position] == null) VIEW_TYPE_SEPARATOR else VIEW_TYPE_CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            VIEW_TYPE_SEPARATOR -> {
                val view = inflater.inflate(R.layout.item_separator, parent, false)
                return DishesViewHolder.SeparatorViewHolder(view)
            }
            VIEW_TYPE_CONTENT -> {
                val view = inflater.inflate(R.layout.item_dish, parent, false)
                return DishesViewHolder.ContentViewHolder(view, onItemClickListener)
            }
            else -> throw IllegalStateException()
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    override fun onBindViewHolder(holder: DishesViewHolder, position: Int) {
        if (holder is DishesViewHolder.ContentViewHolder) {
            val dishEntity = dishes[position]!!
            holder.bind(dishEntity)
        }
    }

    fun updateDishes(dishes: List<DishEntity?>) {
        this.dishes = dishes
        notifyDataSetChanged()
    }

    sealed class DishesViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        class ContentViewHolder(
            view: View,
            private val onItemClickListener: (dish: DishEntity) -> Unit
        ): DishesViewHolder(view) {
            fun bind(dish: DishEntity) {
                titleTextView.text = dish.title
                descriptionTextView.text = dish.description

                Glide.with(containerView)
                    .load(dish.image)
                    .centerCrop()
                    .into(dishImageView)

                itemView.setOnClickListener {
                    onItemClickListener.invoke(dish)
                }
            }
        }

        class SeparatorViewHolder(view: View): DishesViewHolder(view)

    }

}