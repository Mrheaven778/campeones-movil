package com.example.proyecto_final

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity(){

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campeon)
          val btnCampeones = findViewById<Button>(R.id.button)
        btnCampeones.setOnClickListener { naverCampeones() }
    }

    private fun naverCampeones() {
       val intent = Intent(this, CampeonActivity::class.java)
        startActivity(intent)
    }

}