package com.techvg.vks.society.service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.Notification;
import com.techvg.vks.society.mapper.NotificationMapper;
import com.techvg.vks.society.model.NotificationDto;
import com.techvg.vks.society.model.NotificationPageList;
import com.techvg.vks.society.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
	
	private final NotificationMapper notificationMapper;
	private final NotificationRepository notificationRepository;
	
	@Override
	public NotificationDto addNotification(NotificationDto notificationDto, Authentication authentication) {
		log.debug("REST request to save Notification : {}", notificationDto);
		return notificationMapper.notificationToNotificationDto(notificationRepository.save(notificationMapper.notificationDtoToNotification(notificationDto)));

	}

	@Override
	public NotificationPageList listAllNotification(Pageable pageable) {
		log.debug("Request to get Notification : {}");

		Page<Notification> notificationPage;
		notificationPage = notificationRepository.findByisDeleted(pageable,false);

		return new NotificationPageList(notificationPage
										.getContent()
										.stream()
										.map(notificationMapper::notificationToNotificationDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(notificationPage.getPageable().getPageNumber(),
													notificationPage.getPageable().getPageSize()),
											notificationPage.getTotalElements());

	}

	@Override
	public NotificationDto deleteNotificationById(Long id) {
		Notification notification = notificationRepository.findById(id).orElseThrow(NotFoundException::new);
		if (notification != null) {
			notification.setIsDeleted(true);
			notificationRepository.save(notification);
		}
	return notificationMapper.notificationToNotificationDto(notification);

	}

	@Override
	public NotificationDto updateNotificationDto(Long id, NotificationDto notificationDto) {
		Notification notification = notificationRepository.findById(id).orElseThrow(NotFoundException::new);
		notificationDto.setId(notification.getId());	
		Notification notification1 =notificationMapper.notificationDtoToNotification(notificationDto);
		return notificationMapper.notificationToNotificationDto(notificationRepository.save(notification1));
	}

	@Override
	public NotificationDto getNotificationById(Long id) {
		log.debug("REST request to get notification : {}", id);
		Notification notification = notificationRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No notification found for Id : " +id));
		return notificationMapper.notificationToNotificationDto(notification);

	}

}
