package com.random.example.usergenerator.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.random.example.usergenerator.R

class UsersLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<UsersLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: UsersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): UsersLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_load_state, parent, false)
        return UsersLoadStateViewHolder(view, retry)
    }
}

class UsersLoadStateViewHolder(itemView: View, retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar_bottom)
    private val retryButton: Button = itemView.findViewById(R.id.retry_button)

    init {
        retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        retryButton.isVisible = loadState is LoadState.Error
    }
}
