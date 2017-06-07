package com.aaric.alimobile;

import java.io.File;
import java.io.InputStream;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.util.Xml;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.aaric.alimobile.helper.network.HttpClientHelper;
import com.aaric.alimobile.utils.DownloadUtils;
import com.aaric.alimobile.utils.NetworkUtils;
import com.aaric.alimobile.utils.SystemUtils;
import com.aaric.alimobile.utils.ToastUtils;

/**
 * Main Activity.
 * 
 * @author Aaric
 *
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {
	
	/**
	 * TAG
	 */
	private static final String TAG = MainActivity.class.getSimpleName();
	
	/**
	 * Components.
	 */
	@ViewById(R.id.et_main_username)
	protected EditText mEditTextUserName;
	@ViewById(R.id.et_main_password)
	protected EditText mEditTextPassword;
	@ViewById(R.id.cb_main_keep_password)
	protected CheckBox mCheckBoxKeepPassword;
	@ViewById(R.id.cb_main_auto_login)
	protected CheckBox mCheckBoxAutoLogin;
	@ViewById(R.id.btn_main_login)
	protected Button mButtonLogin;
	@ViewById(R.id.btn_main_sign_up)
	protected Button mButtonSignUp;
	
	@AfterViews
	public void init() {
		Log.v(TAG, "Network: " + NetworkUtils.isNetworkAvail(this));
		// doNetworkCheck();
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		// doNetworkCheck();
		
	}

	/**
	 * Do check network.
	 */
	public void doNetworkCheck() {
		/**
		 * Whether the network is available.
		 */
		if (NetworkUtils.isNetworkAvail(this)) {
			// Network is available.
			ToastUtils.showShort(this, R.string.network_is_available);
			if (NetworkUtils.isWIFINetworkAvail(this)) {
				// WIFI is available
				ToastUtils.showShort(this, R.string.network_wifi_is_available);
				Log.v(TAG, "WIFI: Please login..");
				doNetworkLogin();
				
			} else {
				// WIFI is not available.
				new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.dialog_title_alert)
					.setMessage(R.string.network_wifi_is_not_available)
					.setPositiveButton(R.string.dialog_button_confirm, new DialogInterface.OnClickListener() {
	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Log.v(TAG, "Mobile: Please login..");
							doNetworkLogin();
							
						}
						
					}).setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Log.v(TAG, "Exit applicatoin...");
							SystemUtils.exit();
							
						}
						
					}).show();
				
			}
			
		} else {
			// Network is not available.
			new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle(R.string.dialog_title_hint)
				.setMessage(R.string.network_is_not_available)
				.setPositiveButton(R.string.dialog_button_confirm, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// The mobile network.
						// startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
						// The WIFI network.
						startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
						
					}
					
				}).setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.v(TAG, "Exit applicatoin...");
						SystemUtils.exit();
						
					}
					
				}).show();
			
		}
		
	}
	
	/**
	 * Do login request.
	 */
	public void doNetworkLogin() {
		// Install.
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				doNetworkDownloadAndInstall();
				
			}
		}).start();
		
	}
	
	/**
	 * Download new version apk and install it.
	 */
	public void doNetworkDownloadAndInstall() {
		String DEFAULT_URL_APPLICATION_UPDATE = "http://10.0.2.2:8280/release/";
		try {
			// Get application information of the new version.
			String versionCode =  null, versionName = null, downloadURL = null;
			String url = DEFAULT_URL_APPLICATION_UPDATE + "version.xml";
			InputStream is = HttpClientHelper.getInstance().doInputStreamGet(url);
			if (null != is) {
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(is, HTTP.UTF_8);
				int type = parser.getEventType();
				while (XmlPullParser.END_DOCUMENT != type) {
					// Analysis.
					switch (type) {
					case XmlPullParser.START_TAG:
						if ("versionCode".equals(parser.getName())) {
							versionCode = parser.nextText();
						} else if ("versionName".equals(parser.getName())) {
							versionName = parser.nextText();
						} else if ("downloadURL".equals(parser.getName())) {
							downloadURL = parser.nextText();
						}
						break;
					case XmlPullParser.END_TAG:
						// Nothing.
						break;
					}
					// Next.
					type = parser.next();
				}
			}
			
			// Output.
			Log.v(TAG, "versionCode: " + versionCode);
			Log.v(TAG, "versionName: " + versionName);
			Log.v(TAG, "downloadURL: " + downloadURL);
			
			// If it has download new version, then start intent to install it.
			if (null != versionName && !"".equals(versionName.trim())) {
				if (null == downloadURL || "".equals(downloadURL.trim())) {
					downloadURL = DEFAULT_URL_APPLICATION_UPDATE
							+ this.getPackageName()
							+ "-"
							+ versionName.trim()
							+ ".apk";
				}
				// Download And Install.
				File installFile = new File(Environment.getExternalStorageDirectory(), "install.apk");
				String storagePath = installFile.getAbsolutePath();
				Log.v(TAG, "downloadURL: " + downloadURL);
				if (DownloadUtils.download(MainActivity.this, downloadURL, storagePath)) {
					Log.v(TAG, "Please install apk file.");
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					intent.addCategory("android.intent.category.DEFAULT");
					intent.setDataAndType(Uri.fromFile(installFile), "application/vnd.android.package-archive");
					startActivity(intent);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
