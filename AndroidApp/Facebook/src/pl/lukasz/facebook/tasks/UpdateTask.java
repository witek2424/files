package pl.lukasz.facebook.tasks;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

public class UpdateTask extends AsyncTask<Void, Void, Void> {

	private ProgressBar progressBar;
	private OnTaskCompleteListener listener;

	public UpdateTask(ProgressBar progressBar, OnTaskCompleteListener listener) {
		this.progressBar = progressBar;
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {

	}

	@Override
	protected Void doInBackground(Void... params) {
		simulateProgress();
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		listener.onTaskComplete();
	}
	
	private void simulateProgress() {
		
		for (int i = 0; i < 100; i++) {
			try {
				progressBar.setProgress(i + 1);
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
	
