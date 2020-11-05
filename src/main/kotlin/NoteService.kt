import java.lang.RuntimeException

class NoteNotFoundException(message: String) : RuntimeException(message)
class CommentNotFoundException(message: String) : RuntimeException(message)

object NoteService {
    var id = 0
    var commentId = 0
    var notes = mutableListOf<Note>()
    var noteComments = mutableListOf<NoteComment>()
    var deletedComments = mutableListOf<NoteComment>()

    fun add(note: Note): Int {
        notes.add(note.copy(id = id++))
        return notes.last().id
    }

    fun delete(note: Note): Boolean {
        val target = notes.find { it.id == note.id }
        if (target == null) {
            throw NoteNotFoundException("Заметка с указанным id '${note.id}' не найдена")
        } else {
            notes.remove(target)
            noteComments.removeAll { it.noteId == target.id }
        }
        return true
    }

    fun edit(note: Note): Boolean {
        val indexOfOld = notes.indexOfFirst { it.id == note.id }
        val old = notes.getOrElse(indexOfOld) { throw NoteNotFoundException("Заметка с указанным id '${note.id}' не найдена") }
        notes[indexOfOld] = note.copy(id = old.id, ownerId = old.ownerId)
        return true
    }

    fun get(vararg note: Note): MutableList<Note> {
        val getNoteList = mutableListOf<Note>()
        for (getNote in note) {
            val target = notes.find { it.id == getNote.id }
            if (target == null) throw NoteNotFoundException("Заметка с указанным id '${getNote.id}' не найдена") else getNoteList.add(target)
            getNoteList.sortBy { it.date }
        }
        return getNoteList
    }

    fun getById(id: Int): Note? {
        for (getNote in notes) {
            val target = notes.find { it.id == id }
            if (target == null) throw NoteNotFoundException("Заметка с указанным id '$id' не найдена") else return target
        }
        return null
    }

    fun createComment(noteComment: NoteComment): Int {
        val indexOfPost = notes.indexOfFirst { it.id == noteComment.noteId }
        val old = notes.getOrElse(indexOfPost) { throw NoteNotFoundException("Заметка с указанным id '${noteComment.noteId}' не найдена") }
        noteComments.add(noteComment.copy(id = commentId++))
        notes[indexOfPost] = old.copy(comments = old.comments + 1)
        return noteComments.last().id
    }

    fun deleteComment(noteComment: NoteComment): Boolean {
        val target = noteComments.find { it.id == noteComment.id }
        return if (target == null) {
            throw CommentNotFoundException("Комментарий с указанным id '${noteComment.id}' не найден")
        } else {
            noteComments.remove(target)
            deletedComments.add(target)
        }
    }

    fun editComment(noteComment: NoteComment): Boolean {
        val indexOfOld = noteComments.indexOfFirst { it.id == noteComment.id }
        val old = notes.getOrElse(indexOfOld) { throw CommentNotFoundException("Комментарий с указанным id '${noteComment.id}' не найден") }
        noteComments[indexOfOld] = noteComment.copy(id = old.id, ownerId = old.ownerId)
        return true
    }

    fun getComment(vararg noteComment: NoteComment): MutableList<NoteComment> {
        val getNoteCommentList = mutableListOf<NoteComment>()
        for (getNoteComment in noteComment) {
            val target = noteComments.find { it.id == getNoteComment.id }
            if (target == null) throw CommentNotFoundException("Комментарий с указанным id '${getNoteComment.id}' не найден") else getNoteCommentList.add(target)
            getNoteCommentList.sortBy { it.date }
        }
        return getNoteCommentList
    }

    fun restoreComment(id: Int): Boolean {
        val target = deletedComments.find { it.id == id }
        if (target == null) {
            throw CommentNotFoundException("Комментарий с указанным id '$id' в числе удаленных не найден")
        } else {
            noteComments.add(target)
            deletedComments.remove(target)
            noteComments.sortBy { it.id }
            return true
        }
    }
}
