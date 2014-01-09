package com.arack.secretver1_0;

import java.util.ArrayList;
import java.util.Map;








import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class AllSecretFragment extends Fragment {
	
	public static ListView secretListView;
	int secretPos = 0;
	
	private OnScrollListener secretsListViewOnScrollListener = new OnScrollListener()
	{

		@Override
		public void onScroll(AbsListView lw, final int firstVisibleItem,
                final int visibleItemCount, final int totalItemCount)
		{
			 
			if ( lw.getId() == R.id.secretsListView )
			{		System.out.println("Scrolling");
           // Make your calculation stuff here. You have all your
           // needed info from the parameters of this function.

           // Sample calculation to determine if the last 
           // item is fully visible.	
					
					final int lastItem = firstVisibleItem + visibleItemCount;
					System.out.println("firstVisibleItem:"+firstVisibleItem);
					System.out.println("VisibleCount:"+visibleItemCount);
					System.out.println("totalItemCount:"+totalItemCount);
					if(lastItem == totalItemCount && lastItem != 0 )
					{
						try{
							secretsList.addAll(new GetAllSecretAsyncTask(getActivity()).execute(String.valueOf(secretPos),getResources().getString(R.string.getAllSecretsURL)).get());
							adapter.notifyDataSetChanged();
							secretPos += 20;
						}
						catch ( Exception e )
						{
							e.printStackTrace();
						}
			
					}
					
			}
		}

   

		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {
			//System.out.print("Scrolling");
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	public static ArrayList<Map<String, Object>> secretsList;
	
	public static SecretListViewAdapter adapter;
	View v; 
	//FragmentActivity act;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		//act = this.getActivity();
		
		if (v == null )
		{
			adapter = new SecretListViewAdapter(getActivity());
			secretsList = new ArrayList<Map<String, Object>>();
			
			try
			{
				//secretsList.addAll(new GetAllSecretAsyncTask(getActivity()).execute(String.valueOf(secretPos),getResources().getString(R.string.getAllSecretsURL)).get());
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
		}
		
		v = new View(getActivity());
		
		v = inflater.inflate(R.layout.fragment_all_secret, container , false); 
		secretListView = (ListView) v.findViewById(R.id.secretsListView);
		secretListView.setOnScrollListener(secretsListViewOnScrollListener);
		secretListView.setAdapter(adapter);
		

		return v;
    } 
	
	public void onSaveInstanceState(Bundle icicle) {

        // NEVER CALLED
		System.out.println("called onsave()");
        super.onSaveInstanceState(icicle);
        //More stuff
      }
	
	public void onPause ()
	{
		super.onPause();
		System.out.println("fragment on paused");
	}
	public void onDestroyView ()
	{
		super.onDestroyView ();
		System.out.println("fragment on DestroyView");
	}
	

}
