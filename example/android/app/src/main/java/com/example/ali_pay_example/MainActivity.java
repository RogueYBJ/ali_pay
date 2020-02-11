package com.example.ali_pay_example;

import androidx.annotation.NonNull;

import com.example.ali_pay.AliPayPlugin;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
    AliPayPlugin.activity = this;
    GeneratedPluginRegistrant.registerWith(flutterEngine);
  }
}
