package org.wikipedia.userstatistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.userstatistics.Database.AchievementDao;
import org.wikipedia.userstatistics.Database.AchievementEntity;

import java.util.List;

/**
 * Created by Ken on 2018-04-10.
 */

public class AchievementService {

    public interface UnlockAchievementCallback {
        void afterUnlockAchievement();
    }

    public interface GetAllAchievementsCallback {
        void afterGetAllAchievements(List<AchievementEntity> achievements);
    }

    public interface GetUnlockedAchievementsCallback {
        void afterGetUnlockedAchievements(List<AchievementEntity> achievements);
    }

    public interface AddAchievementCallback {
        void afterAddAchievement();
    }

    public interface CheckAchievementsCallback {
        void afterCheckAchievements();
    }

    private Context context;
    private AppDatabase db;
    private AchievementDao achievementDao;

    public AchievementService(Context context) {
        this.context = context;
        this.db = AppDatabase.getInstance(context);
        this.achievementDao = db.achievementDao();
    }

    public AchievementService(Context context, AppDatabase db) {
        this.context = context;
        this.db = db;
        this.achievementDao = db.achievementDao();
    }

    @SuppressLint("StaticFieldLeak")
    public void getAllAchievements(GetAllAchievementsCallback callback) {
        new AsyncTask<Object, Void, List<AchievementEntity>>() {

            @Override
            protected List<AchievementEntity> doInBackground(Object... objects) {
                List<AchievementEntity> achievementEntities = achievementDao.getAllAchievements();
                return achievementEntities;
            }
            protected void onPostExecute(List<AchievementEntity> result) {
                callback.afterGetAllAchievements(result);
            }
        }.execute(new Object());
    }

    @SuppressLint("StaticFieldLeak")
    public void getUnlockedAchievements(GetUnlockedAchievementsCallback callback) {
        new AsyncTask<Object, Void, List<AchievementEntity>>() {

            @Override
            protected List<AchievementEntity> doInBackground(Object... objects) {
                List<AchievementEntity> achievementEntities = achievementDao.getUnlockedAchievements();
                return achievementEntities;
            }
            protected void onPostExecute(List<AchievementEntity> result) {
                callback.afterGetUnlockedAchievements(result);
            }
        }.execute(new Object());
    }

    @SuppressLint("StaticFieldLeak")
    public void addAchievement(Achievement newAchievement, AddAchievementCallback callback) {
        new AsyncTask<Object, Void, Void>() {

            @Override
            protected Void doInBackground(Object... objects) {
                AchievementEntity achievementEntity = new AchievementEntity(newAchievement.getName(), newAchievement.getDescription());
                achievementDao.addAchievement(achievementEntity);
                callback.afterAddAchievement();
                return null;
            }
        }.execute(new Object());
    }

    @SuppressLint("StaticFieldLeak")
    public void unlockAchievement(Achievement achievement, UnlockAchievementCallback callBack) {
        new AsyncTask<Object, Void, Void>() {

            @Override
            protected Void doInBackground(Object... objects) {
                AchievementEntity achievementEntity = achievementToAchievementEntity(achievement);
                achievementDao.updateAchievement(achievementEntity);
                callBack.afterUnlockAchievement();
                return null;
            }
        }.execute(new Object());
    }

    @SuppressLint("StaticFieldLeak")
    public void checkAchievements(CheckAchievementsCallback callBack) {
        new AsyncTask<Object, Void, Void>() {

            @Override
            protected Void doInBackground(Object... objects) {
                achievementDao.checkAchievements();
                callBack.afterCheckAchievements();
                return null;
            }
        }.execute(new Object());
    }


    private Achievement achievementEntityToAchievement(AchievementEntity achievementEntity) {
        Achievement achievement = new Achievement(achievementEntity.getId(), achievementEntity.getName(), achievementEntity.getDescription(), achievementEntity.getObtained(), achievementEntity.getObtainedDate());
        return achievement;
    }

    private AchievementEntity achievementToAchievementEntity(Achievement achievement) {
        AchievementEntity achievementEntity = new AchievementEntity(achievement.getName(), achievement.getDescription(), achievement.getObtained(), achievement.getObtainedDate());
        achievementEntity.setId(achievement.getId());
        return achievementEntity;
    }
}
