package zhou.com.snail.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import zhou.com.snail.R;
import zhou.com.snail.base.BaseActivity;

/**
 * 改界面没有什么功能，就是选择注册还是登陆
 */
public class AppActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_app;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.bt_login, R.id.bt_register})
    void onCliuck(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                startToActivity(LoginActivity.class);
                break;
            case R.id.bt_register:
                startToActivity(RegisterActivity.class);
                break;
        }
    }
}
