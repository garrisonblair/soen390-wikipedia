package org.wikipedia.userstatistics.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.wikipedia.userstatistics.Achievement;

import java.util.List;

/**
 * Created by Ken on 2018-04-10.
 */
@Dao
public interface AchievementDao {

    @Query("SELECT * FROM achievements")
    List<AchievementEntity> getAllAchievements();

    @Query("SELECT * FROM achievements WHERE obtained = 1")
    List<AchievementEntity> getUnlockedAchievements();

    @Query("SELECT * FROM achievements WHERE obtained = 0")
    List<AchievementEntity> getLockedAchievements();

    @Query("SELECT * FROM achievements WHERE name = :name")
    Achievement getAchievement(String name);

    @Update
    void updateAchievement(AchievementEntity achievement);

    @Insert
    void addAchievement(AchievementEntity achievement);

    @Query("UPDATE achievements SET checked = 1 WHERE obtained = 1")
    void checkAchievements();
}

