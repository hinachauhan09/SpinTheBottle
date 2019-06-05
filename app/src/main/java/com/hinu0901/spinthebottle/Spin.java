package com.hinu0901.spinthebottle;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class Spin extends AppCompatActivity {

    Button add,reset;
    ImageView image;
    Random random;
    int angle,member=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);

        random=new Random();
        add=(Button)findViewById(R.id.btn_addMember);
        reset=(Button)findViewById(R.id.btn_reset);
        image=(ImageView)findViewById(R.id.image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(member==0)
                {
                    int temp=angle;
                    angle=random.nextInt(3600);
                    Log.e("Angle",""+angle);
                    int duration;
                    if(temp>angle)
                        duration=temp-angle;
                    else
                        duration=angle-temp;
                    animation(temp,angle,duration*2);
                }
                else
                {
                    int temp=angle;
                    angle=random.nextInt(3600);
                    final int part=360/member;
                    int r=angle%part;
                    angle=angle-r;
                    Log.e("Angle",""+angle);
                    int duration;
                    if(temp>angle)
                        duration=temp-angle;
                    else
                        duration=angle-temp;
                    animation(temp,angle,duration*2);

                    final int a=angle%360;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Spin.this, "Bottle is pointing to Player "+(a/part+1), Toast.LENGTH_LONG).show();
                        }
                    },angle);

                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(Spin.this);
                //dialog.setTitle(R.id.title);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                dialog.setCanceledOnTouchOutside(false);
                Button ok=(Button)dialog.findViewById(R.id.btn_ok);
                Button cancel=(Button)dialog.findViewById(R.id.btn_cancel);
                final EditText e=(EditText) dialog.findViewById(R.id.player);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(e.getText().length()!=0)
                        {
                            int num= Integer.parseInt(e.getText().toString());
                            if(num%2==0 && num<=12 && num!=0) {
                                member = num;
                                add.setText("Players : "+member);
                                dialog.dismiss();
                            }
                            else if(num==0)
                            {
                                member = num;
                                add.setText("Add Players");
                                dialog.dismiss();
                            }
                            else if(num>12)
                            {
                                add.setText("Add Players");
                                Toast.makeText(Spin.this,"No. of players should not exceed 12..!!!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                add.setText("Add Players");
                                Toast.makeText(Spin.this,"You can not play between "+num+" Players..!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if(member!=0)
                {
                    e.setText(Integer.toString(member));
                }
                dialog.show();


            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation(angle,0,angle*2);
                add.setText("Add Players");
                angle=0;
                member=0;
            }
        });
    }
    void animation(int start,int end,int duration)
    {
        RotateAnimation rotate=new RotateAnimation(start,end,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotate.setFillAfter(true);
        rotate.setDuration(duration);
        rotate.setInterpolator(new AccelerateDecelerateInterpolator());
        image.startAnimation(rotate);
    }
}

