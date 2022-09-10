package com.techvg.vks.society.mapper;

import org.mapstruct.Mapper;

import com.techvg.vks.society.domain.Notification;
import com.techvg.vks.society.model.NotificationDto;


@Mapper(componentModel = "spring")
public interface NotificationMapper {

	NotificationDto notificationToNotificationDto(Notification notification);
	Notification notificationDtoToNotification(NotificationDto notificationDto);
}