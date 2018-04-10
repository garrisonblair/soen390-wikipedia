package org.wikipedia.userstatistics;

import android.content.Context;

public class StatCalculator {

    private ArticleStatCalculator articleStatCalculator;
    private NoteStatCalculator noteStatCalculator;

    public StatCalculator(Context context) {
        this.articleStatCalculator = new ArticleStatCalculator(context);
        this.noteStatCalculator = new NoteStatCalculator(context);
    }

    public ArticleStatCalculator getArticleStats() {
        return articleStatCalculator;
    }

    public  NoteStatCalculator getNoteStats() {
        return noteStatCalculator;
    }
}
