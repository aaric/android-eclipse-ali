package com.aaric.alimobile.tests;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;
import android.util.Xml;

import com.aaric.alimobile.entity.bpo.Version;
import com.aaric.alimobile.entity.bpo.VersionUpdateInfo;
import com.aaric.alimobile.entity.bpo.Wall;
import com.aaric.alimobile.helper.DatabaseHelper;
import com.aaric.alimobile.helper.network.HttpClientHelper;
import com.j256.ormlite.dao.Dao;

/**
 * Test the class of UpdateAppTest.
 * 
 * @author Aaric
 * 
 */
public class AppUpdateTest extends AndroidTestCase {

	/**
	 * TAG
	 */
	private static final String TAG = AppUpdateTest.class.getSimpleName();

	/**
	 * Update application base URL.
	 */
	public static final String DEFAULT_URL_APPLICATION_UPDATE = "http://10.0.2.2:8280/release/";

	/**
	 * Test file path.
	 * 
	 * @throws NameNotFoundException
	 */
	public void testFilePath() throws NameNotFoundException {
		PackageInfo pkgInfo = this
				.getContext()
				.getPackageManager()
				.getPackageInfo(this.getContext().getPackageName(),
						PackageManager.GET_CONFIGURATIONS);
		Log.v(TAG, pkgInfo.versionCode + "");
		Log.v(TAG, pkgInfo.versionName);
		Log.v(TAG, this.getContext().getPackageName());
		Log.v(TAG, this.getContext().getCacheDir().getAbsolutePath());
		Log.v(TAG, this.getContext().getFilesDir().getAbsolutePath());
		Log.v(TAG, Environment.getDownloadCacheDirectory().getAbsolutePath());

	}

	/**
	 * Test get version XML.
	 */
	public void testGetVersionXML() {
		String url = DEFAULT_URL_APPLICATION_UPDATE + "version.xml";
		String xml = HttpClientHelper.getInstance().doGet(url);
		Log.v(TAG, xml);

	}

	/**
	 * Test parse version XML.
	 * 
	 * @throws Exception
	 */
	public void testParseVersionXML() throws Exception {
		String versionCode = null;
		String versionName = null;
		String downloadURL = null;
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

	}

	/**
	 * Test parse new version XML.
	 * Sample:
	 * <?xml version="1.0" encoding="UTF-8"?>
	 * <version>
	 *     <versionCode>2</versionCode>
	 *     <versionName>0.0.2</versionName>
	 *     <downloadURL></downloadURL>
	 *     <description>
	 *         <item>1.To optimize the user interface (UI).</item>
	 *         <item>2.Enhance the usage experience.</item>
	 *     </description>
	 *     <walls>
	 *         <wall>
	 *             <imageId>1409646070510</imageId>
	 *             <imageTitle>Baidu</imageTitle>
	 *             <imageSrc>http://www.baidu.com/img/bdlogo.png</imageSrc>
	 *             <imageClickToUrl>http://www.baidu.com/</imageClickToUrl>
	 *             <imageDescription>This is an url for baidu.</imageDescription>
	 *             <imageExpireTime>2014-11-11 00:00:00</imageExpireTime>
	 *         </wall>
	 *         <wall>
	 *             <imageId>1409646083110</imageId>
	 *             <imageTitle>Google</imageTitle>
	 *             <imageSrc>https://www.google.com/images/srpr/logo11w.png</imageSrc>
	 *             <imageClickToUrl>https://www.google.com/?gws_rd=ssl</imageClickToUrl>
	 *             <imageDescription>This is an url for google.</imageDescription>
	 *             <imageExpireTime>2014-11-11 00:00:00</imageExpireTime>
	 *         </wall>
	 *     </walls>
	 * </version>
	 * 
	 * @throws Exception
	 */
	public void testParseNewVersionXML() throws Exception {
		Version version = null;
		VersionUpdateInfo versionUpdateInfo = null;
		List<VersionUpdateInfo> versionUpdateInfos = null;
		Wall wall = null;
		List<Wall> walls = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
		String url = DEFAULT_URL_APPLICATION_UPDATE + "version.xml";
		InputStream is = HttpClientHelper.getInstance().doInputStreamGet(url);
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
							// versionCode = parser.nextText();
							version.setVersionCode(Integer.parseInt(parser.nextText()));
	
						} else if ("versionName".equals(parser.getName())) {
							// versionName = parser.nextText();
							version.setVersionName(parser.nextText());
	
						} else if ("downloadURL".equals(parser.getName())) {
							// downloadURL = parser.nextText();
							version.setDownloadURL(parser.nextText());
	
						} else if("description".equals(parser.getName())) {
							versionUpdateInfos = new ArrayList<VersionUpdateInfo>();
							
						} else if("item".equals(parser.getName())) {
							versionUpdateInfo = new VersionUpdateInfo();
							versionUpdateInfo.setVersionDescription(parser.nextText());
							versionUpdateInfos.add(versionUpdateInfo);
							versionUpdateInfo = null;
							
						} else if("walls".equals(parser.getName())) {
							walls = new ArrayList<Wall>();
							
						} else if("wall".equals(parser.getName())) {
							wall = new Wall();
							
						} else if("imageId".equals(parser.getName())) {
							wall.setImageId(parser.nextText());
							
						} else if("imageTitle".equals(parser.getName())) {
							wall.setImageTitle(parser.nextText());
							
						} else if("imageSrc".equals(parser.getName())) {
							wall.setImageSrc(parser.nextText());
							
						} else if("imageClickToUrl".equals(parser.getName())) {
							wall.setImageClickToUrl(parser.nextText());
							
						} else if("imageDescription".equals(parser.getName())) {
							wall.setImageDescription(parser.nextText());
							
						} else if("imageExpireTime".equals(parser.getName())) {
							wall.setImageExpireTime(dateFormat.parse(parser.nextText()));
							
						}
						break;
					case XmlPullParser.END_TAG:
						if ("wall".equals(parser.getName())) {
							Log.v(TAG, "---------wall");
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
		DatabaseHelper helper = new DatabaseHelper(this.getContext());
		Dao<Version, Integer> versionDao = helper.getDao(Version.class);
		Dao<VersionUpdateInfo, Integer> versionUpdateInfoDao = helper.getDao(VersionUpdateInfo.class);
		Dao<Wall, Integer> wallDao = helper.getDao(Wall.class);
		
		//=============================================//
		Log.v(TAG, "object-->" + version);
		versionDao.create(version);
		if (null != versionUpdateInfos && 0 != versionUpdateInfos.size()) {
			for (VersionUpdateInfo object : versionUpdateInfos) {
				Log.v(TAG, "item-->" + object);
				object.setVersion(version);
				versionUpdateInfoDao.create(object);
			}
		}
		if (null != walls && 0 != walls.size()) {
			for (Wall object : walls) {
				Log.v(TAG, "wall-->" + object);
				wallDao.create(object);
			}
		}
		//=============================================//
		
		helper.close();
		versionDao = null;
		versionUpdateInfoDao = null;
		wallDao = null;

	}

}
