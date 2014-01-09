package com.arack.secretver1_0;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddSecretFragment extends Fragment {
	
	View v;
	EditText et;
	Spinner spinner;
	Button button;
	private ArrayAdapter<String> adapter;
	
	private String[] universities;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		universities = getResources().getStringArray(R.array.universities);
        v = inflater.inflate(R.layout.fragment_add_secret, container , false ); 
        et = (EditText) v.findViewById(R.id.addSecretEditText);
        spinner = (Spinner) v.findViewById(R.id.addSecretSpinner);
        button = (Button) v.findViewById(R.id.addSecretSubmitButton);
        button.setOnClickListener(ocl);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,universities);
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        
        //将adapter 添加到spinner中  
        spinner.setAdapter(adapter);  
          
        //添加事件Spinner事件监听    
        spinner.setOnItemSelectedListener(ssl);  
          
        //设置默认值  
        spinner.setVisibility(View.VISIBLE); 
        return v;
		     
    }
	private OnClickListener ocl = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			new AddSecretAsyncTask().execute(String.valueOf(spinner.getSelectedItemPosition()),et.getText().toString(),getResources().getString(R.string.addSecretsURL));
			et.setText(null);
			
		}
		
	};
	private OnItemSelectedListener ssl = new OnItemSelectedListener()
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			System.out.println("POS:"+arg2);
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};

}
