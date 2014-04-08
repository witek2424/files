package pl.lukasz.facebook.tasks;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

public class UpdateTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private ProgressBar progressBar;

	public UpdateTask(Context context, ProgressBar progressBar) {
		this.context = context;
		this.progressBar = progressBar;
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
		new UpdateCompleteDialog(context).show();
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
	
	private class UpdateCompleteDialog extends AlertDialog.Builder {

		public UpdateCompleteDialog(Context context) {
			super(context);
			
			setTitle("Update!");
			setMessage("Update 2.0 Available Download Now!");
			setPositiveButton("OK", null);
		}	
	}
}
	
