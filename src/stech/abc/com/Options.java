package stech.abc.com;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class Options {

		public static void showRateDialog(final Context mContext) {
		final Dialog dialog = new Dialog(mContext);
		
		
		final SharedPreferences prefs = mContext.getSharedPreferences("options", Context.MODE_WORLD_READABLE);
		final SharedPreferences.Editor editor = prefs.edit();

		
		dialog.setTitle("Options");

		LinearLayout ll = new LinearLayout(mContext);
		ll.setOrientation(LinearLayout.VERTICAL);

		final CheckBox sound = new CheckBox(mContext);
		sound.setText("Sound");
		sound.setTextColor(mContext.getResources().getColor(R.color.White));
		if (prefs.getBoolean("sound", true)) {
			sound.setChecked(true);
		}
		else 
		{
			sound.setChecked(false);
		}
		
		ll.addView(sound);

		sound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				if (((CheckBox) v).isChecked()) 
				{
					editor.putBoolean("sound", true);
				}
				else
				{
					editor.putBoolean("sound", false);
				}
			}
		});

		
		
		
		
		
		CheckBox vib = new CheckBox(mContext);
		vib.setText("Vibrations");
		vib.setTextColor(mContext.getResources().getColor(R.color.White));
		if (prefs.getBoolean("vibrate", true)) {
			vib.setChecked(true);
		}
		else 
		{
			vib.setChecked(false);
		}
		
		ll.addView(vib);

		vib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				if (((CheckBox) v).isChecked()) 
				{
					editor.putBoolean("vibrate", true);
				}
				else
				{
					editor.putBoolean("vibrate", false);
				}
			}
		});

	
		
		Button b3 = new Button(mContext);
		b3.setText("OK");
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				editor.commit();	

				dialog.dismiss();
			}
		});
		ll.addView(b3);

		dialog.setContentView(ll);
		dialog.show();
	}
		
		
		
		
}