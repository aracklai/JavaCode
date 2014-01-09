package com.arack.secretver1_0;

import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class InsideSecretActivity extends Activity {

	
	TextView secretContentTextView;
	TableLayout secretDetailCommentTableLayout;
	EditText  secretDetailCommentEditText;
	Button secretDetailCommentButton;
	String secretId;
	//ArrayList<CommentNode> commentNodes;
	private static String url_load_comment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.e("onCreate()", "created!");
		setContentView(R.layout.activity_inside_secret);
		secretContentTextView = (TextView) findViewById(R.id.secretDetailTextView); 
		secretContentTextView.setMovementMethod(new ScrollingMovementMethod());
		Intent intent= getIntent();
		String content = intent.getStringExtra("content");
		secretContentTextView.setText(content);
		secretId = intent.getStringExtra("id");
		
		secretDetailCommentTableLayout = (TableLayout) findViewById(R.id.secretDetailCommentTableLayout);
		secretDetailCommentButton = (Button) findViewById(R.id.secretDetailCommentButton);
		secretDetailCommentEditText = (EditText) findViewById(R.id.secretDetailCommentEditText);
		
		//secretDetailCommentButton.setOnClickListener(secretDetailCommentButtonListener);
		
		//commentNodes = new ArrayList<CommentNode>();
		//new LoadComments().execute(secretId,getResources().getString(R.string.showCommentURL));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inside_secret, menu);
		return true;
	}

}
