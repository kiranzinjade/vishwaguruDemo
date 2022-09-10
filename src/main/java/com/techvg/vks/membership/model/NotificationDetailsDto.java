package com.techvg.vks.membership.model;

import java.time.LocalDateTime;
import java.util.Date;

import com.techvg.vks.base.BaseEntityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDetailsDto extends BaseEntityDto{
	
	private Date notificationDate ;
	
	private String notificationStatus;

	private Long notificationId;
	
	private Long memberId;
	
	private String fullName;
	
	private String notificationType;
    
	
	public NotificationDetailsDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Date notificationDate, String notificationStatus,
			Long notificationId, Long memberId, String fullName, String notificationType) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.notificationDate = notificationDate;
		this.notificationStatus = notificationStatus;
		this.notificationId = notificationId;
		this.memberId = memberId;
		this.fullName = fullName;
		this.notificationType=notificationType;
	}
    
	
	
	
	
}
