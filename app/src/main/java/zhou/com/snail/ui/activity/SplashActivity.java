package zhou.com.snail.ui.activity;

import android.os.Handler;

import zhou.com.snail.R;
import zhou.com.snail.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startToActivity(LoginActivity.class);
            }
        },1500);
    }
}
