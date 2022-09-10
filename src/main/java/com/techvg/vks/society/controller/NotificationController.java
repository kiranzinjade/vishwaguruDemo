package com.techvg.vks.society.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.techvg.vks.society.model.NotificationDto;
import com.techvg.vks.society.model.NotificationPageList;
import com.techvg.vks.society.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/notification") 
public class NotificationController {

 private final NotificationService notificationService;
 
 @PostMapping
	public ResponseEntity<NotificationDto> addNotification(@Validated  @RequestBody NotificationDto notificationDto,  Authentication authentication) {
		log.debug("REST request to save Notification : {}", notificationDto);
		return new ResponseEntity<>(notificationService.addNotification(notificationDto,authentication) , HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<NotificationPageList> listAllNotification(Pageable pageable) {

		log.debug("REST request to get a all records of Notification");
		NotificationPageList notificationPageList = notificationService.listAllNotification(pageable);
		return new ResponseEntity<>(notificationPageList, HttpStatus.OK);
	}
	
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<NotificationDto> deleteNotificationById(@PathVariable("id") Long id) {
		log.debug("REST request to delete NotificationDto : {}", id);
		return new ResponseEntity<>(notificationService.deleteNotificationById(id), HttpStatus.OK);
	}
	@PutMapping(path = { "/{id}" })
	public ResponseEntity<NotificationDto> updateNotification(@PathVariable("id") Long id, @Validated @RequestBody NotificationDto notificationDto) {
		log.debug("REST request to update Notification : {}", notificationDto);
		return new ResponseEntity<>(notificationService.updateNotificationDto(id,notificationDto), HttpStatus.OK);
	}
	
	@GetMapping({ "/{id}" })
	public ResponseEntity<NotificationDto> getNpaSetting(@PathVariable("id") Long id) {
		log.debug("REST request to get Notification : {}", id);
		return new ResponseEntity<>(notificationService.getNotificationById(id), HttpStatus.OK);
	}
}
