package com.example.myapplication.Tutor

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.QuestionAdapter
import com.example.myapplication.Data.DataModel
import com.example.myapplication.Data.Prefs
import com.example.myapplication.Data.TestRepository
import com.example.myapplication.Data.TestViewModel
import com.example.myapplication.Data.TestViewModelFactory
import com.example.myapplication.Data.TutorViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCreateTestBinding
import com.example.myapplication.interfaces.QuestionClickListener
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class CreateTestFragment : Fragment(),  QuestionAdapter.onclickListner {
    lateinit var binding: FragmentCreateTestBinding
    lateinit var adapter: QuestionAdapter

    val firebaseDatabase = Firebase.database
    val testRepository = TestRepository(firebaseDatabase)
    val viewModelFactory = TestViewModelFactory(testRepository)
    val viewModel: TestViewModel by viewModels { viewModelFactory }
    var testId = ""
    var uid: String? = ""
    var questionsList = ArrayList<DataModel.Question>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTestBinding.inflate(inflater, container, false)
        viewModel.testRepository = testRepository

        uid = Prefs.getUID(requireContext())
        viewModel.initNewTest(uid!!)
        viewModel.addEmptyQuestion()
        adapter = QuestionAdapter(viewModel.questions.value?: mutableListOf(), this)

        binding.rvTest.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTest.adapter = adapter
        viewModel.questions.observe(viewLifecycleOwner){questions->
            adapter.questions = questions
            adapter.notifyDataSetChanged()
        }
        binding.imgBackCreateTest.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imgSaveCreateTest.setOnClickListener {
           showPopUp()
        }
        binding.imgAddQuestion.setOnClickListener {
            viewModel.addEmptyQuestion()
            adapter.notifyItemInserted(adapter.itemCount)
        }



        return binding.root
    }

    fun showPopUp(){
        val dialogInflater = LayoutInflater.from(requireContext())
        val view = dialogInflater.inflate(R.layout.custom_alert_dialog, null)

        val btnCreate: Button = view.findViewById(R.id.btnCreateCustomAlertDialog)
        val btnCancel: Button = view.findViewById(R.id.btnCancelAlertDialog1)

        val alertDialog = AlertDialog.Builder(requireContext()).setView(view)
            .setCancelable(false).create()

        btnCreate.setOnClickListener {
            testId = testRepository.generateTestId()
            viewModel.saveTestAndQuestions(testId,questionsList,uid)
            alertDialog.dismiss()
        }
        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onQuestionInteraction(question: DataModel.Question, position: Int) {
        //viewModel.addQuestionToTest(testId, question)
    }

    override fun onSaveClicked(
        position: Int,
        question: String,
        option1: String,
        option2: String,
        option3: String,
        option4: String,
        marks: String,
        answer: String
    ) {
        if(question != "" && option1 != "" && option2 != "" && option3 != "" && option4 != "" && answer != "" && marks != "" ) {
            val newQuestion = DataModel.Question(
                "${position}",
                question,
                listOf(option1, option2, option3, option4),
                answer,
                marks.toInt()
            )

            val existingQuestion = questionsList.find { it.questionId == newQuestion.questionId }
            if (existingQuestion == null) {
                questionsList.add(newQuestion)
            } else {
                existingQuestion.text = question
                existingQuestion.options = listOf(option1, option2, option3, option4)
                existingQuestion.correctOption = answer
                existingQuestion.marks = marks.toInt()
            }
        }
    }

}