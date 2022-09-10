package com.techvg.vks.membership.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
public class MemCropRegDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;

    private Integer year;
    private String season;
    private String landType;
    private String landGatno;
    private Integer landAreaHector;
    private Integer landAreaR;
    private String cropName;
    private String memberName;
    private boolean kmpStatus = false;
    private boolean societyKmpStatus=false;
    private Long cropId;
    private Long memberId;

}
