//package com.tao.userloginandauth.util;
//
///**
// * @Description TODO
// * @Author puxing
// * @Date 2025/4/14
// */
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
///*
// * 利用HttpClient进行post请求的工具类
// */
//public class HttpClientUtil {
//    public static String doPost(String url,Map<String,String> map,String charset){
//        HttpClient httpClient = null;
//        HttpPost httpPost = null;
//        String result = null;
//        try{
//            httpClient = new SSLClient();
//            httpPost = new HttpPost(url);
//            //设置参数
//            List<NameValuePair> list = new ArrayList<NameValuePair>();
//            Iterator iterator = map.entrySet().iterator();
//            while(iterator.hasNext()){
//                Entry<String,Object> elem = (Entry<String, Object>) iterator.next();
//                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue().toString()));
//            }
//            if(list.size() > 0){
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
//                httpPost.setEntity(entity);
//            }
//            HttpResponse response = httpClient.execute(httpPost);
//            if(response != null){
//                HttpEntity resEntity = response.getEntity();
//                if(resEntity != null){
//                    result = EntityUtils.toString(resEntity,charset);
//                }
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//
//    public static String doPostHttps(String url,Map<String,Object> map,String charset){
//        SkipHttpsUtil  skipHttpsUtil=new SkipHttpsUtil();
//        CloseableHttpClient httpclient = null;
//        CloseableHttpResponse response = null;
//        httpclient =  (CloseableHttpClient) SkipHttpsUtil.wrapClient();
//        HttpPost httpPost = null;
//        String result = null;
//        try{
//            httpPost = new HttpPost(url);
//            //设置参数
//            List<NameValuePair> list = new ArrayList<NameValuePair>();
//            Iterator iterator = map.entrySet().iterator();
//            while(iterator.hasNext()){
//                Entry<String,Object> elem = (Entry<String, Object>) iterator.next();
//                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue().toString()));
//            }
//            if(list.size() > 0){
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
//                httpPost.setEntity(entity);
//            }
//            response = httpclient.execute(httpPost);
//            if(response != null){
//                HttpEntity resEntity = response.getEntity();
//                if(resEntity != null){
//                    result = EntityUtils.toString(resEntity,charset);
//                }
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     * md5加密
//     *
//     * @param str
//     * @return
//     */
//    public static String md5(String str) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(str.getBytes());
//            byte[] byteDigest = md.digest();
//            int i;
//            StringBuffer buf = new StringBuffer("");
//            for (byte element : byteDigest) {
//                i = element;
//                if (i < 0) {
//                    i += 256;
//                }
//                if (i < 16) {
//                    buf.append("0");
//                }
//                buf.append(Integer.toHexString(i));
//            }
//            // 32位加密
//            return buf.toString().toUpperCase();
//            // 16位的加密
//            // return buf.toString().substring(8, 24);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
//
