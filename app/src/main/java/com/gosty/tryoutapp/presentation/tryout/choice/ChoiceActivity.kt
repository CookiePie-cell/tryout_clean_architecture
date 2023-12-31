package com.gosty.tryoutapp.presentation.tryout.choice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.databinding.ActivityChoiceBinding
import com.gosty.tryoutapp.presentation.tryout.problem.ProblemActivity
import com.gosty.tryoutapp.core.utils.Result
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChoiceBinding
    private val viewModel: ChoiceViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewModel.getSubjects()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        initView()
    }

    /***
     * This method is to initialize all view.
     * @author Ghifari Octaverin
     * @since Sept 5th, 2023
     * Updated Sept 11th, 2023 by Ghifari Octaverin
     */
    private fun initView() {
        viewModel.tryouts.observe(this@ChoiceActivity) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.stateView.viewState = MultiStateView.ViewState.LOADING
                }

                is Result.Success -> {
                    binding.stateView.viewState = MultiStateView.ViewState.CONTENT
                    binding.btnDataDanKetidakpastian.setOnClickListener {
                        val intent = Intent(this@ChoiceActivity, ProblemActivity::class.java)
                        intent.putExtra(ProblemActivity.EXTRA_TRYOUT_TYPE, result.data[0].tryout!![0])
                        intent.putExtra(ProblemActivity.EXTRA_TOTAL_QUESTIONS, result.data[0].tryout!![0]?.question?.size)
                        startActivity(intent)
                    }

                    binding.btnGeometriDanPengukuran.setOnClickListener {
                        val intent = Intent(this@ChoiceActivity, ProblemActivity::class.java)
                        intent.putExtra(ProblemActivity.EXTRA_TRYOUT_TYPE, result.data[0].tryout!![1])
                        intent.putExtra(ProblemActivity.EXTRA_TOTAL_QUESTIONS, result.data[0].tryout!![1]?.question?.size)
                        startActivity(intent)
                    }
                }

                is Result.Error -> {
                    binding.stateView.viewState = MultiStateView.ViewState.ERROR
                    Toast.makeText(this@ChoiceActivity, result.error, Toast.LENGTH_SHORT).show()
                    val error = binding.stateView.getView(MultiStateView.ViewState.ERROR)
                    if (error != null) {
                        val btn: Button = error.findViewById(R.id.btnRefreshProblem)
                        btn.setOnClickListener {
                            viewModel.getSubjects()
                        }
                    }
                }
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getSubjects()
            binding.refreshLayout.isRefreshing = false
        }
    }
}