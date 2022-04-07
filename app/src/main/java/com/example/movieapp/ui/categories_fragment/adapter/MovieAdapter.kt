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

class MovieAdapter ( val listener : OnMovieClickListener) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val _movieList = ArrayList<Movies?>()

    var isLoading = false;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_movie_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val temp = _movieList[position]

        holder.bind.tvMovieTitle.text = temp?.title
        Glide.with(holder.bind.root.context)
            .load(createImageUrl(temp!!.poster_path))
            .into(holder.bind.ivMoviePoster)

    }

    fun addItems(list :List<Movies?>){
        val oldCount =itemCount
        _movieList.addAll(list)
        notifyItemRangeInserted(oldCount,list.size)
        isLoading = false
    }

    override fun getItemCount(): Int {
        return _movieList.size
    }

  inner  class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bind = RvMovieItemBinding.bind(itemView)

      init {
          bind.root.setOnClickListener(){
              listener.onMovieClick(_movieList[adapterPosition]!!.id,adapterPosition)
          }
      }
    }


}

interface OnMovieClickListener {
    fun onMovieClick(id:Long,position:Int)
}