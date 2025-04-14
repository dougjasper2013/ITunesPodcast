package com.trios2025dj.contactlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val contactList = listOf(
            Contact("Alice Johnson", "123-456-7890", "alice@example.com"),
            Contact("Bob Smith", "987-654-3210", "bob@example.com"),
            Contact("Carol White", "555-123-4567", "carol@example.com"),
            Contact("David Lee", "444-555-6666", "david@example.com"),
            Contact("Eva Green", "222-333-4444", "eva@example.com")
        )

        val adapter = ContactAdapter(contactList) { contact ->
            val intent = Intent(this, ContactDetailActivity::class.java).apply {
                putExtra("name", contact.name)
                putExtra("phone", contact.phoneNumber)
                putExtra("email", contact.email)
            }
            startActivity(intent)
        }

        recyclerView.adapter = adapter
    }
}

