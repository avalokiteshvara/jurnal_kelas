package com.app.jurnalkelas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.jurnalkelas.util.SharedPrefManager;
import com.app.jurnalkelas.util.api.UtilsApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloWorld extends AppCompatActivity {

    @BindView(R.id.txtHello)
    TextView txtHello;

    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hello);
        ButterKnife.bind(this);

        mContext = this;
    }

    @OnClick(R.id.txtHello)
    public void gotoMainActivity() {
        startActivity(new Intent(HelloWorld.this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}
