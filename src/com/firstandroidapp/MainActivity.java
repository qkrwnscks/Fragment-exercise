package com.firstandroidapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	private MainFragment mFragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState == null) {
			//add fragment when initial activity sets up
			//state is empty
			mFragment = new MainFragment();
			getSupportFragmentManager()
				.beginTransaction()
				.add(android.R.id.content, mFragment)
				.commit();
		} else {
			//set fragment from restored state info
			mFragment = (MainFragment) getSupportFragmentManager()
					.findFragmentById(android.R.id.content);
		}
		
		//print out key hash, use hash value (catching invalid key hash)
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.firstandroidapp.MainActivity",
					PackageManager.GET_SIGNATURES);
			for (Signature sig : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				Log.d("KeyHash:",Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
			
		} catch (NoSuchAlgorithmException e) {
			
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
