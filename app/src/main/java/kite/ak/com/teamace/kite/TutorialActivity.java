package kite.ak.com.teamace.kite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import kite.ak.com.teamace.kite.adapters.TutorialPagerAdapter;
import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private TutorialPagerAdapter mTutorialPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mTutorialPagerAdapter = new TutorialPagerAdapter(this);

        mViewPager = (ViewPager) findViewById(R.id.tutorialActivity_tabHost);
        mViewPager.setAdapter(mTutorialPagerAdapter);

        mCircleIndicator = (CircleIndicator) findViewById(R.id.tutorialActivity_tabIndicator);
        mCircleIndicator.setViewPager(mViewPager);


    }
}
