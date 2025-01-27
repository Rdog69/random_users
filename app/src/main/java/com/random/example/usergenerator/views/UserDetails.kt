package com.random.example.usergenerator.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.random.example.usergenerator.databinding.FragmentUserDetailsBinding
import com.random.example.usergenerator.network.response.PersonInfo


class UserDetails : Fragment() {
    val args : UserDetailsArgs by navArgs()
    lateinit var person: PersonInfo
    private lateinit var _binding : FragmentUserDetailsBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = FragmentUserDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        person = args.personInfo
        bindPersonInfoToUI(person)
        setupCopyListeners()
    }

    private fun bindPersonInfoToUI(person: PersonInfo) {
        loadImage(binding.userImage,person.picture.large)
        binding.userDetail.actualFullName.text = "${person.name.first} ${person.name.last}"
        binding.userDetail.actualDob.text = person.dob.date.slice(IntRange(0,9))
        binding.userDetail.actualAge.text = person.dob.age.toString()
        binding.userDetail.actualUsername.text = person.login.username
        binding.userDetail.actualPassword.text = person.login.password
        binding.detailContact.actualEmail.text = person.email
        binding.detailContact.actualHomeNumber.text = person.phone
        binding.detailContact.actualMobileNumber.text = person.cell
        binding.detailLocation.actualAddress.text = "${person.location.street.name} ${person.location.street.number}, ${person.location.city}, ${person.location.state}, ${person.location.country}"

    }
    private fun copyToClipboard(text: String, label: String) {
        val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context,"$label is copied!",Toast.LENGTH_SHORT).show()

    }
    private fun setupCopyListeners() {
        // Username copy listener
        binding.userDetail.copyUserName.setOnClickListener {
            copyToClipboard(binding.userDetail.actualUsername.text.toString(), "Username")
        }
        binding.userDetail.copyFullName.setOnClickListener {
            copyToClipboard(binding.userDetail.actualFullName.text.toString(), "FullName")
        }

        binding.detailContact.copyEmail.setOnClickListener {
            copyToClipboard(binding.detailContact.actualEmail.text.toString(), "Email")
        }

        binding.detailContact.copyHomeNumber.setOnClickListener {
            copyToClipboard(binding.detailContact.actualHomeNumber.text.toString(), "Home Number")
        }

        binding.detailContact.copyMobileNumber.setOnClickListener {
            copyToClipboard(binding.detailContact.actualMobileNumber.text.toString(), "Mobile Number")
        }

        binding.detailLocation.copyAddress.setOnClickListener {
            copyToClipboard(binding.detailLocation.actualAddress.text.toString(), "Address")
        }

        binding.userDetail.copyPassword.setOnClickListener {
            copyToClipboard(binding.userDetail.actualPassword.text.toString(), "Password")
        }
    }
    private fun loadImage(imageView: ImageView, url: String) {
        // Use any image loading library like Glide or Picasso
        Glide.with(this)
            .load(url)
            .into(imageView)
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}