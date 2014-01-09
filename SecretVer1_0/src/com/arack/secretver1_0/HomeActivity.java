package com.arack.secretver1_0;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends FragmentActivity {
	private FragmentTabHost mTabHost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);  
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        
        mTabHost.addTab(mTabHost.newTabSpec("All")  
                .setIndicator("ALL"), AllSecretFragment.class, null); 
        mTabHost.addTab(mTabHost.newTabSpec("Add")  
                .setIndicator("Add"), AddSecretFragment.class, null); 
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
		
		System.out.println("refreshed");
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        
     
        case R.id.action_refresh:
        	try
        	{
        		AllSecretFragment.secretsList.clear();
        		AllSecretFragment.secretsList.addAll(new GetAllSecretAsyncTask(this).execute(String.valueOf(0),getResources().getString(R.string.getAllSecretsURL)).get());
        		AllSecretFragment.adapter.notifyDataSetChanged();
        		AllSecretFragment.secretListView.setSelection(0);
        	}
        	catch ( Exception e )
        	{
        		e.printStackTrace();
        	}
            return true;
   
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
}
