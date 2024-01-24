package com.codingwithsaurav.mvvmarchitecture.post.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingwithsaurav.mvvmarchitecture.databinding.PostItemBinding
import com.codingwithsaurav.mvvmarchitecture.post.domain.model.PostItem

class PostAdapter(private val postList: ArrayList<PostItem>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            PostItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        with(holder) {
            with(postList[position]) {
                binding.textViewId.text = this.id.toString()
                binding.textViewTittle.text = this.title.toString()
                binding.textViewBody.text = this.body.toString()
                binding.rootCV.setCardBackgroundColor(getRandomColor())
            }
        }
    }

    private fun getRandomColor(): Int {
        val red = (0..255).random()
        val green = (0..255).random()
        val blue = (0..255).random()
        return Color.rgb(red, green, blue)
    }

    inner class PostViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

}