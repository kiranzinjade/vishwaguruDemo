package com.techvg.vks.membership.mapper;

import com.techvg.vks.membership.domain.NotificationDetails;
import com.techvg.vks.membership.model.NotificationDetailsDto;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotificationDetailsMapper {
	
	@Mapping(source = "notificationDetails.member.id", target="memberId")
	@Mapping(source = "notificationDetails.notification.id", target="notificationId")
	NotificationDetailsDto notificationDetailsToNotificationDetailsDto(NotificationDetails notificationDetails);
	NotificationDetails notificationDetailsDtoToNotificationDetails(NotificationDetailsDto notificationDetailsDto);
	
	@AfterMapping
	default void getMemberName(@MappingTarget NotificationDetailsDto notificationDetailsDto, NotificationDetails notificationDetails) {
	       notificationDetailsDto.setFullName(notificationDetails.getMember().getLastName()+" "+notificationDetails.getMember().getFirstName()+" "+notificationDetails.getMember().getMiddleName());
	       notificationDetailsDto.setNotificationType(notificationDetails.getNotification().getNotificationType());
	}
    
}
