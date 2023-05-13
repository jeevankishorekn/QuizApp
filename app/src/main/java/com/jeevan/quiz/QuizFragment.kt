package com.jeevan.quiz

import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jeevan.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private lateinit var binding : FragmentQuizBinding
    lateinit var ctx:Context
    var index = 0
    var points = 0
    var tempIndex =0
    var tempPoints = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        points = savedInstanceState?.getInt("points", 0)?:0
        index = savedInstanceState?.getInt("index", 0)?:0
        Log.d(TAG, "onViewCreated: points: $points, index:$index")
        val quesArray = arrayOf(
            QuizModel(ctx.getString(R.string.q1), true),
            QuizModel(ctx.getString(R.string.q2), false),
            QuizModel(ctx.getString(R.string.q3), true),
            QuizModel(ctx.getString(R.string.q4), false),
            QuizModel(ctx.getString(R.string.q5), true),
            QuizModel(ctx.getString(R.string.q6), false),
            QuizModel(ctx.getString(R.string.q7), true),
            QuizModel(ctx.getString(R.string.q8), false),
            QuizModel(ctx.getString(R.string.q9), true),
            QuizModel(ctx.getString(R.string.q10), false)
        )


        binding.question.text = quesArray[index].question
        binding.result.text = points.toString()
        binding.apply {

            trueBtn.setOnClickListener{
                if(quesArray[index].answer){
                    points++
                    tempPoints = points
                    binding.result.text = points.toString()
                }
                progressBar.progress += 10
                index++
                tempIndex = index
                if(!checkMaxQues(index,quesArray,points)){
                    binding.question.text = quesArray[index].question                }
            }

            falseBtn.setOnClickListener{
                if(!quesArray[index].answer){
                    points++
                    tempPoints = points
                    binding.result.text = points.toString()
                }
                progressBar.progress += 10
                index++
                tempIndex = index
                if(!checkMaxQues(index,quesArray,points)){
                binding.question.text = quesArray[index].question
                }
            }
        }

    }

    private fun reset(quesArray: Array<QuizModel>) {
        index = 0
        points = 0
        binding.progressBar.progress = 0
        binding.result.text = points.toString()
        binding.question.text = quesArray[index].question
    }

    private fun checkMaxQues(index: Int, quesArray: Array<QuizModel>, result : Int): Boolean{
        if(index == quesArray.size){
            Toast.makeText(ctx,"Quiz has completed", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(ctx)
                .setTitle("Quiz completed Successfully")
                .setMessage("Score: $result")
                .setPositiveButton("Restart Quiz"
                ) { dialog, _ -> reset(quesArray)
                    dialog.dismiss() }
                .setCancelable(false)
                .show()
            return true
        }
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: points: $tempPoints, index:$tempIndex")
        outState.putInt("points",tempPoints)
        outState.putInt("index",tempIndex)
    }
}