package com.example.submission_intermediete_dicoding.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission_intermediete_dicoding.ui.view.activity.LoginActivity
import com.example.submission_intermediete_dicoding.ui.view.fragment.OnBoardFirstScreen
import com.example.submission_intermediete_dicoding.ui.view.fragment.OnBoardSecondScreen
import com.example.submission_intermediete_dicoding.util.LoginPreference
import com.example.submission_intermediete_dicoding.util.OnBoardPreference

class OnBoardPageAdaprter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private var onBoardInteractionListener: OnBoardInteractionListener? = null

    fun setOnBoardInteractionListener(listener: OnBoardInteractionListener) {
        onBoardInteractionListener = listener
    }
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnBoardFirstScreen().apply {

                setOnNextClickListener {
                    onBoardInteractionListener?.moveToNextPage()
                }
            }
            1 -> OnBoardSecondScreen().apply {

                setOnFinishClickListener {
                    onBoardInteractionListener?.finishOnBoarding()
                }
            }
            else -> throw IndexOutOfBoundsException("Invalid position")
        }
    }
}

interface OnBoardInteractionListener {
    fun moveToNextPage()
    fun finishOnBoarding()
}
