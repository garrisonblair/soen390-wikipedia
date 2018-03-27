package org.wikipedia.relatedvideos;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by KammyWong on 2018-03-26.
 */

public class VideoTest {
    Video video;
    @Before
    public void beforeTest(){
        video = new Video("Loving cat", "123", "Why do we love cat", "http://youtube.com/cat");
    }

    @Test
    public void getTitleTest(){
        assertEquals("Loving cat", video.getTitle());
    }

    @Test
    public void getIDTest(){
        assertEquals("123", video.getID());
    }

    @Test
    public void getDescriptionTest(){
        assertEquals("Why do we love cat", video.getDescription());
    }

    @Test
    public void getURLTest(){
        assertEquals("http://youtube.com/cat", video.getURL());
    }
}
