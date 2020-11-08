package com.vp.detail

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vp.detail.model.MovieDetail

class VeepeeSharedPreferences(private val context: Context) {

    private val gson = Gson()

    companion object {
        private const val KEY_LIST = "favoriteLsit"
        private const val PREFERENCES_NAME = "veepeeSharedPreferences"
        private var map: HashMap<String, MovieDetail>? = HashMap<String, MovieDetail>()
        private fun getSharedPref(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        }
    }


    fun save(movieId: String, myObject: MovieDetail) {
        map?.set(movieId, myObject)
        editListOfMovie(getSharedPref(context).edit())
    }

    fun getValueMovie(movieId: String): MovieDetail? {
        if (map?.size == 0)
            map = getMoviesList()
        return map?.get(movieId)
    }

    fun getMoviesList(): HashMap<String, MovieDetail>? {
        val json = getSharedPref(context).getString(KEY_LIST, null)
        val turnsType = object : TypeToken<HashMap<String, MovieDetail>>() {}.type
        if (json != null)
            map = Gson().fromJson<HashMap<String, MovieDetail>>(json, turnsType)
        if (map == null) {
            map = HashMap()
        }
        return map
    }

    fun removeValue(KEY_NAME: String) {
        map?.remove(KEY_NAME)
        editListOfMovie(getSharedPref(context).edit())
    }

    private fun editListOfMovie(editor: SharedPreferences.Editor) {
        val jsonList = gson.toJson(map)
        editor.putString(KEY_LIST, jsonList)
        editor.commit()
    }


}