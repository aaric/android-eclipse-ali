package com.aaric.alimobile.utils;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * File Utils.
 * 
 * @author Aaric
 * 
 */
public final class FileUtils {

	/**
	 * TAG
	 */
	private static final String TAG = FileUtils.class.getSimpleName();

	/**
	 * Assets base directory.
	 */
	public static final String DEFAULT_ASSETS_DIRECTORY = "file:///android_asset/";

	/**
	 * External base directory.
	 */
	public static final File DEFAULT_EXTERNAL_DIRECTORY = Environment.getExternalStorageDirectory();

	/**
	 * External base directory.
	 */
	public static final String DEFAULT_EXTERNAL_DIRECTORY_PATH = DEFAULT_EXTERNAL_DIRECTORY
			.getAbsolutePath();

	/**
	 * Internal base directory.
	 */
	public static final File DEFAULT_INTERNAL_DIRECTORY = Environment.getDataDirectory();

	/**
	 * Internal base directory.
	 */
	public static final String DEFAULT_INTERNAL_DIRECTORY_PATH = DEFAULT_INTERNAL_DIRECTORY
			.getAbsolutePath();

	/**
	 * Private constructor.
	 */
	private FileUtils() {
		super();
	}

	/**
	 * Whether the SDcard is available.
	 * 
	 * @param applyForMallocZize
	 *            Apply for malloc size.
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isSDCradAvail(int applyForMallocZize) {
		try {
			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
				StatFs externalStatFs = new StatFs(DEFAULT_EXTERNAL_DIRECTORY_PATH);
				int externalBlockSize = externalStatFs.getBlockSize();
				int externalAvailableBlocks = externalStatFs.getAvailableBlocks();
				int externalAvailableSize = externalAvailableBlocks * externalBlockSize;
				if (externalAvailableSize > applyForMallocZize) {
					return true;
				}
			}
		} catch (Exception e) {
			Log.i(TAG, "The external storage is not available...");
		}
		return false;
	}

	/**
	 * Whether the internal memory is available.
	 * 
	 * @param applyForMallocZize
	 *            Apply for malloc size.
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isInternalAvail(int applyForMallocZize) {
		try {
			StatFs internalStatFs = new StatFs(DEFAULT_INTERNAL_DIRECTORY_PATH);
			int internalBlockSize = internalStatFs.getBlockSize();
			int internalAvailableBlocks = internalStatFs.getAvailableBlocks();
			int internalAvailableSize = internalAvailableBlocks * internalBlockSize;
			if (internalAvailableSize > applyForMallocZize) {
				return true;
			}

		} catch (Exception e) {
			Log.i(TAG, "The internal storage is not available...");
		}

		return false;
	}

//	/**
//	 * Whether the SDcard is available.
//	 * 
//	 * @param applyForMallocZize
//	 *            Apply for malloc size.
//	 * @return
//	 */
//	@SuppressLint("NewApi")
//	public static boolean isSDCradAvail(long applyForMallocZize) {
//		try {
//			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//				StatFs externalStatFs = new StatFs(DEFAULT_EXTERNAL_DIRECTORY_PATH);
//				long externalBlockSize = externalStatFs.getBlockSizeLong();
//				long externalAvailableBlocks = externalStatFs.getAvailableBlocksLong();
//				long externalAvailableSize = externalAvailableBlocks * externalBlockSize;
//				if (externalAvailableSize > applyForMallocZize) {
//					return true;
//				}
//			}
//		} catch (Exception e) {
//			Log.i(TAG, "The external storage is not available...");
//		}
//		return false;
//	}
//
//	/**
//	 * Whether the internal memory is available.
//	 * 
//	 * @param applyForMallocZize
//	 *            Apply for malloc size.
//	 * @return
//	 */
//	@SuppressLint("NewApi")
//	public static boolean isInternalAvail(long applyForMallocZize) {
//		try {
//			StatFs internalStatFs = new StatFs(DEFAULT_INTERNAL_DIRECTORY_PATH);
//			long internalBlockSize = internalStatFs.getBlockSizeLong();
//			long internalAvailableBlocks = internalStatFs.getAvailableBlocksLong();
//			long internalAvailableSize = internalAvailableBlocks * internalBlockSize;
//			if (internalAvailableSize > applyForMallocZize) {
//				return true;
//			}
//
//		} catch (Exception e) {
//			Log.i(TAG, "The internal storage is not available...");
//		}
//
//		return false;
//	}

}
