package com.aaric.alimobile.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;

/**
 * Sms Utils.
 * 
 * @author Aaric
 * 
 */
public class SmsUtils {

	/**
	 * SmsUtils
	 */
	private static final String TAG = SmsUtils.class.getName();

	/**
	 * SMS URI.
	 */
	private static final String CONTENT_BASE_URI_SMS = "content://sms";
	private static final String CONTENT_BASE_URI_SMS_SEND = CONTENT_BASE_URI_SMS + "/sent";

	/**
	 * Private constructor.
	 */
	private SmsUtils() {
		super();
	}

	/**
	 * Query all message.
	 * 
	 * @param context
	 *            The context of application
	 * @return
	 */
	public static List<SmsObject> queryAll(Context context) {
		List<SmsObject> smsObjects = new ArrayList<SmsObject>();
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(Uri.parse(CONTENT_BASE_URI_SMS), null, null, null, null);

		// SMS Content Output.
		SmsObject object = null;
		while (cursor.moveToNext()) {
			int _id = cursor.getInt(cursor.getColumnIndex("_id"));
			String address = cursor.getString(cursor.getColumnIndex("address"));
			String body = cursor.getString(cursor.getColumnIndex("body"));
			int type = cursor.getInt(cursor.getColumnIndex("type"));
			int read = cursor.getInt(cursor.getColumnIndex("read"));
			long date = cursor.getLong(cursor.getColumnIndex("date"));
			long date_sent = cursor.getLong(cursor.getColumnIndex("date_sent"));
			object = new SmsObject(_id, address, body, type, 1 == read ? true : false, new Date(date_sent), new Date(date));

			// Added.
			// Log.v(TAG, object.toString());
			smsObjects.add(object);

		}
		cursor.close();
		return smsObjects;
	}

	/**
	 * Insert a message to database which has been read.
	 * 
	 * @param context
	 *            The context of application.
	 * @param address
	 *            The phone number to send.
	 * @param body
	 *            The content to send.
	 * @return
	 */
	public static int insert (Context context, String address, String body) {
		int insert = -1;
		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put("address", address);
		values.put("body", body);
		values.put("type", String.valueOf(1));
		long token = Binder.clearCallingIdentity();
		try {
			Uri contentUri = resolver.insert(Uri.parse(CONTENT_BASE_URI_SMS_SEND), values);
			insert = (int) ContentUris.parseId(contentUri);
			Log.v(TAG, "Insert --> " + insert);
		} finally {
			Binder.restoreCallingIdentity(token);
		}
		return insert;
	}

	public static class SmsObject {

		/**
		 * Type.
		 */
		public static final int TYPE_INBOX = 0x01;
		public static final int TYPE_SENT = 0x02;
		public static final int TYPE_DRAFT = 0x03;
		public static final int TYPE_OUTBOX = 0x04;
		public static final int TYPE_FAILED = 0x05;
		public static final int TYPE_QUEUED = 0x06;

		/**
		 * Read type.
		 */
		public static final int READ_NO = 0x00;
		public static final int READ_OK = 0x01;

		private int id;
		private String address;
		private String body;
		private int type;
		private boolean read;
		private Date sendDate;
		private Date receiptDate;

		public SmsObject() {
			super();
		}

		public SmsObject(int id, String address, String body, int type,
				boolean read, Date sendDate, Date receiptDate) {
			super();
			this.id = id;
			this.address = address;
			this.body = body;
			this.type = type;
			this.read = read;
			this.sendDate = sendDate;
			this.receiptDate = receiptDate;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public boolean getRead() {
			return read;
		}

		public void setRead(boolean read) {
			this.read = read;
		}

		public Date getSendDate() {
			return sendDate;
		}

		public void setSendDate(Date sendDate) {
			this.sendDate = sendDate;
		}

		public Date getReceiptDate() {
			return receiptDate;
		}

		public void setReceiptDate(Date receiptDate) {
			this.receiptDate = receiptDate;
		}

		@Override
		@SuppressLint("SimpleDateFormat")
		public String toString() {
			return "SmsObject [id=" + id + ", address=" + address + ", body="
					+ body + ", type=" + type + ", read=" + read + ", sendDate="
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S").format(sendDate)
					+ ", receiptDate="
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S")
							.format(receiptDate) + "]";
		}

	}

}
