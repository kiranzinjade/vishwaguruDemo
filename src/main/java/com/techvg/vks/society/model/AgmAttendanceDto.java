package com.techvg.vks.society.model;

import com.opencsv.bean.CsvBindByName;
import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AgmAttendanceDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 8506582894893716606L;

	@CsvBindByName(column = "member_id")
	Long memberId;

	@CsvBindByName(column = "member_name")
	String memberName;

	@CsvBindByName(column = "attendance_status")
	String attendanceStatus;

	@Builder
	public AgmAttendanceDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,
			@Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted, Long memberId,
			String memberName, String attendanceStatus, String status) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.memberId = memberId;
		this.memberName = memberName;
		this.attendanceStatus = attendanceStatus;
	}

}
