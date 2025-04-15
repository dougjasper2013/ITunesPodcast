package com.trios2025dj.contactlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private val contactList: List<Contact>,
    private val onItemClick: (Contact, Int) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.contact_name)
        val phoneTextView: TextView = itemView.findViewById(R.id.contact_phone)
        val emailTextView: TextView = itemView.findViewById(R.id.contact_email)

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemClick(contactList[pos], pos)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.nameTextView.text = contact.name
        holder.phoneTextView.text = contact.phoneNumber
        holder.emailTextView.text = contact.email
    }

    override fun getItemCount(): Int = contactList.size
}

