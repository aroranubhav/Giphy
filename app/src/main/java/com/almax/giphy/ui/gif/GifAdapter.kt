package com.almax.giphy.ui.gif

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.almax.giphy.R
import com.almax.giphy.data.local.GifEntity
import com.almax.giphy.databinding.ItemGifBinding
import com.almax.giphy.ui.favourites.FavouritesFragment
import com.almax.giphy.util.GifItemRemovedListener
import com.almax.giphy.util.GifItemSavedListener
import com.almax.giphy.util.GifShareListener
import com.almax.giphy.util.tint
import com.bumptech.glide.Glide

class GifAdapter(
    private val fragment: Fragment,
    private val gifsList: ArrayList<GifEntity>
) : RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    lateinit var itemSavedListener: GifItemSavedListener<GifEntity>

    lateinit var itemShareListener: GifShareListener<String>

    lateinit var itemRemovedListener: GifItemRemovedListener<GifEntity>

    inner class GifViewHolder(private val binding: ItemGifBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(gifEntity: GifEntity) {
            binding.apply {
                gifEntity.apply {
                    Glide.with(root.context)
                        .asGif()
                        .load(gif.images.downsized.url)
                        .placeholder(R.drawable.ic_loading)
                        .into(ivGif)
                    updateDrawableTintColor(isSaved, binding.root.context, ivFav)
                }
                if (fragment is GifFragment) {
                    ivFav.setOnClickListener {
                        gifEntity.isSaved = !gifEntity.isSaved
                        gifEntity.savedTimeStamp = System.currentTimeMillis().toString()
                        updateDrawableTintColor(gifEntity.isSaved, binding.root.context, ivFav)
                        itemSavedListener(gifEntity, gifEntity.isSaved)
                    }
                } else if (fragment is FavouritesFragment) {
                    ivFav.setOnClickListener {
                        gifsList.remove(gifEntity)
                        itemRemovedListener(gifEntity)
                        notifyDataSetChanged()
                    }
                }
                ivShare.setOnClickListener {
                    itemShareListener(gifEntity.gif.url)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        return GifViewHolder(
            ItemGifBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int =
        gifsList.size

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.onBind(gifsList[position])
    }

    private fun updateDrawableTintColor(isSaved: Boolean, context: Context, view: ImageView) {
        if (isSaved) {
            view.tint(context, android.R.color.holo_red_light)
        } else {
            view.tint(context, android.R.color.black)
        }
    }

    fun setData(gifsList: List<GifEntity>) {
        this.gifsList.clear()
        this.gifsList.addAll(gifsList)
        notifyDataSetChanged()
    }
}