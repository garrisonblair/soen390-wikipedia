package org.wikipedia.notebook;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.notebook.database.NoteDao;
import org.wikipedia.notebook.database.NoteEntity;
import org.wikipedia.notebook.database.ReferenceDao;
import org.wikipedia.notebook.database.ReferenceEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.wikipedia.notebook.NoteReferenceService.DeleteNoteCallBack;
import static org.wikipedia.notebook.NoteReferenceService.GetNotesCallback;
import static org.wikipedia.notebook.NoteReferenceService.SetCommentCallBack;
import static org.wikipedia.notebook.NoteReferenceService.UpdateNoteTextCallBack;

/**
 * Created by Andres on 2018-03-10.
 */

@RunWith(RobolectricTestRunner.class)
public class NoteReferenceServiceTest {

    private AppDatabase dbMock = mock(AppDatabase.class);
    private NoteDao noteDaoMock = mock(NoteDao.class);
    private ReferenceDao referenceDaoMock = mock(ReferenceDao.class);
    private Context contextMock = mock(Context.class);


    private NoteReferenceService nrs;

    @Before
    public void setupTests() {
        when(dbMock.referenceDao()).thenReturn(this.referenceDaoMock);
        when(dbMock.noteDao()).thenReturn(this.noteDaoMock);
        NoteReferenceService service = new NoteReferenceService(contextMock, dbMock);
        nrs = spy(service);
    }

    @Test
    public void deleteNoteTest() {
        DeleteNoteCallBack callBack = mock(DeleteNoteCallBack.class);
        Note noteMock = mock(Note.class);
        when(noteMock.getId()).thenReturn(1);
        when(noteMock.getArticleid()).thenReturn(1);
        when(noteMock.getArticleTitle()).thenReturn("");
        when(noteMock.getText()).thenReturn("");
        nrs.deleteNote(noteMock, callBack);
        verify(noteDaoMock).deleteNote(any(NoteEntity.class));
    }

    @Test
    public void addNoteTest() {
        doNothing().when(nrs).checkAchievements();
        NoteReferenceService.SaveCallback callback = mock(NoteReferenceService.SaveCallback.class);
        InOrder inOrder = Mockito.inOrder(this.noteDaoMock, this.referenceDaoMock);
        Note noteMock = mock(Note.class);
        when(noteMock.getArticleid()).thenReturn(1);
        when(noteMock.getArticleTitle()).thenReturn("");
        when(noteMock.getText()).thenReturn("");
        Reference referenceMock = mock(Reference.class);
        when(referenceMock.getNumber()).thenReturn(1);
        when(this.noteDaoMock.addNote(any(NoteEntity.class))).thenReturn((long)1);
        List<Reference> referenceListMock = mock(List.class);
        when(noteMock.getAllReferences()).thenReturn(referenceListMock);
        Iterator iteratorMock = mock(Iterator.class);
        when(iteratorMock.next()).thenReturn(referenceMock);
        when(referenceListMock.iterator()).thenReturn(iteratorMock);
        when(iteratorMock.hasNext()).thenReturn(true, false);

        nrs.addNote(noteMock, callback);
        inOrder.verify(this.noteDaoMock).addNote(any(NoteEntity.class));
        inOrder.verify(this.referenceDaoMock).addReferences(any(List.class));
    }

    @Test
    public void getAllArticleNotesTest() {
        NoteReferenceService.GetNotesCallback callback = new GetNotesCallback() {
            @Override
            public void afterGetNotes(List<Note> notes) {
                assertEquals(notes.size(), 1);
                assertEquals(notes.get(0).getAllReferences().size(), 1);
            }
        };
        List<NoteEntity> noteEntityListMock = mock(List.class);
        List<ReferenceEntity> referenceEntityListMock = mock(List.class);
        NoteEntity noteEntityMock = mock(NoteEntity.class);
        ReferenceEntity referenceEntityMock = mock(ReferenceEntity.class);
        when(this.noteDaoMock.getAllNotesForArticle(1)).thenReturn(noteEntityListMock);
        when(this.referenceDaoMock.getAllArticleReferences(1)).thenReturn(referenceEntityListMock);
        Iterator iteratorMockNoteEntity = mock(Iterator.class);
        when(noteEntityListMock.iterator()).thenReturn(iteratorMockNoteEntity);
        when(referenceEntityListMock.size()).thenReturn(1);
        when(referenceEntityListMock.get(0)).thenReturn(referenceEntityMock);
        when(iteratorMockNoteEntity.hasNext()).thenReturn(true,false);
        when(iteratorMockNoteEntity.next()).thenReturn(noteEntityMock);
        when(noteEntityMock.getId()).thenReturn(1);
        when(noteEntityMock.getArticleId()).thenReturn(2);
        when(noteEntityMock.getArticleTitle()).thenReturn("title");
        when(noteEntityMock.getText()).thenReturn("text");
        when(referenceEntityMock.getNoteId()).thenReturn(1);
        when(referenceEntityMock.getReferenceNum()).thenReturn(1);
        when(referenceEntityMock.getText()).thenReturn("reference text");
        nrs.getAllArticleNotes(1, callback);
        verify(this.noteDaoMock).getAllNotesForArticle(1);
        verify(this.referenceDaoMock).getAllArticleReferences(1);
    }

    @Test
    public void testGetAllNotedArticles () {

        List<String> titles = new ArrayList<String>();
        titles.add("Canada");
        titles.add("Concordia");
        when(noteDaoMock.getAllArticles()).thenReturn(titles);
        assertEquals(2, nrs.getAllNotedArticles().size());
    }

    @Test
    public void testArticleCannotDelete() {
        List<String> titles = new ArrayList<String>();
        titles.add("Canada");
        titles.add("Concordia");
        when(nrs.getAllNotedArticles()).thenReturn(titles);
        boolean deleteOrNot = nrs.articleCannotDelete(contextMock, "Concordia");
        assertTrue(deleteOrNot);
    }

    @Test
    public void updateNoteTextTest() {
        UpdateNoteTextCallBack callBackMock = mock(UpdateNoteTextCallBack.class);
        Note noteMock = mock(Note.class);
        when(noteMock.getId()).thenReturn(1);
        when(noteMock.getArticleTitle()).thenReturn("title");
        when(noteMock.getOriginalText()).thenReturn("text");
        when(noteMock.getUpdatedText()).thenReturn("updated");
        when(noteMock.getArticleid()).thenReturn(123);
        nrs.updateNoteText(noteMock, callBackMock);

        verify(callBackMock).afterUpdateNoteText();
    }

    @Test
    public void setCommentOnNoteTest() {
        SetCommentCallBack callBackMock = mock(SetCommentCallBack.class);
        Note noteMock = mock(Note.class);
        when(noteMock.getId()).thenReturn(1);
        when(noteMock.getArticleTitle()).thenReturn("title");
        when(noteMock.getComment()).thenReturn("my comment");
        when(noteMock.getSpan()).thenReturn("span");
        when(noteMock.getOriginalText()).thenReturn("text");
        when(noteMock.getUpdatedText()).thenReturn("updated");
        when(noteMock.getArticleid()).thenReturn(123);
        nrs.setCommentOnNote(noteMock, callBackMock);
        verify(noteMock).getComment();
        verify(noteMock).getId();
        verify(callBackMock).afterSetComment();
        verify(noteDaoMock).updateNote(any(NoteEntity.class));

    }

    @Test
    public void deleteCommentOnNoteTest() {
        DeleteNoteCallBack callBackMock = mock(DeleteNoteCallBack.class);
        Note noteMock = mock(Note.class);
        when(noteMock.getId()).thenReturn(1);
        when(noteMock.getArticleTitle()).thenReturn("title");
        when(noteMock.getComment()).thenReturn("my comment");
        when(noteMock.getSpan()).thenReturn("span");
        when(noteMock.getOriginalText()).thenReturn("text");
        when(noteMock.getUpdatedText()).thenReturn("updated");
        when(noteMock.getArticleid()).thenReturn(123);
        nrs.deleteCommentOnNote(noteMock, callBackMock);
        verify(callBackMock).afterDeleteNote();
        verify(noteDaoMock).updateNote(any(NoteEntity.class));
    }
}
