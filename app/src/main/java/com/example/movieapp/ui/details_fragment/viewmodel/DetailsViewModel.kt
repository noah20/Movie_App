package com.example.movieapp.ui.details_fragment.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.movieapp.data.api.MovieRestApi
import com.example.movieapp.data.model.MoviesDetailsResponse
import com.example.movieapp.data.repository.MainRepository
import com.example.movieapp.utils.Resource
import com.example.movieapp.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(private val restApi : MainRepository): ViewModel() {

//    private val _movieDetails = MutableLiveData<MoviesDetailsResponse>()
//    val movieDetails : LiveData<MoviesDetailsResponse> = _movieDetails
//
//    fun getMovieDetails(id:Long){
//        viewModelScope.launch {
//
//            val response = async {
//                restApi.getMovieDetails(id)
//            }
//
//            response.await()?.let {
//                _movieDetails.value = it
//            }
//
//        }
//    }


    fun getMovieDetails(id:Long) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = restApi.getMovieDetails(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
        }
    }

}