package com.example.subwaymateui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.LocaleHelper;
import com.example.ThemeUtil;
import com.example.subwaymateui.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ThemeUtil.applyTheme(ThemeUtil.modLoad(MainActivity.this));

        LocaleHelper.preferences = getSharedPreferences(LocaleHelper.FILE_NAME, Activity.MODE_PRIVATE);
        LocaleHelper.setLocale(MainActivity.this, LocaleHelper.preferences.getString(LocaleHelper.SELECTED_LANGUAGE, "ko"));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_community, R.id.navigation_statistics)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if(isFirst == true) {
            //to apply current language or theme at the first run
            Log.d("isFirst: true", "onCreate: in isFirst==true conditional statement!@#@!$!@$@!");
            isFirst = false;
            recreate();
        }

        showDialog();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public void onBackPressed(){
        //???????????? ????????? ????????? ?????????
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("??????")
                .setMessage("???????????? ??????????")
                .setPositiveButton("???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNeutralButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "???????????? ????????????", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
    }

    public void showDialog() {
        // ??? ?????? ??? ?????? ????????? ????????? ??????????????? ?????? ??? ?????????
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("??????");
        builder.setMessage("\n????????? ?????? ?????? ????????? ???????????????????");
        builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
            private static final String CHANNEL_ID = "channel1";

            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                // "???" ?????? ?????? ??? ???????????? ?????????
                Toast.makeText(getBaseContext(), "???????????? ?????? ?????? ????????? ???????????????!", Toast.LENGTH_SHORT).show();

                // ?????? ????????? ?????? ??????
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // ?????? ?????? ?????? ??? ??????
                    private createNotificationChannel(){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            CharSequence name = getString(R.string.channel_name);
                            String description = getString(R.string.channel_description);
                            int importance = NotificationManager.IMPORTANCE_DEFAULT;
                            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                            channel.setDescription(description);
                            NotificationManager notificationManager = getSystemService(NotificationManager.class);
                            notificationManager.createNotificationChannel(channel);
                        }
                    }
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationChannel notificationChannel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationChannel.setDescription("channel description");
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
                    notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                    notificationManager.createNotificationChannel(notificationChannel);
                }
            }
        });
        builder.setNegativeButton("?????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                Toast.makeText(getBaseContext(), "???????????? ?????? ?????? ????????? ???????????? ????????????!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("??????", null);
        builder.create().show();
    }
}