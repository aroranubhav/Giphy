package com.almax.giphy.ui.gif

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.almax.giphy.R
import com.almax.giphy.data.model.GifData
import com.almax.giphy.databinding.ItemGifBinding
import com.bumptech.glide.Glide

class GifAdapter(
    private val gifsList: ArrayList<GifData>
) : RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    inner class GifViewHolder(private val binding: ItemGifBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(gif: GifData) {
            binding.apply {
                gif.apply {
                    Glide.with(root.context)
                        .asGif()
                        .load(images.downsized.url)
                        .placeholder(R.drawable.ic_loading)
                        .into(ivGif)
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

    fun setData(gifsList: List<GifData>) {
        this.gifsList.clear()
        this.gifsList.addAll(gifsList)
        notifyDataSetChanged()
    }
}