package id.co.imastudio.adabkepadaorangtua;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idn on 3/3/2017.
 */
public class AdabAdapter extends PagerAdapter {
    private final Activity activity;
    private int[] imagesId;
    private int[] imagesText;
    private int[] sounds;

    Map<Integer, View> imageViews = new HashMap<Integer, View>();
    Map<Integer, View> imageTexts = new HashMap<Integer, View>();
    Map<Integer, View> suara = new HashMap<Integer, View>();



    Context context;
    private String kondisi;

    public AdabAdapter(Activity activity, int[] imagesId, int[]imagesText, int[]sounds, Context context) {
        this.activity = activity;
        this.imagesId = imagesId;
        this.imagesText = imagesText;
        this.sounds = sounds;
        this.context = context;

    }

    public Map<Integer, View> getImageViews() {
        return imageViews;
    }

    @Override
    public int getCount() {
        return imagesId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = activity.getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_adab_element, null);



        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        ImageView imageText = (ImageView) view.findViewById(R.id.imageText);
//        Glide.with(context).load(imagesText[position]).fitCenter().into(imageText);
        Glide.with(context).load(imagesId[position]).into(imageView);
//        imageView.setImageResource(imagesId[position]);
//        imageText.setImageResource(imagesText[position]);
//        imageText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MediaPlayer mPlayer = MediaPlayer.create(context, sounds[position]);
//                if(kondisi == "lanjut"){
////                    Toast.makeText(context, "Stoop", Toast.LENGTH_SHORT).show();
////                    return;
//                    mPlayer.stop();
//                    kondisi = "berhenti";
//                } else {
//                    mPlayer.start();
////                    Toast.makeText(context, "Start", Toast.LENGTH_SHORT).show();
//                    kondisi = "lanjut";
//                }
//
//
//            }
//        });
        container.addView(view);
        imageViews.put(position, imageView);
        imageTexts.put(position, imageText);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
