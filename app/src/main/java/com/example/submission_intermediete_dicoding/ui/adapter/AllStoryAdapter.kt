package com.example.submission_intermediete_dicoding.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.ui.view.activity.DetailActivity

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
            tvUsername.text = story.name
            tvDesc.text = story.description
            Glide.with(context).load(story.photoUrl).into(ivContent)

            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(TAG_USERNAME, story.name)
                    intent.putExtra(TAG_PHOTO, story.photoUrl)
                    intent.putExtra(TAG_DESC, story.description)

                itemView.context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity).toBundle())
            }
        }
    }

    companion object {
        private const val TAG_USERNAME = "username"
        private const val TAG_PHOTO = "photoUrl"
        private const val TAG_DESC = "description"
    }


}