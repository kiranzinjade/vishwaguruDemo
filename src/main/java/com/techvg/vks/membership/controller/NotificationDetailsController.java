package com.techvg.vks.membership.controller;

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

import com.techvg.vks.membership.model.NotificationDetailsDto;
import com.techvg.vks.membership.model.NotificationDetailsPageList;
import com.techvg.vks.membership.service.NotificationDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/notificationdetails")
public class NotificationDetailsController {
	
	private final NotificationDetailsService notificationDetailsService;
	
	 @PostMapping
	  public ResponseEntity<NotificationDetailsDto>addNotificationDetails(@Validated @RequestBody NotificationDetailsDto notificationDetailsDto) {
	  log.debug("REST request to save NotificationDetails : {}", notificationDetailsDto);
	  return new ResponseEntity<NotificationDetailsDto>(notificationDetailsService.addNotificationDetails(notificationDetailsDto), HttpStatus.CREATED);
	  
	  }
	 @PutMapping(path = { "/{id}" })
		public ResponseEntity<NotificationDetailsDto> updateNotificationDetails(@PathVariable("id") Long id,@Validated @RequestBody NotificationDetailsDto notificationDetailsDto, Authentication authentication) {
		 log.debug("REST request to update NotificationDetails : {}",notificationDetailsDto);
		return new ResponseEntity<NotificationDetailsDto>(notificationDetailsService.updateNotificationDetails(id,notificationDetailsDto, authentication), HttpStatus.OK);
	}
	  
	  @DeleteMapping(path = { "/{id}" }) // deleting data
		public ResponseEntity<NotificationDetailsDto> deleteNotificationDetailsById(@PathVariable("id") Long id) {
			log.debug("REST request to delete NotificationDetails : {}", id);
			return new ResponseEntity<NotificationDetailsDto>(notificationDetailsService.deleteNotificationDetailsById(id), HttpStatus.OK);
	 }
	  
     @GetMapping
		public ResponseEntity<NotificationDetailsPageList> listAllNotificationDetails(Pageable pageable) {
			log.debug("REST request to get a all records of NotificationDetails");
			NotificationDetailsPageList NotificationDetailsList = notificationDetailsService.listAllNotificationDetails(pageable);
			return new ResponseEntity<>(NotificationDetailsList, HttpStatus.OK);
		}
	  
	  @GetMapping({ "/{id}" })
		public ResponseEntity<NotificationDetailsDto> getNotificationDetailsById(@PathVariable("id") Long id) {
			log.debug("REST request to get NotificationDetails : {}", id);
			return new ResponseEntity<NotificationDetailsDto>(notificationDetailsService.getNotificationDetailsById(id), HttpStatus.OK);
		}


}
