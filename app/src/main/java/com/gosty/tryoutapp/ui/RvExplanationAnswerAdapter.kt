package com.gosty.tryoutapp.ui

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.domain.models.ScoreModel
import com.gosty.tryoutapp.domain.models.SelectionAnswerModel
import com.gosty.tryoutapp.domain.models.SelectionModel
import com.gosty.tryoutapp.databinding.ItemAnswerBinding

class RvExplanationAnswerAdapter(
    var selectionAnswer: List<SelectionAnswerModel?>?,
    var option: List<SelectionModel?>,
    var answer: ScoreModel?,
    var totalOption: Int,
    var context: Context,
) : RecyclerView.Adapter<RvExplanationAnswerAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemAnswerBinding, val context: Context) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemAnswerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = totalOption

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = option[position]

        holder.binding.tvOpsiABC.text = "${(65 + position).toChar()}. "

        val flags = Html.FROM_HTML_MODE_COMPACT or Html.FROM_HTML_MODE_LEGACY

        holder.binding.mvAnswer.isVisible = true
        holder.binding.mvAnswer.text = Html.fromHtml(currentItem?.selectionText, flags, { source ->
            Glide.with(context)
                .load(source.replace(""""""", ""))
                .into(holder.binding.ivAnswer)
            null
        }, null).toString()

        if (answer?.answers == null){
            for (a in selectionAnswer!!){
                if (currentItem?.selectionText?.isEmpty() == true){
                    if (currentItem?.image == a?.image){
                        holder.binding.mvAnswer.isVisible = false
                        Glide.with(context)
                            .load(currentItem.image)
                            .into(holder.binding.ivAnswer)
                        holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_green_8_full_radius)
                    } else{
                        holder.binding.mvAnswer.isVisible = false
                        Glide.with(context)
                            .load(currentItem.image)
                            .into(holder.binding.ivAnswer)
                    }
                }
            }
        } else {
            for (i in answer?.answers!!){
                if (i.questionId == currentItem?.questionId){
                    if (currentItem?.selectionText?.isEmpty() == true){
                        holder.binding.mvAnswer.isVisible = false
                        Glide.with(context)
                            .load(currentItem.image)
                            .into(holder.binding.ivAnswer)
                    }
                } else {
                    for (a in selectionAnswer!!){
                        if (currentItem?.selectionText?.isEmpty() == true){
                            if (currentItem?.image == a?.image){
                                holder.binding.mvAnswer.isVisible = false
                                Glide.with(context)
                                    .load(currentItem.image)
                                    .into(holder.binding.ivAnswer)
                                holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_green_8_full_radius)
                            } else{
                                holder.binding.mvAnswer.isVisible = false
                                Glide.with(context)
                                    .load(currentItem.image)
                                    .into(holder.binding.ivAnswer)
                            }
                        }
                    }
                }
            }
        }

        if (answer?.answers == null){
            for (a in selectionAnswer!!){
                if (currentItem?.selectionText?.isEmpty() == false){
                    if (currentItem?.selectionText == a?.selectionText){
                        holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_green_8_full_radius)
                    }
                } else {
                    if (currentItem?.image == a?.image){
                        holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_green_8_full_radius)
                    }
                }
            }
        } else {
            for (i in answer?.answers!!){
                if (i.questionId == currentItem?.questionId){
                    for (j in i.answer!!){
                        if (currentItem?.selectionText?.isEmpty() == false){
                            if (j == currentItem.selectionText){
                                holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_red_8_full_radius)
                                for (k in selectionAnswer!!){
                                    if (j == k?.selectionText){
                                        holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_green_8_full_radius)
                                    }
                                }
                            }
                        } else {
                            if (j == currentItem?.image){
                                holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_red_8_full_radius)
                                for (k in selectionAnswer!!){
                                    if (j == k?.image){
                                        holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_green_8_full_radius)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (a in selectionAnswer!!){
                        if (currentItem?.selectionText?.isEmpty() == false){
                            if (currentItem?.selectionText == a?.selectionText){
                                holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_green_8_full_radius)
                            }
                        } else {
                            if (currentItem?.image == a?.image){
                                holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_green_8_full_radius)
                            }
                        }
                    }
                }
            }
        }
    }
}