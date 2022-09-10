package com.techvg.vks.accounts.controller;

import com.techvg.vks.accounts.model.VoucherTransDto;
import com.techvg.vks.accounts.model.VoucherTransPageList;
import com.techvg.vks.accounts.service.VoucherTransService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/vouchertrans")
public class VoucherTransController {

    private final VoucherTransService voucherTransService;

    @GetMapping
    public ResponseEntity<VoucherTransPageList> listAllVoucherTrans(Pageable pageable) {
        return new ResponseEntity<>(voucherTransService.listVoucherTrans(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VoucherTransDto> addVoucherTrans(@Validated @RequestBody VoucherTransDto voucherTransDto) {
        return new ResponseEntity<>( voucherTransService.addVoucherTrans(voucherTransDto), HttpStatus.CREATED);
    }

    @PutMapping(path = { "/{voucherTransId}" })
    public ResponseEntity<VoucherTransDto> updateVoucherTrans(@PathVariable("voucherTransId") Long voucherTransId,
                                                                 @Validated @RequestBody VoucherTransDto voucherTransDto, Authentication authentication) {
        return new ResponseEntity<>(voucherTransService.updateVoucherTrans(voucherTransId, voucherTransDto, authentication), HttpStatus.OK);
    }

    @GetMapping({ "/{voucherTransId}" })
    public ResponseEntity<VoucherTransDto> getVoucherTrans(@PathVariable("voucherTransId") Long voucherTransId) {
        return new ResponseEntity<>(voucherTransService.getVoucherTransById(voucherTransId), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{voucherTransId}" }) // deleting data
    public ResponseEntity<VoucherTransDto>  deleteVoucherTransById(@PathVariable("voucherTransId") Long voucherTransId) {
        return new ResponseEntity<>(voucherTransService.deleteVoucherTransById(voucherTransId), HttpStatus.OK);
    }
}
