package com.random.example.usergenerator.views

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.random.example.usergenerator.R
import com.random.example.usergenerator.SharedViewModel
import com.random.example.usergenerator.UserComparator
import com.random.example.usergenerator.databinding.FragmentUsersBinding
import com.random.example.usergenerator.utils.SharedViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class UsersFragment : Fragment() {
    private lateinit var _binding : FragmentUsersBinding
    private val binding get() = _binding
    private lateinit var pagerAdapter : UsersAdapter
    private val viewModel: SharedViewModel by lazy {
        val factory = SharedViewModelFactory(requireActivity().application)
        ViewModelProvider(this, factory).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding  = FragmentUsersBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       pagerAdapter = UsersAdapter(UserComparator,viewModel.personRepository){
            val action = UsersFragmentDirections.actionUsersFragmentToUserDetails(it)
            findNavController().navigate(action)
        }
        binding.usersRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.usersRecyclerView.adapter = pagerAdapter.withLoadStateFooter(
            footer = UsersLoadStateAdapter { pagerAdapter.retry() }
        )
        lifecycleScope.launch {
            pagerAdapter.loadStateFlow.collectLatest {loadStates->
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                if ((loadStates.refresh is LoadState.Error)){
                    showCustomDialog()
                }
            }
        }

        loadData()
    }

    private fun showCustomDialog() {
        val dialogView = requireActivity().layoutInflater.inflate(R.layout.retry, null)
        val alertDialog = AlertDialog.Builder(requireActivity()).create()
        alertDialog.setView(dialogView)
        alertDialog.setCancelable(false)

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogView.findViewById<Button>(R.id.btnReload).setOnClickListener {
            alertDialog.dismiss()
            pagerAdapter.retry()
        }

        // Show the dialog
        alertDialog.show()
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                pagerAdapter.submitData(pagingData)
            }
        }
    }

}