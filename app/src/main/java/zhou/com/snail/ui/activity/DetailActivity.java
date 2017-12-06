package zhou.com.snail.ui.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zhou.com.snail.R;
import zhou.com.snail.base.App;
import zhou.com.snail.base.BaseActivity;
import zhou.com.snail.bean.PersonalBean;
import zhou.com.snail.config.Constant;
import zhou.com.snail.view.Xcircleindicator;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.tv_username) TextView tv_username;
    @BindView(R.id.tv_province) TextView tv_province;
    @BindView(R.id.tv_city) TextView tv_city;
    @BindView(R.id.tv_constellation) TextView tv_constellation;
    @BindView(R.id.tv_major) TextView tv_major;
    @BindView(R.id.tv_labels) TextView tv_labels;
    @BindView(R.id.tv_sports) TextView tv_sports;
    @BindView(R.id.tv_musics) TextView tv_musics;
    @BindView(R.id.tv_foods) TextView tv_foods;
    @BindView(R.id.tv_films) TextView tv_films;
    @BindView(R.id.tv_books) TextView tv_books;
    @BindView(R.id.tv_travels) TextView tv_travels;
    @BindView(R.id.list_pager) ViewPager list_pager;
    @BindView(R.id.Xcircleindicator) Xcircleindicator mXcircleindicator;

    private List<String> photoUrlList;
    private List<View> list_view;

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void init() {
        setInfo();
    }

    private void setInfo() {
        photoUrlList = new ArrayList<>();
        PersonalBean personalBean = App.getInstence().getPersonalBean();
        if (personalBean==null){
            return;
        }
        List<PersonalBean.MemInfoBean.PicturesBean> pictures = personalBean.getMemInfo().getPictures();
        for (int i=0;i<pictures.size();i++){
            photoUrlList.add(pictures.get(i).getPhotoUrl());
            System.out.println(photoUrlList.get(i));
        }
        tv_username.setText(TextUtils.isEmpty(personalBean.getMemInfo().getRealname())?"":personalBean.getMemInfo().getRealname());
        tv_province.setText(TextUtils.isEmpty(personalBean.getMemInfo().getCity().getProvince().getName())?"":personalBean.getMemInfo().getCity().getProvince().getName());
        tv_city.setText(TextUtils.isEmpty(personalBean.getMemInfo().getCity().getName())?"":personalBean.getMemInfo().getCity().getName());
        tv_constellation.setText(TextUtils.isEmpty(personalBean.getMemInfo().getConstellation())?"":personalBean.getMemInfo().getConstellation());
        tv_major.setText(TextUtils.isEmpty(personalBean.getMemInfo().getMajor().getMajorName())?"":personalBean.getMemInfo().getMajor().getMajorName());
        tv_labels.setText(TextUtils.isEmpty(personalBean.getMemInfo().getLabels())?"":personalBean.getMemInfo().getLabels());
        tv_sports.setText(TextUtils.isEmpty(personalBean.getMemInfo().getSports())?"":personalBean.getMemInfo().getSports());
        tv_musics.setText(TextUtils.isEmpty(personalBean.getMemInfo().getMusic())?"":personalBean.getMemInfo().getMusic());
        tv_foods.setText(TextUtils.isEmpty(personalBean.getMemInfo().getFoods())?"":personalBean.getMemInfo().getFoods());
        tv_films.setText(TextUtils.isEmpty(personalBean.getMemInfo().getFilms())?"":personalBean.getMemInfo().getFilms());
        tv_books.setText(TextUtils.isEmpty(personalBean.getMemInfo().getBooks())?"":personalBean.getMemInfo().getBooks());
        tv_travels.setText(TextUtils.isEmpty(personalBean.getMemInfo().getTravels())?"":personalBean.getMemInfo().getTravels());
        setPhoto();
    }

    private void setPhoto() {
        list_view = new ArrayList<>();
        for (int i=0;i<photoUrlList.size();i++){
            //System.out.println(photoUrlList.get(i));
            LayoutInflater inflater = getLayoutInflater();
            View inflate = inflater.inflate(R.layout.fragment_page, null);
            ImageView iv_image = inflate.findViewById(R.id.iv_image);
            Glide.with(getApplicationContext()).load(Constant.URL+photoUrlList.get(i)).into(iv_image);
            list_view.add(inflate);
        }
        list_pager.setAdapter(new MypageAdapter());
        //设置总共的页数
        mXcircleindicator.initData(photoUrlList.size(), 0);
        //设置当前的页面
        mXcircleindicator.setCurrentPage(0);
        list_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mXcircleindicator.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * PagerAdapter 适配器
     */
    public class MypageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list_view.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(list_view.get(position));
            return list_view.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list_view.get(position));
        }
    }

    @OnClick({R.id.iv_all_back,R.id.tv_edit}) void onClick(View view){
        switch (view.getId()){
            case R.id.iv_all_back:
                finish();
                break;
            case R.id.tv_edit:
                startToActivity(EditActivity.class);
                break;
        }
    }
}
