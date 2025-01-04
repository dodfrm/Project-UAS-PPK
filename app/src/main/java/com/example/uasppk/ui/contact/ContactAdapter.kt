package com.example.uasppk.ui.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uasppk.R
import com.example.uasppk.data.api.response.Contact

class ContactAdapter: RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private val contacts = mutableListOf<Contact>()

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val phoneTextView: TextView = view.findViewById(R.id.phoneTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.nameTextView.text = contact.fullName
        holder.phoneTextView.text = contact.phone
    }

    override fun getItemCount(): Int = contacts.size

    fun submitList(newContacts: List<Contact>) {
        contacts.clear()
        contacts.addAll(newContacts)
        notifyDataSetChanged()
    }
}