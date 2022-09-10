package com.techvg.vks.loan.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class KMPDto  extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -1774662676661288240L;

    private Integer year;
    private Boolean kmpGenerationStatus = false;
    private Boolean kmpApprovalStatus = false;
}
