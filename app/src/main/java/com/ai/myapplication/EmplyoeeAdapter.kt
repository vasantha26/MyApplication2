package com.ai.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ai.myapplication.databinding.ItemEmplyoeeBinding

class EmplyoeeAdapter(
    private val onEdit: (EmplyoeeModel) -> Unit,
    private val onDelete: (EmplyoeeModel) -> Unit
) : ListAdapter<EmplyoeeModel, EmplyoeeAdapter.EmplyoeeViewHolder>(DIFF_CALLBACK) {

    inner class EmplyoeeViewHolder(
        private val binding: ItemEmplyoeeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(emplyoee: EmplyoeeModel) = with(binding) {
            tvName.text = emplyoee.name
            tvDept.text = emplyoee.dept
            btnEdit.setOnClickListener { onEdit(emplyoee) }
            btnDelete.setOnClickListener { onDelete(emplyoee) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmplyoeeViewHolder {
        val binding = ItemEmplyoeeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EmplyoeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmplyoeeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EmplyoeeModel>() {

            override fun areItemsTheSame(old: EmplyoeeModel, new: EmplyoeeModel) =
                old.id == new.id

            override fun areContentsTheSame(old: EmplyoeeModel, new: EmplyoeeModel) =
                old == new
        }
    }
}
