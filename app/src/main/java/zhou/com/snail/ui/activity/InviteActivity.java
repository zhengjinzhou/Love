package zhou.com.snail.ui.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zhou.com.snail.R;
import zhou.com.snail.adapter.base.BaseCommonAdapter;
import zhou.com.snail.adapter.base.ViewHolder;
import zhou.com.snail.base.App;
import zhou.com.snail.base.BaseActivity;
import zhou.com.snail.bean.InvitBean;
import zhou.com.snail.config.Constant;
import zhou.com.snail.util.CurrentTimeUtil;
import zhou.com.snail.util.Md5Util;

public class InviteActivity extends BaseActivity {

    private static final String TAG = "InviteActivity";
    @BindView(R.id.tv_inviteCode) TextView tv_inviteCode;
    @BindView(R.id.tv_head) TextView tv_head;
    private String invited;

    @Override
    protected int getLayout() {
        return R.layout.activity_invite;
    }

    @Override
    protected void init() {
        tv_head.setText("邀请好友");
        //生成邀请码
        getInvited();
    }

    private void getInvited() {
        if (App.getInstence().getUserInfo() != null){
            String token = App.getInstence().getUserInfo().getToken();
            String opt = "12";
            String _t = CurrentTimeUtil.nowTime();
            String joint = "_t=" + _t +  "&opt=" + opt + "&token="+ token + Constant.APP_ENCRYPTION_KEY;
            Log.d(TAG, "getInvited: "+joint);
            String _s = Md5Util.encoder(joint);
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("token", token)
                    .add("opt", opt)
                    .add("_t", _t)
                    .add("_s", _s)
                    .build();
            Request request = new Request.Builder().post(body).url(Constant.LOGIN_URL).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    Log.d(TAG, "onResponse: "+string);
                    getResult(string);
                }
            });
        }
    }

    //生成邀请码
    private void getResult(String data) {
        Gson gson = new Gson();
        final InvitBean invitBean = gson.fromJson(data, InvitBean.class);
        if (invitBean.getError().equals("-1")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_inviteCode.setText(invitBean.getInviteCode());
                    invited = invitBean.getInviteCode();
                }
            });
        }
    }

    /**
     * 将邀请码通过以下方式
     * <p>
     * 发送到给用户
     */
    private void toInvited() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, "邀请码为："+invited);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }

    @OnClick({R.id.back,R.id.tv_invite})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_invite:
                toInvited();
                break;
        }
    }
}
