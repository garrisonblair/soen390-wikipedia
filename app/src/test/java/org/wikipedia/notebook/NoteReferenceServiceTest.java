package org.wikipedia.notebook;

import android.content.Context;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.notebook.database.NoteDao;
import org.wikipedia.notebook.database.NoteEntity;
import org.wikipedia.notebook.database.ReferenceDao;
import org.wikipedia.notebook.database.ReferenceEntity;

import java.util.Iterator;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Andres on 2018-03-10.
 */

public class NoteReferenceServiceTest {

    @Mock
    private AppDatabase dbMock = mock(AppDatabase.class);
    @Mock
    private NoteDao noteDaoMock = mock(NoteDao.class);
    @Mock
    private ReferenceDao referenceDaoMock = mock(ReferenceDao.class);
    @Mock
    private Context contextMock = mock(Context.class);

    private NoteReferenceService nrs;

    @Before
    public void setupTests() {
        when(dbMock.referenceDao()).thenReturn(this.referenceDaoMock);
        when(dbMock.noteDao()).thenReturn(this.noteDaoMock);
        nrs = new NoteReferenceService(contextMock, dbMock);
    }

    @Test
    public void deleteNoteTest() {
        Note noteMock = mock(Note.class);
        when(noteMock.getArticleid()).thenReturn(1);
        when(noteMock.getArticleTitle()).thenReturn("");
        when(noteMock.getText()).thenReturn("");
        nrs.deleteNote(noteMock);
        verify(noteDaoMock).deleteNote(any(NoteEntity.class));
    }

    @Test
    public void addNoteTest() {
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

        nrs.addNote(noteMock);
        inOrder.verify(this.noteDaoMock).addNote(any(NoteEntity.class));
        inOrder.verify(this.referenceDaoMock).addReferences(any(List.class));
    }
}
