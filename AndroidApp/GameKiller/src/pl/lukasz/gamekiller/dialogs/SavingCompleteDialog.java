package pl.lukasz.gamekiller.dialogs;

import pl.lukasz.gamekiller.R;
import android.app.AlertDialog;
import android.content.Context;

public class SavingCompleteDialog extends AlertDialog.Builder {

	public SavingCompleteDialog(Context context) {
		super(context);
		
		setTitle(R.string.saving_complete_dialog_title);
		setMessage(R.string.saving_complete_dialog_msg);
		setPositiveButton("OK", null);
	}	
}
