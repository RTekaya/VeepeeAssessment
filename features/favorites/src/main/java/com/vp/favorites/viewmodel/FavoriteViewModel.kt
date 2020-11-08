package com.vp.favorites.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vp.detail.VeepeeSharedPreferences


class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreference :VeepeeSharedPreferences = VeepeeSharedPreferences(application)
    val movieList = sharedPreference.getMoviesList()?.map { it.value }
}