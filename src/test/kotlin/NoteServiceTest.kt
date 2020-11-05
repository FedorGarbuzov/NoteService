import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun add() {
        NoteService.add(Note())
    }

    @Test(expected = NoteNotFoundException::class)
    fun shouldThrowNoteNotFoundException() {
        NoteService.delete(Note(id = 7))
    }

    @Test
    fun delete() {
        NoteService.add(Note())
        NoteService.createComment(NoteComment())
        NoteService.delete(Note())
    }

    @Test
    fun edit() {
        NoteService.edit(Note(id = 1))
    }

    @Test
    fun get() {
        NoteService.get(Note(id = 1))
    }

//    @Test(expected = NoteNotFoundException::class)
//    fun shouldThrow() {
//        NoteService.getById(1)
//    }

    @Test
    fun getById() {
        NoteService.getById(1)
    }

    @Test
    fun createComment() {
        NoteService.add(Note())
        NoteService.createComment(NoteComment(noteId = 1))
    }

    @Test
    fun deleteComment() {
        NoteService.deleteComment(NoteComment(id = 2))
    }

    @Test(expected = CommentNotFoundException::class)
    fun shouldThrow() {
        NoteService.deleteComment(NoteComment())
    }

    @Test
    fun editComment() {
        NoteService.add(Note())
        NoteService.createComment(NoteComment())
        NoteService.editComment(NoteComment())
    }

    @Test
    fun getComment() {
        NoteService.add(Note())
        NoteService.createComment(NoteComment(noteId = 1))
        NoteService.getComment(NoteComment(id = 3))
    }

    @Test(expected = CommentNotFoundException::class)
    fun shouldThrowExeption() {
        NoteService.restoreComment(9)
    }

    @Test
    fun restoreComment() {
        NoteService.add(Note())
        NoteService.createComment(NoteComment(noteId = 1))
        NoteService.deleteComment(NoteComment(id = 2))
        NoteService.restoreComment(2)
    }
}