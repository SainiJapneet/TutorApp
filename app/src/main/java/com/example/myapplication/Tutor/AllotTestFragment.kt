package com.example.myapplication.Tutor

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.Adapter.AdapterStudentTest
import com.example.myapplication.Adapter.TestPagerAdapter
import com.example.myapplication.Data.DataModel
import com.example.myapplication.Data.Prefs
import com.example.myapplication.Data.TestRepository
import com.example.myapplication.Data.TestViewModel
import com.example.myapplication.Data.TestViewModelFactory
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAllotTestBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AllotTestFragment : Fragment() {
    lateinit var binding: FragmentAllotTestBinding
    lateinit var auth: FirebaseAuth
    var firebaseDatabase = Firebase.database
    lateinit var databaseReference: DatabaseReference
    lateinit var adapter: AdapterStudentTest
    val testRepository = TestRepository(firebaseDatabase)
    val viewModelFactory = TestViewModelFactory(testRepository)
    val viewModel: TestViewModel by viewModels { viewModelFactory }
    var testId = ""
    var tutorUid : String? = ""
    var finalStudentIdsListArray = ArrayList<String>()
    var finalQuestionsListArray = ArrayList<DataModel.Question>()
    var questionsListJSON: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllotTestBinding.inflate(inflater, container, false)
        tutorUid = Prefs.getUID(requireContext())
        //finalQuestionsListArray = navquestionlist.toList()
        if (arguments?.getInt("questions_list") != null) {
            questionsListJSON = arguments?.getString("questions_list")!!
        }
        val gson = Gson()
        val questionListType = object : TypeToken<ArrayList<DataModel.Question>>() {}.type
        finalQuestionsListArray = gson.fromJson(questionsListJSON, questionListType)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imgBackAllotTest.setOnClickListener {
                findNavController().navigateUp()
            }
            imgCreateTest.setOnClickListener {
                showPopUp()
            }
            viewPagerTest.adapter = TestPagerAdapter(requireActivity())
            TabLayoutMediator(tabTest, viewPagerTest){tab, position ->
                when(position){
                    0 -> tab.text = "Groups"
                    1 -> tab.text = "Students"
                }
            }.attach()
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventStudentIdList(passedArrayStudentIdsList : ArrayList<String>) {
        finalStudentIdsListArray = passedArrayStudentIdsList
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
            viewModel.saveTestAndQuestions(testId,finalQuestionsListArray,tutorUid,finalStudentIdsListArray)
            alertDialog.dismiss()
        }
        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}