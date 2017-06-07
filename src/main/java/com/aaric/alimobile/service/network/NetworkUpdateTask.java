package com.aaric.alimobile.service.network;

import java.util.TimerTask;

import com.aaric.alimobile.utils.NetworkUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Network Update Task.
 * 
 * @author Aaric
 * 
 */
public class NetworkUpdateTask extends TimerTask {

	/**
	 * TAG
	 */
	private static final String TAG = NetworkUpdateTask.class.getSimpleName();

	/**
	 * MSG_WHAT
	 */
	private static final int MSG_WHAT_DEAL = 0x01;

	private static long index = 0;

	private Context context;

	private Handler mHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_WHAT_DEAL:
				Log.v(TAG, "--index: " + index);
				return true;
			}
			return false;
		}

	});

	public NetworkUpdateTask(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void run() {
		/**
		 * Check network and update data.
		 */
		if (NetworkUtils.isNetworkAvail(context)) {
			// Simple Output.
			++index;
			// Log.v(TAG, "--index: " + index);
			Message msg = new Message();
			msg.what = MSG_WHAT_DEAL;
			msg.obj = index;
			mHandler.sendMessage(msg);

		} else {
			Log.v(TAG, "Network is not Availiable...");
		}

	}

}
