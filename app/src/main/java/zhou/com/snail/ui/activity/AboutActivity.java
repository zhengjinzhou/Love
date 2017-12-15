package zhou.com.snail.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.OnClick;
import zhou.com.snail.R;
import zhou.com.snail.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.iv_all_back) void back(){
        
        finish();
    }
}
