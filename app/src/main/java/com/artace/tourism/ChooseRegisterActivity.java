package com.artace.tourism;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.artace.tourism.databinding.ActivityChooseRegisterBinding;

public class ChooseRegisterActivity extends AppCompatActivity {

    ActivityChooseRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_choose_register);

        binding.providerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseRegisterActivity.this, RegisterProviderActivity.class);
                startActivity(intent);
            }
        });

        binding.travellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseRegisterActivity.this, RegisterTrevelerActivity.class);
                startActivity(intent);
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
