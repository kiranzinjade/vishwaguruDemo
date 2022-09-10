package com.techvg.vks.membership.service;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.domain.NotificationDetails;
import com.techvg.vks.membership.mapper.NotificationDetailsMapper;
import com.techvg.vks.membership.model.NotificationDetailsDto;
import com.techvg.vks.membership.model.NotificationDetailsPageList;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.membership.repository.NotificationDetailsRepository;
import com.techvg.vks.society.domain.Notification;
import com.techvg.vks.society.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationDetailsServiceImpl implements NotificationDetailsService {
	
	private final  NotificationDetailsRepository notificationDetailsRepository; 
	private final  NotificationDetailsMapper notificationDetailsMapper;
	private final  MemberRepository memberRepository;
	private final  NotificationRepository notificationRepository;
	
	@Override
	public NotificationDetailsDto addNotificationDetails(NotificationDetailsDto notificationDetailsDto) {
    
		Member member = memberRepository.findById(notificationDetailsDto.getMemberId()).orElseThrow(() -> new NotFoundException(
				"No member found for id : " + notificationDetailsDto.getMemberId()));
		Notification notification = notificationRepository.findById(notificationDetailsDto.getNotificationId()).orElseThrow(() -> new NotFoundException(
				"No notification found for id : " + notificationDetailsDto.getNotificationId()));
		NotificationDetails notificationDetails=notificationDetailsMapper.notificationDetailsDtoToNotificationDetails(notificationDetailsDto);
		notificationDetails.setMember(member);
		notificationDetails.setNotification(notification);
		notificationDetails=notificationDetailsRepository.save(notificationDetails);
		return notificationDetailsMapper.notificationDetailsToNotificationDetailsDto(notificationDetails);
	}

	@Override
	public NotificationDetailsDto updateNotificationDetails(Long id, NotificationDetailsDto notificationDetailsDto,Authentication authentication) {
		
		NotificationDetails notificationDetails=notificationDetailsRepository.findById(id).orElseThrow(NotFoundException::new);
		Member member = memberRepository.findById(notificationDetailsDto.getMemberId()).orElseThrow(() -> new NotFoundException(
				"No member found for id : " + notificationDetailsDto.getMemberId()));
		Notification notification = notificationRepository.findById(notificationDetailsDto.getNotificationId()).orElseThrow(() -> new NotFoundException(
				"No notification found for id : " + notificationDetailsDto.getNotificationId()));
	
		notificationDetailsDto.setId(notificationDetails.getId());
		NotificationDetails notificationDetails1=notificationDetailsMapper.notificationDetailsDtoToNotificationDetails(notificationDetailsDto);
		notificationDetails1.setMember(member);
		notificationDetails1.setNotification(notification);
		notificationDetails1 = notificationDetailsRepository.save(notificationDetails1);
		return notificationDetailsMapper.notificationDetailsToNotificationDetailsDto(notificationDetails1) ;

	}

	@Override
	public NotificationDetailsDto deleteNotificationDetailsById(Long id) {
		NotificationDetails notificationDetails=notificationDetailsRepository.findById(id).orElseThrow(() -> new NotFoundException(
				"No notificationDetails found for id : " + id));
		if (notificationDetails != null) {
			notificationDetails.setIsDeleted(true);
			notificationDetailsRepository.save(notificationDetails);
		}
	return notificationDetailsMapper.notificationDetailsToNotificationDetailsDto(notificationDetails);
	
	}

	@Override
	public NotificationDetailsPageList listAllNotificationDetails(Pageable pageable) {
		log.debug("Request to get NotificationDetails : {}");

		Page<NotificationDetails> notificationDetailsPage;
		notificationDetailsPage = notificationDetailsRepository.findByisDeleted(false,pageable);

		return new NotificationDetailsPageList(notificationDetailsPage
										.getContent()
										.stream()
										.map(notificationDetailsMapper::notificationDetailsToNotificationDetailsDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(notificationDetailsPage.getPageable().getPageNumber(),
													notificationDetailsPage.getPageable().getPageSize()),
											notificationDetailsPage.getTotalElements());

	}

	@Override
	public NotificationDetailsDto getNotificationDetailsById(Long id) {
		NotificationDetails notificationDetails=notificationDetailsRepository.findById(id).orElseThrow(() -> new NotFoundException(
				"No notificationDetails found for id : " + id));		
		return notificationDetailsMapper.notificationDetailsToNotificationDetailsDto(notificationDetails);
	}

	

}
