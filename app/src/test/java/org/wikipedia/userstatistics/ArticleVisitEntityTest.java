package org.wikipedia.userstatistics;

import org.junit.Test;
import org.wikipedia.userstatistics.Database.ArticleVisitEntity;

import static org.junit.Assert.assertEquals;

/**
 * Created by steve on 07/04/18.
 */

public class ArticleVisitEntityTest {


    @Test
    public void testArticleVisitEntityConstructor() {
        ArticleVisitEntity articleVisitEntity = new ArticleVisitEntity("Article Title", 20000, 1111);
        assertEquals(articleVisitEntity.getId(), 0);
        assertEquals(articleVisitEntity.getArticleTitle(), "Article Title");
        assertEquals(articleVisitEntity.getTimeSpentReading(), 20000);
        assertEquals(articleVisitEntity.getTimeStart(), 1111);
    }

    @Test
    public void testArticleVisitEntityGetters() {
        ArticleVisitEntity articleVisitEntity = new ArticleVisitEntity("Article Title", 20000, 1111);
        assertEquals(articleVisitEntity.getId(), 0);
        assertEquals(articleVisitEntity.getArticleTitle(), "Article Title");
        assertEquals(articleVisitEntity.getTimeSpentReading(), 20000);
        assertEquals(articleVisitEntity.getTimeStart(), 1111);
    }

    @Test
    public void testArticleVisitEntitySetters() {
        ArticleVisitEntity articleVisitEntity = new ArticleVisitEntity("Article Title", 20000, 1111);
        articleVisitEntity.setId(11);
        articleVisitEntity.setArticleTitle("Article Title");
        articleVisitEntity.setTimeSpentReading(11111111);

        assertEquals(articleVisitEntity.getId(), 11);
        assertEquals(articleVisitEntity.getArticleTitle(), "Article Title");
        assertEquals(articleVisitEntity.getTimeSpentReading(), 11111111);
    }
}
