package com.mobile.eflores.ulassessment.data


interface UserRepository {

    suspend fun fetchUsers() : List<User>

    suspend fun fetchPosts(userId: Int) : List<Post>

    suspend fun fetchComments(postId: Int) : List<Comment>

    suspend fun fetchAlbums(userId: Int) : List<Album>

    suspend fun fetchPhotos(albumId: Int) : List<Photo>

}