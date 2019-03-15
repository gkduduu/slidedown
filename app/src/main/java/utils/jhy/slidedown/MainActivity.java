package utils.jhy.slidedown;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout LL_WHOLE;
    RelativeLayout RL_BAR;
    RelativeLayout RL_BACK;

    float donw_Y;
    float up_Y;
    float move_Y;


    int screenX;
    int screenY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;

        RL_BACK = findViewById(R.id.RL_BACK);
        RL_BACK.setTranslationY(-RL_BACK.getHeight());

        LL_WHOLE = findViewById(R.id.LL_WHOLE);
        RL_BAR = findViewById(R.id.RL_BAR);

        RL_BAR.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //event.getAction  2 : 눌름   1 : 뗌

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    donw_Y = LL_WHOLE.getY();
//                    if(donw_Y == 0) {
//                        RL_BACK.setTranslationY(-RL_BACK.getHeight());
//                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (LL_WHOLE.getY() + event.getY() < 0) {
                        LL_WHOLE.setTranslationY(0);
                        RL_BACK.setTranslationY(-RL_BACK.getHeight());
                    } else {
                        LL_WHOLE.setTranslationY(LL_WHOLE.getY() + event.getY());
                        RL_BACK.setTranslationY(-RL_BACK.getHeight() + LL_WHOLE.getY());
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    up_Y = LL_WHOLE.getY();
                    move_Y = up_Y;
                    Log.i("jhy", "moving y =>  " + getNavigationBarHeight());
                    if (up_Y - donw_Y > 0) {
                        //슬라이드 쭉 내려줌(열기)

                        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 2094-LL_WHOLE.getHeight()-getNavigationBarHeight()-up_Y - donw_Y);
                        animation.setDuration(500); // duartion in ms
                        animation.setFillEnabled(true);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                LL_WHOLE.setTranslationY(2094-LL_WHOLE.getHeight()-getNavigationBarHeight());
                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        LL_WHOLE.startAnimation(animation);

                    } else {
                        //슬라이드 올려줌(닫기)
                        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -move_Y);
                        animation.setDuration(500); // duartion in ms
                        animation.setFillEnabled(true);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                LL_WHOLE.setTranslationY(0);
                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        LL_WHOLE.startAnimation(animation);
                    }
                }

                return true;
            }
        });

    }

    private float getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
