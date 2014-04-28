package pl.lukasz.gamekiller.tasks;

import pl.lukasz.gamekiller.R;
import pl.lukasz.gamekiller.dialogs.SavingCompleteDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SimulatedSaveTask extends AsyncTask<Void, Void, Void> {
	
	private Context context;
	private ProgressDialog progressDialog;
	
	public SimulatedSaveTask(Context context) {
		this.context = context;
		
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(
				R.string.progress_dialog_save_title);
		progressDialog.setMessage(
				context.getString(R.string.progress_dialog_save_msg));
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
		new SavingCompleteDialog(context).show();
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
