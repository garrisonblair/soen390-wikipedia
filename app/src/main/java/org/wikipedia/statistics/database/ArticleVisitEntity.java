package org.wikipedia.statistics.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by steve on 07/04/18.
 */

@Entity(tableName = "articleVisits")
public class ArticleVisitEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int articleId;
    private long timeSpentReading;
    private long timeStart;

    public ArticleVisitEntity(int articleId, long timeSpentReading, long timeStart) {
        this.articleId = articleId;
        this.timeSpentReading = timeSpentReading;
        this.timeStart = timeStart;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getArticleId() {
        return this.articleId;
    }

    public void setTimeSpentReading(long timeSpentReading) {
        this.timeSpentReading = timeSpentReading;
    }

    public long getTimeSpentReading() {
        return this.timeSpentReading;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeStart() {
        return timeStart;
    }
}
