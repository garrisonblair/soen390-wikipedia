package org.wikipedia.notebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.notebook.database.NoteDao;
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
    private Context mockApplicationContext;
    @Mock
    private Resources mockContextResources;
    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    AppDatabase dbMock;
    @Mock
    NoteDao noteDaoMock;
    @Mock
    ReferenceDao referenceDaoMock;
    private NoteReferenceService nrs;

    @Before
    public void setupTests() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // During unit testing sometimes test fails because of your methods
        // are using the app Context to retrieve resources, but during unit test the Context is null
        // so we can mock it.

        when(mockApplicationContext.getResources()).thenReturn(mockContextResources);
        when(mockApplicationContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences);

        when(mockContextResources.getString(anyInt())).thenReturn("mocked string");
        when(mockContextResources.getStringArray(anyInt())).thenReturn(new String[]{"mocked string 1", "mocked string 2"});
        when(mockContextResources.getColor(anyInt())).thenReturn(Color.BLACK);
        when(mockContextResources.getBoolean(anyInt())).thenReturn(false);
        when(mockContextResources.getDimension(anyInt())).thenReturn(100f);
        when(mockContextResources.getIntArray(anyInt())).thenReturn(new int[]{1,2,3});
        // here you can mock additional methods ...

        // if you have a custom Application class, you can inject the mock context like this
        //MyCustomApplication.setAppContext(mockApplicationContext);
        nrs = new NoteReferenceService(this.mockApplicationContext);

        when(dbMock.noteDao()).thenReturn(noteDaoMock);
        when(dbMock.referenceDao()).thenReturn(referenceDaoMock);
        nrs.superSedeDbForTest(dbMock);
    }
    @Test
    public void deleteNoteTest() {
        NoteReferenceService spyNrs = spy(nrs);
        Note note = mock(Note.class);
        spyNrs.deleteNote(note);
        verify(noteDaoMock).deleteNote(any());
    }
}
