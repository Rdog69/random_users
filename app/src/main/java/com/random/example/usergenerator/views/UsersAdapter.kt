package com.random.example.usergenerator.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.random.example.usergenerator.R
import com.random.example.usergenerator.databinding.ListItemBinding
import com.random.example.usergenerator.network.local.PersonRepository
import com.random.example.usergenerator.network.response.PersonInfo
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersAdapter(diffCallback: DiffUtil.ItemCallback<PersonInfo>, private val personRepository: PersonRepository,
                   private val onItemClick: (PersonInfo) -> Unit) : PagingDataAdapter<PersonInfo, UsersAdapter.UserViewHolder>(diffCallback)  {

    inner class UserViewHolder(private val binding:  ListItemBinding) : ViewHolder(binding.root){
        fun bind(item: PersonInfo?) {

            if (item != null) {
                fullName.text = item.name.first +" "+ item.name.last
            }
            email.text = item?.email
            phone.text = item?.phone
            if (item?.gender == "female"){
                gender.setImageResource(R.drawable.female_ic)
            }else gender.setImageResource(R.drawable.male_ic)
            Picasso.get().load(item?.picture?.medium).into(avatar)

            if (item != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val savedPerson = personRepository.getPersonByEmail(item.email)
                    withContext(Dispatchers.Main) {
                        if (savedPerson != null) {
                            save.setImageResource(R.drawable.save_filled)  // Filled heart if saved
                        } else {
                            save.setImageResource(R.drawable.save)  // Outlined heart if not saved
                        }
                    }
                }
            }

        }

        var avatar= binding.avatar
        var fullItem = binding.fullItem
        var email= binding.email
        var phone  = binding.phone
        var gender = binding.gender
        var save = binding.save
        var fullName = binding.fullName
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.save.setOnClickListener {
            if (item != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val savedPerson = personRepository.getPersonByEmail(item.email)
                    if (savedPerson == null) {
                        personRepository.addPerson(item)
                        withContext(Dispatchers.Main) {
                            holder.save.setImageResource(R.drawable.save_filled)  // Change icon to filled heart
                        }
                    } else {
                        // Remove the user if already saved
                        personRepository.deletePerson(savedPerson)
                        withContext(Dispatchers.Main) {
                            holder.save.setImageResource(R.drawable.save)  // Change icon to outlined heart
                        }
                    }
                }
            }
        }
        holder.fullItem.setOnClickListener {
            onItemClick(item!!)
        }

    }


}