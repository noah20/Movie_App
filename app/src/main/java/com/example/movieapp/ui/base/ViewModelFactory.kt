package com.example.movieapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.data.api.ApiHelper
import com.example.movieapp.data.repository.MainRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {



    override fun <T : ViewModel> create(modelClass: Class<T>): T {

//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            return MainViewModel(MainRepository(apiHelper)) as T
//        }
        throw IllegalArgumentException("Unknown class name")
    }

}