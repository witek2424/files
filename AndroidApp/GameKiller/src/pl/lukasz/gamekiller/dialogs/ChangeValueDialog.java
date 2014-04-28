package pl.lukasz.gamekiller.dialogs;

import pl.lukasz.gamekiller.Item;
import pl.lukasz.gamekiller.R;
import pl.lukasz.gamekiller.activities.MainActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ChangeValueDialog extends AlertDialog.Builder {

	private MainActivity mainActivity;
	private Item item;
	
	private EditText valueET;

	public ChangeValueDialog(MainActivity mainActivity, Item item) {
		super(mainActivity);
		
		this.mainActivity = mainActivity;
		this.item = item;
		
		setTitle("Change value");
		
		initView();
		setButtons();
	}
	
	private void initView() {
		
		View content = mainActivity
				.getLayoutInflater()
				.inflate(R.layout.dialog_change_value, null);

		valueET = (EditText) content.findViewById(R.id.valueET);
		valueET.setText(item.getValue());

		setView(content);
	}

	private void changeValue() {
		String newValue = valueET.getText().toString();
		item.setValue(newValue);
		item.setModified(true);
		mainActivity.refreshResults();
	}

	private void setButtons() {
		setPositiveButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				changeValue();		
			}
		});
		setNegativeButton("Cancel", null);
	}
	
}
