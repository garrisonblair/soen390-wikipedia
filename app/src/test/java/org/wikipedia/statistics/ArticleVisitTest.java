package org.wikipedia.statistics;

import org.junit.Test;
import org.wikipedia.statistics.database.ArticleVisit;

import static org.junit.Assert.assertEquals;

/**
 * Created by steve on 07/04/18.
 */

public class ArticleVisitTest {


    @Test
    public void testArticleVisitConstructors() {

        // Constructor 1
        ArticleVisit articleVisit1 = new ArticleVisit(1, 10, 20000);
        assertEquals(articleVisit1.getArticleId(), 1);
        assertEquals(articleVisit1.getVisitId(), 10);
        assertEquals(articleVisit1.getTotalVisitTime(), 20000);

        // Constructor 2
        ArticleVisit articleVisit2 = new ArticleVisit(2, 5000, 15000, 10000);
        assertEquals(articleVisit2.getArticleId(), 2);
        assertEquals(articleVisit2.getVisitId(), -1);
        assertEquals(articleVisit2.getVisitTimeStart(), 5000);
        assertEquals(articleVisit2.getVisitTimeEnd(), 15000);
        assertEquals(articleVisit2.getTotalVisitTime(), 10000);
    }

    @Test
    public void testArticleVisitGetters() {
        ArticleVisit articleVisit = new ArticleVisit(2, 5000, 15000, 10000);
        assertEquals(articleVisit.getArticleId(), 2);
        assertEquals(articleVisit.getVisitId(), -1);
        assertEquals(articleVisit.getVisitTimeStart(), 5000);
        assertEquals(articleVisit.getVisitTimeEnd(), 15000);
        assertEquals(articleVisit.getTotalVisitTime(), 10000);
    }

    @Test
    public void testArticleVisitSetters() {
        ArticleVisit articleVisit = new ArticleVisit(2, 5000, 15000, 10000);
        articleVisit.setArticleId(11);
        articleVisit.setVisitId(22);
        articleVisit.setVisitTimeStart(33);
        articleVisit.setVisitTimeEnd(44);
        articleVisit.setTotalVisitTime(55);

        assertEquals(articleVisit.getArticleId(), 11);
        assertEquals(articleVisit.getVisitId(), 22);
        assertEquals(articleVisit.getVisitTimeStart(), 33);
        assertEquals(articleVisit.getVisitTimeEnd(), 44);
        assertEquals(articleVisit.getTotalVisitTime(), 55);
    }
}
