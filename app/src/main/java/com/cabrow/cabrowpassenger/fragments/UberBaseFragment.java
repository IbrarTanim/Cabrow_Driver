package com.cabrow.cabrowpassenger.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.Volley;
import com.androidquery.callback.ImageOptions;
import com.cabrow.cabrowpassenger.R;
import com.cabrow.cabrowpassenger.activities.MainDrawerActivity;
import com.cabrow.cabrowpassenger.parse.AsyncTaskCompleteListener;
import com.cabrow.cabrowpassenger.parse.VolleyHttpRequest;
import com.cabrow.cabrowpassenger.utils.AndyUtils;
import com.cabrow.cabrowpassenger.utils.Const;
import com.cabrow.cabrowpassenger.utils.PreferenceHelper;

import java.util.HashMap;


/**
 * @author Hardik A Bhalodi
 */
@SuppressLint("ValidFragment")
abstract public class UberBaseFragment extends Fragment implements
		OnClickListener, AsyncTaskCompleteListener, ErrorListener {
	MainDrawerActivity activity;
	private RequestQueue requestQueue;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (MainDrawerActivity) getActivity();
		requestQueue = Volley.newRequestQueue(activity);
	}

	protected abstract boolean isValidate();

	@Override
	public void onTaskCompleted(final String response, int serviceCode) {

	}

	protected ImageOptions getAqueryOption() {
		ImageOptions options = new ImageOptions();
		options.targetWidth = 200;
		options.memCache = true;
		options.fallback = R.drawable.default_user;
		options.fileCache = true;
		return options;
	}

	protected void login() {
		if (!AndyUtils.isNetworkAvailable(activity)) {
			AndyUtils.showToast(getResources().getString(R.string.no_internet),
					activity);
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Const.URL, Const.ServiceType.LOGIN);
		map.put(Const.Params.EMAIL, activity.pHelper.getEmail());
		map.put(Const.Params.PASSWORD, activity.pHelper.getPassword());
		map.put(Const.Params.DEVICE_TYPE, Const.DEVICE_TYPE_ANDROID);
		map.put(Const.Params.DEVICE_TOKEN, activity.pHelper.getDeviceToken());
		map.put(Const.Params.LOGIN_BY, Const.MANUAL);
		// new HttpRequester(activity, map, Const.ServiceCode.LOGIN, this);
		requestQueue.add(new VolleyHttpRequest(Method.POST, map,
				Const.ServiceCode.LOGIN, this, this));

	}

	protected void loginSocial(String id, String loginType) {
		if (!AndyUtils.isNetworkAvailable(activity)) {
			AndyUtils.showToast(getResources().getString(R.string.no_internet),
					activity);
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Const.URL, Const.ServiceType.LOGIN);
		map.put(Const.Params.SOCIAL_UNIQUE_ID, id);
		map.put(Const.Params.DEVICE_TYPE, Const.DEVICE_TYPE_ANDROID);
		map.put(Const.Params.DEVICE_TOKEN,
				new PreferenceHelper(activity).getDeviceToken());
		map.put(Const.Params.LOGIN_BY, loginType);
		// new HttpRequester(activity, map, Const.ServiceCode.LOGIN, this);
		requestQueue.add(new VolleyHttpRequest(Method.POST, map,
				Const.ServiceCode.LOGIN, this, this));
	}

}
