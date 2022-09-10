package com.techvg.vks.share.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.share.model.ShareHistoryPageList;
import com.techvg.vks.share.service.ShareHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/share/history")
@CrossOrigin(origins ="*")
@RequiredArgsConstructor
public class ShareHistoryController {
private final ShareHistoryService shareHistoryService;
	
@GetMapping
public ShareHistoryPageList getAllShareHistory(Pageable page)
{ 
	
return shareHistoryService.getShareHistory(page);
}
	
}
