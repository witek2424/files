package pl.lukasz.gamekiller;

import pl.lukasz.gamekiller.R;
import pl.lukasz.gamekiller.activities.MainActivity;
import pl.lukasz.gamekiller.tasks.SimulatedSaveTask;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;

public class KeyboardView extends LinearLayout implements OnClickListener {

	private MainActivity mainActivity;
	
	private Button BT0, BT1, BT2, BT3, BT4, BT5, BT6, BT7, BT8, BT9, saveBT, clearBT;

	public KeyboardView(MainActivity mainActivity) {
		super(mainActivity);
		this.mainActivity = mainActivity;
		
		inflate(mainActivity, R.layout.view_keyborad, this);
		
		BT0 = (Button) findViewById(R.id.BT0);
		BT1 = (Button) findViewById(R.id.BT1);
		BT2 = (Button) findViewById(R.id.BT2);
		BT3 = (Button) findViewById(R.id.BT3);
		BT4 = (Button) findViewById(R.id.BT4);
		BT5 = (Button) findViewById(R.id.BT5);
		BT6 = (Button) findViewById(R.id.BT6);
		BT7 = (Button) findViewById(R.id.BT7);
		BT8 = (Button) findViewById(R.id.BT8);
		BT9 = (Button) findViewById(R.id.BT9);
		saveBT = (Button) findViewById(R.id.saveBT);
		clearBT = (Button) findViewById(R.id.clearBT);
		
		BT0.setOnClickListener(this);
		BT1.setOnClickListener(this);
		BT2.setOnClickListener(this);
		BT3.setOnClickListener(this);
		BT4.setOnClickListener(this);
		BT5.setOnClickListener(this);
		BT6.setOnClickListener(this);
		BT7.setOnClickListener(this);
		BT8.setOnClickListener(this);
		BT9.setOnClickListener(this);
		saveBT.setOnClickListener(this);
		clearBT.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {		
		switch (v.getId()) {
		case R.id.BT0:
			mainActivity.appendNumber("0");
			break;
		case R.id.BT1:
			mainActivity.appendNumber("1");
			break;
		case R.id.BT2:
			mainActivity.appendNumber("2");
			break;
		case R.id.BT3:
			mainActivity.appendNumber("3");
			break;
		case R.id.BT4:
			mainActivity.appendNumber("4");
			break;
		case R.id.BT5:
			mainActivity.appendNumber("5");
			break;
		case R.id.BT6:
			mainActivity.appendNumber("6");
			break;
		case R.id.BT7:
			mainActivity.appendNumber("7");
			break;
		case R.id.BT8:
			mainActivity.appendNumber("8");
			break;
		case R.id.BT9:
			mainActivity.appendNumber("9");
			break;
		case R.id.saveBT:
			mainActivity.onSaveBTClick();
			break;
		case R.id.clearBT:
			mainActivity.removeNumber();
			break;
		}
		
	}
}
