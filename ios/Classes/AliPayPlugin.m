#import "AliPayPlugin.h"

@implementation AliPayPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"ali_pay"
            binaryMessenger:[registrar messenger]];
  AliPayPlugin* instance = [[AliPayPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  } else if ([@"pay" isEqualToString:call.method]) {
  [self alipay:call.arguments];
      if (![[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"alipay://"]]) {
          result([@"iOS " stringByAppendingString:@"请先安装支付宝App"]);
          [self alipay:call.arguments];
      }else{
          [self alipay:call.arguments];
          result([@"iOS " stringByAppendingString:@"支付宝支付"]);
      }
  } else {
    result(FlutterMethodNotImplemented);
  }
}

-(void) alipay:(NSString *)data{
    [[AlipaySDK defaultService] payOrder:data fromScheme:@"点运-货主版" callback:^(NSDictionary *resultDic) {
        NSLog(@"reslut = %@",resultDic);
    }];
}

- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<NSString*, id> *)options
{
    if ([url.host isEqualToString:@"safepay"]) {
        //跳转支付宝钱包进行支付，处理支付结果
        [[AlipaySDK defaultService] processOrderWithPaymentResult:url standbyCallback:^(NSDictionary *resultDic) {
            NSLog(@"result = %@",resultDic);
        }];
    }
    return YES;
}

@end
