package edu.intec.willgo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.widget.ProgressBar;

/**
 * Created by (N-works) on 6/4/15.
 */
public class SplashActivity extends ActionBarActivity {
    ProgressBar pBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        pBar = (ProgressBar) SplashActivity.this.findViewById(R.id.pbproceso);
        pBar.setMax(3000);

        CountDownTimer countDownTimer = new CountDownTimer(3000,100) {
            private boolean warned = false;
            @Override
            public void onTick(long millisUntilFinished_) {
                pBar.incrementProgressBy(new Long(millisUntilFinished_).intValue());
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, InterestListActivity.class);
                startActivity(intent);
            }
        }.start();
    }
}
