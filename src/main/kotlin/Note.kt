data class Note(
    val id: Int = 0,
    val ownerId: Int = 0,
    val date: Int = 0,
    val title: String = "",
    val text: String = "",
    val privacyView: String = "all",
    val privacyComment: String = "all",
    val comments: Int = 0
)