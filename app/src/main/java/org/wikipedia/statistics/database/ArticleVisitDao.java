package org.wikipedia.statistics.database;

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

    @Query("SELECT * FROM articleVisits ORDER BY articleId ASC")
    List<ArticleVisitEntity> getTotalUniqueVisits();

    @Query("SELECT * FROM articleVisits WHERE articleId = :articleId")
    List<ArticleVisitEntity> getSpecificArticleUniqueVisits(int articleId);

    @Query("DELETE FROM articleVisits WHERE articleId = :articleId")
    void deleteSpecificArticleVisits(int articleId);

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
