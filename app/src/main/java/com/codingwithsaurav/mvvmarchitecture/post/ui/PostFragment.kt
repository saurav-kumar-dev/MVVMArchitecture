package com.codingwithsaurav.mvvmarchitecture.post.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.codingwithsaurav.mvvmarchitecture.R
import com.codingwithsaurav.mvvmarchitecture.databinding.FragmentPostBinding
import com.codingwithsaurav.mvvmarchitecture.post.domain.model.PostItem
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


@AndroidEntryPoint
class PostFragment : Fragment() {

    private var binding: FragmentPostBinding? = null
    private var adapter: PostAdapter? = null
    private val postViewModel by viewModels<PostViewModel>()
    private var postList: ArrayList<PostItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postViewModel.postStatus.observe(viewLifecycleOwner) { result ->
            result?.let {
                binding?.progressBar?.isVisible = it.isLoading
                if (it.data.isNullOrEmpty().not()) {
                    postList = result?.data
                    adapter = PostAdapter(result?.data)
                    binding?.postRV?.adapter = adapter
                    adapter?.notifyDataSetChanged()
                }
            }
        }

        postViewModel.getPosts()
    }

}