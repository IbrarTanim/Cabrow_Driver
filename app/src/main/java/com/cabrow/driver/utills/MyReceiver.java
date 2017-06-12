package com.cabrow.driver.utills;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cabrow.driver.activities.SettingActivity;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equalsIgnoreCase("Go Offline")) {
			String s = Context.NOTIFICATION_SERVICE;
			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(s);

			Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
			context.sendBroadcast(it);
			Intent offlineIntent = new Intent(context, SettingActivity.class);
			intent.putExtra("offlineStatus", true);
			AppLog.Log("MyReceiver",
					"" + intent.getBooleanExtra("offlineStatus", false));
			offlineIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			PreferenceHelper pHelper = new PreferenceHelper(context);
			pHelper.putDriverOffline(true);
			context.startActivity(offlineIntent);

			notificationManager.cancelAll();

		} else if (intent.getAction().equalsIgnoreCase("Cancel")) {
			String s = Context.NOTIFICATION_SERVICE;
			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(s);
			Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
			context.sendBroadcast(it);
			notificationManager.cancelAll();
		}
	}

}
