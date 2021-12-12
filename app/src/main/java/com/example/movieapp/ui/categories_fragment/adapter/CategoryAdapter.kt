package com.example.movieapp.ui.categories_fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.model.CategoryResponse
import com.example.movieapp.databinding.RvCategoryItemBinding

class CategoryAdapter(private val categoryArr: ArrayList<CategoryResponse?>, val listener: OnCategoryInteract): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_category_item,parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val temp  = categoryArr[position]
        val movieAdapter = MovieAdapter(temp!!.movies,object : OnMovieClickListener {
            override fun onMovieClick(id: Long, position: Int) {
                listener.onMovieClick(id,position)
            }
        })
        holder.binding.rvMovies.adapter = movieAdapter
        holder.binding.textView.text = temp.categoryName
    }

    override fun getItemCount(): Int {
        return categoryArr.size
    }


    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val binding = RvCategoryItemBinding.bind(itemView)

        private  var layoutMgr: LinearLayoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.HORIZONTAL,false)
        private var moviesPage = 1

        init {
            binding.rvMovies.layoutManager = layoutMgr
            binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val totalItemCount = layoutMgr.itemCount
                    val visibleItemCount = layoutMgr.childCount
                    val firstVisibleItem = layoutMgr.findFirstVisibleItemPosition()

                    if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                       // binding.rvMovies.removeOnScrollListener(this)
                        moviesPage++
                        loadMoreMovies(moviesPage,totalItemCount)
                    }

                }
            })
        }


        private fun loadMoreMovies(page:Int,nestedItemCount:Int) {
            Log.d("category", "loadMoreMovies: noah here willing god -> $page")
            listener.loadMoreMovies(page,adapterPosition,nestedItemCount)
        }

    }





    interface OnCategoryInteract{
        fun onMovieClick(id:Long,pos:Int)
        fun onCategoryTitleClick(name:String ,pos:Int)
        fun loadMoreMovies(page: Int, pos: Int, nestedItemCount: Int)
    }

}

