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

    public ArticleVisit(int articleId, long visitTimeStart, long visitTimeEnd, long totalVisitTime) {
        this.articleId = articleId;
        this.totalVisitTime = visitTimeEnd - visitTimeStart;
        this.visitTimeStart = visitTimeStart;
        this.visitTimeEnd = visitTimeEnd;
        this.visitId = -1;
    }

    public ArticleVisit(int articleId, int visitId, long totalVisitTime) {
        this.articleId = articleId;
        this.visitId = visitId;
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
