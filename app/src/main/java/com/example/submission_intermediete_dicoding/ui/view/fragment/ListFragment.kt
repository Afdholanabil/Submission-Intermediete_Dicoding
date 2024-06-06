package com.example.submission_intermediete_dicoding.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_intermediete_dicoding.data.retrofit.Injection
import com.example.submission_intermediete_dicoding.databinding.FragmentListBinding
import com.example.submission_intermediete_dicoding.ui.adapter.StoryLoadStateAdapter
import com.example.submission_intermediete_dicoding.ui.adapter.StoryPagingAdapter
import com.example.submission_intermediete_dicoding.ui.viewmodel.StoryViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.StoryViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var storyViewModel: StoryViewModel
    private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val storyRepository = Injection.provideStoryRepository(requireContext())
        storyViewModel = ViewModelProvider(this, StoryViewModelFactory(storyRepository)).get(StoryViewModel::class.java)

        setupRecyclerView()

        lifecycleScope.launch {
            storyViewModel.storiesWithPaging.collectLatest { pagingData ->
                (binding.rvListStory.adapter as ConcatAdapter).adapters
                    .filterIsInstance<StoryPagingAdapter>()
                    .firstOrNull()?.submitData(pagingData)
            }
        }

        storyViewModel.loading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        storyViewModel.snackbar.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { snackBar ->
                Snackbar.make(requireView(), snackBar, Snackbar.LENGTH_SHORT).show()
            }
        }
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvListStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvListStory.addItemDecoration(itemDecoration)

        val adapter = StoryPagingAdapter(requireContext())
        val concatAdapter = adapter.withLoadStateFooter(
            footer = StoryLoadStateAdapter { adapter.retry() }
        )
        binding.rvListStory.adapter = concatAdapter

        adapter.addLoadStateListener { loadState ->
            binding.rvListStory.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressCircular.isVisible = loadState.source.refresh is LoadState.Loading
            binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error
            binding.tvError.isVisible = loadState.source.refresh is LoadState.Error
        }

        binding.btnRetry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressCircular.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}