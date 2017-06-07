package com.aaric.alimobile.ui.abs;

import com.aaric.alimobile.R;
import com.aaric.alimobile.utils.SystemUtils;
import com.aaric.alimobile.utils.ToastUtils;

import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Abstract Activity Object.
 * 
 * @author Aaric
 * 
 */
public class AbstractActivityObject extends FragmentActivity {

	/**
	 * Added menus from configuration.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Added menu item clicked listeners.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				ToastUtils.showShort(this, R.string.action_settings);
				return true;
			case R.id.action_exit:
				SystemUtils.exit();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
