package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationDto extends BaseEntityDto implements Serializable{
	private static final long serialVersionUID = 8506582894893716606L;
	
	private String notificationName;
	
	private String notificationType;
	
	private String notificationMessage;
	
	private Integer notificationPeriod;

	@Builder
	public NotificationDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, String notificationName, String notificationType,
			String notificationMessage, Integer notificationPeriod) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.notificationName = notificationName;
		this.notificationType = notificationType;
		this.notificationMessage = notificationMessage;
		this.notificationPeriod = notificationPeriod;
	}
	
	
}
