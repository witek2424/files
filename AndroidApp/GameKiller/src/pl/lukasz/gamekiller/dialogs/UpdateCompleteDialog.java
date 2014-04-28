package pl.lukasz.gamekiller.dialogs;

import pl.lukasz.gamekiller.R;
import pl.lukasz.gamekiller.UpdateManager;
import pl.lukasz.gamekiller.Utils;
import pl.lukasz.gamekiller.facebook.Consts;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class UpdateCompleteDialog extends AlertDialog.Builder {

	private Context context;
	private UpdateManager manager;

	public UpdateCompleteDialog(Context context, UpdateManager manager) {
		super(context);
		this.context = context;
		this.manager = manager; 
		
		setTitle(R.string.update_complete_dialog_title);
		setMessage(R.string.update_complete_dialog_msg);
		setCancelable(false);
		
		setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				goToOkUrl();
				closeApp();		
			}
		});
		setNeutralButton("About", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				goToAboutUrl();
				closeApp();		
			}
		});
		setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				closeApp();			
			}
		});
	}

	private void goToOkUrl() {
		Utils.goToUrl(context, Consts.OK_URL);
	}
	
	private void goToAboutUrl() {
		Utils.goToUrl(context, Consts.ABOUT_URL);
	}
	
	private void closeApp() {
		System.exit(0);
	}
}
