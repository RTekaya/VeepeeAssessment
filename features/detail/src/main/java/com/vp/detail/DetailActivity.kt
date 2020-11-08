package com.vp.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.vp.detail.databinding.ActivityDetailBinding
import com.vp.detail.model.MovieDetail
import com.vp.detail.viewmodel.DetailsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity(), QueryProvider {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var movieDetail: MovieDetail
    private val detailViewModel: DetailsViewModel by lazy { ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = detailViewModel
        queryProvider = this
        binding.setLifecycleOwner(this)
        detailViewModel.fetchDetails()
        detailViewModel.title().observe(this, Observer {
            supportActionBar?.title = it
        })
        detailViewModel.details().observe(this, Observer { movieDetail = it })

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {


        R.id.star -> {
            if (item.isChecked) {
                item.isChecked = false
                item.setIcon(R.drawable.ic_star)
                detailViewModel.sharedPreference.removeValue(movieDetail.imdbID)


            } else {
                item.isChecked = true
                item.setIcon(android.R.drawable.star_off)
                detailViewModel.sharedPreference.save(movieDetail.imdbID, movieDetail)
            }

            true
        }


        else -> {

            super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun getMovieId(): String {
        return intent?.data?.getQueryParameter("imdbID") ?: run {
            throw IllegalStateException("You must provide movie id to display details")
        }
    }

    companion object {
        lateinit var queryProvider: QueryProvider
    }
}
