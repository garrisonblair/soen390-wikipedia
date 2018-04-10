package org.wikipedia.userstatistics;

import java.util.List;

import android.content.Context;

import org.wikipedia.database.room.AppDatabase;
import org.wikipedia.statistics.database.ArticleVisitDao;
import org.wikipedia.statistics.database.ArticleVisitEntity;

public class StatCalculator {

    private AppDatabase db;
    private ArticleVisitDao articleVisitDao;
    private List<ArticleVisitEntity> visitedArticles;
    private List<ArticleVisitEntity> uniqueVisitedArticles;

    private long totalTimeSpentReading;
    private long averageTimeSpentReading;
    private long dailyTimeSpentReading;
    private int longestReadArticleId;
    private int totalArticlesRead;
    private int uniqueArticlesRead;

    private int totalNotes;
    private int totalArticlesWithNotes;

    private int textSearches;
    private int gallerySearches;
    private int cameraSearches;

    private int ttsUses;

    public StatCalculator(Context context) {
        this.db = AppDatabase.getInstance(context);
        this.articleVisitDao = db.articleVisitDao();

        visitedArticles = articleVisitDao.getTotalUniqueVisits();
    }

    // TODO: calculate different statistics
}
