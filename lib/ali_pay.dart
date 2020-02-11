import 'dart:async';

import 'package:flutter/services.dart';

class AliPay {
  static const MethodChannel _channel =
      const MethodChannel('ali_pay');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  static Future<String> pay(String data) async {
    final String version = await _channel.invokeMethod('pay',data);
    return version;
  }
}
