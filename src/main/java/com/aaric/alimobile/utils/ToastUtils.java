package com.aaric.alimobile.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast Utils.
 * 
 * @author Aaric
 *
 */
public final class ToastUtils {

	/**
	 * Private constructor.
	 */
	private ToastUtils() {
		super();
	}
	
	/**
	 * Toast show the string.
	 * 
	 * @param context The context to use.
	 * @param text The text to show.
	 * @param duration How long to display the message.
	 */
	public static void show(Context context, CharSequence text, int duration) {
		Toast.makeText(context, text, duration).show();
	}
	
	/**
	 * Toast show the string resource.
	 * 
	 * @param context The context to use.
	 * @param resId The resource id of the string resource to use.
	 * @param duration How long to display the message.
	 */
	public static void show(Context context, int resId, int duration) {
		Toast.makeText(context, resId, duration).show();
	}
	
	/**
	 * Toast show short.
	 * 
	 * @param context The context to use.
	 * @param text The text to show.
	 */
	public static void showShort(Context context, CharSequence text) {
		show(context, text, Toast.LENGTH_SHORT);
	}
	
	/**
	 * Toast show short.
	 * 
	 * @param context The context to use.
	 * @param resId The resource id of the string resource to use.
	 */
	public static void showShort(Context context, int resId) {
		show(context, resId, Toast.LENGTH_SHORT);
	}
	
	/**
	 * Toast show long.
	 * 
	 * @param context The context to use.
	 * @param text The text to show.
	 */
	public static void showLong(Context context, CharSequence text) {
		show(context, text, Toast.LENGTH_LONG);
	}
	
	/**
	 * Toast show long.
	 * 
	 * @param context The context to use.
	 * @param resId The resource id of the string resource to use.
	 */
	public static void showLong(Context context, int resId) {
		show(context, resId, Toast.LENGTH_LONG);
	}

}
