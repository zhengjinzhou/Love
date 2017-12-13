package zhou.com.snail.ui.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zhou.com.snail.R;
import zhou.com.snail.base.App;
import zhou.com.snail.base.BaseActivity;
import zhou.com.snail.bean.PersonalBean;
import zhou.com.snail.bean.UserInfo;
import zhou.com.snail.config.Constant;
import zhou.com.snail.util.CurrentTimeUtil;
import zhou.com.snail.util.DES3Util;
import zhou.com.snail.util.Md5Util;
import zhou.com.snail.util.ToastUtil;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.circle) CircleImageView circle;
    @BindView(R.id.et_username) EditText etUsername;
    @BindView(R.id.et_password) EditText etPassword;

    private String TAG = "LoginActivity";

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        etUsername.setText("2014414");
        etPassword.setText("123");
    }


    @OnClick({R.id.bt_login})
    void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                login();
                break;
        }
    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtil.show(getApplicationContext(), "账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.show(getApplicationContext(), "密码不能为空");
            return;
        }

        dialog.show();

        String pwd = DES3Util.encrypt3DES(password, Constant.ENCRYPTION_KEY, Charset.forName("UTF-8"));
        String opt = "2";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&actNumber=" + username + "&opt=" + opt + "&pwd=" + pwd + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);

        System.out.println("拼接后_t的数据--------"+joint);

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody body = new FormBody.Builder()
                .add("actNumber", username)
                .add("pwd", pwd)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder()
                .url(Constant.LOGIN_URL)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d(TAG, "onFailure: " + e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String string = response.body().string();
            Log.d(TAG, "onResponse: "+string);
            getResult(string);
        }
    };

    private void getResult(String data) {
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(data, UserInfo.class);
        String token = userInfo.getToken();
        String uid = userInfo.getUid();

        if (!userInfo.getError().equals("-1")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(getApplicationContext(),"访问超时，请检查您的系统时间是否是24小时制");
                }
            });
            return;
        }
        Log.d(TAG, "getResult: "+token);
        Log.d(TAG, "getResult: "+uid);

        String opt = "5";
        String _t = CurrentTimeUtil.nowTime();
        String joint = "_t=" + _t + "&opt=" + opt + "&token=" + token + "&uid=" + uid + Constant.APP_ENCRYPTION_KEY;
        String _s = Md5Util.encoder(joint);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody build = new FormBody.Builder()
                .add("uid", uid)
                .add("token", token)
                .add("opt", opt)
                .add("_t", _t)
                .add("_s", _s)
                .build();
        Request request = new Request.Builder().url(Constant.LOGIN_URL).post(build).build();
        Call call2 = okHttpClient.newCall(request);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e);
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                Gson gson1 = new Gson();
                PersonalBean personalBean = gson1.fromJson(res.body().string(), PersonalBean.class);
                App.getInstence().setPersonalBean(personalBean);
            }
        });

        App.getInstence().setUserInfo(userInfo);
        if (userInfo.getError().equals("-1")) {
            startToActivity(HomeActivity.class);
        } else if (userInfo.getError().equals("-2")) {
            startToActivity(RegisterActivity.class);
        }
    }
}
