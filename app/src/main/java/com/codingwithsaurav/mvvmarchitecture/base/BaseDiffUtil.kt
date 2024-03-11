package com.codingwithsaurav.mvvmarchitecture.base

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtil<T : Any>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val itemCallback: DiffUtil.ItemCallback<T>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return itemCallback.areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return itemCallback.areContentsTheSame(oldList[oldItemPosition], newList[newItemPosition])
    }

}
