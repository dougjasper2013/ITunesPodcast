package com.trios2025dj.androidexample

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var greetingTextView: TextView
    private lateinit var anotherTextView: TextView
    private lateinit var onemoreTextView: TextView

    private lateinit var changeButton: Button
    private lateinit var changeBackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        greetingTextView = findViewById(R.id.txtGreeting)
        anotherTextView = findViewById(R.id.txtAnother)
        onemoreTextView = findViewById(R.id.txtOneMore)

        changeButton = findViewById(R.id.btnChange)

        changeButton.setOnClickListener { changeTextViews() }

        changeBackButton = findViewById(R.id.btnChangeBack)

        changeBackButton.setOnClickListener { changeBackTextViews() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun changeTextViews() {
        greetingTextView.setTextColor(getColor(R.color.green))
        anotherTextView.setTextColor(getColor(R.color.green))
        onemoreTextView.setTextColor(getColor(R.color.green))
    }

    private fun changeBackTextViews() {
        greetingTextView.setTextColor(getColor(R.color.blue))
        anotherTextView.setTextColor(getColor(R.color.blue))
        onemoreTextView.setTextColor(getColor(R.color.blue))
    }

}