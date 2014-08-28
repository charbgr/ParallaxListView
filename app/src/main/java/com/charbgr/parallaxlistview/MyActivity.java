package com.charbgr.parallaxlistview;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //samples
        ArrayList<Drawable> drawables = new ArrayList<Drawable>();
        drawables.add(getResources().getDrawable(R.drawable.dog));
        drawables.add(getResources().getDrawable(R.drawable.hawk));
        drawables.add(getResources().getDrawable(R.drawable.hippo1));
        drawables.add(getResources().getDrawable(R.drawable.turtle));
        drawables.add(getResources().getDrawable(R.drawable.giraffe));


        CustomListView mListView = (CustomListView) findViewById(R.id.listView);
        mListView.setAdapter(new MyAdapter(this, R.layout.listview_item, drawables));
        mListView.setOnScrollListener(new ParallaxOnScrollListener(R.id.animalPhoto));

    }

}
