package com.aaric.alimobile.service.xml;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.RootContext;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.aaric.alimobile.consts.Ali;
import com.aaric.alimobile.entity.bpo.Version;
import com.aaric.alimobile.entity.bpo.VersionUpdateInfo;
import com.aaric.alimobile.entity.bpo.Wall;
import com.aaric.alimobile.helper.DatabaseHelper;
import com.aaric.alimobile.helper.network.HttpClientHelper;
import com.aaric.alimobile.utils.NetworkUtils;
import com.j256.ormlite.dao.Dao;

/**
 * Version XML Analysis.
 * 
 * @see https://github.com/github-aaric/ali-client/blob/master/release/version.xml
 * @author Aaric
 * 
 */
@EBean(scope = Scope.Singleton)
public class VersionXmlAnalysis {

	/**
	 * TAG
	 */
	private static final String TAG = VersionXmlAnalysis.class.getSimpleName();

	/**
	 * Application update site.
	 */
	private static final String DEFAULT_URL_UPDATE_SITE = Ali.DEFAULT_URL_RELEASE_UPDATE + "version.xml";

	/**
	 * The context to use.
	 */
	@RootContext
	protected Context context;

	/**
	 * The OrmLiteDao which need to be injected.
	 */
	@OrmLiteDao(helper = DatabaseHelper.class, model = Version.class)
	protected Dao<Version, Integer> versionDao;
	@OrmLiteDao(helper = DatabaseHelper.class, model = VersionUpdateInfo.class)
	protected Dao<VersionUpdateInfo, Integer> versionUpdateInfoDao;
	@OrmLiteDao(helper = DatabaseHelper.class, model = Wall.class)
	protected Dao<Wall, Integer> wallDao;

	/**
	 * Check the version form network.
	 */
	@Background
	public void doVersionChecked() {
		// Define some parameters.
		Version version = null;
		VersionUpdateInfo versionUpdateInfo = null;
		List<VersionUpdateInfo> versionUpdateInfos = null;

		// Whether is the network available?
		if (NetworkUtils.isNetworkAvail(context)) {
			try {
				InputStream is = HttpClientHelper.getInstance().doInputStreamGet(DEFAULT_URL_UPDATE_SITE);
				if (null != is) {
					XmlPullParser parser = Xml.newPullParser();
					parser.setInput(is, HTTP.UTF_8);
					int type = parser.getEventType();
					while (XmlPullParser.END_DOCUMENT != type) {
						// Analysis.
						switch (type) {
						case XmlPullParser.START_DOCUMENT:
							version = new Version();
							break;
						case XmlPullParser.START_TAG:
							if ("versionCode".equals(parser.getName())) {
								version.setVersionCode(Integer.parseInt(parser.nextText()));
							} else if ("versionName".equals(parser.getName())) {
								version.setVersionName(parser.nextText());
							} else if ("downloadURL".equals(parser.getName())) {
								version.setDownloadURL(parser.nextText());
							} else if ("description".equals(parser.getName())) {
								versionUpdateInfos = new ArrayList<VersionUpdateInfo>();
							} else if ("item".equals(parser.getName())) {
								versionUpdateInfo = new VersionUpdateInfo();
								versionUpdateInfo.setVersionDescription(parser.nextText());
								versionUpdateInfos.add(versionUpdateInfo);
								versionUpdateInfo = null;
							}
							break;
						case XmlPullParser.END_TAG:
							break;
						}

						// Next.
						type = parser.next();

					}

				}

				// Output.
				if (null != version && null != versionUpdateInfos
						&& 0 != versionUpdateInfos.size()) {
					Map<String, Object> fieldValues = new HashMap<String, Object>();
					fieldValues.put("version_code", version.getVersionCode());
					List<Version> list = versionDao.queryForFieldValues(fieldValues);
					if (null == list || 0 == list.size()) {
						versionDao.create(version);
						for (VersionUpdateInfo object : versionUpdateInfos) {
							object.setVersion(version);
							versionUpdateInfoDao.create(object);
						}
					}

				}

			} catch (Exception e) {
				Log.e(TAG, "VersionXmlAnalysis: doVersionChecked has an error by " + e.getMessage());
			}

		}

	}

	/**
	 * Update the walls from network.
	 */
	@Background
	public void doWallsUpdate() {
		// Define some parameters.
		Wall wall = null;
		List<Wall> walls = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);

		// Whether is the network available?
		if (NetworkUtils.isNetworkAvail(context)) {
			try {
				InputStream is = HttpClientHelper.getInstance().doInputStreamGet(DEFAULT_URL_UPDATE_SITE);
				if (null != is) {
					XmlPullParser parser = Xml.newPullParser();
					parser.setInput(is, HTTP.UTF_8);
					int type = parser.getEventType();
					while (XmlPullParser.END_DOCUMENT != type) {
						// Analysis.
						switch (type) {
						case XmlPullParser.START_DOCUMENT:
							break;
						case XmlPullParser.START_TAG:
							if ("walls".equals(parser.getName())) {
								walls = new ArrayList<Wall>();
							} else if ("wall".equals(parser.getName())) {
								wall = new Wall();
							} else if ("imageId".equals(parser.getName())) {
								wall.setImageId(parser.nextText());
							} else if ("imageTitle".equals(parser.getName())) {
								wall.setImageTitle(parser.nextText());
							} else if ("imageSrc".equals(parser.getName())) {
								wall.setImageSrc(parser.nextText());
							} else if ("imageClickToUrl".equals(parser
									.getName())) {
								wall.setImageClickToUrl(parser.nextText());
							} else if ("imageDescription".equals(parser.getName())) {
								wall.setImageDescription(parser.nextText());
							} else if ("imageExpireTime".equals(parser.getName())) {
								wall.setImageExpireTime(dateFormat.parse(parser.nextText()));
							}
							break;
						case XmlPullParser.END_TAG:
							if ("wall".equals(parser.getName())) {
								walls.add(wall);
								wall = null;
							}
							break;
						}

						// Next.
						type = parser.next();

					}

				}

				// Output.
				if (null != walls) {
					if (0 != walls.size()) {
						List<Wall> list = null;
						Map<String, Object> fieldValues = null;
						for (Wall object : walls) {
							// Log.v(TAG, "-----" + object.toString());
							fieldValues = new HashMap<String, Object>();
							fieldValues.put("image_id", object.getImageId());
							list = wallDao.queryForFieldValues(fieldValues);
							if (null == list || 0 == list.size()) {
								wallDao.create(object);
							}
						}
					} else {
						// If the size of walls equals 0, delete all object now.
						List<Wall> list = wallDao.queryForAll();
						wallDao.delete(list);
					}

				}

			} catch (Exception e) {
				Log.e(TAG, "VersionXmlAnalysis: doWallsUpdate has an error by " + e.getMessage());
			}

		}

	}

	/**
	 * Download new version apk and install it.
	 */
	public void doNetworkDownloadAndInstall() {
		// Check version with database and compare version with itself.
		

		// If the apk has been download to install it, or to continue downloading it...
		

	}

}
