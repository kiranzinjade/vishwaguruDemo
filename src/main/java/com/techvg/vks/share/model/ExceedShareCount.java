
package com.techvg.vks.share.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceedShareCount {

    private Double remainingShareAmount;
    private int canAllocateShareCount;

    private Double allocatedShareAmount;
    private int allocatedShareCount;
	
}
