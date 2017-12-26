package zhou.com.snail.ui.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import zhou.com.snail.R;
import zhou.com.snail.adapter.base.BaseCommonAdapter;
import zhou.com.snail.adapter.base.ViewHolder;
import zhou.com.snail.base.BaseActivity;

public class InviteActivity extends BaseActivity {

    @BindView(R.id.tv_inviteCode)
    TextView tv_inviteCode;
    @BindView(R.id.tv_head)
    TextView tv_head;

    @Override
    protected int getLayout() {
        return R.layout.activity_invite;
    }

    @Override
    protected void init() {
        tv_head.setText("邀请好友");
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
        intent.putExtra(Intent.EXTRA_TEXT, "邀请码");
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
