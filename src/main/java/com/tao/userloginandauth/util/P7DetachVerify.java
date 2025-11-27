//package com.tao.userloginandauth.util;
//
//import cn.com.jit.new_vstk.AdvanceSignClient;
//import cn.com.jit.new_vstk.Bean.VerifyResult;
//import cn.com.jit.new_vstk.exception.NewCSSException;
//
///**
// * @Description TODO 证书验签
// * @Author puxing
// * @Date 2025/3/24
// */
//public class P7DetachVerify {
//    public static void main(String[] args) {
//        verifyMethod();
//    }
//    public static VerifyResult verifyMethod() {
//        VerifyResult result = null;
//        try {
//            // 如果找不到类路径下的cssconfig.properties文件，请写绝对路径
//            AdvanceSignClient client = new AdvanceSignClient("cssconfig.properties");
//            //需要验签名的原文
//            byte[] plain = "icspdemo_GXQXT_YB:Ythpt@2024".getBytes();
//            //制作非base64格式的签名结果
//            byte[] signData = "aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==".getBytes();
//            System.out.println("signData");
//            //验签名
//            //todo signData是前端生成的随机数  plain是前端生成的随机数经过调用证书插件生成的签名
//            result = client.verify(signData, plain);
//            System.out.println("****验签成功****");
//            System.out.println("    证书subjectdn: " + result.getSubjectdn());
//            System.out.println("    证书issuer： " + result.getIssure());
//            System.out.println("    证书SN： " + result.getSn());
//            System.out.println("    证书实体base64： " + result.getDsCert());
//        } catch (NewCSSException e) {
//            System.out.println("****操作失败****");
//            System.out.println("错误号为：" + e.getCode());
//            System.out.println("错误描述为: " + e.getDescription());
//            System.out.println("日志标识码: " + e.getSerialNumber());
//
//        }
//        return result;
//    }
//}
//
