package pl.lukasz.gamekiller.tasks;

import pl.lukasz.gamekiller.R;
import pl.lukasz.gamekiller.activities.MainActivity;
import pl.lukasz.gamekiller.dialogs.SavingCompleteDialog;
import pl.lukasz.gamekiller.dialogs.UpdateCompleteDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SimulatedSearchingTask extends AsyncTask<Void, Void, Void> {
	
	private MainActivity mainActivity;
	private ProgressDialog progressDialog;
	
	public SimulatedSearchingTask(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		
		progressDialog = new ProgressDialog(mainActivity);
		progressDialog.setTitle(
				R.string.progress_dialog_search_title);
		progressDialog.setMessage(
				mainActivity.getString(R.string.progress_dialog_search_msg));
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
	}

	@Override
	protected void onPreExecute() {
		progressDialog.show();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		simulateProgress();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		progressDialog.dismiss();
		mainActivity.applyNewSearch();
	}
	
	private void simulateProgress() {
		
		for (int i = 0; i < 100; i++) {
			try {
				progressDialog.setProgress(i + 1);
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
