package org.wikipedia.userstatistics;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.userstatistics.Database.ArticleVisitDao;
import org.wikipedia.userstatistics.Database.ArticleVisitEntity;

import java.io.IOException;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class ArticleStatCalculatorTest {

    private Context context;
    private ArticleVisitDao articleVisitDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        this.context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        articleVisitDao = db.articleVisitDao();
    }

    @Before
    public void populateDb() {
        ArticleVisitEntity article1 = new ArticleVisitEntity(
                "Article 1",
                60000,
                new Date().getTime()
        );
        articleVisitDao.addArticleVisit(article1);

        ArticleVisitEntity article2 = new ArticleVisitEntity(
                "Article 2",
                120000,
                new Date().getTime()
        );
        articleVisitDao.addArticleVisit(article2);

        ArticleVisitEntity article3 = new ArticleVisitEntity(
                "Article 1",
                30000,
                new Date().getTime()
        );
        articleVisitDao.addArticleVisit(article3);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testArticleStatCalculator() throws Exception {

        ArticleStatCalculator statCalculator = new ArticleStatCalculator(context, db);

        // Total number of articles read should be 3
        Assert.assertEquals(3, statCalculator.getTotalArticlesRead());
        // Total number of unique articles read should be 2
        Assert.assertEquals(2, statCalculator.getUniqueArticlesRead());
        // Longest Read Article Title should be "Article 2" ie article2
        Assert.assertEquals("Article 2", statCalculator.getLongestReadArticleTitle());
        // Longest Read Article Time should be 120000 milliseconds
        Assert.assertEquals(120000, statCalculator.getLongestReadArticleTime());
        // Total time spent reading should be 210000 milliseconds
        Assert.assertEquals(210000, statCalculator.getTotalTimeSpentReading());
        // Average time spent reading should be 70000 ie 210000 / 3
        Assert.assertEquals(70000, statCalculator.getAverageTimeSpentReading());
    }

}
