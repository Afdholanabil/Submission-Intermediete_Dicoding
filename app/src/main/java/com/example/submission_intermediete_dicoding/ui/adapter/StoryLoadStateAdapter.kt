package com.example.submission_intermediete_dicoding.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_intermediete_dicoding.databinding.ItemLoadStateBinding

class StoryLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<StoryLoadStateAdapter.LoadStateViewHolder>() {
    class LoadStateViewHolder(private val binding: ItemLoadStateBinding, private val retry:()->Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRetry.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvErrorMsg.text = loadState.error.localizedMessage
            }

            binding.progressBar.visibility = if(loadState is LoadState.Loading) View.VISIBLE else View.GONE
            binding.btnRetry.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            binding.tvErrorMsg.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding, retry)
    }
}