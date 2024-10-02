package ru.netology

import Comment
import CommentNotFoundException
import OwnerNotFoundException
import Post
import PostNotFoundException
import ReasonNotFoundException
import Report

class WallService {
    private val reasons = intArrayOf(0 ,1, 2, 3, 4, 5, 6, 7, 8)
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reports = emptyArray<Report>()

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
        comments += comment.copy (id = comments.size + 1, postId = postId)
        return comments.last()
    }

    fun reportComment(ownerId: Int, commentId: Int, reason: Int) :Int {
        // Ищщем коментарий по id
        val comment = comments.find {
            it.id == commentId
        }
        if (comment == null) {
            throw CommentNotFoundException("Комментарий с ID $commentId не найден")
        }
        if (comment.fromId != ownerId) {
            throw OwnerNotFoundException("Владелец с ID $ownerId не найден")
        }
        // Ищщем причину в списке причин жалоб
        val findReason = reasons.find {
            it == reason
        }
        if (findReason == null) {
            throw ReasonNotFoundException("Причина $reason не найдена")

        }
        // Добавляем жалобу в список жалоб
        reports += Report(
            id = reports.size + 1,
            postId = comment.postId,
            ownerId = ownerId,
            commentId =  commentId,
            reason = reason
        )
        return  1
    }
}

