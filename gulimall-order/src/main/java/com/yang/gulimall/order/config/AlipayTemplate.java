package com.yang.gulimall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.yang.gulimall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "2021000117682923";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCl681K8VFXVcByximBgv2QQPMxWV25LE+2uVi6fz+f/Z6UjgWWfrJ29GC4SKv6aDS67yGpmw1ZYgbZKGzyIbEIDL6XIufO4d/BAI7mJyXrUzE4XMejxRoMm7SI1hztNWOxNQ5BSmHGXiMRIijnbP65b1TOpzLklpIn4KZ4EWl5ISCC9U06etJWVJ9CL+6rKSi52yAYi1z/tThlnCMaPCPOXSjhapff4m40VZOg5Zax6GQLcRKN9X6JA1S+hAeGxwdvRqOnxxv00mj4+RQF8BNJm4//yMc950gu3FHHhH/ZioSCXtjPJOkzj12ZFmvROF3Kjq+xSBH83pblooJE0LKnAgMBAAECggEAbc2WR7FZ3k0+lfY0Q6RXf96r0o3Mq536hvc0E//gMFYcWz19zzG7k2N4mpxlS8sokkls4pxdAjkQRSguqTLIwIYGn2iXNd9fP4/KAeAIhSrCGQW1jNkEuzFUEXZ5IDZD6mMtMuIxwKBN3qnXnggQrtFRs40M7iADS2atZCpJvcGv18GjP2BE/WgtZOhOhxLhzyh+0iGQhzTK6eUAbAZVJ+EfXepYAwlHVelBqo1Xn4hhbNnS42NEMO8uzDo459W+zGjnnJSw4Cnoaz4NsrPMbEDVOA0uWc2OFwqUjNJ3Q2kxRtxN01gfti3Ewo1ceuuDszXSJkzflW1/UINJ14NlEQKBgQDfxvsbfk37xhjuvzfF8EaK0SeuG3qvnh/+6PKDgSoJLcpZYxugpvDB/gEk3i/HDfLURD3Thi/L890Vw7Bvs6aHJ7Qv5op9jgGIlRDJA3HpYIr+tddQ9p6qFrCMT2qRLke8IIDErJie4c2EHUa8Gf7V5rFKG/oKSfWrk4u9P31czwKBgQC90BcldSiLeRIN0x88xdsM6Y4WjFp8IzDhW4/AKYaK86T9LE/Q7KPQ0x85OzwLzlbvupK4wHHdsZj4Q8Iq/K7cegdnyBT8e8DrIEpvbJCYv5eA0kW2ew+9uQSKGUtUkM2KwNqfIWvBS6axNWSbmqH8G3U+ddgRbKy+fx2z+bIyqQKBgESdX5uTg9fiavyxtylDPm4MKc2R8XFt28Ouq8W00i3UU4pLAEqnRYVGHBWw5xdy6HU7o3V7XKZ91Nl6klv9yt8ARpi1aNuMsl2tz2LDYqc7igMFwMq7g/UFVgb/xt5TQX2mvbUQcw+sjvamf2znz6LIxK3DnEyRVvnQGPXjRM4zAoGBAKowDuDV+CF9z3M3zbVGzY35Rx3ugB73ypb06qHoWarypAcgGPyBgKv27/L3qnIHcymFBpLCchXNbBOFZW33m60xeLxTyIVNTH9FN5hKJife+S9W2YyxMD7yyiAsZ7+OCc0T97FgXOY2N2KCNg/SiNSrpeb38GBHKWewP+ijxGPpAoGAVMPjIi5kb6RP4UcVl/DQeRsS4kG5UBAYDpOFt5ZyjCW0ro7AzIfvaGv02yjgGpQP6rf4aeHTOG51+8GLhk9+HJCZUz+XD/xPhcvgAYGSfIrW441y0Y+pU5Udlpoen4Ub5+AgsauqY11d26WkgDkYn+0zRpHRi5NJ8+FBUr1ameU=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnJVBFiK+Y03HiJ6s+C3rCVVaWABqdkOLXs07UqEEczJq94GxzQgMX6dv+1Gr8Xq8tFGD3oRyqwukNjIevmpuYKXgmwh2RTDFBJDBNTMHVx+HPBmVfU3cK0mhiBgVUpQJy+fcNR+9NTSb3qqAorXrP5lWVmok6GvGPolxm2+NQ7iljw4mDrN/oeKEugXBZ3/Rs2N4JfCUgAbWESUtSWzONzwh8Fj/R71qfVNjIdKq55D7QCjqvIp6UIufPWHc/8NoTh7LADmB9DYwT/RKIV34mIsoJ9pn2cOvfYJZ5d2bWNGlgVetC3S+CoWXPDrxt2oIKTzNmRELC6aMW5Ry1mjNRwIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url="http://order.gulimall.com/memberOrder.html";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url="http://order.gulimall.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                +"\"timeout_express\":\"1m\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
