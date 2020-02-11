import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:ali_pay/ali_pay.dart';

void main() {
  const MethodChannel channel = MethodChannel('ali_pay');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await AliPay.platformVersion, '42');
  });
}
