package com.sevde.apideneme
import com.sevde.apideneme.R

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.android.volley.RequestQueue
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btn_noInput = findViewById(R.id.btn_noInput) as Button
        val btn_Input = findViewById(R.id.btn_Input) as Button
        val et_Search = findViewById(R.id.et_Search) as EditText

        btn_noInput.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = "https://www.omdbapi.com/?apikey=dad2aa7f&t=lord"


            val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { err ->
                    Toast.makeText(applicationContext, err.toString(), Toast.LENGTH_SHORT).show() })
            queue.add(stringRequest)
        }
        btn_Input.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
                val et_Search = findViewById(R.id.et_Search) as EditText // Bu satır sadece bir kez eklenmeli ve kodun yukarısında zaten var
                val searchTerm = et_Search.text.toString() // EditText'ten alınan metni değişkene atayın
                val url = "https://www.omdbapi.com/?apikey=dad2aa7f&t=$searchTerm" // URL'ye arama terimini ekleyin
            
                val stringRequest  = StringRequest(
                    Request.Method.GET, url,
                    Response.Listener <String> { response ->
                        Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener { err ->
                        Toast.makeText(applicationContext, err.toString(), Toast.LENGTH_SHORT).show()
                    })
                queue.add(stringRequest)
        }
    }
}