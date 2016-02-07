package kite.ak.com.teamace.kite.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import kite.ak.com.teamace.kite.R;

/**
 * Created by S R on 07/02/2016.
 */
public  class TutorialPagerAdapter extends PagerAdapter {

    private final int[] ResId = {R.drawable.tutorialone, R.drawable.tutorialtwo, R.drawable.totorialthree, R.drawable.totorialfour,000};

    private Context context;

    public TutorialPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return ResId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        if(position<getCount()-1) {
            imageView.setImageResource(ResId[position]);
        }
        else{
            ((Activity) context).finish();
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}