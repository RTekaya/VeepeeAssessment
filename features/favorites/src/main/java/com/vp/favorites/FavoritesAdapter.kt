package com.vp.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vp.detail.model.MovieDetail


class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ListViewHolder>() {
    private var listItems: List<MovieDetail> = emptyList()
    private val EMPTY_ON_ITEM_CLICK_LISTENER: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(imdbID: String?) {
        }
    }
    private var onItemClickListener = EMPTY_ON_ITEM_CLICK_LISTENER
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val poster = listItems[position].poster
        if (NO_IMAGE != poster) {
            val density = holder.image.resources.displayMetrics.density
            Glide.with(holder.image)
                    .load(poster)
                    .apply(RequestOptions().override((300 * density).toInt(), (600 * density).toInt()))
                    .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.placeholder)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun setItems(listItems: List<MovieDetail>) {
        this.listItems = listItems
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var image: ImageView
        override fun onClick(v: View) {
            onItemClickListener.onItemClick(listItems[adapterPosition].imdbID)
        }

        init {
            itemView.setOnClickListener(this)
            image = itemView.findViewById(R.id.poster)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(imdbID: String?)
    }

    companion object {
        private const val NO_IMAGE = "N/A"
    }
}