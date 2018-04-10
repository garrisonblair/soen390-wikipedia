package org.wikipedia.userstatistics;

import android.content.Context;

import org.wikipedia.userstatistics.Database.AchievementEntity;

/**
 * Created by Ken on 4/10/2018.
 */

public class AchievementChecker {

    private Context context;

    //This is where we define the conditions for each achievements
    public AchievementChecker(String achievementName, int value, Context context) {
        this.context = context;
        switch (achievementName){
            case "ach1":
                if (value < 1) {
                    unlockAchievement(achievementName);
                }
                break;
            case "ach2":
                if (value < 1) {
                    unlockAchievement(achievementName);
                }
                break;
            default: break;
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
