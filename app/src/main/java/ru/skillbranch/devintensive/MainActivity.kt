package ru.skillbranch.devintensive

import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.os.Bundle
//import android.os.PersistableBundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender
import ru.skillbranch.devintensive.models.Bender.Question

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // var benderImage: ImageView? = null
    // same as
    lateinit var benderImage: ImageView
    lateinit var textQuestion: TextView
    lateinit var textAnswer: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // benderImage = findViewById(R.id.iv_bender) as ImageView
        // same as
        // benderImage = findViewById<ImageView>(R.id.iv_bender)
        // same as
        // benderImage = findViewById(R.id.iv_bender)
        // same as
        benderImage = iv_bender
        textQuestion = tv_text
        textAnswer = et_message
        sendBtn = iv_send

        makeSendOnActionDone(textAnswer)

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        Log.d("M_MainActivity", "onCreate $status $question")

        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        val (r,g,b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)

        // textQuestion.setText(benderObj.askQuestion())
        // same as
        textQuestion.text = benderObj.askQuestion()
        sendBtn.setOnClickListener(this)
    }

    private fun makeSendOnActionDone(editText: EditText) {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) sendBtn.performClick()
            false
        }
    }

    override fun onClick(v:View?) {
        if (v?.id == R.id.iv_send) {
            if (isAnswerValid()) sendAnswer()
            else makeErrorMessage()
            hideKeyboard()
        }
    }

    private fun isAnswerValid(): Boolean {
        return benderObj.question.validate(textAnswer.text.toString())
    }

    private fun sendAnswer() {
        val (nextQuestion, color) = benderObj.listenAnswer(textAnswer.text.toString().toLowerCase())
        textAnswer.setText("")
        val(r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        textQuestion.text = nextQuestion
    }

    private fun makeErrorMessage() {
        val errorMessage = when (benderObj.question) {
            Question.NAME -> "Имя должно начинаться с заглавной буквы"
            Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
            Question.MATERIAL -> "Материал не должен содержать цифр"
            Question.BDAY -> "Год моего рождения должен содержать только цифры"
            Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
            else -> "На этом все, вопросов больше нет"
        }
        textQuestion.text = errorMessage + "\n" + benderObj.question.question
        textAnswer.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("STATUS", benderObj.status.name)
        outState?.putString("QUESTION", benderObj.question.name)
        Log.d("M_MainActivity", "onSaveInstanceState ${benderObj.status.name} ${benderObj.question.name}")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity", "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity", "onDestroy")
    }


}
