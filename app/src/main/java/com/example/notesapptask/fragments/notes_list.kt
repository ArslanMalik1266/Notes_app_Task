package com.example.notesapptask.fragments

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapptask.NoteAdapter
import com.example.notesapptask.NoteViewModel
import com.example.notesapptask.R
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class notes_list : Fragment() {

    private val noteViewModel: NoteViewModel by activityViewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        adapter = NoteAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            adapter.setNotes(notes)
        }

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = adapter.getNoteAt(viewHolder.adapterPosition)
                noteViewModel.delete(note)
                Snackbar.make(requireView(), "Note deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        noteViewModel.insert(note)
                    }
                    .show()

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.holo_red_dark
                        )
                    )
                    .addActionIcon(R.drawable.delete_icon)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        })
        itemTouchHelper.attachToRecyclerView(recyclerView)


        return view
    }

}