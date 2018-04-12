package org.wikipedia.userstatistics;

import android.content.Context;

import org.wikipedia.userstatistics.Database.AchievementEntity;

/**
 * Created by Ken on 4/10/2018.
 */

public class AchievementChecker {

    private Context context;

    public AchievementChecker(Context context) {
        this.context = context;
    }

    //This is where we define the conditions for each achievements
    public void check(String achievementName, int value) {
        for (AchievementsList ach: AchievementsList.values()) {
            if (achievementName.equals(ach.getName())) {
                if (ach.getOperator().equals("<")) {
                    if (value <= ach.getMinValue()) {
                        unlockAchievement(achievementName);
                    }
                } else {
                    if (value >= ach.getMinValue()) {
                        unlockAchievement(achievementName);
                    }
                }
            }
        }
    }

    private void unlockAchievement(String achievementName) {
        AchievementService service = new AchievementService(context);
        service.getLockedAchievements(achievements -> {
            for (AchievementEntity achievementEntity: achievements) {
                if (achievementEntity.getName().equals(achievementName)) {
                    Achievement achievement = service.achievementEntityToAchievement(achievementEntity);
                    achievement.unlocked();
                    service.unlockAchievement(achievement, () -> {
                        //TODO: Notify new achievement
                    });
                }
                return;
            }
        });

    }

}
