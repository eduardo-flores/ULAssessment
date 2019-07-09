package com.mobile.eflores.ulassessment.data


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface UserService {

    @GET("/users")
    fun getMessages(): Call<List<User>>

    @GET("/posts")
    fun getPosts(@Query("userId") page: Int): Call<List<Post>>

    @GET("/comments")
    fun getComments(@Query("postId") page: Int): Call<List<Comment>>

    @GET("/albums")
    fun getAlbums(@Query("userId") page: Int): Call<List<Album>>

    @GET("/photos")
    fun getPhotos(@Query("albumId") page: Int): Call<List<Photo>>

}