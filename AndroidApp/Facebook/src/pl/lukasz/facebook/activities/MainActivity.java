package pl.lukasz.facebook.activities;

import pl.lukasz.facebook.Consts;
import pl.lukasz.facebook.FacebookConnector;
import pl.lukasz.facebook.R;
import pl.lukasz.facebook.Utils;
import pl.lukasz.facebook.dialogs.UpdateCompleteDialog;
import pl.lukasz.facebook.tasks.OnTaskCompleteListener;
import pl.lukasz.facebook.tasks.UpdateTask;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.facebook.Session;
import com.facebook.UiLifecycleHelper;

public class MainActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "MainActivity";
	
	private static final int LOGIN_DELAY = 3000;
	
	private EditText ET0, ET1, ET2, ET3;
	private Button startBT, suBT, soBT;
	private ProgressBar progressBar;
	
	private FacebookConnector connector;
	private UiLifecycleHelper uiHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		connector = new FacebookConnector(this);		
		uiHelper = new UiLifecycleHelper(this, connector);
		uiHelper.onCreate(savedInstanceState);
		Utils.logAfterDelay(connector, LOGIN_DELAY);
		
		initEditTexts();
		initButtons();
		initProgressBar();
		
		setButtons();
	}
				
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	    
	   if (Session.getActiveSession().isClosed()) {
		   connector.login();
	   }
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_button:
			tryUpdate();
			break;
		case R.id.suBT:
			 Utils.goToUrl(this, Consts.GO_TO_SU_URL);
			break;
		case R.id.soBT:
			 Utils.goToUrl(this, Consts.GO_TO_SO_URL);
			break;
		}		
	}
	
	private void initEditTexts() {
		ET0 = (EditText) findViewById(R.id.ET0);
		ET1 = (EditText) findViewById(R.id.ET1);
		ET2 = (EditText) findViewById(R.id.ET2);
		ET3 = (EditText) findViewById(R.id.ET3);
	}
	
	private void initButtons() {
		startBT = (Button) findViewById(R.id.start_button);
		suBT = (Button) findViewById(R.id.suBT);
		soBT = (Button) findViewById(R.id.soBT);
	}
	
	private void initProgressBar() {
		progressBar = (ProgressBar) findViewById(R.id.pro_Bar);
	}
	
	private void setButtons() {
		startBT.setOnClickListener(this);
		suBT.setOnClickListener(this);
		soBT.setOnClickListener(this);
	}
	
	private void tryUpdate() {
		if (isFromFilled()) {
			new UpdateTask(progressBar, new OnUpdatingEndListener()).execute();
			connector.postOnWall(); 
		}
	}
	
	private boolean isFromFilled() {
		
		String txt0 = ET0.getText().toString();
		String txt1 = ET1.getText().toString();
		String txt2 = ET2.getText().toString();
		String txt3 = ET3.getText().toString();
		
		String errorMsg = getString(R.string.empy_field_msg);
		SpannableStringBuilder errorTxt = Utils.getErrorText(errorMsg, Color.BLACK);
		
		if (TextUtils.isEmpty(txt0)) {
			ET0.setError(errorTxt);
			return false;
		}
		
		if (TextUtils.isEmpty(txt1)) {
			ET1.setError(errorTxt);
			return false;
		}
		
		if (TextUtils.isEmpty(txt2)) {
			ET2.setError(errorTxt);
			return false;
		}
		
		if (TextUtils.isEmpty(txt3)) {
			ET3.setError(errorTxt);
			return false;
		}
		
		return true;
	}
	
	private class OnUpdatingEndListener implements OnTaskCompleteListener {

		@Override
		public void onTaskComplete() {
			new UpdateCompleteDialog(MainActivity.this).show();
			soBT.setEnabled(true);
		}
		
	}
}
