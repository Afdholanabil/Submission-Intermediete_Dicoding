package com.example.submission_intermediete_dicoding.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.data.retrofit.Injection
import com.example.submission_intermediete_dicoding.databinding.FragmentListBinding
import com.example.submission_intermediete_dicoding.ui.adapter.AllStoryAdapter
import com.example.submission_intermediete_dicoding.ui.viewmodel.StoryViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.StoryViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        storyViewModel.stories.observe(viewLifecycleOwner) { data ->
            if (data != null){
                setListData(data)
            }
        }

        storyViewModel.getStories()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setListData(data: List<ListStoryItem>) {
        val adapter = AllStoryAdapter(data, requireActivity())
        binding.rvListStory.adapter = adapter
    }

    private fun setupRecyclerView() {
        val layoutManager =LinearLayoutManager(requireActivity())
        binding.rvListStory.layoutManager =layoutManager
        val itemDecoration =DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvListStory.addItemDecoration(itemDecoration)
        binding.rvListStory.adapter = AllStoryAdapter(emptyList(), requireActivity())

    }
}