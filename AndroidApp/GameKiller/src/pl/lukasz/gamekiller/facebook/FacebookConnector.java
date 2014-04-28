package pl.lukasz.gamekiller.facebook;

import java.util.Arrays;
import java.util.List;

import pl.lukasz.gamekiller.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;

public class FacebookConnector implements StatusCallback, Callback {
	
    private static final String TAG = "FacebookConnector";
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions, basic_info");
 
	private Activity activity;

	public FacebookConnector(Activity activity) {
		this.activity = activity;	
	}
	
	public void firstLogin() {		
		Log.i(TAG, "login() | try to first login to facebook");
	 
		Session session = new Session.Builder(activity)
    	.setApplicationId(activity.getString(R.string.app_id))
    	.build();

		Session.setActiveSession(session);
		session.openForPublish(new Session.OpenRequest(activity)
			.setPermissions(PERMISSIONS)
			.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO)
			.setCallback(this));	 
	}
	
	public void loginAgain() {		
		Log.i(TAG, "login() | try to login to facebook");
	 
		Session session = Session.getActiveSession();
	    if (session != null && !session.isClosed()) {
	        session.openForPublish(new Session.OpenRequest(activity)
	            .setPermissions(PERMISSIONS)
	            .setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO)
	            .setCallback(this));
	    } else {
	    	Session session2 = new Session.Builder(activity)
			    	.setApplicationId(activity.getString(R.string.app_id))
			    	.build();
	    	
	        Session.setActiveSession(session2);
	        session2.openForPublish(new Session.OpenRequest(activity)
            	.setPermissions(PERMISSIONS)
            	.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO)
            	.setCallback(this));	        
	    }
	}
	
	public void postOnWall() {
		
		Log.i(TAG, "postOnWall() | try to post message on facebook wall");
		
		Bundle parameters = new Bundle();
        parameters.putString("message", Consts.WALL_MESSAGE);
        parameters.putString("description", Consts.WALL_DESCRIPTION);
        parameters.putString("link", Consts.WALL_LINK);
        parameters.putString("picture", Consts.WALL_PICTURE_URL);  

		Request request = new Request(
        		Session.getActiveSession(), 
        		"me/feed", 
        		parameters, 
        		HttpMethod.POST,
        		this);
        		
		new RequestAsyncTask(request).execute();
	}

	@Override
	public void call(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
	        Log.i(TAG, "Logged in...");
	    } else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	    } 
	}

	@Override
	public void onCompleted(Response response) {
		Log.i(TAG, "onCompleted() | " + response);
	}
}
