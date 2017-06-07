package com.aaric.alimobile.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Network Utils.
 * 
 * @author Aaric
 *
 */
public final class NetworkUtils {

	/**
	 * Private constructor.
	 */
	private NetworkUtils() {
		super();
	}

	/**
	 * Get connectivity manager.
	 * 
	 * @param context The context to use.
	 * @return
	 */
	private static ConnectivityManager getConnectivityManager(Context context) {
		return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	/**
	 * Whether the network is available.
	 * 
	 * @param context The context to use.
	 * @return
	 */
	public static boolean isNetworkAvail(Context context) {
		ConnectivityManager connectivityManager = getConnectivityManager(context);
		if (null != connectivityManager) {
			NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
			if (null != networkInfos) {
				for (NetworkInfo networkInfo : networkInfos) {
					if (NetworkInfo.State.CONNECTED == networkInfo.getState()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Whether the network of WIFI is available.
	 * 
	 * @param context The context to use.
	 * @return
	 */
	public static boolean isWIFINetworkAvail(Context context) {
		if (isNetworkAvail(context)) {
			ConnectivityManager connectivityManager = getConnectivityManager(context);
			if (null != connectivityManager) {
				NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				// if (null != networkInfo && NetworkInfo.State.CONNECTED == networkInfo.getState()) {
				if (null != networkInfo && networkInfo.isAvailable()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Whether the network of mobile is available.
	 * 
	 * @param context The context to use.
	 * @return
	 */
	public static boolean isMobileNetworkAvail(Context context) {
		if (isNetworkAvail(context)) {
			ConnectivityManager connectivityManager = getConnectivityManager(context);
			if (null != connectivityManager) {
				NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				// if (null != networkInfo && NetworkInfo.State.CONNECTED == networkInfo.getState()) {
				if (null != networkInfo && networkInfo.isAvailable()) {
					return true;
				}
			}
		}
		return false;
	}

}
