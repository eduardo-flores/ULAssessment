package com.mobile.eflores.ulassessment.domain

import com.mobile.eflores.ulassessment.data.*

class GetUsers(private val userRepository: UserRepository) {
    suspend fun executeUser(): List<User> {
        return this.userRepository.fetchUsers()
    }

    suspend fun executePost(userId: Int): List<Post> {
        return this.userRepository.fetchPosts(userId)
    }

    suspend fun executeComment(postId: Int): List<Comment> {
        return this.userRepository.fetchComments(postId)
    }

    suspend fun executeAlbum(userId: Int): List<Album> {
        return this.userRepository.fetchAlbums(userId)
    }

    suspend fun executePhoto(albumId: Int): List<Photo> {
        return this.userRepository.fetchPhotos(albumId)
    }
}