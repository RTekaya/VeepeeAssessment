package com.vp.favorites

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.vp.detail.model.MovieDetail
import com.vp.favorites.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_favorite.favorite_recycler_view as recyclerView

class FavoritesActivity : AppCompatActivity(), FavoritesAdapter.OnItemClickListener {

    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        initViewModel()
        getFavouritesMovies()?.let { populateList(it) }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }

    private fun getFavouritesMovies(): List<MovieDetail>? {
        return viewModel.movieList
    }


    private fun populateList(movies: List<MovieDetail>) {
        val adapter = FavoritesAdapter()
        adapter.setItems(movies)
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = GridLayoutManager(this,
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3)
        recyclerView.layoutManager = linearLayoutManager
    }

    override fun onItemClick(imdbID: String?) {
        val uri = Uri.parse("app://movies/detail?imdbID=$imdbID")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage(packageName)
        startActivity(intent)
    }

}