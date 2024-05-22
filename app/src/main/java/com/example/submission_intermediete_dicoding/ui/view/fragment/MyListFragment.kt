package com.example.submission_intermediete_dicoding.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_intermediete_dicoding.data.retrofit.Injection
import com.example.submission_intermediete_dicoding.database.myStory.MyStory
import com.example.submission_intermediete_dicoding.databinding.FragmentMyListBinding
import com.example.submission_intermediete_dicoding.ui.adapter.MyStoryAdapter
import com.example.submission_intermediete_dicoding.ui.viewmodel.StoryViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.StoryViewModelFactory
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentMyListBinding
    private lateinit var myStoryViewModel : StoryViewModel

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
    ) : View {
        binding = FragmentMyListBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val storyRepository = Injection.provideStoryRepository(requireActivity())
        myStoryViewModel = ViewModelProvider(this, StoryViewModelFactory(storyRepository)).get(StoryViewModel::class.java)

        setupRecyclerView()
        myStoryViewModel.allMyStories.observe(viewLifecycleOwner){ data ->
            if (data != null) {
                setListData(data)
            }
        }
        myStoryViewModel.getMyStoryALl()
        myStoryViewModel.loading.observe(requireActivity()) {
            showLoading(it)
        }

        myStoryViewModel.snackbar.observe(requireActivity()) {
            it.getContentIfNotHandled()?.let { snackBar ->
                Snackbar.make(requireView(), snackBar, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setListData(data: List<MyStory>) {
        val adapter = MyStoryAdapter(data, requireActivity())
        binding.rvMyListStory.adapter = adapter

        if (data.isEmpty()) {
            binding.tvFollow.visibility = View.VISIBLE
        } else {
            binding.tvFollow.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMyListStory.layoutManager =layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvMyListStory.addItemDecoration(itemDecoration)
        binding.rvMyListStory.adapter = MyStoryAdapter(emptyList(), requireActivity())
    }

    private fun showLoading(a: Boolean) {
        if (a) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

}