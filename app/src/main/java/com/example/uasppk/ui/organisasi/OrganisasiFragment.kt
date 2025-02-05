package com.example.uasppk.ui.organisasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.uasppk.databinding.FragmentOrganisasiBinding

class OrganisasiFragment : Fragment() {

    private var _binding: FragmentOrganisasiBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val organisasiViewModel = ViewModelProvider(this).get(OrganisasiViewModel::class.java)
        _binding = FragmentOrganisasiBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}