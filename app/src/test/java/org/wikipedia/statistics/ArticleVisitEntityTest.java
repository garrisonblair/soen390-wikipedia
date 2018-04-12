package org.wikipedia.statistics;

import org.junit.Test;
import org.wikipedia.statistics.database.ArticleVisit;
import org.wikipedia.statistics.database.ArticleVisitEntity;

import static org.junit.Assert.assertEquals;

/**
 * Created by steve on 07/04/18.
 */

public class ArticleVisitEntityTest {


    @Test
    public void testArticleVisitEntityConstructor() {
        ArticleVisitEntity articleVisitEntity = new ArticleVisitEntity(1, "cat", 2, 1111);
        assertEquals(articleVisitEntity.getId(), 1);
        assertEquals(articleVisitEntity.getArticleId(), 2);
        assertEquals(articleVisitEntity.getTimeSpentReading(), 1111);
    }

    @Test
    public void testArticleVisitEntityGetters() {
        ArticleVisitEntity articleVisitEntity = new ArticleVisitEntity(1, "dog", 2, 1111);
        assertEquals(articleVisitEntity.getId(), 1);
        assertEquals(articleVisitEntity.getArticleId(), 2);
        assertEquals(articleVisitEntity.getTimeSpentReading(), 1111);
    }

    @Test
    public void testArticleVisitEntitySetters() {
        ArticleVisitEntity articleVisitEntity = new ArticleVisitEntity(1, "wikipedia", 2, 1111);
        articleVisitEntity.setId(11);
        articleVisitEntity.setArticleId(22);
        articleVisitEntity.setTimeSpentReading(11111111);

        assertEquals(articleVisitEntity.getId(), 11);
        assertEquals(articleVisitEntity.getArticleId(), 22);
        assertEquals(articleVisitEntity.getTimeSpentReading(), 11111111);
    }
}
