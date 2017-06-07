package com.aaric.alimobile.entity.bpo;

import com.aaric.alimobile.entity.AbstractEntityObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * VersionUpdateInfo.
 * 
 * @author Aaric
 * 
 */
@DatabaseTable(tableName = "version_update_info")
public class VersionUpdateInfo extends AbstractEntityObject {

	@DatabaseField(columnName = "_id", generatedId = true)
	private Long id;
	@DatabaseField(columnName = "version_description")
	private String versionDescription;
	@DatabaseField(columnName = "version_id", foreign = true, foreignColumnName = "_id", foreignAutoRefresh = true)
	private Version version;

	public VersionUpdateInfo() {
		super();
	}

	public VersionUpdateInfo(String versionDescription) {
		super();
		this.versionDescription = versionDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersionDescription() {
		return versionDescription;
	}

	public void setVersionDescription(String versionDescription) {
		this.versionDescription = versionDescription;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

}
