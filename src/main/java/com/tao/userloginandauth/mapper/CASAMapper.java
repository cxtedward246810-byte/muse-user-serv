package com.tao.userloginandauth.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author puxing
 * @Date 2025/4/14
 */
@Mapper
public interface CASAMapper {
    List<HashMap<String, Object>> selectData();

    List<HashMap<String, Object>> selectEncryData();

    List<HashMap<String, Object>> selectStaInfoData();

    List<HashMap<String, Object>> selectGisPolygon();

    List<HashMap<String, Object>> selectMATERIAL();

    List<HashMap<String, Object>> selectEncryStationInfo();
}

