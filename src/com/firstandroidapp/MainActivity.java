package com.firstandroidapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
