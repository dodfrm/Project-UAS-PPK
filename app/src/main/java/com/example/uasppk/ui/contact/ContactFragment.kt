package com.example.uasppk.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uasppk.data.api.ApiClient
import com.example.uasppk.data.repository.ContactRepository
import com.example.uasppk.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

  private var _binding: FragmentContactBinding? = null
  private val binding get() = _binding!!

  private lateinit var contactsRepository: ContactRepository
  private val contactsViewModel: ContactViewModel by viewModels {
    ContactViewModelFactory(contactsRepository)
  }

  private lateinit var adapter: ContactAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val apiService = ApiClient.getApiService(accessToken = null)
    contactsRepository = ContactRepository(apiService)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentContactBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    adapter = ContactAdapter()
    binding.recyclerView.layoutManager = LinearLayoutManager(context)
    binding.recyclerView.adapter = adapter

    // Observe data from ViewModel
    contactsViewModel.contacts.observe(viewLifecycleOwner) { contacts ->
      adapter.submitList(contacts)
    }

    contactsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
      binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // Fetch contacts
    contactsViewModel.fetchContact()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
