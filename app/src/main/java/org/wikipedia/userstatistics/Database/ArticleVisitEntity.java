package org.wikipedia.userstatistics.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by steve on 07/04/18.
 */

@Entity(tableName = "articleVisits")
public class ArticleVisitEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String articleTitle;
    private long timeSpentReading;
    private long timeStart;

    public ArticleVisitEntity(String articleTitle, long timeSpentReading, long timeStart) {
        this.articleTitle = articleTitle;
        this.timeSpentReading = timeSpentReading;
        this.timeStart = timeStart;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleTitle() {
        return this.articleTitle;
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
