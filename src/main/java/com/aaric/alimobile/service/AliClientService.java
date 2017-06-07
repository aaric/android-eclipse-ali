package com.aaric.alimobile.service;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.aaric.alimobile.service.xml.VersionXmlAnalysis;

/**
 * AliClient Service.
 * 
 * @author Aaric
 * 
 */
@EService
public class AliClientService extends Service {

	/**
	 * TAG
	 */
	private static final String TAG = AliClientService.class.getSimpleName();
	
	@Bean
	protected VersionXmlAnalysis versionXmlAnalysis;

	@AfterInject
	public void init() {
		Log.v(TAG, "AliClientService: init");
		// Timer mTimer = new Timer();
		// mTimer.schedule(new NetworkUpdateTask(this), 0, 1000);
		// Log.v(TAG, "AliClientService: mTimer start");
		
		// Check the version and update the walls.
		versionXmlAnalysis.doVersionChecked();
		versionXmlAnalysis.doWallsUpdate();
		
		// Compare the version with local and download new version application.
		Log.v(TAG, "Download...");
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.v(TAG, "AliClientService: onCreate");
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "AliClientService: onDestroy");
		super.onDestroy();
	}

}
