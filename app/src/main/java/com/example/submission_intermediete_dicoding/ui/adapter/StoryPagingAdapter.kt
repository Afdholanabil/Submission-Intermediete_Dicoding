package com.example.submission_intermediete_dicoding.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.databinding.ItemAllStoryBinding
import com.example.submission_intermediete_dicoding.ui.view.activity.DetailActivity

class StoryPagingAdapter(private val context: Context) : PagingDataAdapter<ListStoryItem, StoryPagingAdapter.StoryViewHolder>(
    DiffCallback) {
    inner class StoryViewHolder(private val binding: ItemAllStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.apply {
                tvNameAllStory.text = story.name
                tvDescAllStory.text = story.description
                Glide.with(binding.ivContentAllStory.context)
                    .load(story.photoUrl)
                    .into(binding.ivContentAllStory)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(TAG_USERNAME, story.name)
                intent.putExtra(TAG_PHOTO, story.photoUrl)
                intent.putExtra(TAG_DESC, story.description)

                itemView.context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity).toBundle())
            }
        }
    }



    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        story?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoryViewHolder {
        val binding = ItemAllStoryBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return StoryViewHolder(binding)
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }

        private const val TAG_USERNAME = "username"
        private const val TAG_PHOTO = "photoUrl"
        private const val TAG_DESC = "description"
    }
}