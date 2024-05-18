package com.example.submission_intermediete_dicoding.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.data.response.StoryResponse
import com.example.submission_intermediete_dicoding.database.AllStory.AllStory

class AllStoryAdapter(private var stories: List<ListStoryItem>, private val context: Context) : RecyclerView.Adapter<AllStoryAdapter.AllStoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllStoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_story, parent, false)
        return AllStoryViewHolder(view)
    }

    override fun getItemCount(): Int = stories.size

    override fun onBindViewHolder(holder: AllStoryViewHolder, position: Int) {
        val storyB = stories[position]
        holder.bind(storyB)
    }

    inner class AllStoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUsername : TextView = itemView.findViewById(R.id.tv_name_allStory)
        private val tvDesc : TextView = itemView.findViewById(R.id.tv_desc_allStory)
        private val ivProfile : ImageView = itemView.findViewById(R.id.iv_profile_allStory)
        private val ivContent : ImageView = itemView.findViewById(R.id.iv_content_allStory)

        fun bind(story: ListStoryItem) {
            Glide.with(context).load(R.drawable.profile_1_black).circleCrop().fitCenter().into(ivProfile)
            tvUsername.text =story.name
            tvDesc.text = story.description
            Glide.with(context).load(story.photoUrl).into(ivContent)
        }
    }

}