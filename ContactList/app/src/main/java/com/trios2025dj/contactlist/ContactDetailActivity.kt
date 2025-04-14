package com.trios2025dj.contactlist

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ContactDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")

        findViewById<TextView>(R.id.detail_name).text = name
        findViewById<TextView>(R.id.detail_phone).text = phone
        findViewById<TextView>(R.id.detail_email).text = email
    }
}
