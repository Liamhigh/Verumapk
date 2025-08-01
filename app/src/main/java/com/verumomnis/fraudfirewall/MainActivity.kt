package com.verumomnis.fraudfirewall

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultBox: TextView
    private val PICK_FILE_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uploadBtn = findViewById<Button>(R.id.uploadBtn)
        val forensicBtn = findViewById<Button>(R.id.forensicBtn)
        resultBox = findViewById(R.id.resultBox)

        uploadBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_FILE_REQUEST_CODE)
        }

        forensicBtn.setOnClickListener {
            Toast.makeText(this, "Running forensic check... (placeholder)", Toast.LENGTH_SHORT).show()
            resultBox.text = "Analyzing document... (AI hook not yet implemented)"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedFile: Uri? = data?.data
            resultBox.text = "Selected: ${selectedFile?.lastPathSegment}"
        }
    }
}
