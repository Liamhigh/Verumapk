package com.verumomnis.fraudfirewall

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream
import java.security.MessageDigest

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
            Toast.makeText(this, "Please upload a file first.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val fileUri: Uri? = data?.data
            if (fileUri != null) {
                val hash = computeSha512(fileUri)
                resultBox.text = "\n✔ File Selected\nSHA-512:\n$hash"
            } else {
                resultBox.text = "Failed to read file."
            }
        }
    }

    private fun computeSha512(uri: Uri): String {
        val md = MessageDigest.getInstance("SHA-512")
        contentResolver.openInputStream(uri)?.use { stream ->
            val buffer = ByteArray(8192)
            var bytesRead: Int
            while (stream.read(buffer).also { bytesRead = it } != -1) {
                md.update(buffer, 0, bytesRead)
            }
        }
        return md.digest().joinToString("") { "%02x".format(it) }
    }
}
