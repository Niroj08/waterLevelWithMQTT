package com.technotroop.mqttdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

public class UserRegisterActivity extends AppCompatActivity {

    RelativeLayout buttonEffect;

    TextView tourTitle, tourMessage;
    ImageView imgWaterTank, imgRouter, imgIOS, imgAndroid;
    View linetitle;

    Button btnBeginTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        tourTitle = (TextView) findViewById(R.id.textTourTitle);
        tourMessage = (TextView) findViewById(R.id.textTourMessage);

        imgWaterTank = (ImageView) findViewById(R.id.imgWaterTank);
        imgRouter = (ImageView) findViewById(R.id.imgRouter);
        imgIOS = (ImageView) findViewById(R.id.imgDeviceIOS);
        imgAndroid = (ImageView) findViewById(R.id.imgDeviceAndroid);

        linetitle = findViewById(R.id.lineTitle);

        btnBeginTour = (Button) findViewById(R.id.btnBeginTour);
        buttonEffect = (RelativeLayout) findViewById(R.id.viewEffect);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(1000);

        Animation zoomIn = new ScaleAnimation(1, 4, 1, 4, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        zoomIn.setDuration(1000);

        final AnimationSet focusAnim = new AnimationSet(true);
        focusAnim.addAnimation(zoomIn);
        focusAnim.addAnimation(fadeOut);

        buttonEffect.startAnimation(focusAnim);

        focusAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                buttonEffect.startAnimation(focusAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btnBeginTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonEffect.getAnimation() != null) {
                    buttonEffect.setAnimation(null);
                    buttonEffect.setVisibility(View.GONE);
                    btnBeginTour.setVisibility(View.GONE);
                }

                tourTitle.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInDown)
                        .duration(1000)
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                                linetitle.setVisibility(View.VISIBLE);
                                YoYo.with(Techniques.SlideInRight)
                                        .duration(1000)
                                        .withListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                imgWaterTank.setVisibility(View.VISIBLE);
                                                YoYo.with(Techniques.ZoomIn)
                                                        .duration(1500)
                                                        .withListener(new Animator.AnimatorListener() {
                                                            @Override
                                                            public void onAnimationStart(Animator animation) {

                                                            }

                                                            @Override
                                                            public void onAnimationEnd(Animator animation) {
                                                                imgRouter.setVisibility(View.VISIBLE);
                                                                YoYo.with(Techniques.SlideInRight)
                                                                        .duration(800)
                                                                        .withListener(new Animator.AnimatorListener() {
                                                                            @Override
                                                                            public void onAnimationStart(Animator animation) {

                                                                            }

                                                                            @Override
                                                                            public void onAnimationEnd(Animator animation) {
                                                                                imgIOS.setVisibility(View.VISIBLE);
                                                                                YoYo.with(Techniques.Shake)
                                                                                        .withListener(new Animator.AnimatorListener() {
                                                                                            @Override
                                                                                            public void onAnimationStart(Animator animation) {

                                                                                            }

                                                                                            @Override
                                                                                            public void onAnimationEnd(Animator animation) {
                                                                                                imgAndroid.setVisibility(View.VISIBLE);
                                                                                                YoYo.with(Techniques.Tada)
                                                                                                        .duration(1000)
                                                                                                        .withListener(new Animator.AnimatorListener() {
                                                                                                            @Override
                                                                                                            public void onAnimationStart(Animator animation) {

                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onAnimationEnd(Animator animation) {
                                                                                                                tourMessage.setVisibility(View.VISIBLE);
                                                                                                                YoYo.with(Techniques.FadeIn)
                                                                                                                        .duration(1000)
                                                                                                                        .playOn(tourMessage);
                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onAnimationCancel(Animator animation) {

                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onAnimationRepeat(Animator animation) {

                                                                                                            }
                                                                                                        }).playOn(imgAndroid);
                                                                                            }

                                                                                            @Override
                                                                                            public void onAnimationCancel(Animator animation) {

                                                                                            }

                                                                                            @Override
                                                                                            public void onAnimationRepeat(Animator animation) {

                                                                                            }
                                                                                        }).playOn(imgIOS);
                                                                            }

                                                                            @Override
                                                                            public void onAnimationCancel(Animator animation) {

                                                                            }

                                                                            @Override
                                                                            public void onAnimationRepeat(Animator animation) {

                                                                            }
                                                                        })
                                                                        .playOn(imgRouter);
                                                            }

                                                            @Override
                                                            public void onAnimationCancel(Animator animation) {

                                                            }

                                                            @Override
                                                            public void onAnimationRepeat(Animator animation) {

                                                            }
                                                        })
                                                        .playOn(imgWaterTank);
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {

                                            }
                                        })
                                        .playOn(linetitle);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .playOn(tourTitle);
            }
        });
    }
}
