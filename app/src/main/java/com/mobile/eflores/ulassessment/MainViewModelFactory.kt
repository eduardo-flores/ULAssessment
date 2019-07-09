package com.mobile.eflores.ulassessment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.eflores.ulassessment.domain.GetUsers
import kotlinx.coroutines.CoroutineDispatcher


class MainViewModelFactory constructor(
    private val mainDispather: CoroutineDispatcher,
    private val ioDispatcher: CoroutineDispatcher,
    private val getUsers: GetUsers
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(mainDispather, ioDispatcher, getUsers) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}