package com.yidao.threekmo.parameter;

/**
 * Created by Administrator on 2017\8\23 0023.
 */

public interface WebParameters {
    //   public String SERVERURL = "http://120.77.245.123:8080/tkm/";
    //测试服务器
//    String SERVERURL = "http://192.168.0.80:7070/";
    String SERVERURL = "http://app.3kmo.com/";  //  for test environment please use .cn and .com is the release environment


//    public String SERVERURL = "http://192.168.0.83:8081/";
//    public String SERVERURL = "http://192.168.0.103:8080/";
    //正式服务器
//    public String SERVERURL = "http://116.62.70.97:8080/tkm/";

//    public String SERVERURL = "http://test.3kmo.cn/";
    //   public String SERVERURL = "http://www.3kmo.cn/";

    //  String AliYunHost = "http://tkm.oss-cn-hangzhou.aliyuncs.com/";

    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1JHASpGA//mrq4z2HoRdfmzCv" +
            "QnNwm2aNdfBcA0eKxZm6yCpBBjhORDdslsa58eq6uMhLhyGags4PNOgZfhYdzmMxi2lTX5D9" +
            "00WLPNO5qdxQCiaN4Zcc/BwsFuc23F2X/R6mKRfNCbAzhIqcFDHQMJiloLT9iS7EGGPwIxd" +
            "NhwIDAQAB";

    int PAGE_SIZE = 15;

    String LOGIN_REG = "app/user/login_code";

    String LOGIN_PSD = "app/user/login";

    String GETREGCODE = "app/reg/send_reg_code";

    String GETLOGCODE = "app/reg/send_login_code";

    String CHECKCODE = "app/reg/check_verification_code";

    //楼栋url
    String BANNUM = "app/cDictionary/list";
    //楼层url
    String FOOLNUM = "app/cDictionary/floorList";
}
