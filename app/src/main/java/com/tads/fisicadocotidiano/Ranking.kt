package com.tads.fisicadocotidiano

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tads.fisicadocotidiano.databinding.ActivityRankingBinding
import kotlin.collections.ArrayList

class Ranking : AppCompatActivity() {

    private lateinit var binding: ActivityRankingBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val rankingList = ArrayList<Player>()

    data class Player(val name: String, val score: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val playerName = intent.getStringExtra("login") ?: "Jogador"
        val playerScore = intent.getIntExtra("score", 0)
        val usuario = intent.getStringExtra("login")


        sharedPreferences = getSharedPreferences("ranking_prefs", MODE_PRIVATE)



        addPlayerToRanking(playerName, playerScore)


        loadRanking()



        binding.buttonVoltar.setOnClickListener {
            var intent = Intent(this, Escolha::class.java)
            intent.putExtra("login", usuario)
            startActivity(intent)
            finish()
        }


        binding.buttonCompartilhar.setOnClickListener {
            shareRankingOnWhatsApp()
        }

    }


    private fun addPlayerToRanking(playerName: String, playerScore: Int) {
        val editor = sharedPreferences.edit()
        val playerList = loadRankingFromPreferences()


        if (playerName != "Jogador" && playerScore != 0) {
            playerList.add(Player(playerName, playerScore))
        }


        playerList.sortByDescending { it.score }


        if (playerList.size > 10) {
            playerList.removeAt(playerList.size - 1)
        }


        val playerListString = playerList.joinToString(",") { "${it.name}:${it.score}" }
        editor.putString("ranking_list", playerListString)
        editor.apply()
    }

    private fun loadRanking() {
        val playerList = loadRankingFromPreferences()


        val adapter = RankingAdapter(playerList)
        binding.recyclerViewRanking.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRanking.adapter = adapter
    }

    private fun loadRankingFromPreferences(): ArrayList<Player> {
        val playerList = ArrayList<Player>()


        val rankingData = sharedPreferences.getString("ranking_list", "")
        if (!rankingData.isNullOrEmpty()) {
            val players = rankingData.split(",")
            for (playerData in players) {
                val playerInfo = playerData.split(":")
                if (playerInfo.size == 2) {
                    val name = playerInfo[0]
                    val score = playerInfo[1].toIntOrNull() ?: 0
                    playerList.add(Player(name, score))
                }
            }
        }

        return playerList
    }

    private fun shareRankingOnWhatsApp() {
        val playerName = intent.getStringExtra("login") ?: "Jogador"
        val playerScore = intent.getIntExtra("score", 0)

        val message =
            "Olá! Confira minha pontuação no Quiz da Física no Cotidiano: $playerName - $playerScore"

        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.setPackage("com.whatsapp")

        try {
            startActivity(sendIntent)
        } catch (ex: ActivityNotFoundException) {

            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://web.whatsapp.com"))
            startActivity(webIntent)

        }
    }


}
