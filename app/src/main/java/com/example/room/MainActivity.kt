package com.example.room

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.data.db.ToDoEntity
import com.example.room.databinding.ActivityMainBinding
import com.example.room.databinding.ViewDialogBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()
    private val adapter: ToDoAdapter = ToDoAdapter(this)
    private var deletedList = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter




        adapter.click = {
            if (!deletedList) {
                it.done = !it.done
            } else {
                it.deleted = !it.deleted
            }
            viewModel.updateToDo(it)
        }

        if (deletedList) {
            setDeletedListAdapter()
        } else {
            setToDoListAdapter()
        }

        binding.clear.setOnClickListener {
            if (!deletedList) {
                deleteAlert(
                    getString(R.string.move_to_trash),
                    getString(R.string.are_you_sure_you_want_to_move_all_tasks_to_trash_you_can_restore_them_from_there),
                    viewModel.deleteAll()
                )
            } else {
                deleteAlert(
                    getString(R.string.delete_all_forever),
                    getString(R.string.are_you_sure_you_want_to_delete_all_tasks_you_won_t_be_able_to_restore_them),
                    viewModel.deleteAllCompletely()
                )
            }
        }

        binding.deleted.setOnClickListener {
            deletedList = !deletedList
            binding.circleCounter.title =
                if (deletedList) getString(R.string.deleted_list)
                else getString(R.string.to_do_list)
            binding.deleted.text =
                if (deletedList) getString(R.string.to_do)
                else getString(R.string.deleted)
            binding.addButton.isClickable = !deletedList
            binding.addButton.isVisible = !deletedList
            getData()
        }

        getData()
        addToDo()

    }

    private fun addToDo() {
        binding.addButton.setOnClickListener {
            val view = ViewDialogBinding.inflate(layoutInflater)
            val alert = AlertDialog.Builder(this)
                .setView(view.root)
                .create()

            view.cancel.setOnClickListener {
                alert.dismiss()
            }
            view.save.setOnClickListener {
                if (!view.input.text.isNullOrBlank()) {
                    viewModel.saveTodo(view.input.text.toString())
                }
                alert.dismiss()
            }
            alert.show()
        }
    }

    private fun deleteAlert(title: String, message: String, action: Unit) {
        binding.clear.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes") { _, _ ->
                    action
                }
                .setNegativeButton("No") { _, _ -> }

            alertDialog.show()
        }
    }

    private fun setToDoListAdapter() {
        adapter.delete = {
            it.deleted = true
            viewModel.updateToDo(it)
        }
    }

    private fun setDeletedListAdapter() {
        adapter.delete = {
            viewModel.deleteById(it.id)
        }
    }

    private fun getData() {
        viewModel.todoListLiveData.observe(this) {
            val toDoList = it.filter { deleted -> !deleted.deleted }
            if (!deletedList)
                setList(toDoList)
        }

        viewModel.deletedToDoListLiveData.observe(this) {
            val toDoList = it.filter { deleted -> deleted.deleted }
            if (deletedList)
                setList(toDoList)
        }
    }

    private fun setList(list: List<ToDoEntity>) {
        binding.emptyList.root.isVisible = list.isEmpty()
        adapter.submitList(list)
        binding.circleCounter.total = list.size
        val done = list.filter { toDo -> toDo.done }.size
        binding.circleCounter.done = done
        binding.clear.isVisible = list.isNotEmpty()
    }

}