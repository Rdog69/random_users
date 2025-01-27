package com.random.example.usergenerator

import androidx.recyclerview.widget.DiffUtil
import com.random.example.usergenerator.network.response.PersonInfo

object UserComparator : DiffUtil.ItemCallback<PersonInfo>() {
    override fun areItemsTheSame(oldItem: PersonInfo, newItem: PersonInfo): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonInfo, newItem: PersonInfo): Boolean {
        return oldItem == newItem
    }
}
