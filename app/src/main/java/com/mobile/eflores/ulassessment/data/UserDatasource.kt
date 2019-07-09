package com.mobile.eflores.ulassessment.data


class UserDatasource(private val userService: UserService) : UserRepository {

    override suspend fun fetchUsers() =
        this.userService.getMessages().execute().body() ?: throw NullPointerException("No body")

    override suspend fun fetchPosts(userId: Int) =
        this.userService.getPosts(userId).execute().body() ?: throw NullPointerException("No body")

    override suspend fun fetchComments(postId: Int) =
        this.userService.getComments(postId).execute().body() ?: throw NullPointerException("No body")

    override suspend fun fetchAlbums(userId: Int) =
        this.userService.getAlbums(userId).execute().body() ?: throw NullPointerException("No body")

    override suspend fun fetchPhotos(albumId: Int) =
        this.userService.getPhotos(albumId).execute().body() ?: throw NullPointerException("No body")

}