package org.wikipedia.userstatistics.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.wikipedia.userstatistics.AchievementsList;

/**
 * Created by Ken on 2018-04-10.
 */

@Entity(tableName = "achievements")
public class AchievementEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private int obtained;
    private String obtainedDate;
    private int checked;

    public AchievementEntity(String name, String description) {
        this.name = name;
        this.description = description;
        obtained = 0;
        obtainedDate = null;
        checked = 0;
    }

    public AchievementEntity(String name, String description, int obtained, String obtainedDate) {
        this.name = name;
        this.description = description;
        this.obtained = obtained;
        this.obtainedDate = obtainedDate;
        checked = 0;
    }

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

    public static AchievementEntity[] populateData() {

        int size = AchievementsList.values().length;
        AchievementEntity[] list = new AchievementEntity[size];
        int i = 0;
        for (AchievementsList ach : AchievementsList.values()) {
            list[i] = new AchievementEntity(ach.getName(), ach.getDescription());
            i++;
        }
        return list;
    }
}
