package com.example.movieapp.ui.categories_fragment.view

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.movieapp.databinding.CatigoriesFragmentBinding
import com.example.movieapp.ui.categories_fragment.viewmodel.CategoriesViewModel
import com.example.movieapp.ui.categories_fragment.adapter.CategoryAdapter
import com.example.movieapp.ui.categories_fragment.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoriesFragment : Fragment(), CategoryAdapter.OnCategoryInteract {

    private  val viewModel: CategoriesViewModel by viewModels()
    private lateinit var  adapter :CategoryAdapter
    private lateinit var binding : CatigoriesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = CatigoriesFragmentBinding.inflate(inflater,container,false)

        viewModel.category.observe(viewLifecycleOwner){
            binding.progressBar.visibility = View.GONE
            if(it.size==0){
                binding.errorMessage.visibility = View.VISIBLE
                binding.errorMessage.text = "empty list found \ncheck internet Connection..."
            }else{
                adapter = CategoryAdapter(it,this@CategoriesFragment)
                binding.rvCategoriesMain.adapter = adapter
            }

        }
        return binding.root
    }



    override fun onMovieClick(id: Long, pos: Int) {
        val action = CategoriesFragmentDirections.actionCategoriesFragmentToDetailsFragment(id)
        findNavController().navigate(action)
    }

    override fun onCategoryTitleClick(name: String, pos: Int) {

    }

    override fun loadMoreMovies(page: Int, pos: Int, nestedItemCount: Int, adapter:MovieAdapter) {
        Log.d("Count", "loadMoreMovies: noah call load more with count : $nestedItemCount")

        viewModel.loadMoreMovies(page,pos).observe(this, {

            adapter.addItems(it.movies)
        })

//        binding.rvCategoriesMain.getChildAt(viewModel.loadMoreMovies(page,pos))
//            .findViewById<RecyclerView>(R.id.rv_movies)
//            .adapter?.notifyItemChanged(nestedItemCount)

//        this.adapter.notifyItemChanged(pos)

    }

}