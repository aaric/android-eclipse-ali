package com.aaric.alimobile.entity.bpo;

import java.util.Date;

import com.aaric.alimobile.entity.AbstractEntityObject;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Version.
 * 
 * @author Aaric
 * 
 */
@DatabaseTable
public class Version extends AbstractEntityObject {

	@DatabaseField(columnName = "_id", generatedId = true)
	private Long id;
	@DatabaseField(columnName = "version_code", unique = true, canBeNull = false)
	private Integer versionCode;
	@DatabaseField(columnName = "version_name", canBeNull = false)
	private String versionName;
	@DatabaseField(columnName = "download_url")
	private String downloadURL;
	@DatabaseField(columnName = "is_update", canBeNull = false)
	private Boolean isUpdate;
	@DatabaseField(columnName = "insert_time", canBeNull = false, dataType = DataType.DATE_LONG)
	private Date insertTime;

	@ForeignCollectionField(eager = true)
	private ForeignCollection<VersionUpdateInfo> versionUpdateInfos;

	public Version() {
		super();
		this.isUpdate = false;
		this.insertTime = new Date(System.currentTimeMillis());
	}

	public Version(Integer versionCode, String versionName, String downloadURL,
			ForeignCollection<VersionUpdateInfo> versionUpdateInfos) {
		super();
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.downloadURL = downloadURL;
		this.isUpdate = false;
		this.insertTime = new Date(System.currentTimeMillis());
		this.versionUpdateInfos = versionUpdateInfos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public Boolean getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public ForeignCollection<VersionUpdateInfo> getVersionUpdateInfos() {
		return versionUpdateInfos;
	}

	public void setVersionUpdateInfos(
			ForeignCollection<VersionUpdateInfo> versionUpdateInfos) {
		this.versionUpdateInfos = versionUpdateInfos;
	}

}
