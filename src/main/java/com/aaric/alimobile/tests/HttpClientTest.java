package com.aaric.alimobile.tests;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.util.VersionInfo;

import com.aaric.alimobile.helper.network.HttpClientHelper;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Test the class of HttpClientHelper.
 * 
 * @author Aaric
 * 
 */
public class HttpClientTest extends AndroidTestCase {

	/**
	 * TAG
	 */
	private static final String TAG = HttpClientTest.class.getSimpleName();

	/**
	 * Test the version of HttpClient.
	 */
	@Deprecated
	public void testVersion() {
		VersionInfo[] versionInfos = VersionInfo.loadVersionInfo(new String[] {
				"org.apache.http", "org.apache.http.client" }, this.getClass()
				.getClassLoader());
		if (null != versionInfos && 0 != versionInfos.length) {
			for (VersionInfo versionInfo : versionInfos) {
				Log.v(TAG, "VersionInfo: " + versionInfo);
			}
		}

	}

	/**
	 * Test the method of doGet.
	 */
	public void testDoGet() {
		String url = "http://10.0.2.2:8280/release/version.xml";
		String result = HttpClientHelper.getInstance().doGet(url);
		Log.v(TAG, "result: " + result);

	}

	/**
	 * Test the method of doPost.
	 */
	public void testDoPost() {
		String url = "http://10.0.2.2:8280/submit.jsp";
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("username", "admin");
		args.put("password", "123456");
		String result = HttpClientHelper.getInstance().doPost(url, args);
		Log.v(TAG, "result: " + result);

	}

}
