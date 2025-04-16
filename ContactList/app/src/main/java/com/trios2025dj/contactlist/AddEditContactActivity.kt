package com.trios2025dj.contactlist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class AddEditContactActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var deleteButton: Button

    private var isEditMode = false
    private var editIndex = -1

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            selectedImageUri = uri.toString()
            imageView.setImageURI(uri)
        }
    }

    private var selectedImageUri: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_contact)

        imageView = findViewById(R.id.imageView)

        nameEditText = findViewById(R.id.edit_name)
        phoneEditText = findViewById(R.id.edit_phone)
        emailEditText = findViewById(R.id.edit_email)
        deleteButton = findViewById(R.id.button_delete)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val selectImageButton = findViewById<Button>(R.id.btn_select_image)

        selectImageButton.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }


        isEditMode = intent.getBooleanExtra("isEdit", false)
        if (isEditMode) {
            title = "Edit Contact"
            selectedImageUri = intent.getStringExtra("imageUri")
            if (!selectedImageUri.isNullOrEmpty()) {
                try {
                    imageView.setImageURI(Uri.parse(selectedImageUri))
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Optionally load a fallback/default image here
                    imageView.setImageResource(R.drawable.ic_person_placeholder)
                }
            }
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
                putExtra("imageUri", selectedImageUri)
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
