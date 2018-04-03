package org.wikipedia.journey;

import org.wikipedia.page.PageProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fred on 2018-04-02.
 */

public class Visit {

    private List<Visit> subVisits;

    private PageProperties page;

    public Visit(PageProperties articleID) {
        this.page = articleID;
        subVisits = new ArrayList<Visit>();
    }

    public List<Visit> getSubVisits() {
        return subVisits;
    }

    public PageProperties getArticleID() {
        return page;
    }

    public void addSubVisit(Visit visit) {
        subVisits.add(visit);
    }
}
