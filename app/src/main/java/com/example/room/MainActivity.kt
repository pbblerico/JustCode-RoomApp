package com.example.room

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.databinding.ActivityMainBinding
import com.example.room.databinding.ViewDialogBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()
    private val adapter: ToDoAdapter = ToDoAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter

        adapter.click = {
            it.done = !it.done
            viewModel.updateToDo(it)
        }

        adapter.delete = {
            it.deleted = true
            viewModel.updateToDo(it)
        }

        binding.clear.setOnClickListener {
            viewModel.deleteAll()
        }

        viewModel.todoListLiveData.observe(this) {
            val toDoList = it.filter { deleted -> !deleted.deleted }
            binding.emptyList.root.isVisible = toDoList.isEmpty()
            adapter.submitList(toDoList)
            binding.circleCounter.total = it.size
            val done = toDoList.filter { toDo -> toDo.done }.size
            binding.circleCounter.done = done
        }

        viewModel.deletedToDoListLiveData.observe(this) {
            binding.deleted.isClickable = it.isNotEmpty()
        }

        binding.addButton.setOnClickListener {

            val view = ViewDialogBinding.inflate(layoutInflater)
            val alert = AlertDialog.Builder(this)
                .setView(view.root)
                .create()

            view.cancel.setOnClickListener {
                alert.dismiss()
            }
            view.save.setOnClickListener {
                if(!view.input.text.isNullOrBlank()) {
                    viewModel.saveTodo(view.input.text.toString())
                }
                alert.dismiss()
            }

            alert.show()

        }
    }
}