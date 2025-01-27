package com.random.example.usergenerator.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.random.example.usergenerator.SharedViewModel
import com.random.example.usergenerator.databinding.FragmentSavedBinding
import com.random.example.usergenerator.network.local.PersonEntity
import com.random.example.usergenerator.utils.SharedViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class SavedFragment : Fragment() {
    private lateinit var _binding : FragmentSavedBinding
    private val binding get() = _binding
    private val viewModel: SharedViewModel by lazy {
        val factory = SharedViewModelFactory(requireActivity().application)
        ViewModelProvider(this, factory).get(SharedViewModel::class.java)
    }

    private lateinit var listEntities: List<PersonEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = FragmentSavedBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SavedUsersAdapter(
            onItemClick = { user ->
                // Handle item click, e.g., show user details
            },
            onDeleteClicked = { user ->
                viewModel.deleteSavedPerson(user)
            }
        )

        binding.savedUsersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.savedUsersRecyclerView.adapter = adapter

        viewModel.personRepository.getAll().observe(viewLifecycleOwner) { listEntities ->
            if (listEntities.isEmpty()) {
                // Handle empty list case (show a message or hide the RecyclerView)
                binding.empty.visibility = View.VISIBLE
                binding.savedUsersRecyclerView.visibility = View.GONE
            } else {
                binding.empty.visibility = View.GONE
                binding.savedUsersRecyclerView.visibility = View.VISIBLE
            }
            adapter.updateUsers(listEntities)
        }
    }
}