package com.tads.fisicadocotidiano

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tads.fisicadocotidiano.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        binding.buttonHomeComecar.setOnClickListener{
            val usuario = binding.loginUsuario.text.toString()
            if (usuario.isNotEmpty()) {
                var intent = Intent(this, Escolha::class.java)
                intent.putExtra("login", usuario)
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(this, "Digite um nome de usuário!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}