package com.mobile.eflores.ulassessment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mobile.eflores.livedataapi.util.BASE_URL
import com.mobile.eflores.livedataapi.util.INTENT_EXTRA_POST
import com.mobile.eflores.livedataapi.util.LiveDataResult
import com.mobile.eflores.ulassessment.data.Comment
import com.mobile.eflores.ulassessment.data.Post
import com.mobile.eflores.ulassessment.data.UserDatasource
import com.mobile.eflores.ulassessment.data.UserService
import com.mobile.eflores.ulassessment.domain.GetUsers
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerCommentsAdapter

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

    private val dataObserver = Observer<LiveDataResult<List<Comment>>> {
        // User data
        when (it?.status) {
            LiveDataResult.STATUS.ERROR -> {
                progressBar.visibility = View.GONE
                tvStatus.text = it.error?.message
            }
            LiveDataResult.STATUS.SUCCESS -> {
                adapter = RecyclerCommentsAdapter(it.data as List<Comment>)
                recyclerViewComments.adapter = adapter
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
        setContentView(R.layout.activity_post)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val post = intent.getSerializableExtra(INTENT_EXTRA_POST) as Post

        this.mainViewModel.repositoriesLiveDataComments.observe(this, this.dataObserver)

        this.mainViewModel.fetchComments(post.id)

        postTitle.text = post.title
        postBody.text = post.body
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
