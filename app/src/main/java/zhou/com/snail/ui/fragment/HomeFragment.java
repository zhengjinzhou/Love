package zhou.com.snail.ui.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zhou.com.snail.R;
import zhou.com.snail.base.App;
import zhou.com.snail.base.BaseFragment;
import zhou.com.snail.config.Constant;
import zhou.com.snail.swipe.SwipeFlingAdapterView;
import zhou.com.snail.ui.activity.HerActivity;
import zhou.com.snail.util.CurrentTimeUtil;
import zhou.com.snail.util.Md5Util;

/**
 * Created by zhou on 2017/10/22.
 */

public class HomeFragment extends BaseFragment implements SwipeFlingAdapterView.onFlingListener,
        SwipeFlingAdapterView.OnItemClickListener, View.OnClickListener {


    @BindView(R.id.swipe_view) SwipeFlingAdapterView swipeView;
    @BindView(R.id.swipeLeft) View vLeft;
    @BindView(R.id.swipeRight) View vRight;

    private static final String TAG = "HomeFragment";
    int [] headerIcons = {
            R.drawable.i1,
            R.drawable.i2,
            R.drawable.i3,
            R.drawable.i4,
            R.drawable.i5,
            R.drawable.i6
    };

    Random ran = new Random();
    private int cardWidth;
    private int cardHeight;

    private InnerAdapter adapter;
    private List<String> nameList;
    private List<String> cityList;
    private List<String> edusList;
    private List<String> yearsList;


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(View v) {

        getInfo();

        nameList = new ArrayList<>();
        cityList = new ArrayList<>();
        edusList = new ArrayList<>();
        yearsList = new ArrayList<>();
        nameList.add("张三");nameList.add("李四");nameList.add("王小二"); nameList.add("王五");nameList.add("汇一城");nameList.add("他");
        cityList.add("背景");cityList.add("背景");cityList.add("背r景");cityList.add("背e景");cityList.add("背d景");cityList.add("背景");
        edusList.add("奔溃");edusList.add("奔溃");edusList.add("奔溃");edusList.add("奔g溃");edusList.add("奔4溃");edusList.add("奔2溃");
        yearsList.add("1年");yearsList.add("2年");yearsList.add("3年");yearsList.add("14年");yearsList.add("1年");yearsList.add("1年");

        initView();
        loadData();
    }

    /**
     * 获取异性信息
     */
    private void getInfo() {
        if (App.getInstence().getUserInfo() != null){
            String token = App.getInstence().getUserInfo().getToken();
            Log.d(TAG, "getInfo: "+token);
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("token",token)
                    .build();
            Request request = new Request.Builder().url(Constant.ISOMERISM_URL).post(body).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "获取异性信息 onFailure: "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, "获取异性信息 onResponse: "+response.body().string());
                }
            });
        }
    }

    private void initView() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        cardWidth = (int) (dm.widthPixels - (2 * 18 * density));
        cardHeight = (int) (dm.heightPixels - (338 * density));

        if (swipeView != null) {
            swipeView.setIsNeedSwipe(true);
            swipeView.setFlingListener(this);
            swipeView.setOnItemClickListener(this);

            adapter = new InnerAdapter();
            swipeView.setAdapter(adapter);
        }

        if (vLeft != null) {
            vLeft.setOnClickListener(this);
        }
        if (vRight != null) {
            vRight.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swipeLeft:
                swipeView.swipeLeft();
                //swipeView.swipeLeft(250);
                break;
            case R.id.swipeRight:
                swipeView.swipeRight();
                //swipeView.swipeRight(250);
        }
    }

    @Override
    public void onItemClicked(MotionEvent event, View v, Object dataObject) {

    }

    @Override
    public void removeFirstObjectInAdapter() {
        adapter.remove(0);
    }

    @Override
    public void onLeftCardExit(Object dataObject) {

    }

    @Override
    public void onRightCardExit(Object dataObject) {

    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter == 3) {
            loadData();
        }
    }

    @Override
    public void onScroll(float progress, float scrollXProgress) {

    }

    @SuppressLint("StaticFieldLeak")
    private void loadData() {
        new AsyncTask<Void, Void, List<Talent>>() {
            @Override
            protected List<Talent> doInBackground(Void... params) {
                ArrayList<Talent> list = new ArrayList<>(10);
                Talent talent;
                for (int i = 0; i < 10; i++) {
                    talent = new Talent();
                    talent.headerIcon = headerIcons[i % headerIcons.length];
                    talent.nickname = nameList.get(ran.nextInt(nameList.size()-1));
                    talent.cityName = cityList.get(ran.nextInt(cityList.size()-1));
                    talent.educationName = edusList.get(ran.nextInt(edusList.size()-1));
                    talent.workYearName = yearsList.get(ran.nextInt(yearsList.size()-1));

                    list.add(talent);
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Talent> list) {
                super.onPostExecute(list);
                adapter.addAll(list);
            }
        }.execute();
    }

    private class InnerAdapter extends BaseAdapter {

        ArrayList<Talent> objs;

        public InnerAdapter() {
            objs = new ArrayList<>();
        }

        public void addAll(Collection<Talent> collection) {
            if (isEmpty()) {
                objs.addAll(collection);
                notifyDataSetChanged();
            } else {
                objs.addAll(collection);
            }
        }

        public void clear() {
            objs.clear();
            notifyDataSetChanged();
        }

        public boolean isEmpty() {
            return objs.isEmpty();
        }

        public void remove(int index) {
            if (index > -1 && index < objs.size()) {
                objs.remove(index);
                notifyDataSetChanged();
            }
        }


        @Override
        public int getCount() {
            return objs.size();
        }

        @Override
        public Talent getItem(int position) {
            if(objs==null ||objs.size()==0) return null;
            return objs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // TODO: getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            Talent talent = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_new_item, parent, false);
                holder  = new ViewHolder();
                convertView.setTag(holder);
                convertView.getLayoutParams().width = cardWidth;
                holder.portraitView = (ImageView) convertView.findViewById(R.id.portrait);
                holder.portraitView.getLayoutParams().height = cardHeight;
                holder.nameView = (TextView) convertView.findViewById(R.id.name);
                holder.cityView = (TextView) convertView.findViewById(R.id.city);
                holder.eduView = (TextView) convertView.findViewById(R.id.education);
                holder.workView = (TextView) convertView.findViewById(R.id.work_year);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.portraitView.setImageResource(talent.headerIcon);

            holder.nameView.setText(String.format("%s", talent.nickname));
            //holder.jobView.setText(talent.jobName);

            final CharSequence no = "暂无";

            holder.cityView.setHint(no);
            holder.cityView.setText(talent.cityName);
            holder.cityView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_location,0,0);

            holder.eduView.setHint(no);
            holder.eduView.setText(talent.educationName);
            holder.eduView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_edu,0,0);

            holder.workView.setHint(no);
            holder.workView.setText(talent.workYearName);
            holder.workView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_work_year,0,0);

            return convertView;
        }

    }

    private static class ViewHolder {
        ImageView portraitView;
        TextView nameView;
        TextView cityView;
        TextView eduView;
        TextView workView;
        CheckedTextView collectView;

    }

    public static class Talent {
        public int headerIcon;
        public String nickname;
        public String cityName;
        public String educationName;
        public String workYearName;
    }
}
