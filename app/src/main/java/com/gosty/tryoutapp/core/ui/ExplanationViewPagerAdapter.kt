package com.gosty.tryoutapp.core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gosty.tryoutapp.core.domain.models.QuestionModel
import com.gosty.tryoutapp.core.domain.models.ScoreModel
import com.gosty.tryoutapp.presentation.explanation.ExplanationFragment

class ExplanationViewPagerAdapter(
    activity : AppCompatActivity,
    private val questions: List<QuestionModel?>?,
    private val score: ScoreModel?,
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = questions?.size!!

    override fun createFragment(position: Int): Fragment {
        val fragment = ExplanationFragment()
        val bundle = Bundle()

        bundle.putParcelable(ExplanationFragment.EXTRA_DATA_EXPLANATION, questions?.get(position))
        bundle.putParcelable(ExplanationFragment.EXTRA_DATA_SCORE, score)
        bundle.putInt(ExplanationFragment.EXTRA_POSITION, position)
        bundle.putInt(ExplanationFragment.EXTRA_TOTAL_QUESTION, questions?.size!!)

        fragment.arguments = bundle

        return fragment
    }
}