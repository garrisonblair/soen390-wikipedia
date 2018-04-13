package org.wikipedia.userstatistics;

/**
 * Created by Ken on 4/10/2018.
 */

public enum AchievementsList {

    //Article stat achievements
    A1("Big Reader!", "Spent more than 1 minute reading articles.", ">", 60),
    A2("That was a long article...", "Spent more than 3 minutes on an article.", ">", 180),
    A3("In a Hurry?", "Spent less than 10 seconds on an article.", "<", 10),
    A4("Wikinerd", "Read more than 5 articles.", ">", 5),

    //Note stat achievements
    A5("Tryhard", "Created more than 5 notes.", ">", 5);

    private String name;
    private String description;
    private String operator;
    private int minValue;

    AchievementsList(String name, String description, String operator, int minValue) {
        this.name = name;
        this.description = description;
        this.operator = operator;
        this.minValue = minValue;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOperator() {
        return operator;
    }

    public int getMinValue() {
        return minValue;
    }
}
