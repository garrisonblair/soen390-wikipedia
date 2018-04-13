package org.wikipedia.database.room;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.userstatistics.Database.AchievementDao;
import org.wikipedia.userstatistics.Database.AchievementEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ken on 2018-04-12.
 */

@RunWith(AndroidJUnit4.class)
public class AchievementDaoTest {
    private AchievementDao mAchievementDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mAchievementDao = mDb.achievementDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void readAndWriteAndDeleteAchievement() throws Exception {
        AchievementEntity achievementEntity = new AchievementEntity("Achievement test","this is a test achievement");

        mAchievementDao.addAchievement(achievementEntity);

        List<AchievementEntity> allAchievements = mAchievementDao.getAllAchievements();
        assertEquals(allAchievements.get(0).getName(),achievementEntity.getName());
        assertEquals(allAchievements.get(0).getDescription(),achievementEntity.getDescription());
        assertEquals(1,allAchievements.size());

        //Delete
        mAchievementDao.delete("Achievement test");
        allAchievements = mAchievementDao.getAllAchievements();

        assertEquals(0,allAchievements.size());

    }

    @Test
    public void updateAchievementTest() throws Exception {
        AchievementEntity achievementEntity = new AchievementEntity("Achievement test","this is a test achievement");

        mAchievementDao.addAchievement(achievementEntity);
        List<AchievementEntity> allAchievements = mAchievementDao.getAllAchievements();
        achievementEntity = allAchievements.get(0);
        achievementEntity.setName("updated Name");
        achievementEntity.setDescription("updated Description");

        mAchievementDao.updateAchievement(achievementEntity);
        allAchievements = mAchievementDao.getAllAchievements();
        assertEquals(1, allAchievements.size());
        achievementEntity = allAchievements.get(0);
        assertEquals("updated Name", achievementEntity.getName());
        assertEquals("updated Description", achievementEntity.getDescription());
    }
}
