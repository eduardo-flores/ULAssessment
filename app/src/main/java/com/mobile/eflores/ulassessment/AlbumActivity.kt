package com.mobile.eflores.ulassessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mobile.eflores.livedataapi.util.BASE_URL
import com.mobile.eflores.livedataapi.util.INTENT_EXTRA_ALBUM
import com.mobile.eflores.livedataapi.util.INTENT_EXTRA_POST
import com.mobile.eflores.livedataapi.util.LiveDataResult
import com.mobile.eflores.ulassessment.data.*
import com.mobile.eflores.ulassessment.domain.GetUsers
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.squareup.picasso.Picasso

class AlbumActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerPhotosAdapter

    private val userService: UserService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(UserService::class.java)
    }

    private val mainViewModelFactory: MainViewModelFactory =
        MainViewModelFactory(Dispatchers.Main, Dispatchers.IO, GetUsers(UserDatasource(this.userService)))

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
    }

    private val dataObserver = Observer<LiveDataResult<List<Photo>>> {
        // User data
        when (it?.status) {
            LiveDataResult.STATUS.ERROR -> {
                progressBar.visibility = View.GONE
                tvStatus.text = it.error?.message
            }
            LiveDataResult.STATUS.SUCCESS -> {
                adapter = RecyclerPhotosAdapter(it.data as List<Photo>)
                recyclerViewPhotos.adapter = adapter
                tvStatus.text = ""
                progressBar.visibility = View.GONE
            }
            LiveDataResult.STATUS.LOADING -> {
                tvStatus.text = "LOADING"
                progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val album = intent.getSerializableExtra(INTENT_EXTRA_ALBUM) as Album

        this.mainViewModel.repositoriesLiveDataPhotos.observe(this, this.dataObserver)

        this.mainViewModel.fetchPhotos(album.id)

        tvTitle.text = album.title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
