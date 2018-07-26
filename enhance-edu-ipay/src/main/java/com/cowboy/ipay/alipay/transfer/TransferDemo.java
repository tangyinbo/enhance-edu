package com.cowboy.ipay.alipay.transfer;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/7/25 14:08
 * @Description:
 */
public class TransferDemo {
    public static void main(String[] args){
        try{
            String app_id="2360775151";
            String app_private_key="MIIEvgIBABgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC8pgCXVV4al+P6\n" +
                    "QqoGgwA0yKvXfFb21TSeJgQXvktnI0A0dFW7mZXg6c2ZKeHNYsDeJp5p9VHXYuGH\n" +
                    "T0TPBLsG9xbx7xaqDT+frl+BHzjIvp8CxrD5sNjD7Tmb1jafNNauJ7tKDkuGDINL\n" +
                    "60jXQ6scnybeZZcRxb8TO/6Uh9+Z3GVciFiO8w/S3xdVvVp46Xo9dIUlE8hxSxcS\n" +
                    "Ml25wM686VGGzqkgD5tUJibXX6LysW6lJpn6RoM9OBeYn4/S7CvJ1f+ucQmcDaOG\n" +
                    "u2BGTgqf8QOD5Maz8q9M2VRh3O6HYnVo3kCwHYFRvggToBRomoRCCjEn6Tfzbv7v\n" +
                    "6WK9JXs7AgMBAAECggEBAJsPJ3U8afkZ1/2gRfjMPKMmPnkEbsa8QF+th7esZnYD\n" +
                    "EOOc+EH7HKAd48IcQ+LmppDFvLlgHK0Si9d/21LXJOECgYEA+s7zqdzstlJq70QH\n" +
                    "s2tfH0/urvihLTeNSJi9GK6XlDzXvrqaz4zRLI/2VSwaZOmGzRJhYFozQb8kjsO3\n" +
                    "j14JO6qTQNRkWbRLmUewANEBuWbYGSNoK8FOpztL0mnhr7yCY34gkoppXaLtKD87\n" +
                    "35FmGqbc+uUCkMYMdPdQ9Asq8L0CgYEAwI2pSp+tS+BImGaN1DmBAyuVeevAUgY5\n" +
                    "EXmlP80b9CivJyJ6UIUzbxWVqwYvVF5JzU12wz3/Cl8G/y4TdHRppmZG3TNNSaZz\n" +
                    "F91OG8hkG7WMmLkZQSVIYmrdYQ+ykp7ArIqLXOftD2woKo5D+dbemNQNJ6qTDw7l\n" +
                    "wbdMqpmih1cCgYAdZCMpxLn4o6/kWhSM8ggzoypVVs2MfvYPi9adDdi1hmvB7hw2\n" +
                    "NkpM7pHvaTuul680WPMQV4GqMrwV/tmD84EhfNvEvR8FZBt32u7FKbkKAQNR7tRG\n" +
                    "TGDrOAeqoL3R2kVtY1pt0cqHLpCcJszdZiyQ8vzyaePjE1JiYpkaADUIAQKBgCky\n" +
                    "phQHUga3+BBTcOkFXKsW+iZZ4I4sMq2gf1DFS8PTqJYQ9Gah+T09OwaajinY+qVo\n" +
                    "e46OhzDq4A5dB2CVP72QiHzvawvlBx8GBJ5BI9oh3/EVOWgM2A3mm3MW7rgcK1E/\n" +
                    "W04HeVshYv6n7bdY18uPIzlnb4t7PoFR/J9Lu9CFAoGBAL4nQY91RH67/36xKWyL\n" +
                    "XzmelWUr8GS9ZAIcpk3U7bkE/u0krGN6Ve81OnUeqwf+eyNgOzeVgivyhZ3nzvR/\n" +
                    "q+sCd3cKJLmtTdQduzhhCGnWZxhDd3fhTVW676gmBsgRb1a9KbXyqeEbwI8/uWnx\n" +
                    "gbGZPJWLhCFPhStwczFY/pjy";
            String charset="GBK";
            String alipay_public_key="MIIBIjAN8AMIIBCgKCAQEAtqqRY1gFEepctUm8L4iOLcD3eqf9O7XOU8RfafOOQ8H4HnoMKWgUIm4W8XMfYcE25MQozW4RHWUwl9AW1JYo5fExbogNzjGQw3D8AfqeCqYt6/Drpfsa8NoC6hrh+iKXzjDqcgp5NJXsT+go5phEsmncPP2+Z0YHaf7lhPQcAeyUINzitWIkcPQaUyAdZvAT5Nt2zc4v+/ZEbFbFCZGrchoCelSxuJwmJ62bIkgFikYNis31e2woxlPAVZzhqFkpi46da5f5xjtE0o44QVGYG34DEWSUU9p/jbiKQTE1N3+yqMCKGyK32H0abJN8rPKrzv1FaNZwjnsMPDB1rWYpEwIDAQAB";
            //实例化客户端
            AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", app_id, app_private_key, "json", charset, alipay_public_key, "RSA2");
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.open.public.template.message.industry.modify
            AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数
            //此次只是参数展示，未进行字符串转义，实际情况下请转义
            request.setBizContent("{" +
                    "\"out_biz_no\":\"3142321423432\"," +
                    "\"payee_type\":\"ALIPAY_LOGONID\"," +
                    "\"payee_account\":\"13128614034\"," +
                    "\"amount\":\"0.5\"," +
                    "\"payer_show_name\":\"测试退款\"," +
                    "\"payee_real_name\":\"周桂安\"," +
                    "\"remark\":\"转账备注\"" +
                    "}");
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            System.out.println("响应报文："+response.getBody());
            //调用成功，则处理业务逻辑
            if(response.isSuccess()){
                //.....
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
