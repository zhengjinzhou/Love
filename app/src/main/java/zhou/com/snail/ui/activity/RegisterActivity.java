package zhou.com.snail.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import zhou.com.snail.R;
import zhou.com.snail.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_head) TextView tv_head;

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        tv_head.setText("注册");
    }

    @OnClick({R.id.back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
