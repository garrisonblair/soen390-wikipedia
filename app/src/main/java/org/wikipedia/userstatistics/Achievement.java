package org.wikipedia.userstatistics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ken on 4/10/2018.
 */

public class Achievement {

    private int id;
    private String name;
    private String description;
    private int obtained;
    private String obtainedDate;
    private int checked;

    public Achievement(int id, String name, String description, int obtained, String obtainedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.obtained = obtained;
        this.obtainedDate = obtainedDate;
    }

    //Create a new achievement
//    public Achievement(String name, String description) {
//        this.name = name;
//        this.description = description;
//        obtained = 0;
//        obtainedDate = null;
//        checked = 0;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getObtained() {
        return obtained;
    }

    public void setObtained(int obtained) {
        this.obtained = obtained;
    }

    public String getObtainedDate() {
        return obtainedDate;
    }

    public void setObtainedDate(String obtainedDate) {
        this.obtainedDate = obtainedDate;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public void unlocked() {
        obtained = 1;
        obtainedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
}
