package com.techvg.vks.share.controller;

import com.techvg.vks.share.model.ShareTransferRequest;
import com.techvg.vks.share.service.ShareTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/share/transfer")
@RequiredArgsConstructor
@Slf4j
public class ShareTransferController {

    private final ShareTransferService shareTranseferService;

    @PostMapping
    public ResponseEntity<?> shareTransferRequest(@Validated @RequestBody ShareTransferRequest request) {

        log.info("The Post Details Has been received for member =" + request.getMember() + "and nominee =" + request.getNominee());
        return this.shareTranseferService.shareTransfer(request.getMember(), request.getNominee());
    }

    @PostMapping("/approve")
    public ResponseEntity<?> transferShareApproval() {

        return null;
    }


}
