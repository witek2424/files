package pl.lukasz.facebook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

public class Utils {
	
	public static void goToUrl(Context context, String url) {
		Uri uriUrl = Uri.parse(url);
	    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
	    context.startActivity(launchBrowser);
	}
	
	public static void logAfterDelay(final FacebookConnector logger, int delay) {
		final Handler handler = new Handler();
		
		handler.postDelayed(new Runnable() {
			
			  @Override
			  public void run() {				
				  logger.login();
			  }
			  
		}, delay);
	}
	
	public static SpannableStringBuilder getErrorText(String message, int color) {
		ForegroundColorSpan fgcspan = new ForegroundColorSpan(color);
		SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
		ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
		
		return ssbuilder;
	}

}
