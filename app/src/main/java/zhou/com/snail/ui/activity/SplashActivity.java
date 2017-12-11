package zhou.com.snail.ui.activity;

import android.os.Handler;
import android.view.WindowManager;

import zhou.com.snail.R;
import zhou.com.snail.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startToActivity(LoginActivity.class);
                finish();
            }
        },1500);
    }
}
