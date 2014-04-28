package pl.lukasz.gamekiller;

import pl.lukasz.gamekiller.tasks.SimulatedUpdatingTask;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UpdateManager {
	
	private static final String UPDATE_MANAGER = "updateManager";
	private static final String IS_UPDATED = "updateManager.isUpdated";

	private Context context;
	private SharedPreferences preferences;


	public UpdateManager(Context context) {
		this.context = context;
		
		preferences = context.getSharedPreferences(
				UPDATE_MANAGER, 
				Activity.MODE_PRIVATE);
	}

	public void checkStatus() {
		boolean isUpdated = isUpdated();
		
		if (isUpdated) {
			new SimulatedUpdatingTask(context, this).execute();
		}
	}
	
	private boolean isUpdated() {
		return preferences.getBoolean(IS_UPDATED, false);
	}
	
	public void setUpdated(boolean isUpdated) {
		Editor editor = preferences.edit();
		editor.putBoolean(IS_UPDATED, isUpdated);
		editor.commit();
	}
		
}
