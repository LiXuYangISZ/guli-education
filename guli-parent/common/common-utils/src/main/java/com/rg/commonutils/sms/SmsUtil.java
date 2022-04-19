// /*
// package com.rg.commonutils.sms;
//
// import org.apache.http.HttpResponse;
//
// import java.util.HashMap;
// import java.util.Map;
//
//
// /**
//  * @author lxy
//  * @version 1.0
//  * @Description
//  * @date 2022/3/6 14:35
//  */
//
// import com.sun.deploy.net.HttpResponse;
//
// import java.util.HashMap;
// import java.util.Map;
//
// public class SmsUtil {
//
// /*
//      *
//      * @param mobile  手机号
//      * @param code    发送的验证码
//      * @param minute  多长时间有效
//      * @param smsSignId  签名id
//      * @param templateId 模板id
//      * @param minute
//      */
//
//     public static void sendMessage(String appCode,String mobile,String code,String minute,String smsSignId,String templateId){
//         String host = "https://gyytz.market.alicloudapi.com";
//         String path = "/sms/smsSend";
//         String method = "POST";
//         Map <String, String> headers = new HashMap <String, String>();
//         //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//         headers.put("Authorization", "APPCODE " + appCode);
//         Map<String, String> querys = new HashMap<String, String>();
//         querys.put("mobile", mobile);
//         querys.put("param", "**code**:"+code+",**minute**:"+minute);
//         querys.put("smsSignId", smsSignId);
//         querys.put("templateId", templateId);
//         Map <String, String> bodys = new HashMap <String, String>();
//
//
//         try {
//
// /*
//              * 重要提示如下:
//              * HttpUtils请从12
//              * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//              * 下载
//              *
//              * 相应的依赖请参照
//              * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//              */
//
//             HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//             System.out.println(response.toString());
//             //获取response的body
//             //System.out.println(EntityUtils.toString(response.getEntity()));
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
//
