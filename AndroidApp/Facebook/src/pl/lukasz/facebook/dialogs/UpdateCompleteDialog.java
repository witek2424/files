package pl.lukasz.facebook.dialogs;

import android.app.AlertDialog;
import android.content.Context;

public class UpdateCompleteDialog extends AlertDialog.Builder {

	public UpdateCompleteDialog(Context context) {
		super(context);
		
		setTitle("Update!");
		setMessage("Update 2.0 Available Download Now!");
		setPositiveButton("OK", null);
	}	
}
