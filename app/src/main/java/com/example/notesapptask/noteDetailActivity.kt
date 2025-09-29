package com.example.notesapptask

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapptask.database.Note

class noteDetailActivity : AppCompatActivity() {
    private val noteViewModel: NoteViewModel by viewModels()
    private var note: Note? = null
    private lateinit var etTitle: EditText
    private lateinit var etDesc: EditText
    private lateinit var btnUpdate: Button
    private lateinit var backArrow: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_note_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etTitle = findViewById(R.id.title_et)
        etDesc = findViewById(R.id.desc_et)
        btnUpdate = findViewById(R.id.updateBtn)
        backArrow = findViewById(R.id.backArrow_id)

        note = intent.getSerializableExtra("note") as? Note

        note?.let {
            etTitle.setText(it.title)
            etDesc.setText(it.description)
        }

        makeReadOnly()


        etTitle.setOnClickListener { enableEditing() }
        etDesc.setOnClickListener { enableEditing() }

        backArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnUpdate.setOnClickListener {
            val newTitle = etTitle.text.toString().trim()
            val newDesc = etDesc.text.toString().trim()

            if (note != null) {
                val updatedNote = note!!.copy(title = newTitle, description = newDesc)
                noteViewModel.update(updatedNote)

                Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun enableEditing() {
        etTitle.isFocusable = true
        etTitle.isFocusableInTouchMode = true
        etTitle.isCursorVisible = true

        etDesc.isFocusable = true
        etDesc.isFocusableInTouchMode = true
        etDesc.isCursorVisible = true

        etTitle.requestFocus()
    }
    private fun makeReadOnly() {
        etTitle.isFocusable = false
        etTitle.isFocusableInTouchMode = false
        etTitle.isCursorVisible = false

        etDesc.isFocusable = false
        etDesc.isFocusableInTouchMode = false
        etDesc.isCursorVisible = false
    }
}