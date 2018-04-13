package org.wikipedia.userstatistics.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by steve on 07/04/18.
 */
@Dao
public interface ArticleVisitDao {

    @Query("SELECT * FROM articleVisits ORDER BY id ASC")
    List<ArticleVisitEntity> getTotalUniqueVisits();

    @Query("SELECT * FROM articleVisits WHERE articleTitle = :articleTitle")
    List<ArticleVisitEntity> getSpecificArticleUniqueVisits(String articleTitle);

    @Query("DELETE FROM articleVisits WHERE articleTitle = :articleTitle")
    void deleteSpecificArticleVisits(int articleTitle);

    @Query("UPDATE articleVisits SET timeSpentReading = :timeSpentReading WHERE id = :id")
    void upDateTimeSpentReading(long timeSpentReading, int id);

    @Query("UPDATE articleVisits SET articleTitle = :articleTitle WHERE id = :id")
    void updateArticleTitle(String articleTitle, int id);

    @Insert
    void addArticleVisit(ArticleVisitEntity articleVisitEntity);

    @Delete
    void deleteArticleVisit(ArticleVisitEntity articleVisitEntity);

    @Update
    void updateArticleVisit(ArticleVisitEntity articleVisitEntity);
}
