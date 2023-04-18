package com.example.practicaltest1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //create spinner
        val spinner : Spinner = findViewById(R.id.courseSelect)

        spinner.onItemSelectedListener = this

        spinner.adapter = ArrayAdapter.createFromResource(this,
            R.array.course_selection,
            android.R.layout.simple_spinner_item).also{ adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        val gradeButton: Button = findViewById<Button>(R.id.grade)

        gradeButton.setOnClickListener {

            gradeButton.isPressed = !gradeButton.isPressed

            val score = findViewById<EditText>(R.id.inputScore)

            val result = when{
                score.text.toString().toDouble() >= 80 -> "A"
                score.text.toString().toDouble() >= 75 -> "A-"
                score.text.toString().toDouble() >= 70 -> "B+"
                score.text.toString().toDouble() >= 65 -> "B"
                score.text.toString().toDouble() >= 60 -> "B-"
                score.text.toString().toDouble() >= 55 -> "C+"
                score.text.toString().toDouble() >= 50 -> "C"

                else -> {"D"}
            }

            findViewById<TextView>(R.id.finalGrade).text = getString(R.string.resultString, result)

        }

        findViewById<Button>(R.id.contactButton).setOnClickListener {
            startActivity(contactDepartment())
        }


    }

    private fun contactDepartment(): Intent {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("departmentOfExam@gmail.com"))
        emailIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Name : ${findViewById<EditText>(R.id.editName).text}\n"
                    + "Course : ${findViewById<Spinner>(R.id.courseSelect).selectedItem}\n"
                    + "Score : ${findViewById<EditText>(R.id.inputScore).text}\n"
                    + "Grade : ${findViewById<TextView>(R.id.finalGrade).text}"
        )

        return emailIntent

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id:Long){
        Toast.makeText(this, "Course selected is ${parent.getItemAtPosition(pos)}", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}