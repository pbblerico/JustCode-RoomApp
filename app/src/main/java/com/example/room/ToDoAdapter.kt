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
            if(!item.done) {
                binding.checkBox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ellipse))
            } else {
                binding.checkBox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.check_circle))
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