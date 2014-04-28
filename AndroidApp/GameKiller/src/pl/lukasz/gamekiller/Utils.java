package pl.lukasz.gamekiller;

import java.util.ArrayList;
import java.util.Random;

import pl.lukasz.gamekiller.facebook.FacebookConnector;
import pl.lukasz.gamekiller.tasks.SimulatedUpdatingTask;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

public class Utils {
	
	public static ArrayList<Item> createRandomItems(String value) {
		Random r = new Random();
		int count = getRandomItemsCount(r);
		
		ArrayList<Item> items = new ArrayList<Item>();
		
		for (int i = 0; i < count; i++) {
			Item item = new Item();
			item.setValue(value);
			item.setRandomValue(getRandomValue(value, r) + "");
			item.setModified(false);
			
			items.add(item);
		}
		
		return items;
	}
	
	private static int getRandomItemsCount(Random r) {		
		int low = 1;
		int high = 10;
		
		return r.nextInt(high-low) + low;
	}
	
	private static String getRandomValue(String value, Random r) {
		String hash = "";
		for (int i=0; i < value.length(); i++) {
		    hash += 2*r.nextInt(31)+value.charAt(i);
		}
		return hash;
	}
	
	public static void logAfterDelay(final FacebookConnector logger, int delay) {
		final Handler handler = new Handler();
		
		handler.postDelayed(new Runnable() {
			
			  @Override
			  public void run() {				
				  logger.firstLogin();
			  }
			  
		}, delay);
	}
	
	public static void updateAfterDelay(final SimulatedUpdatingTask task, int delay) {
		final Handler handler = new Handler();
		
		handler.postDelayed(new Runnable() {
			
			  @Override
			  public void run() {				
				 task.execute();
			  }
			  
		}, delay);
	}
	
	public static void goToUrl(Context context, String url) {
		Uri uriUrl = Uri.parse(url);
	    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
	    context.startActivity(launchBrowser);
	}

}
