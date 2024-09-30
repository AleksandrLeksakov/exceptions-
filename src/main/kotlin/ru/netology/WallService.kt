package ru.netology

import Comment
import Post
import PostNotFoundException

class WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()

    fun add(post: Post): Post {
        posts += post.copy(id = posts.size + 1)
        return posts.last()
    }

    fun getById(id: Int): Post {
        return posts.find { it.id == id } ?: throw PostNotFoundException("Post $id not found")
    }

    fun update(post: Post): Boolean {
        val index = posts.indexOfFirst { it.id == post.id }
        if (index == -1) {
            return false
        }
        posts[index] = post.copy()
        return true
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        // Находим пост по ID
        val post = posts.find { it.id == postId }

        // Проверяем, существует ли пост
        if (post == null) {
            throw PostNotFoundException("Пост с ID $postId не найден")
        }

        // Добавляем комментарий в массив comments
        comments += comment.copy(id = comments.size + 1, postId = postId)
        return comment
    }
}