package com.douncoding.busnotifier;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.douncoding.busnotifier.data.BusLocation;
import com.douncoding.busnotifier.data.Station;
import com.douncoding.busnotifier.data.repository.StationRepository;
import com.douncoding.busnotifier.net.api.BusLocationApi;

import java.util.List;

public class NotifierService extends Service {
    private static final String TAG = NotifierService.class.getSimpleName();
    private static final int PERIOD_REQUEST_TIME = 10000; // 10SEC

    NotifierBroadcastReceiver mBroadcastReceiver;

    Handler handler = new Handler();

    TakeoffLocationTrackTask mTask;

    StationRepository stationRepository;

    public NotifierService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        stationRepository = StationRepository.getInstance(this);

        setupBroadcastReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy() -> 자동생성 호출");
        registerRestartAlarm();
    }

    private void startForeground() {
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("실행중")
                .setSmallIcon(R.drawable.ic_notification)
                .build();

        startForeground(Constant.SERVICE_FORGROUND_NOTIFICATION_ID, notification);
    }

    private void stopForeground() {
        stopForeground(true);
    }

    private void setupBroadcastReceiver() {
        mBroadcastReceiver = new NotifierBroadcastReceiver();
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter(Constant.NOTIFIER_BROADCAST_ACTION_START);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    class NotifierBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Constant.NOTIFIER_BROADCAST_ACTION_START)) {
                final int idRoute = intent.getIntExtra(EXTRA_PARAM1, 0);
                final int idStation = intent.getIntExtra(EXTRA_PARAM2, 0);
                final String noPlate = intent.getStringExtra(EXTRA_PARAM3);
                handleActionStartTrack(idRoute, idStation, noPlate);
            } else {
                handleActionStopTrack();
            }
        }
    }

    private static final String EXTRA_PARAM1 = "com.douncoding.busnotifier.extra.ROUTE_ID";
    private static final String EXTRA_PARAM2 = "com.douncoding.busnotifier.extra.STATION_ID";
    private static final String EXTRA_PARAM3 = "com.douncoding.busnotifier.extra.PLATE_NO";
    public static void sendStartTrackBroadcast(Context context, int idRoute,
                                                  int idStation, String plate) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        Intent intent = new Intent();
        intent.setAction(Constant.NOTIFIER_BROADCAST_ACTION_START);
        intent.putExtra(EXTRA_PARAM1, idRoute);
        intent.putExtra(EXTRA_PARAM2, idStation);
        intent.putExtra(EXTRA_PARAM3, plate);
        broadcastManager.sendBroadcast(intent);
    }

    public static void sendStopTrackBroadcast(Context context) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        Intent intent = new Intent();
        intent.setAction(Constant.NOTIFIER_BROADCAST_ACTION_STOP);
        broadcastManager.sendBroadcast(intent);
    }

    private void handleActionStartTrack(int idRoute, int idStation, String plate) {
        Log.e("CHECK", "위치 추적 서비스 시작:" + idRoute + "//" + idStation + "//" + plate);
        startForeground();

        handler.removeCallbacks(mTask);
        mTask = new TakeoffLocationTrackTask(idRoute, idStation, plate);
        handler.post(mTask);
    }

    private void handleActionStopTrack() {
        Log.e("CHECK", "위치 추적 서비스 종료");
        stopForeground();

        if (mTask != null) {
            handler.removeCallbacks(mTask);
        }
    }


    private class TakeoffLocationTrackTask implements Runnable {
        int idRoute;
        int idStation;
        String noPlate;

        public TakeoffLocationTrackTask(int idRoute, int idStation, String noPlate) {
            this.idRoute = idRoute;
            this.idStation = idStation;
            this.noPlate = noPlate;
        }

        @Override
        public void run() {
            BusLocationApi.getBusLocationList(idRoute, new BusLocationApi.OnCallback() {
                @Override
                public void onResponse(List<BusLocation> list) {
                    boolean state = false;

                    for (BusLocation busLocation : list) {
                        if (busLocation.getPlateNo().equals(noPlate)) {
                            Log.e("CHECK", "현재위치:" + busLocation.getIdStation());
                            if (idStation == busLocation.getIdStation()) {
                                state = true;
                            }
                        }
                    }

                    if (state) {
                        sendNotification(idStation);
                    } else {
                        handler.postDelayed(mTask, PERIOD_REQUEST_TIME);
                    }
                }
            });
        }
    }

    private void sendNotification(int idStation) {
        List<Station> station = stationRepository.findStationById(idStation);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new android.support.v4.app.NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("하차 준비하세요")
                        .setContentText("2번쨰 정류장전:" + station.get(0).getName())
                        .setSound(alarmSound);

        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(Constant.ARRIVE_NOTIFICATION_ID, mBuilder.build());

        // 자동종료
        stopForeground();
    }

    /**
     * 서비스가 강제종료된 경우 10초 뒤 재시작
     */
    void registerRestartAlarm() {
        Log.w(TAG, "10초 뒤 서비스를 재시작");

        Intent intent = new Intent(this, NotifierService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 10*1000; // 10초 후에 알람이벤트 발생
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 10*1000, pendingIntent);
    }
}
