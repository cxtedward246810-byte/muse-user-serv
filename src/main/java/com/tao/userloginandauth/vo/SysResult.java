package com.tao.userloginandauth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult implements Serializable {
    private Integer status; //200成功  201失败
    private String  msg;    //提示信息
    private Object  data;   //服务器返回值数据


    public static SysResult fail(){

        return new SysResult(201,"业务调用失败!!",null);
    }
    public static SysResult fail(String msg){

        return new SysResult(201,msg,null);
    }
    public static SysResult fail(Integer status,String msg){

        return new SysResult(status,msg,"");
    }

    public static SysResult fail(String msg,Object data){

        return new SysResult(201,msg,data);
    }

    public static SysResult success(){

        return new SysResult(200,"业务调用成功!!",null);
    }

    public static SysResult success(Object data){

        return new SysResult(200,"业务调用成功!!",data);
    }

    public static SysResult success(String msg, Object data){

        return new SysResult(200,msg,data);
    }
}
