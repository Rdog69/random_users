package com.random.example.usergenerator.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.random.example.usergenerator.R
import com.random.example.usergenerator.databinding.ListItemBinding
import com.random.example.usergenerator.network.local.PersonEntity
import com.squareup.picasso.Picasso

    class SavedUsersAdapter(
        private var savedUsers: List<PersonEntity> = listOf(),
        private val onItemClick: (PersonEntity) -> Unit,
        private val onDeleteClicked: (PersonEntity) -> Unit
    ) : RecyclerView.Adapter<SavedUsersAdapter.SavedUserViewHolder>() {

        inner class SavedUserViewHolder( val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(user: PersonEntity) {
                // Convert PersonEntity to PersonInfo for display
                val personInfo = user// Assuming you have a mapping method
                binding.fullName.text = "${personInfo.firstName} ${personInfo.lastName}"
                binding.email.text = personInfo.email
                binding.phone.text = personInfo.phone
                binding.gender.setImageResource(if (personInfo.gender == "female") R.drawable.female_ic else R.drawable.male_ic)
                Picasso.get().load(personInfo.pictureUrl).into(binding.avatar)
                binding.save.setImageResource(R.drawable.save_filled)
                // Set click listeners
                binding.root.setOnClickListener { onItemClick(personInfo) }



            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedUserViewHolder {
            val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SavedUserViewHolder(binding)
        }

        override fun onBindViewHolder(holder: SavedUserViewHolder, position: Int) {
            val user = savedUsers[position]
            holder.bind(user)
            holder.binding.save.setOnClickListener {
                onDeleteClicked(user)
                notifyItemRemoved(position)
            }
        }

        override fun getItemCount(): Int = savedUsers.size

        // Method to update the list of saved users
        fun updateUsers(newUsers: List<PersonEntity>) {
            savedUsers = newUsers
            notifyDataSetChanged() // Notify the adapter to refresh the data
        }
    }
