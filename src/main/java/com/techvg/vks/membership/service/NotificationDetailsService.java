package com.techvg.vks.membership.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.membership.model.NotificationDetailsDto;
import com.techvg.vks.membership.model.NotificationDetailsPageList;

public interface NotificationDetailsService {

	NotificationDetailsDto addNotificationDetails(NotificationDetailsDto notificationDetailsDto);

	NotificationDetailsDto updateNotificationDetails(Long id, NotificationDetailsDto notificationDetailsDto,Authentication authentication);

	NotificationDetailsDto deleteNotificationDetailsById(Long id);

	NotificationDetailsPageList listAllNotificationDetails(Pageable pageable);

	NotificationDetailsDto getNotificationDetailsById(Long id);

}
