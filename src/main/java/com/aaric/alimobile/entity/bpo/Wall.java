package com.aaric.alimobile.entity.bpo;

import java.util.Date;

import com.aaric.alimobile.entity.AbstractEntityObject;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Wall.
 * 
 * @author Aaric
 * 
 */
@DatabaseTable
public class Wall extends AbstractEntityObject {

	@DatabaseField(columnName = "_id", generatedId = true)
	private Long id;
	@DatabaseField(columnName = "image_id", unique = true, canBeNull = false)
	private String imageId;
	@DatabaseField(columnName = "image_title", canBeNull = false)
	private String imageTitle;
	@DatabaseField(columnName = "image_src", canBeNull = false)
	private String imageSrc;
	@DatabaseField(columnName = "to_url", canBeNull = false)
	private String imageClickToUrl;
	@DatabaseField(columnName = "image_description")
	private String imageDescription;
	@DatabaseField(columnName = "image_expire_time", canBeNull = false, dataType = DataType.DATE_LONG)
	private Date imageExpireTime;
	@DatabaseField(columnName = "insert_time", canBeNull = false, dataType = DataType.DATE_LONG)
	private Date insertTime;

	public Wall() {
		super();
		this.insertTime = new Date(System.currentTimeMillis());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getImageClickToUrl() {
		return imageClickToUrl;
	}

	public void setImageClickToUrl(String imageClickToUrl) {
		this.imageClickToUrl = imageClickToUrl;
	}

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	public Date getImageExpireTime() {
		return imageExpireTime;
	}

	public void setImageExpireTime(Date imageExpireTime) {
		this.imageExpireTime = imageExpireTime;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

}
