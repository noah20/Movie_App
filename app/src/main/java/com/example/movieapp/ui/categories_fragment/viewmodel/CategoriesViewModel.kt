package com.example.movieapp.ui.categories_fragment.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.movieapp.data.model.CategoryResponse
import com.example.movieapp.data.model.Movies
import com.example.movieapp.data.repository.MainRepository
import com.example.movieapp.utils.Resource
import com.example.movieapp.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val restApi: MainRepository) : ViewModel() {


    private val _category = MutableLiveData<ArrayList<CategoryResponse?>>()
    val category: LiveData<ArrayList<CategoryResponse?>> = _category
    private var categoriesList: ArrayList<CategoryResponse?> = ArrayList()

    private val _genresList = arrayOf("popular", "top_rated", "upcoming", "now_playing")


    init {

        loadByAsync()

    }


    private fun loadByAsync() {

        viewModelScope.launch {
            val parent = async {
                for (cat in _genresList) {
                    launch {
                        val response = withContext(Dispatchers.Default) {

                            try {
                                Resource.success(data = restApi.getCategory(cat))
                            } catch (exception: Exception) {
                                Resource.error(
                                    data = null,
                                    msg = exception.message ?: "Error Occurred!"
                                )
                            }
                        }

                        when (response.status) {
                            Status.SUCCESS -> {
                                response.data?.let {
                                    it.categoryName = cat.replace("_", " ")
                                    categoriesList.add(it)
                                }
                            }
                            Status.ERROR -> {
                                Log.d("ERROR_", "onCreateView: noah here -> ${response.message}")
                            }
                            Status.LOADING -> {
                                Log.d(
                                    "ERORr",
                                    "onCreateView: noah loading here -> ${response.message}"
                                )
                            }
                        }

                    }
                }
            }

            parent.await().let {
                _category.value = categoriesList
            }

        }
    }

    fun loadMoreMovies(page: Int, pos: Int): LiveData<CategoryResponse> {

        val stateFlow = MutableStateFlow<CategoryResponse?>(CategoryResponse(1, emptyList<Movies>(),0,0,""))


        var liveData = MutableLiveData<CategoryResponse>()


        viewModelScope.launch {
            val response = withContext(Dispatchers.Default) {
                try {
                    Resource.success(restApi.loadMoreMoviesForCategory(categoriesList[pos]!!.categoryName.replace(" ", "_"), page)

                    )
                } catch (e: Exception) {
                    Resource.error(
                        data = null,
                        msg = e.message ?: "Error Occurred!"
                    )
                }
            }

            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let {
                        liveData.value = it
                        Log.d(
                            "CategoriesViewModel",
                            "onCreateView: noah here  load more success new list-> ${categoriesList[pos]?.movies?.size}"
                        )
                    }
                }
                Status.ERROR -> {
                    Log.d("ERROR_", "onCreateView: noah here -> ${response.message}")
                }
                Status.LOADING -> {
                    Log.d(
                        "ERORr",
                        "onCreateView: noah loading here -> ${response.message}"
                    )
                }
            }
            Log.d("new", "loadMoreMovies: -> ${categoriesList[pos]?.movies?.size} ")
//            stateFlow.emit(pos)
        }

        return liveData
    }
}