package com.example.room

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room.data.db.ToDoEntity
import com.example.room.databinding.ItemToDoBinding
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

class ToDoAdapter(val context: Context): ListAdapter<ToDoEntity, ToDoAdapter.ToDoViewHolder>(ToDoDiffUtils) {
    var click: ((ToDoEntity) -> Unit)? = null
    var delete: ((ToDoEntity) -> Unit)? = null

    object ToDoDiffUtils : DiffUtil.ItemCallback<ToDoEntity>() {

        override fun areItemsTheSame(oldItem: ToDoEntity, newItem: ToDoEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDoEntity, newItem: ToDoEntity): Boolean {
            return oldItem == newItem
        }
    }


    inner class ToDoViewHolder(private val binding: ItemToDoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ToDoEntity) {
            binding.toDoText.text = item.toDo
            setToDoList(item)

            binding.checkBox.setOnClickListener {
                click?.invoke(item)
                if(!item.deleted) setCheckBox(item.done)
            }

            binding.delete.setOnClickListener {
                delete?.invoke(item)
            }
        }
        private fun setCheckBox(done: Boolean) {
            if(!done) {
                binding.checkBox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ellipse))
            } else {
                binding.checkBox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.check_circle))
            }
        }

        private fun setToDoList(toDo: ToDoEntity) {
            if(!toDo.deleted) {
                setCheckBox(toDo.done)
                binding.delete.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.link))
            } else {
                binding.checkBox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_restore))
                binding.delete.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.remove_circle))
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
            ItemToDoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}