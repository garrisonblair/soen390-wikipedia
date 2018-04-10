package org.wikipedia.database.room;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.statistics.database.ArticleVisitDao;
import org.wikipedia.statistics.database.ArticleVisitEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by steve on 07/04/18.
 */

@RunWith(AndroidJUnit4.class)
public class ArticleVisitDaoTest {

    private ArticleVisitDao mAvd;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mAvd = mDb.articleVisitDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testReadWriteAndDeleteArticleVisits() {

        // Create 2 ArticleVisitEntity instances and add them to the room database
        ArticleVisitEntity articleVisitEntity1 = new ArticleVisitEntity(1, 2, 3);
        ArticleVisitEntity articleVisitEntity2 = new ArticleVisitEntity(4, 5, 6);
        mAvd.addArtictleVisit(articleVisitEntity1);
        mAvd.addArtictleVisit(articleVisitEntity2);

        // Get all unique ArticleVisitEntity article visits
        List<ArticleVisitEntity> articleVisitEntities = mAvd.getTotalUniqueVisits();

        // Check that the values of the first entry match those that were created
        assertEquals(articleVisitEntities.get(0).getId(), 1);
        assertEquals(articleVisitEntities.get(0).getArticleId(), 2);
        assertEquals(articleVisitEntities.get(0).getTimeSpentReading(), 3);

        // Check that the values of the second entry match those that were created
        assertEquals(articleVisitEntities.get(1).getId(), 4);
        assertEquals(articleVisitEntities.get(1).getArticleId(), 5);
        assertEquals(articleVisitEntities.get(1).getTimeSpentReading(), 6);

        // Check that the size of the list of ArticleVisitEntity entries is 2
        assertEquals(articleVisitEntities.size(), 2);

        // Remove an entry from the database
        mAvd.deleteArticleVisit(articleVisitEntity2);

        // Get all the ArticleVisitEntity entries again from the db which should now only be one
        articleVisitEntities = mAvd.getTotalUniqueVisits();

        // Check that the size of the obtained entries is 1
        assertEquals(articleVisitEntities.size(), 1);

        // Add a second entry with the same articleId
        ArticleVisitEntity articleVisitEntity3 = new ArticleVisitEntity(2, 2, 10);
        mAvd.addArtictleVisit(articleVisitEntity3);

        // Get all the ArticleVisitEntity entries again from the db which should now only be one
        articleVisitEntities = mAvd.getTotalUniqueVisits();

        // Check that the size of the obtained entries is 1
        assertEquals(articleVisitEntities.size(), 2);

        // Get all the ArticleVisitEntity entries again from the db which should now only be one
        List <ArticleVisitEntity> specificArticleVisitEntities = mAvd.getSpecificArticleUniqueVisits(2);

        // Check that the size of the obtained entries is 1
        assertEquals(specificArticleVisitEntities.size(), 2);

    }
}
