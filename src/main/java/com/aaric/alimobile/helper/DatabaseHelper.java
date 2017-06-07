package com.aaric.alimobile.helper;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aaric.alimobile.entity.bpo.Version;
import com.aaric.alimobile.entity.bpo.VersionUpdateInfo;
import com.aaric.alimobile.entity.bpo.Wall;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database Helper.
 * 
 * @author Aaric
 * 
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	/**
	 * TAG
	 */
	private static final String TAG = DatabaseHelper.class.getSimpleName();

//	/**
//	 * The singleton of database helper.
//	 */
//	private static DatabaseHelper singletonDatabaseHelper = null;

	private static final String DATABASE_NAME = "appdb.db";
	private static final int DATABASE_VERSION = 1;

	/**
	 * The default constructor.
	 * 
	 * @param context
	 *            The context of application
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

//	/**
//	 * Static factory methods.
//	 * 
//	 * @param context
//	 *            The context of application
//	 * @return
//	 */
//	public synchronized static DatabaseHelper getInstance(Context context) {
//		if (null == singletonDatabaseHelper) {
//			singletonDatabaseHelper = new DatabaseHelper(context);
//		}
//		return singletonDatabaseHelper;
//	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(TAG, "Create application database...");
			// TableUtils.createTable(connectionSource, Zodiac.class);
			TableUtils.createTable(connectionSource, Version.class);
			TableUtils.createTable(connectionSource, VersionUpdateInfo.class);
			TableUtils.createTable(connectionSource, Wall.class);

		} catch (SQLException e) {
			Log.e(TAG, "Create application database failure...");
			throw new RuntimeException(e);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(TAG, "onUpgrade...");
			// TableUtils.dropTable(connectionSource, Zodiac.class, true);
			TableUtils.dropTable(connectionSource, Version.class, true);
			TableUtils.dropTable(connectionSource, VersionUpdateInfo.class, true);
			TableUtils.dropTable(connectionSource, Wall.class, true);

			// After we drop the old databases, we create the new ones.
			onCreate(db, connectionSource);

		} catch (Exception e) {
			Log.e(TAG, "Can't drop database...");
			throw new RuntimeException(e);
		}

	}

	@Override
	public void close() {
		super.close();
	}

}
