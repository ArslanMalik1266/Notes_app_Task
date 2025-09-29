package com.example.notesapptask.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.notesapptask.NoteViewModel
import com.example.notesapptask.R
import com.example.notesapptask.database.Note


class add_item : Fragment() {

    private val noteViewModel: NoteViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)

        val etTitle = view.findViewById<EditText>(R.id.titleEditText)
        val etDescription = view.findViewById<EditText>(R.id.descriptionEditText)
        val btnSave = view.findViewById<Button>(R.id.saveBtn)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()

            when {
                title.isEmpty() -> {
                    Toast.makeText(requireContext(), "Please enter a title", Toast.LENGTH_SHORT).show()
                }
                description.isEmpty() -> {
                    Toast.makeText(requireContext(), "Please enter a description", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val note = Note(title = title, description = description)
                    noteViewModel.insert(note)

                    Toast.makeText(requireContext(), "Note saved successfully ", Toast.LENGTH_SHORT).show()

                    etTitle.text.clear()
                    etDescription.text.clear()
                    val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
                    viewPager.currentItem = 1
                }
            }
        }


        return view
    }


}