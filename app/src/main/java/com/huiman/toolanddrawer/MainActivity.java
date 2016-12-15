package com.huiman.toolanddrawer;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private ListView mLvShouye;
    private Switch mSwitch;
    private TextView mNightView;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean isDrawerOpen = false;
    private long lastTime;
    private WindowManager mWindowManager;
    private RelativeLayout mRelative, mRlTouch;
    private ImageView mIvLoading;
    private AnimationDrawable mAnimationDrawable;
    private GestureDetector gestureDetector;
    private LinearLayout llHome;
    private boolean isHiden = false;
    private String items[] = {"List Item1", "List Item2", "List Item3", "List Item4"
            , "List Item5", "List Item6", "List Item7", "List Item8", "List Item9"
            , "List Item10", "List Item11", "List Item12", "List Item13", "List Item14"
            , "List Item15", "List Item16", "List Item17", "List Item18"};

    /**
     * this is change
     * has added
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        gestureDetector = new GestureDetector(this, new MyGesture());
        mLvShouye.setOnTouchListener(this);
        mIvLoading.setImageResource(R.drawable.loading_car);
        mAnimationDrawable = (AnimationDrawable) mIvLoading.getDrawable();
        mAnimationDrawable.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRelative.setVisibility(View.GONE);
            }
        }, 3000);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        ArrayAdapter adapter = new ArrayAdapter(
                this, android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(adapter);
        mLvShouye.setAdapter(adapter);
        toolbar.setTitle("全民阅读");
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                isDrawerOpen = false;
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isDrawerOpen = true;
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isDrawerOpen) {
            mDrawerLayout.closeDrawers();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initViews() {
        mLvShouye = (ListView) findViewById(R.id.lv_shouye);
        //刷新
//        mLvShouye.setMode(PullToRefreshBase.Mode.BOTH);
//        ILoadingLayout iLoadingLayout = mLvShouye.getLoadingLayoutProxy(true, false);
//        iLoadingLayout.setPullLabel("下拉可以刷新...");// 刚下拉时，显示的提示
//        iLoadingLayout.setRefreshingLabel("正在刷新...");// 刷新时
//        iLoadingLayout.setReleaseLabel("松开可以刷新...");// 下来达到一定距离时，显示的提示
//        mLvShouye.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mLvShouye.onRefreshComplete();
//                    }
//                }, 3000);
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mLvShouye.onRefreshComplete();
//                    }
//                }, 3000);
//            }
//        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        mListView = (ListView) findViewById(R.id.lv_items);
        mSwitch = (Switch) findViewById(R.id.swt_night);
        mRelative = (RelativeLayout) findViewById(R.id.rl_loadingcar);
        mIvLoading = (ImageView) findViewById(R.id.iv_car);
        llHome = (LinearLayout) findViewById(R.id.ll_home);
        mRlTouch = (RelativeLayout) findViewById(R.id.rl_touch);
        //白天与夜间模式的切换
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mSwitch.isChecked()) {
                    night();
                } else {
                    if (mNightView != null) {
                        mWindowManager.removeView(mNightView);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu2:
                Toast.makeText(this, "提醒", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu3:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu4:
                Toast.makeText(this, "关于", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doBottom(View view) {
        switch (view.getId()) {
            case R.id.btn_setting:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_finish:
                finish();
                break;
            default:
                break;
        }
    }

    private void night() {
        if (mNightView == null) {
            mNightView = new TextView(this);
            mNightView.setBackgroundColor(0x55000000);
        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        lp.gravity = Gravity.BOTTOM;
        lp.y = 10;

        try {
            mWindowManager.addView(mNightView, lp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime > 2000) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            lastTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    public class MyGesture implements OnGestureListener {
        //TODO
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            float y = motionEvent1.getY() - motionEvent.getY();
            if (y < 0) {
                if (!isHiden) {
                    hide();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toolbar.setVisibility(View.GONE);
                        }
                    }, 500);
                }
            } else {
                if (isHiden) {
                    show();
                    toolbar.setVisibility(View.VISIBLE);
                }
            }
            return false;
        }
    }

    private void hide() {
        ObjectAnimator obj1 = ObjectAnimator.ofFloat(toolbar, "y", toolbar.getY(),
                toolbar.getY() - toolbar.getHeight());
        obj1.setDuration(500);
        obj1.start();
        isHiden = true;
    }

    private void show() {
        ObjectAnimator obj1 = ObjectAnimator.ofFloat(toolbar, "y", toolbar.getY(),
                toolbar.getY() + toolbar.getHeight());
        obj1.setDuration(500);
        obj1.start();
        isHiden = false;
    }

}