package com.tao.userloginandauth.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TDepart {
    private Integer departId;
    private Integer areaId;
    private String departName;
    private Integer parentId;
    private String departCode;
    private String codeOfTownForecast;
    private String codeOfGuidanceForecast;
}
