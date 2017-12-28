package zhou.com.snail.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zhou.com.snail.R;
import zhou.com.snail.base.BaseActivity;
import zhou.com.snail.config.Constant;
import zhou.com.snail.util.CurrentTimeUtil;
import zhou.com.snail.util.Md5Util;
import zhou.com.snail.util.ToastUtil;

public class Register2Activity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm)
    EditText et_confirm;
    @BindView(R.id.et_inviteCode)
    EditText et_inviteCode;
    @BindView(R.id.tv_head)
    TextView tv_head;

    private static final String TAG = "Register2Activity";
    private String mobile;//手机号码
    private String code;//验证码

    public static Intent newIntent(Context context, String mobile, String code) {
        Intent intent = new Intent(context, Register2Activity.class);
        intent.putExtra(Constant.MOBILE, mobile);
        intent.putExtra("code", code);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register2;
    }

    @Override
    protected void init() {
        tv_head.setText("注册");
        Intent intent = getIntent();
        mobile = intent.getStringExtra(Constant.MOBILE);
        code = intent.getStringExtra("code");
        Log.d(TAG, "init: " + mobile + "," + code);
    }

    @OnClick({R.id.back, R.id.bt_register})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_register:
                toRegister();
                break;
        }
    }

    /**
     * 注册
     */
    private void toRegister() {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirm = et_confirm.getText().toString().trim();
        String inviteCode = et_inviteCode.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtil.show(getApplicationContext(), "用户名不能为空");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.show(getApplicationContext(), "密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(confirm)) {
            ToastUtil.show(getApplicationContext(), "确认密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(inviteCode)) {
            ToastUtil.show(getApplicationContext(), "邀请码不能为空");
            return;
        }

        if (!password.equals(confirm)) {
            ToastUtil.show(getApplicationContext(), "密码与确认密码不一致");
            return;
        }

        //注册
        String opt = "1";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&code=" + code + "&mobile=" + mobile + "&opt=" + opt + "&password=" + password + "&username=" + username + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        Log.d(TAG, "getCode: " + joint + "_s" + _s);

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("opt", opt)
                .add("mobile", mobile)
                .add("code", code)
                .add("username", username)
                .add("password", password)
                .add("inviteCode", inviteCode)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().post(body).url(Constant.URL_REGISTER).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "注册onResponse: "+response.body().string());
                //转到登录界面
            }
        });
    }
}
