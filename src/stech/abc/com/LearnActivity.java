package stech.abc.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.appbrain.AppBrain;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class LearnActivity extends Activity {

	Animation animFadeOut,animSlideDown;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		setContentView(R.layout.activity_main);
		
		loadUI();
	
		AdView adView = (AdView)this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
        AppBrain.init(this);

	}
//========================Menu===========================================
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	     case R.id.Exit:
	     {
	    	 new AlertDialog.Builder(this)
		     //.setIcon(android.R.drawable.ic_dialog_alert)
		     .setTitle("Exit")
		     .setMessage("Are you sure you want Exit ?")
		     .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		 {
		     @Override
			public void onClick(DialogInterface dialog, int which) {
		         AppBrain.getAds().maybeShowInterstitial(getApplicationContext());
		     	finish();    
		     }
		 })
		 .setNegativeButton("No", null)
		 .show();
	     }
    	 break;
    	 
	     case R.id.Rate:
         {
        	 runOnUiThread(new Runnable() {
 	            @Override
 				public void run()
 	            {
 	            	AppRater.showRateDialog(LearnActivity.this, null);
 	            }
 	        });
         }
    	 break;
	     case R.id.Options:
         {
        	 runOnUiThread(new Runnable() {
 	            @Override
 				public void run()
 	            {
 	            	Options.showRateDialog(LearnActivity.this);
 	            }
 	        });
         }
    	 break;
	     }
	     return true;
	 }
	//=================On Back Key==================
	@Override
	 public void onBackPressed() {
	 	new AlertDialog.Builder(this)
	     //.setIcon(android.R.drawable.ic_dialog_alert)
	     .setTitle("Exit")
	     .setMessage("Are you sure you want Exit ?")
	     .setPositiveButton("Yes", new DialogInterface.OnClickListener()
	 {
	     @Override
		public void onClick(DialogInterface dialog, int which) {
	         AppBrain.getAds().maybeShowInterstitial(getApplicationContext());

	     	finish();    
	     }

	 })
	 .setNegativeButton("No", null)
	 .show();
	 }
	//==============Init Image array=================
	int[] images = GlobalVariables.images;
	//----------Intialize UI---------------------
	ImageView abc,left,right;
	int position = 0;

	public void loadUI()
	{
		abc = (ImageView) findViewById(R.id.imageView);
		left = (ImageView) findViewById(R.id.imageViewLeft);
		right = (ImageView) findViewById(R.id.ImageViewRight);
		
		abc.setImageResource(images[0]);
		
		// load the animation
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
         
        animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        
		left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(position == 0)
				{					
					position = 25;
				}
				else
				{
					position = position-1;
				}
				abc.setImageResource(images[position]);
				abc.startAnimation(animSlideDown);
				playSound(position);
				left.startAnimation(animFadeOut);
				right.startAnimation(animFadeOut);
			}
		});
		
		
		right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(position == 25)
				{					
					position = 0;
				}
				else
				{
					position = position+1;
				}
				abc.setImageResource(images[position]);
				abc.startAnimation(animSlideDown);
				playSound(position);
				left.startAnimation(animFadeOut);
				right.startAnimation(animFadeOut);
			}
		});
	}
	//===========plaing Sounds==============================
	MediaPlayer mp;
	int[] lettersounds = GlobalVariables.LetterSoundsEnglish;
	
	public void playSound(int i)
	{
		getpref();
		if(SoundCheck == 1)
		{
		mp = MediaPlayer.create(LearnActivity.this, lettersounds[i]);
        mp.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });   
        mp.start();
		}
	}
	//===============On touch events======================
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		  left.startAnimation(animFadeOut);
		  right.startAnimation(animFadeOut);

		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		  @Override
		  public void run() {
			 
				left.setVisibility(View.GONE);
				right.setVisibility(View.GONE);
						  }
		}, 3000);
		
		return super.onTouchEvent(event);
	}
//Getting Pref===============================================
static int SoundCheck = 1;
static int VibrateCheck = 1;
public void getpref()
{
	SharedPreferences prefs = getApplicationContext().getSharedPreferences("options", Context.MODE_PRIVATE);
	if (prefs.getBoolean("sound", true)) 
		{
			SoundCheck = 1;
		}
	else
		{
			SoundCheck = 0;
		}
	
	if (prefs.getBoolean("vibrate", true)) 
		{
			VibrateCheck = 1;
		}
	else
		{
			VibrateCheck = 0;
		}
}
	
//===================The End===================================================
}
