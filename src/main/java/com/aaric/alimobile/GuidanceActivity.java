package com.aaric.alimobile;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Guidance Activity.
 * 
 * @author Aaric
 * 
 */
@EActivity(R.layout.activity_guidance)
public class GuidanceActivity extends Activity {

	/**
	 * Parameters.
	 */
	private int index;
	private List<View> mViews;
	private ImageView[] mImageViews;

	/**
	 * Components.
	 */
	@ViewById(R.id.vp_guidance)
	protected ViewPager mViewPager;

	@ViewById(R.id.ll_guidance)
	protected LinearLayout mLinearLayout;

	@AfterViews
	public void init() {
		// ToastUtils.showShort(this, "guidance");

		// Initialize parameters.
		mViews = new ArrayList<View>();
		mViews.add(View.inflate(this, R.layout.activity_guidance_layout_0, null));
		mViews.add(View.inflate(this, R.layout.activity_guidance_layout_1, null));

		// Last view.
		View mViewWithButton = View.inflate(this, R.layout.activity_guidance_layout_2, null);
		Button mButton = (Button) mViewWithButton.findViewById(R.id.btn_guidance_click);
		mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Configuration.
				SharedPreferences sp = v.getContext().getSharedPreferences("config", MODE_PRIVATE);
				Editor editor = sp.edit();
				editor.putBoolean(WelcomeActivity.APPLICATION_IS_FIRST_LAUNCHER, false);
				editor.commit();

				// To Load Main Activity.
				startActivity(new Intent(v.getContext(), MainActivity_.class));

				// Finish.
				finish();

			}

		});
		mViews.add(mViewWithButton);

		// Initialize components.
		mViewPager = (ViewPager) this.findViewById(R.id.vp_guidance);
		mViewPager.setAdapter(new ViewPagerAdapter(mViews));
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// History.
				mImageViews[index].setEnabled(true);
				// Current.
				index = position;
				mImageViews[index].setEnabled(false);
			}

			@Override
			public void onPageScrolled(int position,
					float positionOffset, int positionOffsetPixels) {
				// Nothing.

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// Nothing.

			}

		});

		mLinearLayout = (LinearLayout) this.findViewById(R.id.ll_guidance);
		mImageViews = new ImageView[mLinearLayout.getChildCount()];
		for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
			mImageViews[i] = (ImageView) mLinearLayout.getChildAt(i);
			mImageViews[i].setTag(i);
			mImageViews[i].setEnabled(true);
			mImageViews[i].setOnClickListener(new ImageView.OnClickListener() {

				@Override
				public void onClick(View v) {
					// History.
					mImageViews[index].setEnabled(true);
					// Current.
					index = (Integer) v.getTag();
					mImageViews[index].setEnabled(false);
					mViewPager.setCurrentItem(index);
				}

			});
		}
		index = 0;
		mImageViews[index].setEnabled(false);

	}

	/**
	 * Views Pager Adapter.
	 * 
	 * @author Aaric
	 * 
	 */
	public class ViewPagerAdapter extends PagerAdapter {

		public List<View> views;

		public ViewPagerAdapter(List<View> mmViews) {
			super();
			this.views = mmViews;
		}

		@Override
		public int getCount() {
			return this.views.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view == object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(this.views.get(position), 0);
			return this.views.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(this.views.get(position));
		}

	}

}
