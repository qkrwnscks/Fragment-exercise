package com.firstandroidapp;

//import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
//import com.facebook.model.GraphObject;
import com.facebook.widget.LoginButton;


public class MainFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main,container,false);
		
		//overrides default onActivityResult in main activity
		//allow main fragment to handle results
		LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		authButton.setFragment(this);
		//reading permissions
		//authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
		
		return view;
	}
	
	//debugging tag
	private static final String TAG = "Main Fragment";
	
	//open state: authenticated UI reveals new buttons
	//closed state: authenticated UI hides new button
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception){
		if(state.isOpened()) {
			Log.i(TAG,"Logged in");
		} else if (state.isClosed()) {
			Log.i(TAG,"Logged out");
		}
	}
	
	//populates logic that listens for onSessionStateChanges
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session,state,exception);			
		}
	};
	
	private UiLifecycleHelper uiHelper;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}
	
	//override fragment life cycle methods, ends instance of helper
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onResume() {
		super.onResume();
		// error handling, case: when user session is not null
		Session session = Session.getActiveSession();
		if (session != null &&
				session.isOpened() || session.isClosed()) {
			onSessionStateChange(session, session.getState(), null);
		}
		uiHelper.onResume();
	}
	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
	
//	private interface GraphFeed extends GraphObject {
//		String getName();
//		String getFeed();
//	}

}
