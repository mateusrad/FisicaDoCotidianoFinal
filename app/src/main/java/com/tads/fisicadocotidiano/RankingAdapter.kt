package com.tads.fisicadocotidiano

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tads.fisicadocotidiano.databinding.ItemRankingBinding

class RankingAdapter(private val playerList: List<Ranking.Player>) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Ranking.Player) {
            binding.textViewPlayerName.text = player.name
            binding.textViewPlayerScore.text = player.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(playerList[position])
    }

    override fun getItemCount(): Int {
        return playerList.size
    }
}
