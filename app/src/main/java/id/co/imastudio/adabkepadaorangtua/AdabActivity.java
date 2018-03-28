package id.co.imastudio.adabkepadaorangtua;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;

public class AdabActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    int[] picturesnadia = new int[]{
            R.drawable.ot01,
            R.drawable.ot02,
            R.drawable.ot03,
            R.drawable.ot04,
            R.drawable.ot05,
            R.drawable.ot06,
            R.drawable.ot07,
            R.drawable.ot08,
            R.drawable.ot09,
            R.drawable.ot10
    };

    int[] text = new int[]{
            R.drawable.teks01,
            R.drawable.teks02,
            R.drawable.teks03,
            R.drawable.teks04,
            R.drawable.teks05,
            R.drawable.teks06,
            R.drawable.teks07,
            R.drawable.teks08,
            R.drawable.teks09,
            R.drawable.teks10
    };


    int[] suaranadia = new int[]{
            R.raw.suaranadia01,
            R.raw.suaranadia02,
            R.raw.suaranadia03,
            R.raw.suaranadia04,
            R.raw.suaranadia05,
            R.raw.suaranadia06,
            R.raw.suaranadia07,
            R.raw.suaranadia08,
            R.raw.suaranadia09,
            R.raw.suaranadia10
    };

    int[] suarabilal = new int[]{
            R.raw.suarabilal01,
            R.raw.suarabilal02,
            R.raw.suarabilal03,
            R.raw.suarabilal04,
            R.raw.suarabilal05,
            R.raw.suarabilal06,
            R.raw.suarabilal07,
            R.raw.suarabilal08,
            R.raw.suarabilal09,
            R.raw.suarabilal10
    };

    private int width;
    ViewPager viewPager;
    private AdabAdapter adapter;
    Button next, back, home, option;

    Context context;

    int karakter;
    private boolean isLastPageSwiped;
    private int counterPageScroll;

    ImageView naik, turun, angka;
    AudioManager audioManager;
    int volume;
    private String settinganSound;
    private int posisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adab);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViewPagerAndSetAdapter();
        calculateWidth();

        next = (Button) findViewById(R.id.buttonNext);
        back = (Button) findViewById(R.id.buttonBack);
        home = (Button) findViewById(R.id.buttonHome);
        option = (Button) findViewById(R.id.buttonOption);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posisi == (picturesnadia.length - 1)) {
//            Toast.makeText(AdabActivity.this, "akhir", Toast.LENGTH_SHORT).show();


                            final Dialog dialog = new Dialog(AdabActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.dialog_hebat);
                            dialog.show();
                            MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.suarabilalhebat);
                            mPlayer.start();

                            ImageView btnHome = (ImageView) dialog.findViewById(R.id.btn_finish_home);
                            btnHome.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(AdabActivity.this, FullscreenActivity.class);
                                    startActivity(i);
                                    finish();
                                    playSound();
                                }
                            });
                            ImageView btnRepeat = (ImageView) dialog.findViewById(R.id.btn_finish_repeat);
                            btnRepeat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    playSound();
                                    dialog.dismiss();
                                    viewPager.setCurrentItem(0);
                                }
                            });
                        }

                viewPager.setCurrentItem(getItem(+1), true);
                playSound();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(-1), true);
                playSound();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdabActivity.this, FullscreenActivity.class);
                startActivity(i);
                finish();
                playSound();
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();

                viewPager.setCurrentItem(0);
            }
        });
    }

    private void initViewPagerAndSetAdapter() {
        viewPager = (ViewPager) findViewById(R.id.pager);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyChar", 0); // 0 - for private mode
        karakter = pref.getInt("karakter", 0);
        Log.i("karaktere ", "" + karakter);

        if (karakter == 0) {
            adapter = new AdabAdapter(this, picturesnadia, text, suarabilal, getApplicationContext());
        } else {
//            adapter = new AdabAdapter(this, picturesnadia, text, suaranadia, getApplicationContext());
        }

        viewPager.setAdapter(adapter);

        addPageChangeListenerIfSDKAbove11();
    }

    private void addPageChangeListenerIfSDKAbove11() {
        if (Build.VERSION.SDK_INT > 11) {
            viewPager.setOnPageChangeListener(this);
        }
    }

    private void calculateWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        viewPager.getWidth();

        if (Build.VERSION.SDK_INT < 13) {
            width = display.getWidth();
        } else {
            display.getSize(size);
            width = size.x;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        parallaxImages(position, positionOffsetPixels);


        if (position == 6 && positionOffset == 0 && !isLastPageSwiped) {
            if (counterPageScroll != 0) {
                isLastPageSwiped = true;
                //Next Activity here
                Toast.makeText(context, "akhir", Toast.LENGTH_SHORT).show();
            }
            counterPageScroll++;
        } else {
            counterPageScroll = 0;
        }


    }

    @Override
    public void onPageSelected(int position) {
//        Toast.makeText(AdabActivity.this, "Posisi"+position, Toast.LENGTH_SHORT).show();
        posisi = position;
//        if(position == 11)
//            Toast.makeText(context, "akhir", Toast.LENGTH_SHORT).show();
    }

    private void parallaxImages(int position, int positionOffsetPixels) {
        Map<Integer, View> imageViews = adapter.getImageViews();

        for (Map.Entry<Integer, View> entry : imageViews.entrySet()) {
            int imagePosition = entry.getKey();
            int correctedPosition = imagePosition - position;
            int displace = -(correctedPosition * width / 2) + (positionOffsetPixels / 2);

            View view = entry.getValue();
            view.setX(displace);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    public void playSound() {
//        boolean isPlaying= false;
        MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sfxbutton);
        mPlayer.start();


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AdabActivity.this, FullscreenActivity.class);
        startActivity(i);
        finish();
        playSound();
    }

    public void initControls() {

    }
}
