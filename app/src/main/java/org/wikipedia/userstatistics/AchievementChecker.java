package org.wikipedia.userstatistics;

import android.content.Context;

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
    public void check(String achievementName, double value) {
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

//                        // Open achievement notification dialog
//                        SweetAlertDialog dialog = new SweetAlertDialog(context, SUCCESS_TYPE);
//                        dialog.setTitleText("New Achievement Unlocked: " + achievementName);
//                        dialog.setConfirmText("See Achievements");
//                        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                sDialog.dismissWithAnimation();
//
//                                // Open achievements activity
//                                Intent intent = new Intent(context, AchievementActivity.class);
//                                context.startActivity(intent);
//                            }
//                        });
//                        dialog.setCancelButton("Close", new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                sDialog.dismissWithAnimation();
//                            }
//                        });
//                        dialog.show();
                    });
                    return;
                }
            }
        });

    }

}
