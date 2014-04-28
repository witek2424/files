package pl.lukasz.gamekiller.tasks;

import pl.lukasz.gamekiller.R;
import pl.lukasz.gamekiller.UpdateManager;
import pl.lukasz.gamekiller.dialogs.SavingCompleteDialog;
import pl.lukasz.gamekiller.dialogs.UpdateCompleteDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SimulatedUpdatingTask extends AsyncTask<Void, Void, Void> {
	
	private Context context;
	private UpdateManager manager;
	
	private ProgressDialog progressDialog;
	
	public SimulatedUpdatingTask(Context context, UpdateManager manager) {
		this.context = context;
		this.manager = manager; 
		
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(
				R.string.progress_dialog_update_title);
		progressDialog.setMessage(
				context.getString(R.string.progress_dialog_update_msg));
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
		try {
			progressDialog.dismiss();
			new UpdateCompleteDialog(context, manager).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void simulateProgress() {
		
		for (int i = 0; i < 100; i++) {
			try {
				progressDialog.setProgress(i + 1);
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
