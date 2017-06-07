package com.aaric.alimobile.tests;

import com.aaric.alimobile.utils.NetworkUtils;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Test the class of NetworkUtils.
 * 
 * @author Aaric
 *
 */
public class NetworkUtilsTest extends AndroidTestCase {

	/**
	 * TAG
	 */
	private static final String TAG = NetworkUtilsTest.class.getSimpleName();

	/**
	 * Test the method of isAvail.
	 */
	public void testIsAvail() {
		Log.v(TAG, "NET-->" + NetworkUtils.isNetworkAvail(this.getContext()));
	}

	/**
	 * Test the method of isWIFIAvail.
	 */
	public void testIsWIFIAvail() {
		Log.v(TAG, "WIFI-->" + NetworkUtils.isWIFINetworkAvail(this.getContext()));
	}

	/**
	 * Test the method of isMobileAvail.
	 */
	public void testIsMobileAvail() {
		Log.v(TAG, "3G-->" + NetworkUtils.isMobileNetworkAvail(this.getContext()));
	}

}
