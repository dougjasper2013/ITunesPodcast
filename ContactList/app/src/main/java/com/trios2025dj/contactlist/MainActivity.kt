package com.trios2025dj.contactlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()
    private val ADD_EDIT_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ContactAdapter(contactList) { contact, index ->
            val intent = Intent(this, AddEditContactActivity::class.java).apply {
                putExtra("isEdit", true)
                putExtra("name", contact.name)
                putExtra("phone", contact.phoneNumber)
                putExtra("email", contact.email)
                putExtra("index", index)
            }
            startActivityForResult(intent, ADD_EDIT_REQUEST)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        contactList.addAll(listOf(
            Contact("Alice", "123-456-7890", "alice@email.com"),
            Contact("Bob", "555-555-5555", "bob@email.com")
        ))

        adapter.notifyDataSetChanged()

        findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener {
            val intent = Intent(this, AddEditContactActivity::class.java)
            startActivityForResult(intent, ADD_EDIT_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_EDIT_REQUEST && resultCode == RESULT_OK && data != null) {

            // 🛠️ ✅ Check for deletion FIRST
            if (data.getBooleanExtra("delete", false)) {
                val index = data.getIntExtra("index", -1)
                if (index in contactList.indices) {
                    contactList.removeAt(index)
                    adapter.notifyItemRemoved(index)
                }
                return  // ✅ Exit early since this is a delete
            }

            // Continue with add/edit logic
            val name = data.getStringExtra("name") ?: return
            val phone = data.getStringExtra("phone") ?: return
            val email = data.getStringExtra("email") ?: return
            val isEdit = data.getBooleanExtra("isEdit", false)
            val index = data.getIntExtra("index", -1)

            val contact = Contact(name, phone, email)

            if (isEdit && index >= 0) {
                contactList[index] = contact
                adapter.notifyItemChanged(index)
            } else {
                contactList.add(contact)
                adapter.notifyItemInserted(contactList.size - 1)
            }
        }
    }

}
