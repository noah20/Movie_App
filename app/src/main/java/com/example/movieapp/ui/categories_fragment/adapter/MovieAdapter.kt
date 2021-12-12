package com.example.movieapp.ui.categories_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.Movies
import com.example.movieapp.databinding.RvMovieItemBinding
import com.example.movieapp.utils.createImageUrl

class MovieAdapter (private val moviesListL : List<Movies?> , val listener : OnMovieClickListener) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_movie_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val temp = moviesListL[position]

        holder.bind.tvMovieTitle.text = temp?.title
        Glide.with(holder.bind.root.context)
            .load(createImageUrl(temp!!.poster_path))
            .into(holder.bind.ivMoviePoster)

    }

    override fun getItemCount(): Int {
        return moviesListL.size
    }

  inner  class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bind = RvMovieItemBinding.bind(itemView)

      init {
          bind.root.setOnClickListener(){
              listener.onMovieClick(moviesListL[adapterPosition]!!.id,adapterPosition)
          }
      }
    }


}

interface OnMovieClickListener {
    fun onMovieClick(id:Long,position:Int)
}