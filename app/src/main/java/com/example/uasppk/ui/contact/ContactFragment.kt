package com.example.uasppk.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.uasppk.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

  private var _binding: FragmentContactBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val contactViewModel =
            ViewModelProvider(this).get(ContactViewModel::class.java)

    _binding = FragmentContactBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textGallery
      contactViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
    return root
  }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}