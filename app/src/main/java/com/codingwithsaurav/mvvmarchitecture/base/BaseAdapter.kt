package com.codingwithsaurav.mvvmarchitecture.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T : Any, VB : ViewBinding>(
    private var items:ArrayList<T>,
    private val inflate: Inflate<VB>
): RecyclerView.Adapter<BaseAdapter<T, VB>.ViewHolder>() {


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(items[position]){
                bind(this, binding, position)
            }
        }
    }

    abstract fun bind(item: T, binding : VB, position: Int)

    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

}



