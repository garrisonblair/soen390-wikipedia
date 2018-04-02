package org.wikipedia.journey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fred on 2018-04-02.
 */

public class Visit {

    private List<Visit> subVisits;

    private String articleID;

    public Visit(String articleID) {
        this.articleID = articleID;
        subVisits = new ArrayList<Visit>();
    }

    public List<Visit> getSubVisits() {
        return subVisits;
    }

    public String getArticleID() {
        return articleID;
    }

    public void addSubVisit(Visit visit) {
        subVisits.add(visit);
    }
}
