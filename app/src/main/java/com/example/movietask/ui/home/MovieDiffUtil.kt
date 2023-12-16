package com.example.movietask.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.example.movietask.domain.pojo.ResultPojo

object MovieDiffUtil : DiffUtil.ItemCallback<ResultPojo>() {
    override fun areItemsTheSame(oldItem: ResultPojo, newItem: ResultPojo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultPojo, newItem: ResultPojo): Boolean {
        return oldItem == newItem
    }
}