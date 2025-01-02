package com.example.uasppk.ui.dosen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.uasppk.databinding.FragmentDosenBinding

class DosenFragment : Fragment() {

    private var _binding: FragmentDosenBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dosenViewModel = ViewModelProvider(this).get(DosenViewModel::class.java)

        _binding = FragmentDosenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}