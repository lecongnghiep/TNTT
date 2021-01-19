package com.example.appbh.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.appbh.Model.Product;
import com.example.appbh.R;
import com.squareup.picasso.Picasso;

public class GestureIMGActivity extends AppCompatActivity {
    ImageView img;
    int mode ;
    Toolbar toolbar;
    int drag ;
    ScaleGestureDetector scaleGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_i_m_g);
        img = findViewById(R.id.imgGesture);
        toolbar = findViewById(R.id.toolbarIMG);
        init();
        scaleGestureDetector =  new ScaleGestureDetector(this,new myGeture());

        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_scale);

        getDataIntent();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(600,600);
        layoutParams.leftMargin = 50;
        layoutParams.topMargin= 50;
        img.setLayoutParams(layoutParams);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
            }
        });

        img.setOnTouchListener(new View.OnTouchListener() { //keo tha?
            RelativeLayout.LayoutParams params;
            float dx = 0, dy = 0 , x = 0 , y = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView imageView = (ImageView) v;
                scaleGestureDetector.onTouchEvent(event);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                        dx = event.getRawX() - params.leftMargin;
                        dy = event.getRawY() - params.topMargin;
                        mode = drag;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(mode == drag){
                            x = event.getRawX();
                            y = event.getRawY();

                            params.leftMargin = (int) (x - dx);
                            params.topMargin  = (int) (y - dy);

                            params.rightMargin = 0;
                            params.bottomMargin = 0;
                            params.rightMargin = params.leftMargin + (5 * params.width);
                            params.bottomMargin = params.topMargin + (10 * params.height);
                            imageView.setLayoutParams(params);

                        }
                        break;
                }
                return true;
            }
        });
    }
    class myGeture extends ScaleGestureDetector.SimpleOnScaleGestureListener{//phóng to
        float scale = 2.0F, onStart = 0 , onEnd = 0;
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            img.setScaleX(scale);
            img.setScaleY(scale);
            return super.onScale(detector);
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Toast.makeText(GestureIMGActivity.this, "start", Toast.LENGTH_SHORT).show();
            onStart = scale;
            return super.onScaleBegin(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Toast.makeText(GestureIMGActivity.this, "end", Toast.LENGTH_SHORT).show();
            onEnd = scale;
            super.onScaleEnd(detector);
        }
    }
    private void getDataIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        Product product;
        product = (Product) bundle.getSerializable("img");
        Picasso.with(getApplicationContext()).load(product.getMPicture()).into(img);
    }
    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
