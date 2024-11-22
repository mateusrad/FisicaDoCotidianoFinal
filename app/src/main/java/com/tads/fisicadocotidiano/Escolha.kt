package com.tads.fisicadocotidiano

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tads.fisicadocotidiano.databinding.ActivityEscolhaBinding
import com.tads.fisicadocotidiano.databinding.ActivityHomeBinding

class Escolha : AppCompatActivity() {
    private lateinit var binding: ActivityEscolhaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEscolhaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        val usuario = intent.getStringExtra("login")

        binding.bemvindoEscolha.setText("Olá, $usuario")

        binding.buttonVoltar.setOnClickListener{
            var intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonQuiz.setOnClickListener{
            var intent = Intent(this, Perguntas::class.java)
            intent.putExtra("login", usuario)
            startActivity(intent)
            finish()
        }

        binding.buttonRanking.setOnClickListener{
            var intent = Intent(this, Ranking::class.java)
            intent.putExtra("login", usuario)
            startActivity(intent)

        }
    }
 }