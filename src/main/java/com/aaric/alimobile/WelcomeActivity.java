package com.aaric.alimobile;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;

import com.aaric.alimobile.service.AliClientService_;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Welcome Activity.
 * 
 * @author Aaric
 * 
 */
@EActivity
public class WelcomeActivity extends Activity {

	/**
	 * Constants.
	 */
	public static final String APPLICATION_IS_FIRST_LAUNCHER = "application_is_first_launcher";

	@AfterInject
	protected void init() {
		// Start Ali Service.
		startService(new Intent(this, AliClientService_.class));
		
		// Query configuration.
		SharedPreferences sp = this
				.getSharedPreferences("config", MODE_PRIVATE);
		boolean isFirst = sp.getBoolean(APPLICATION_IS_FIRST_LAUNCHER, true);
		if (isFirst) {
			// To Load Guidance Activity.
			startActivity(new Intent(this, GuidanceActivity_.class));
		} else {
			// To Load Main Activity.
			startActivity(new Intent(this, MainActivity_.class));
		}

		// Finish.
		finish();

	}

}
