package org.wikipedia.userstatistics;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.userstatistics.Database.AchievementDao;
import org.wikipedia.userstatistics.Database.AchievementEntity;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.wikipedia.userstatistics.AchievementService.DeleteAchievementCallback;
import static org.wikipedia.userstatistics.AchievementService.GetAllAchievementsCallback;

/**
 * Created by Ken on 2018-04-12.
 */

@RunWith(RobolectricTestRunner.class)
public class AchievementServiceTest {

    private AppDatabase dbMock = mock(AppDatabase.class);
    private AchievementDao achievementDaoMock = mock(AchievementDao.class);
    private Context contextMock = mock(Context.class);


    private AchievementService service;

    @Before
    public void setupTests() {
        when(dbMock.achievementDao()).thenReturn(this.achievementDaoMock);
        service = new AchievementService(contextMock, dbMock);
    }

    @Test
    public void deleteAchievementTest() {
        DeleteAchievementCallback callback = mock(DeleteAchievementCallback.class);
        Achievement achievementMock = mock(Achievement.class);
        when(achievementMock.getName()).thenReturn("mocked achievement");
        service.deleteAchievement(achievementMock, callback);
        verify(achievementDaoMock).delete("mocked achievement");
    }

    @Test
    public void getAllAchievementsTest() {
        GetAllAchievementsCallback callback = new GetAllAchievementsCallback() {
            @Override
            public void afterGetAllAchievements(List<AchievementEntity> achievements) {

            }
        };;
        List<AchievementEntity> achievements = new ArrayList<>();

        AchievementEntity a1 = new AchievementEntity("achievement 1", "description 1");
        AchievementEntity a2 = new AchievementEntity("achievement 2", "description 2");
        achievements.add(a1);
        achievements.add(a2);
        when(achievementDaoMock.getAllAchievements()).thenReturn(achievements);
        service.getAllAchievements(callback);
        verify(achievementDaoMock).getAllAchievements();
        assertEquals(2, achievementDaoMock.getAllAchievements().size());
    }
}
