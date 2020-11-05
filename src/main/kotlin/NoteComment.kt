data class NoteComment(
    val id: Int = 0,
    val noteId: Int = 0,
    val ownerId: Int = 0,
    val replyTo: Int? = null,
    val message: String = "",
    val guid: String = "",
    val date: Int = 0
)