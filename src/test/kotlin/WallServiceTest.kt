
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.netology.WallService
import kotlin.test.assertFailsWith

class WallServiceTest {
    private val service = WallService()

    @Test
    fun shouldAddCommentToExistingPost() {
        // 1. Создаем пост
        val post = Post(ownerId = 1, text = "Test post")
        val addedPost = service.add(post)

        // 2. Создаем комментарий
        val comment = Comment(postId = addedPost.id, fromId = 1, text = "Test comment")
        // 3. Добавляем комментарий к посту
        val createdComment = service.createComment(postId = addedPost.id, comment = comment)
        // 4. Проверяем, что комментарий был добавлен
        assertEquals(createdComment.id, 1)
    }

    @Test
    fun shouldThrowExceptionWhenAddingCommentToNonExistingPost() {
        // 1. Создаем комментарий к несуществующему посту
        val comment = Comment(postId = 100, fromId = 1, text = "Test comment")

        // 2. Проверяем, что функция выкидывает исключение
        assertFailsWith<PostNotFoundException> { service.createComment(postId = 100, comment = comment) }
    }

    @Test
    fun shouldSendReportToExistingComment() {
        val post = Post(ownerId = 1, text = "Test post")
        val addedPost = service.add(post)
        val comment = Comment(postId = addedPost.id, fromId = 1, text = "Test comment")
        // 3. Добавляем комментарий к посту
        val createdComment = service.createComment(postId = addedPost.id, comment = comment)
        val reportResult = service.reportComment(
            ownerId = createdComment.fromId,
            commentId = createdComment.id,
            reason = 0
        )
        assertEquals(reportResult, 1)
    }

    @Test
    fun shouldThrowExceptionWhenReportReasonIsNotFound() {
        val post = Post(ownerId = 1, text = "Test post")
        val addedPost = service.add(post)
        val comment = Comment(postId = addedPost.id, fromId = 1, text = "Test comment")
        // 3. Добавляем комментарий к посту
        val createdComment = service.createComment(postId = addedPost.id, comment = comment)
        assertFailsWith<ReasonNotFoundException> {
            service.reportComment(
                ownerId = createdComment.fromId,
                commentId = createdComment.id,
                reason = 10
            )
        }
    }

    @Test
    fun shouldThrowExceptionWhenReportCommentIdIsNotFound() {
        val post = Post(ownerId = 1, text = "Test post")
        val addedPost = service.add(post)
        val comment = Comment(postId = addedPost.id, fromId = 1, text = "Test comment")
        // 3. Добавляем комментарий к посту
        val createdComment = service.createComment(postId = addedPost.id, comment = comment)
        assertFailsWith<CommentNotFoundException> {
            service.reportComment(
                ownerId = createdComment.fromId,
                commentId = 100,
                reason = 0
            )
        }
    }

    @Test
    fun shouldThrowExceptionWhenReportOwnerIdIsNotFound() {
        val post = Post(ownerId = 1, text = "Test post")
        val addedPost = service.add(post)
        val comment = Comment(postId = addedPost.id, fromId = 1, text = "Test comment")
        // 3. Добавляем комментарий к посту
        val createdComment = service.createComment(postId = addedPost.id, comment = comment)
        assertFailsWith<OwnerNotFoundException> {
            service.reportComment(
                ownerId = 100,
                commentId = createdComment.id,
                reason = 0
            )
        }
    }
}
