package org.wikipedia.userstatistics;

import android.content.Context;
import android.widget.Toast;

import org.wikipedia.userstatistics.Database.AchievementEntity;

//import cn.pedant.SweetAlert.SweetAlertDialog;

//import static cn.pedant.SweetAlert.SweetAlertDialog.SUCCESS_TYPE;

/**
 * Created by Ken on 4/10/2018.
 */

public class AchievementChecker {

    private Context context;

    public AchievementChecker(Context context) {
        this.context = context;
    }

    //This is where we define the conditions for each achievements
    public void check(AchievementsList achievement, double value) {
        if (achievement.getOperator().equals("<")) {
            if (value <= achievement.getMinValue()) {
                unlockAchievement(achievement.getName());
            }
        } else {
            if (value >= achievement.getMinValue()) {
                unlockAchievement(achievement.getName());
            }
        }
    }

    public void unlockAchievement(String achievementName) {
        AchievementService service = new AchievementService(context);
        service.getLockedAchievements(achievements -> {
            for (AchievementEntity achievementEntity: achievements) {
                if (achievementEntity.getName().equals(achievementName)) {
                    Achievement achievement = service.achievementEntityToAchievement(achievementEntity);
                    achievement.unlocked();
                    service.unlockAchievement(achievement, () -> {

                    });
                    //TODO: Make a better message notification when unlocking a new achievement. Currently using a toast.
                    Toast.makeText(context, "Achievement Unlocked: " + achievementName, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

}
