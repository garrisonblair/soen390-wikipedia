package org.wikipedia.notebook;

import android.content.Context;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.notebook.database.NoteDao;
import org.wikipedia.notebook.database.NoteEntity;
import org.wikipedia.notebook.database.ReferenceDao;

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
        NoteReferenceService spyNrs = spy(nrs);
        Note note = mock(Note.class);
        when(note.getArticleid()).thenReturn(1);
        when(note.getArticleTitle()).thenReturn("");
        when(note.getText()).thenReturn("");
        spyNrs.deleteNote(note);
        verify(noteDaoMock).deleteNote(any(NoteEntity.class));
    }
}
