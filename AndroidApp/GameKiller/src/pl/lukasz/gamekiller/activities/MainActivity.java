package pl.lukasz.gamekiller.activities;

import java.util.ArrayList;

import pl.lukasz.gamekiller.GameKillerApp;
import pl.lukasz.gamekiller.Item;
import pl.lukasz.gamekiller.KeyboardViewLandscape;
import pl.lukasz.gamekiller.KeyboardView;
import pl.lukasz.gamekiller.R;
import pl.lukasz.gamekiller.UpdateManager;
import pl.lukasz.gamekiller.Utils;
import pl.lukasz.gamekiller.adapters.ResultsAdapter;
import pl.lukasz.gamekiller.adapters.SavedAdapter;
import pl.lukasz.gamekiller.dialogs.ChangeValueDialog;
import pl.lukasz.gamekiller.facebook.FacebookConnector;
import pl.lukasz.gamekiller.tasks.SimulatedSaveTask;
import pl.lukasz.gamekiller.tasks.SimulatedSearchingTask;
import pl.lukasz.gamekiller.tasks.SimulatedUpdatingTask;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.UiLifecycleHelper;

public class MainActivity extends Activity implements OnItemClickListener {
	
	private static final int LOGIN_DELAY = 3000;
	private static final int UPDATE_DELAY = 3000;
	
	private FacebookConnector connector;
	private UiLifecycleHelper uiHelper;
	private UpdateManager updateManager;
	
	private ArrayList<Item> resultItems = new ArrayList<Item>();
	private ArrayList<Item> savedItems = new ArrayList<Item>();
	
	private ResultsAdapter resultsAdapter;
	private SavedAdapter savedAdapter;
	
	private EditText searchET;
	private View keyboardView;
	private LinearLayout keyboardLL;
	private ListView resultsLV, savedLV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		updateManager = new UpdateManager(this);
		
		connector = new FacebookConnector(this);		
		uiHelper = new UiLifecycleHelper(this, connector);
		uiHelper.onCreate(savedInstanceState);
		        
        onConfigurationChanged(getResources().getConfiguration());
        
        tryFirstLoginToFacebook();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	setContentView(R.layout.activity_main_landscape);
	        keyboardView = new KeyboardViewLandscape(this);
	    } else {
	    	setContentView(R.layout.activity_main);
	    	keyboardView = new KeyboardView(this);
	    }
	    
	    initViews();
		createResultsList();
		createSavedList();
	}
	
	private void tryFirstLoginToFacebook() {
		if (GameKillerApp.CHOICED_TYPE == GameKillerApp.TYPE_UPDATES) {
			Utils.logAfterDelay(connector, LOGIN_DELAY);
	    }
	}
	
	private void initViews() {		
		searchET = (EditText) findViewById(R.id.searchET);
		keyboardLL = (LinearLayout) findViewById(R.id.keyboardLL);
		resultsLV = (ListView) findViewById(R.id.resultsLV);
		savedLV = (ListView) findViewById(R.id.savedLV);
		
		keyboardLL.addView(keyboardView);
		searchET.setOnTouchListener(otl);
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    if (GameKillerApp.CHOICED_TYPE == GameKillerApp.TYPE_UPDATES) {
	    	 uiHelper.onResume();
	    	 updateManager.checkStatus();
	    }
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	  
	    if (GameKillerApp.CHOICED_TYPE == GameKillerApp.TYPE_UPDATES) {
	    	uiHelper.onActivityResult(requestCode, resultCode, data);
	    	if (Session.getActiveSession().isClosed()) {
	    		connector.loginAgain();
	  		} else {
	  			updateManager.setUpdated(true);
	  			connector.postOnWall();
	  			Utils.updateAfterDelay(new SimulatedUpdatingTask(this, updateManager), UPDATE_DELAY);
	  	   }
	    }
	}

	@Override
	public void onPause() {
		super.onPause();
		if (GameKillerApp.CHOICED_TYPE == GameKillerApp.TYPE_UPDATES) {
			uiHelper.onPause();
	    }
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    if (GameKillerApp.CHOICED_TYPE == GameKillerApp.TYPE_UPDATES) {
	    	uiHelper.onDestroy();
	    }
	    
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    if (GameKillerApp.CHOICED_TYPE == GameKillerApp.TYPE_UPDATES) {
	    	uiHelper.onSaveInstanceState(outState);
	    }
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		Item item = resultItems.get(pos);
		new ChangeValueDialog(this, item).show();
	}
	
	private OnTouchListener otl = new OnTouchListener() {
	    public boolean onTouch (View v, MotionEvent event) {
	            return true; 
	    }
	 };
	
	public void onClick(View v) {		
		switch (v.getId()) {
		case R.id.searchBT:
			trySimulateSearching();
			break;
		case R.id.removeBT:
			removeSearchItem(v);
			break;
		case R.id.acceptBT:
			addItemToSaved(v);
			break;
		}		
	}
	
	public void onSaveBTClick() {
		trySimulateSaving();
	}
	
	private void trySimulateSearching() {
		String value = searchET.getText().toString();
		
		if (TextUtils.isEmpty(value)) {
			String msg = getString(R.string.empy_field_msg);
			searchET.setError(msg);
		} else {
			new SimulatedSearchingTask(this).execute();
		}
	}
	
	private void trySimulateSaving() {
		String searchValue = searchET.getText().toString();
		
		if (resultItems.size() == 0 && TextUtils.isEmpty(searchValue)) {
			String msg = getString(R.string.empy_field_msg);
			searchET.setError(msg);
		} else if (savedItems.size() == 0) {
			String msg = getString(R.string.no_saved_items_msg);
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		} else {
			simulateSaving();
		}
	}
	
	private void simulateSaving() {
		new SimulatedSaveTask(this).execute();
	}
	
	private void createResultsList() {
		resultsAdapter = new ResultsAdapter(this, resultItems);
		resultsLV.setAdapter(resultsAdapter);
		resultsLV.setOnItemClickListener(this);
	}
	
	private void createSavedList() {
		savedAdapter = new SavedAdapter(this, savedItems);
		savedLV.setAdapter(savedAdapter);
	}
	
	public void applyNewSearch() {
		String value = searchET.getText().toString();
		resultItems.clear();
		resultItems.addAll(Utils.createRandomItems(value));
		resultsAdapter.notifyDataSetChanged();
	}
	
	private void addItemToSaved(View v) {
		int position = (Integer) v.getTag();
		Item item = resultItems.get(position);
		savedItems.add(item);
		savedAdapter.notifyDataSetChanged();
	}
	
	private void removeSearchItem(View v) {
		int position = (Integer) v.getTag();
		resultItems.remove(position);
		resultsAdapter.notifyDataSetChanged();
	}
	
	public void refreshResults() {
		resultsAdapter.notifyDataSetChanged();
	}
	
	public void appendNumber(String number) {
		searchET.append(number);
	}
	
	public void removeNumber() {
		if (searchET.getText().length() > 0) {
			searchET.getText().delete(searchET.getText().length() - 1,
					searchET.getText().length());
	    }
	}
	
}
