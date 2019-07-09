package com.mobile.eflores.ulassessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mobile.eflores.livedataapi.util.INTENT_EXTRA_USER
import com.mobile.eflores.livedataapi.util.LiveDataResult
import com.mobile.eflores.ulassessment.data.Post
import com.mobile.eflores.ulassessment.data.User
import com.mobile.eflores.ulassessment.data.UserDatasource
import com.mobile.eflores.ulassessment.data.UserService
import com.mobile.eflores.ulassessment.domain.GetUsers
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.mobile.eflores.livedataapi.util.BASE_URL

class UserActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerPostsAdapter

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

    private val dataObserver = Observer<LiveDataResult<List<Post>>> {
        // User data
        when (it?.status) {
            LiveDataResult.STATUS.ERROR -> {
                progressBar.visibility = View.GONE
                tvStatus.text = it.error?.message
            }
            LiveDataResult.STATUS.SUCCESS -> {
                adapter = RecyclerPostsAdapter(it.data as List<Post>)
                recyclerViewPosts.adapter = adapter
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
        setContentView(R.layout.activity_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get the Intent that started this activity and extract the string
        val user = intent.getSerializableExtra(INTENT_EXTRA_USER) as User

        this.mainViewModel.repositoriesLiveDataPosts.observe(this, this.dataObserver)

        this.mainViewModel.fetchPosts(user.id)

        userdetailName.text = user.name
        userdetailUsername.text = user.username
        userdetailEmail.text = user.email
        userdetailStreet.text = user.address.street
        userdetailSuite.text = user.address.suite
        userdetailCity.text = user.address.city
        userdetailZipcode.text = user.address.zipcode
        userdetailPhone.text = user.phone
        userdetailWebsite.text = user.website
        userdetailCompanyname.text = user.company.name
        userdetailCatchPhrase.text = user.company.catchPhrase
        userdetailBS.text = user.company.bs
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
