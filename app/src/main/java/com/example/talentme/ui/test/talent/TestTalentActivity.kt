package com.example.talentme.ui.test.talent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.talentme.R
import com.example.talentme.ViewModelFactory
import com.example.talentme.data.model.PredictRequest
import com.example.talentme.databinding.ActivityTestPassionBinding
import com.example.talentme.databinding.ActivityTestTalentBinding
import com.example.talentme.form.Question
import com.example.talentme.form.QuestionAdapter
import com.example.talentme.helper.PredictionHelper
import com.example.talentme.ui.login.LoginViewModel
import com.example.talentme.ui.result.talent.ResultTalentActivity

class TestTalentActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var questionList: List<Question>
    private lateinit var binding: ActivityTestTalentBinding
    private lateinit var predictionHelper: PredictionHelper
    private val viewModel by viewModels<TestTalentViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTestTalentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)


        questionList = listOf(
            Question(getString(R.string.question_1), getString(R.string.question_1_option1), getString(R.string.question_1_option2), getString(R.string.question_1_option3)),
            Question(getString(R.string.question_2), getString(R.string.question_2_option1), getString(R.string.question_2_option2), getString(R.string.question_2_option3)),
            Question(getString(R.string.question_3), getString(R.string.question_3_option1), getString(R.string.question_3_option2), getString(R.string.question_3_option3)),
            Question(getString(R.string.question_4), getString(R.string.question_4_option1), getString(R.string.question_4_option2), getString(R.string.question_4_option3)),
            Question(getString(R.string.question_5), getString(R.string.question_5_option1), getString(R.string.question_5_option2), getString(R.string.question_5_option3)),
            Question(getString(R.string.question_6), getString(R.string.question_6_option1), getString(R.string.question_6_option2), getString(R.string.question_6_option3)),
            Question(getString(R.string.question_7), getString(R.string.question_7_option1), getString(R.string.question_7_option2), getString(R.string.question_7_option3)),
            Question(getString(R.string.question_8), getString(R.string.question_8_option1), getString(R.string.question_8_option2), getString(R.string.question_8_option3)),
            Question(getString(R.string.question_9), getString(R.string.question_9_option1), getString(R.string.question_9_option2), getString(R.string.question_9_option3)),
            Question(getString(R.string.question_10), getString(R.string.question_10_option1), getString(R.string.question_10_option2), getString(R.string.question_10_option3)),
            Question(getString(R.string.question_11), getString(R.string.question_11_option1), getString(R.string.question_11_option2), getString(R.string.question_11_option3)),
            Question(getString(R.string.question_12), getString(R.string.question_12_option1), getString(R.string.question_12_option2), getString(R.string.question_12_option3)),
            Question(getString(R.string.question_13), getString(R.string.question_13_option1), getString(R.string.question_13_option2), getString(R.string.question_13_option3)),
            Question(getString(R.string.question_14), getString(R.string.question_14_option1), getString(R.string.question_14_option2), getString(R.string.question_14_option3)),
            Question(getString(R.string.question_15), getString(R.string.option_not_good), getString(R.string.option_good), getString(R.string.option_very_good)),
            Question(getString(R.string.question_16), getString(R.string.option_not_good), getString(R.string.option_good), getString(R.string.option_very_good)),
            Question(getString(R.string.question_17), getString(R.string.option_not_good), getString(R.string.option_good), getString(R.string.option_very_good)),
            Question(getString(R.string.question_18), getString(R.string.option_not_good), getString(R.string.option_good), getString(R.string.option_very_good)),
            Question(getString(R.string.question_19), getString(R.string.option_not_good), getString(R.string.option_good), getString(R.string.option_very_good)),
            Question(getString(R.string.question_20), getString(R.string.option_not_good), getString(R.string.option_good), getString(R.string.option_very_good)),
            Question(getString(R.string.question_21), getString(R.string.option_not_good), getString(R.string.option_good), getString(R.string.option_very_good)),
            Question(getString(R.string.question_22), getString(R.string.option_not_good), getString(R.string.option_good), getString(R.string.option_very_good)),
            Question(getString(R.string.question_23), getString(R.string.option_not_good), getString(R.string.option_good), getString(R.string.option_very_good)),
            Question(getString(R.string.question_24), getString(R.string.option_not_good), getString(R.string.option_good), getString(R.string.option_very_good)),
            Question(getString(R.string.question_25), getString(R.string.option_not_good), getString(R.string.option_good), getString(R.string.option_very_good))
        )

        questionAdapter = QuestionAdapter(questionList)
        recyclerView.adapter = questionAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.predictResult.observe(this) { result ->
            val dummyData = viewModel.getDummyResponse()
            if (result?.data != null) {
                Log.d("TestTalentActivity", "Prediction result: $result")
                val intent = Intent(this, ResultTalentActivity::class.java).apply {
                    putExtra("PREDICTION_RESULT", result)
                }
                finish()
                startActivity(intent)
            } else {

                Log.e("TestTalentActivity", "Prediction failed, result is null or empty")
                Toast.makeText(this, "Prediction failed, please try again. using dummy data", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ResultTalentActivity::class.java).apply {
                    putExtra("PREDICTION_RESULT", dummyData)
                }
                finish()
                startActivity(intent)
            }
        }


        binding.continueButton.setOnClickListener {
            val userInput = getUserInput()
            Log.d("TestTalentActivity", "User Input: $userInput")
            if (userInput.contains(-1)) {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show()
            } else {
                val inputData = listOf(userInput)
                val input = PredictRequest(inputData)
                Log.d("TestTalentActivity", "Input Data sent to ViewModel: $input")
                viewModel.predict(input)
                /*predictionHelper.predict(inputData.toString())
                predictionHelper.onResult = { result ->
                    val intent = Intent(this, ResultTalentActivity::class.java).apply {
                        putExtra("PREDICTION_RESULT", result)
                    }
                    startActivity(intent)
                    finish()
                }
                predictionHelper.onError = { errorMessage ->
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }*/
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        //predictionHelper.close()
    }
    private fun getUserInput(): List<Int> {
        return questionList.map { question ->
            question.selectedOptionIndex ?: -1
        }
    }
}
