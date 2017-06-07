package com.aaric.alimobile.utils;

import android.os.Process;

/**
 * System Utils.
 * 
 * @author Aaric
 *
 */
public final class SystemUtils {

	/**
	 * Private constructor.
	 */
	private SystemUtils() {
		super();
	}

	/**
	 * Exit application.
	 */
	public static void exit() {
		Process.killProcess(Process.myPid());
	}

}
