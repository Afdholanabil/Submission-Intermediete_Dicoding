package com.example.submission_intermediete_dicoding.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.database.MyStory.MyStory
import com.example.submission_intermediete_dicoding.ui.view.activity.DetailActivity

class MyStoryAdapter(private val stroies: List<MyStory>, private val context: Context) : RecyclerView.Adapter<MyStoryAdapter.MyStoryViewHolder>() {
    inner class MyStoryViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val tvDesc : TextView = itemView.findViewById(R.id.tv_desc)
        val ivcContent : ImageView = itemView.findViewById(R.id.iv_content)

        fun bind(story: MyStory) {
            Glide.with(context).load(story.photoUrl).into(ivcContent)
            tvDesc.text = story.desc

            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(TAG_USERNAME,"Owner")
                intent.putExtra(TAG_PHOTO,story.photoUrl)
                intent.putExtra(TAG_DESC,story.desc)

                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStoryViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_story, parent, false)
        return MyStoryViewHolder(view)
    }

    override fun getItemCount(): Int {
       return stroies.size
    }

    override fun onBindViewHolder(holder: MyStoryViewHolder, position: Int) {
        val story = stroies[position]
        holder.bind(story)
    }

    companion object {
        private const val TAG_USERNAME = "username"
        private const val TAG_PHOTO = "photoUrl"
        private const val TAG_DESC = "description"
    }
}