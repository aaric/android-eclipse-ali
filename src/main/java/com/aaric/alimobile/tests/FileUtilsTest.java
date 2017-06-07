package com.aaric.alimobile.tests;

import com.aaric.alimobile.utils.FileUtils;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Test the class of FileUtils.
 * 
 * @author Aaric
 *
 */
public class FileUtilsTest extends AndroidTestCase {
	
	/**
	 * TAG
	 */
	private static final String TAG = FileUtilsTest.class.getSimpleName();
	
	/**
	 * Test the method of isSDCradAvail.
	 */
	public void testIsSDCradAvail() {
		Log.v(TAG, "isSDCradAvail-->" + FileUtils.isSDCradAvail(1500));
		// Log.v(TAG, "isSDCradAvail-->" + FileUtils.isSDCradAvail(1500L));
	}
	
	/**
	 * Test the method of isInternalAvail.
	 */
	public void testIsInternalAvail() {
		Log.v(TAG, "isInternalAvail-->" + FileUtils.isInternalAvail(1500));
		// Log.v(TAG, "isInternalAvail-->" + FileUtils.isInternalAvail(1500L));
	}

}
