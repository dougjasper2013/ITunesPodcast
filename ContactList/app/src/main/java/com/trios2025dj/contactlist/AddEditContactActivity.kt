package com.trios2025dj.contactlist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class AddEditContactActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var deleteButton: Button

    private var isEditMode = false
    private var editIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_contact)

        nameEditText = findViewById(R.id.edit_name)
        phoneEditText = findViewById(R.id.edit_phone)
        emailEditText = findViewById(R.id.edit_email)
        deleteButton = findViewById(R.id.button_delete)

        isEditMode = intent.getBooleanExtra("isEdit", false)
        if (isEditMode) {
            title = "Edit Contact"
            nameEditText.setText(intent.getStringExtra("name"))
            phoneEditText.setText(intent.getStringExtra("phone"))
            emailEditText.setText(intent.getStringExtra("email"))
            editIndex = intent.getIntExtra("index", -1)
            deleteButton.visibility = View.VISIBLE
        } else {
            title = "Add Contact"
            deleteButton.visibility = View.GONE
        }

        findViewById<Button>(R.id.button_save).setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("name", nameEditText.text.toString())
                putExtra("phone", phoneEditText.text.toString())
                putExtra("email", emailEditText.text.toString())
                putExtra("index", editIndex)
                putExtra("isEdit", isEditMode)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        deleteButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("delete", true)
                putExtra("index", editIndex)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
