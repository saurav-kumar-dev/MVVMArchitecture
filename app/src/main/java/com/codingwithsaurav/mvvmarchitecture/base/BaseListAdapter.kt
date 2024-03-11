package com.codingwithsaurav.mvvmarchitecture.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
abstract class BaseListAdapter<T : Any, VB : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val inflate: Inflate<VB>
) : ListAdapter<T, BaseListAdapter<T, VB>.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(getItem(position), holder.binding, position)
    }
    abstract fun bind(item: T, binding: VB, position: Int)
    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

}
