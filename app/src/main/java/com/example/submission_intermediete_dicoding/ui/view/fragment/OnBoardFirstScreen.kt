package com.example.submission_intermediete_dicoding.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.submission_intermediete_dicoding.databinding.FragmentOnBoardFirstScreenBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class OnBoardFirstScreen : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var onNextClickListener: (() -> Unit)? = null

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
        val binding = FragmentOnBoardFirstScreenBinding.inflate(inflater, container, false)
        binding.btnNext.setOnClickListener {
            onNextClickListener?.invoke()
        }

        return binding.root
    }

    fun setOnNextClickListener(listener: () -> Unit) {
        onNextClickListener = listener
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OnBoardFirstScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}