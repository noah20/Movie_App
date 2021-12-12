package com.example.movieapp.ui.details_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.Genre
import com.example.movieapp.databinding.RvGenreItemBinding

class GenresAdapter (private val genres : List<Genre>): RecyclerView.Adapter<GenresAdapter.GenresViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        return GenresViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_genre_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.binding.root.text = genres[position].name
    }

    override fun getItemCount(): Int {
        return genres.size
    }

     inner class GenresViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val binding :RvGenreItemBinding = RvGenreItemBinding.bind(itemView)
    }
}