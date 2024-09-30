import ru.netology.WallService


data class Comment(
    val id: Int = 0,
    val postId: Int,
    val fromId: Int,
    val date: Long = System.currentTimeMillis() / 1000,
    val text: String,
    val likes: Int = 0
)

class PostNotFoundException(message: String) : RuntimeException(message)

data class Post(
    val id: Int = 0,
    val ownerId: Int,
    val date: Long = System.currentTimeMillis() / 1000,
    val text: String,
    val comments: MutableList<Comment> = mutableListOf(),
    val likes: Int = 0,
    val reposts: Int = 0,
    val views: Int = 0,
    val isPinned: Boolean = false,
    val isFavorite: Boolean = false,
    val attachments: MutableList<Attachment> = mutableListOf()
)

data class Attachment(
    val type: String, // Например, "photo", "video", "doc"
    val url: String,
    val previewUrl: String? = null, // Опциональный превью
    // ... другие поля, специфичные для типа вложения
)


fun main() {
    val service = WallService()

    // Создаем пост
    val post = Post(ownerId = 1, text = "Test post")
    val added = service.add(post)

    // Создаем комментарий к посту
    val comment = Comment(postId = added.id, fromId = 1, text = "Test comment")
    val createdComment = service.createComment(postId = added.id, comment = comment)

    println("Созданный комментарий: ${createdComment.text}")
}



