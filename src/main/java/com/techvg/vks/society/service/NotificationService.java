package com.techvg.vks.society.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.society.model.NotificationDto;
import com.techvg.vks.society.model.NotificationPageList;

public interface NotificationService {

	NotificationDto addNotification(NotificationDto notificationDto, Authentication authentication);

	NotificationPageList listAllNotification(Pageable pageable);

	NotificationDto deleteNotificationById(Long id);

	NotificationDto updateNotificationDto(Long id, NotificationDto notificationDto);

	NotificationDto getNotificationById(Long id);

}
