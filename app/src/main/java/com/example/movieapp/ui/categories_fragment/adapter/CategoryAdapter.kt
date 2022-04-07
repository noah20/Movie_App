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

class CategoryAdapter(
    private val categoryArr: ArrayList<CategoryResponse?>,
    val listener: OnCategoryInteract
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val _categoryArr = ArrayList<CategoryResponse?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val temp = categoryArr[position]

        holder.movieAdapter.addItems(temp!!.movies)
        holder.binding.textView.text = temp.categoryName
    }

    override fun getItemCount(): Int {
        return categoryArr.size
    }


    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RvCategoryItemBinding.bind(itemView)

        val movieAdapter = MovieAdapter(object : OnMovieClickListener {
            override fun onMovieClick(id: Long, position: Int) {
                listener.onMovieClick(id, position)
            }
        })

        private var layoutMgr: LinearLayoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        private var moviesPage = 1

        init {
            binding.rvMovies.layoutManager = layoutMgr
            binding.rvMovies.adapter = movieAdapter

            binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                var totalItemCount = 0
                var visibleItemCount = 0
                var firstVisibleItem = 0

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(dx>0){
                        totalItemCount = layoutMgr.itemCount
                        visibleItemCount = layoutMgr.childCount
                        firstVisibleItem = layoutMgr.findFirstVisibleItemPosition()
//                    val firstVisibleItem = layoutMgr.findLastVisibleItemPosition()

                        Log.d("category", "loadMoreMovies: noah firstVisible:$firstVisibleItem + visible:$visibleItemCount * items:$totalItemCount-> ")
                        if (!movieAdapter.isLoading &&(firstVisibleItem + visibleItemCount) == totalItemCount) {
                            // binding.rvMovies.removeOnScrollListener(this)
                                movieAdapter.isLoading = true
                            moviesPage++
                            loadMoreMovies(moviesPage, totalItemCount)

                        }
                    }


                }
            })
        }


        private fun loadMoreMovies(page: Int, nestedItemCount: Int) {
            listener.loadMoreMovies(page, adapterPosition, nestedItemCount, movieAdapter)
        }

    }


    interface OnCategoryInteract {
        fun onMovieClick(id: Long, pos: Int)
        fun onCategoryTitleClick(name: String, pos: Int)
        fun loadMoreMovies(page: Int, pos: Int, nestedItemCount: Int, adapter: MovieAdapter)
    }

}

