package org.wikipedia.statistics.database;

/**
 * Created by steve on 07/04/18.
 */

public class ArticleVisit {

    private int articleId;
    private int visitId;
    private long visitTimeStart;
    private long visitTimeEnd;
    private long totalVisitTime;

    public ArticleVisit(int articleId, long totalVisitTime, long visitTimeStart, long visitTimeEnd) {
        this.articleId = articleId;
        this.totalVisitTime = totalVisitTime;
        this.visitTimeStart = visitTimeStart;
        this.visitTimeEnd = visitTimeEnd;
    }

    public ArticleVisit(int visitId, int articleId, long totalVisitTime) {
        this.visitId = visitId;
        this.articleId = articleId;
        this.totalVisitTime = totalVisitTime;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public long getVisitTimeStart() {
        return visitTimeStart;
    }

    public void setVisitTimeStart(long visitTimeStart) {
        this.visitTimeStart = visitTimeStart;
    }

    public long getVisitTimeEnd() {
        return visitTimeEnd;
    }

    public void setVisitTimeEnd(long visitTimeEnd) {
        this.visitTimeEnd = visitTimeEnd;
    }

    public long getTotalVisitTime() {
        return totalVisitTime;
    }

    public void setTotalVisitTime(long totalVisitTime) {
        this.totalVisitTime = totalVisitTime;
    }
}
