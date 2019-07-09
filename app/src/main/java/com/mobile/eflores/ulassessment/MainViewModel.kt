package com.mobile.eflores.ulassessment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.eflores.livedataapi.util.LiveDataResult
import com.mobile.eflores.ulassessment.data.*
import com.mobile.eflores.ulassessment.domain.GetUsers
import kotlinx.coroutines.*


class MainViewModel(
    mainDispacher : CoroutineDispatcher,
    ioDispacher : CoroutineDispatcher,
    private val getUsers: GetUsers
) : ViewModel() {

    private val job = SupervisorJob()

    val repositoriesLiveData = MutableLiveData<LiveDataResult<List<User>>>()

    val repositoriesLiveDataPosts = MutableLiveData<LiveDataResult<List<Post>>>()

    val repositoriesLiveDataComments = MutableLiveData<LiveDataResult<List<Comment>>>()

    val repositoriesLiveDataAlbums = MutableLiveData<LiveDataResult<List<Album>>>()

    val repositoriesLiveDataPhotos = MutableLiveData<LiveDataResult<List<Photo>>>()

    private val uiScope = CoroutineScope(mainDispacher + job)

    private val ioScope = CoroutineScope(ioDispacher + job)

    fun fetchUsers() {

        uiScope.launch {
            repositoriesLiveData.value = LiveDataResult.loading()

            try {
                val data = ioScope.async {
                    return@async getUsers.executeUser()
                }.await()

                repositoriesLiveData.value = LiveDataResult.success(data)
            } catch (e: Exception) {
                repositoriesLiveData.value = LiveDataResult.error(e)
            }


        }

    }

    fun fetchPosts(userId: Int) {

        uiScope.launch {
            repositoriesLiveDataPosts.value = LiveDataResult.loading()

            try {
                val data = ioScope.async {
                    return@async getUsers.executePost(userId)
                }.await()

                repositoriesLiveDataPosts.value = LiveDataResult.success(data)
            } catch (e: Exception) {
                repositoriesLiveDataPosts.value = LiveDataResult.error(e)
            }


        }

    }

    fun fetchComments(postId: Int) {

        uiScope.launch {
            repositoriesLiveDataComments.value = LiveDataResult.loading()

            try {
                val data = ioScope.async {
                    return@async getUsers.executeComment(postId)
                }.await()

                repositoriesLiveDataComments.value = LiveDataResult.success(data)
            } catch (e: Exception) {
                repositoriesLiveDataComments.value = LiveDataResult.error(e)
            }


        }

    }


    fun fetchAlbums(userId: Int) {

        uiScope.launch {
            repositoriesLiveDataAlbums.value = LiveDataResult.loading()

            try {
                val data = ioScope.async {
                    return@async getUsers.executeAlbum(userId)
                }.await()

                repositoriesLiveDataAlbums.value = LiveDataResult.success(data)
            } catch (e: Exception) {
                repositoriesLiveDataAlbums.value = LiveDataResult.error(e)
            }


        }

    }

    fun fetchPhotos(albumId: Int) {

        uiScope.launch {
            repositoriesLiveDataPhotos.value = LiveDataResult.loading()

            try {
                val data = ioScope.async {
                    return@async getUsers.executePhoto(albumId)
                }.await()

                repositoriesLiveDataPhotos.value = LiveDataResult.success(data)
            } catch (e: Exception) {
                repositoriesLiveDataPhotos.value = LiveDataResult.error(e)
            }


        }

    }

    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
    }

}