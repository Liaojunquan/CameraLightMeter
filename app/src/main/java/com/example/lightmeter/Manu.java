package com.example.lightmeter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;


public class Manu extends AppCompatActivity {

    private long lastButtonClickTime = 0l;
    private float phone_aperture = 2.2f;         //华为P9相机光圈
    private boolean isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.manu_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
        final TextView phoneApertureText = (TextView)findViewById(R.id.phone_aperture_text);
        Intent intent = getIntent();
        phone_aperture = intent.getFloatExtra("phone_aperture",2.2f);
        phone_aperture = BigDecimal.valueOf(phone_aperture).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
        ImageButton apertureButtonL = (ImageButton)findViewById(R.id.aperture_L);
        ImageButton apertureButtonR = (ImageButton)findViewById(R.id.aperture_R);
        Button okButton = (Button)findViewById(R.id.ok);
        Button resetButton = (Button)findViewById(R.id.reset);
        phoneApertureText.setText(Float.toString(phone_aperture));

        apertureButtonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone_aperture > 1.0f)
                    phone_aperture -= 0.1f;
                phone_aperture = BigDecimal.valueOf(phone_aperture).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
                phoneApertureText.setText(Float.toString(phone_aperture));
                isChange = true;
            }
        });

        apertureButtonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone_aperture < 64.0f)
                    phone_aperture += 0.1f;
                phone_aperture = BigDecimal.valueOf(phone_aperture).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
                phoneApertureText.setText(Float.toString(phone_aperture));
                isChange = true;
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {    //恢复默认按钮
            @Override
            public void onClick(View view) {
                if(System.currentTimeMillis() - lastButtonClickTime > 2000l){
                    lastButtonClickTime = System.currentTimeMillis();
                    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.remove("Phone_Aperture");
                    editor.remove("SPEED");
                    editor.remove("FocusDistant");
                    editor.remove("ISO");
                    editor.putFloat("Phone_Aperture",2.2f);  //f/2.2默认值
                    editor.putLong("SPEED",40000000L);   //  1/25默认值
                    editor.putFloat("FocusDistant",5.0f);
                    editor.putInt("ISO",0);  //ISO50默认值
                    editor.apply();
                    phone_aperture = 2.2f;
                    phoneApertureText.setText("2.2");
                    Toast.makeText(Manu.this, "恢复默认值成功!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis() - lastButtonClickTime > 2000l){           //防止短时间重复按
            lastButtonClickTime = System.currentTimeMillis();
            if (isChange){
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.remove("Phone_Aperture");
                editor.putFloat("Phone_Aperture",phone_aperture);
                editor.apply();                          //保存菜单数据
            }

            Intent intent = new Intent(Manu.this,
                    CameraActivity.class);     //重启App
            startActivity(intent);
            finish();
        }
    }
}
