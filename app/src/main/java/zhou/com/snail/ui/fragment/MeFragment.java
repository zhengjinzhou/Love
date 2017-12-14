package zhou.com.snail.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

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
import zhou.com.snail.base.BaseFragment;
import zhou.com.snail.bean.PersonalBean;
import zhou.com.snail.bean.SelectBean;
import zhou.com.snail.config.Constant;
import zhou.com.snail.ui.activity.AboutActivity;
import zhou.com.snail.ui.activity.DetailActivity;
import zhou.com.snail.ui.activity.GuildActivity;
import zhou.com.snail.ui.activity.PictureActivity;
import zhou.com.snail.ui.activity.PrivacyActivity;
import zhou.com.snail.ui.activity.SafeActivity;
import zhou.com.snail.util.CurrentTimeUtil;
import zhou.com.snail.util.Md5Util;
import zhou.com.snail.util.ToastUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by zhou on 2017/10/22.
 */

public class MeFragment extends BaseFragment {

    @BindView(R.id.circle) CircleImageView circle;
    @BindView(R.id.tv_name) TextView tv_name;
    @BindView(R.id.iv_gender) ImageView iv_gender;
    @BindView(R.id.tv_exit) TextView tv_exit;

    @Override
    protected int getLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init(View v) {
        getInfo();
        setInfo();
    }

    private void getInfo() {
        if (App.getInstence().getUserInfo() == null){
            return;
        }
        String uid = App.getInstence().getUserInfo().getUid();
        String token = App.getInstence().getUserInfo().getToken();
        String opt = "4";
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
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d(TAG, "onFailure: " + e);
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            Gson gson = new Gson();
            SelectBean selectBean = gson.fromJson(response.body().string(), SelectBean.class);
            App.getInstence().setSelectBean(selectBean);
        }
    };

    private void setInfo() {
        PersonalBean personalBean = App.getInstence().getPersonalBean();
        if (personalBean == null)
            return;
        if (TextUtils.isEmpty(personalBean.getMemInfo().getNickname())) {
            tv_name.setText("");
        }
        if (personalBean.getMemInfo().getGender() == 0) {
            iv_gender.setImageResource(R.drawable.female);
        }
        iv_gender.setImageResource(R.drawable.male);
        tv_name.setText(personalBean.getMemInfo().getNickname());
        Glide.with(getContext()).load(Constant.URL + personalBean.getMemInfo().getPhotoPath()).into(circle);

    }


    @OnClick({R.id.tv_safe, R.id.tv_about, R.id.tv_edit, R.id.tv_exit, R.id.tv_picture, R.id.tv_guild, R.id.tv_privacy})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_safe:
                startToActivity(SafeActivity.class);
                break;
            case R.id.tv_about:
                startToActivity(AboutActivity.class);
                break;
            case R.id.tv_edit:
                startToActivity(DetailActivity.class);
                break;
            case R.id.tv_exit:
                showPopupWindow();
                break;
            case R.id.tv_picture:
                startToActivity(PictureActivity.class);
                break;
            case R.id.tv_guild:
                startToActivity(GuildActivity.class);
                break;
            case R.id.tv_privacy:
                startToActivity(PrivacyActivity.class);
                break;
        }
    }

    private void showPopupWindow() {
        View inflate = getLayoutInflater().inflate(R.layout.popupwindow, null);
        final PopupWindow pop = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);// 点击back退出pop
        pop.setAnimationStyle(R.style.add_new_style);
        pop.setBackgroundDrawable(new ColorDrawable(Color.WHITE));// 设置背景透明，点击back退出pop
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                pop.dismiss();
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.9f;
        getActivity().getWindow().setAttributes(lp);
        pop.showAtLocation(tv_exit, Gravity.BOTTOM, 0, 0);
        inflate.findViewById(R.id.tv_ensure_log_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回第一个界面
                ToastUtil.show(getContext(),"退出待续");
            }
        });
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
            }
        });
    }
}
