package com.example.tranzparency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CircleFragment : Fragment() {

    private lateinit var store: ContactStore
    private lateinit var listView: ListView
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: ArrayAdapter<String>
    private val data = mutableListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater.inflate(R.layout.fragment_circle, container, false)
        listView = v.findViewById(R.id.listCircle)
        fab = v.findViewById(R.id.fabAdd)
        store = ContactStore(requireContext())
        data.clear()
        data.addAll(store.getAll())
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, data)
        listView.adapter = adapter

        // Add contact
        fab.setOnClickListener {
            val input = EditText(requireContext()).apply {
                hint = "Phone number"
                inputType = android.text.InputType.TYPE_CLASS_PHONE
            }
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Add contact")
                .setView(input)
                .setPositiveButton("Add") { _, _ ->
                    val num = input.text.toString().trim()
                    if (num.isNotEmpty()) {
                        store.add(num)
                        data.add(num)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(requireContext(), "Added $num", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Delete on long press
        listView.setOnItemLongClickListener { _, _, pos, _ ->
            val num = data[pos]
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Remove this contact?")
                .setMessage(num)
                .setPositiveButton("Remove") { _, _ ->
                    store.remove(num)
                    data.removeAt(pos)
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("Cancel", null)
                .show()
            true
        }

        return v
    }
}