package org.wikipedia.userstatistics;

/**
 * Created by Ken on 4/10/2018.
 */

public enum AchievementsList {

    //Article stat achievements
    TotalTimeSpent1("TotalTimeSpent", "Big Reader!", "Spent more than 1 minute reading articles.", ">", 60),
    TotalTimeSpent2("TotalTimeSpent", "Bigger Reader!", "Spent more than 10 minutes reading articles.", ">", 600),
    TotalTimeSpent3("TotalTimeSpent", "Biggest Reader!", "Spent more than 60 minutes reading articles.", ">", 3600),

    TimeSpent1("TimeSpent", "In a Hurry?", "Spent less than 10 seconds on an article.", "<", 10),
    TimeSpent2("TimeSpent", "That was a long article...", "Spent more than 3 minutes on an article.", ">", 180),
    TimeSpent3("TimeSpent", "Fell Asleep?", "Spent more than 5 minutes on an article.", ">", 300),

    Read1("Read", "I read an article", "Read more than 5 articles.", ">", 5),
    Read2("Read", "I read an article", "Read more than 5 articles.", ">", 5),
    Read3("Read", "I read an article", "Read more than 5 articles.", ">", 5),
    Read4("Read", "I read an article", "Read more than 5 articles.", ">", 5),
    Read5("Read", "I read an article", "Read more than 5 articles.", ">", 5),

    //Note stat achievements
    Note1("Note", "I took a note", "Created 1 note.", ">", 1),
    Note2("Note", "I took more notes", "Created more than 5 notes.", ">", 5),
    Note3("Note", "I took even more notes", "Created more than 20 notes.", ">", 20),
    Note4("Note", "I must love notes", "Created more than 50 notes.", ">", 50),
    Note5("Note", "I'm the NoteMan", "Created more than 100 notes.", ">", 100);

    private String category;
    private String name;
    private String description;
    private String operator;
    private int minValue;

    AchievementsList(String category, String name, String description, String operator, int minValue) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.operator = operator;
        this.minValue = minValue;
    }

    public String getCategory() {
        return category;
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
