package com.mobile.eflores.ulassessment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mobile.eflores.livedataapi.util.BASE_URL
import com.mobile.eflores.livedataapi.util.INTENT_EXTRA_USERS
import com.mobile.eflores.livedataapi.util.LiveDataResult
import com.mobile.eflores.ulassessment.data.User
import com.mobile.eflores.ulassessment.data.UserDatasource
import com.mobile.eflores.ulassessment.data.UserService
import com.mobile.eflores.ulassessment.domain.GetUsers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerAdapter

    private lateinit var listUser: List<User>

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

    private val dataObserver = Observer<LiveDataResult<List<User>>> {
        // User data
        when (it?.status) {
            LiveDataResult.STATUS.ERROR -> {
                progressBar.visibility = View.GONE
                tvStatus.text = it.error?.message
            }
            LiveDataResult.STATUS.SUCCESS -> {
                listUser = it.data as List<User>
                adapter = RecyclerAdapter(listUser)
                recyclerView.adapter = adapter
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
        setContentView(R.layout.activity_main)

        fab.setOnClickListener(View.OnClickListener {
            try {
                startActivity(Intent(this, MapsActivity::class.java).apply {
                    putExtra(INTENT_EXTRA_USERS, listUser as Serializable)

                })
            } catch (e: UninitializedPropertyAccessException) {
                Toast.makeText(this, "No users to show", Toast.LENGTH_SHORT).show()
            }
        })

        this.mainViewModel.repositoriesLiveData.observe(this, this.dataObserver)

        this.mainViewModel.fetchUsers()

    }

}
