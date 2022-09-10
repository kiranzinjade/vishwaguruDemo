package com.techvg.vks.accounts.controller;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.accounts.model.VouchersPageList;
import com.techvg.vks.accounts.service.VouchersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/vouchers")
public class VouchersController {
    private final VouchersService vouchersService;

    @GetMapping
    public ResponseEntity<VouchersPageList> listAllVouchers(Pageable pageable) {
        VouchersPageList voucherTypePageList = vouchersService.listVouchers(pageable);
        return new ResponseEntity<>(voucherTypePageList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VouchersDto> addVoucher(@Validated @RequestBody VouchersDto vouchersDto, Authentication authentication) {
        return new ResponseEntity<>( vouchersService.addVouchers(vouchersDto, authentication), HttpStatus.CREATED);
    }

    @PutMapping(path = { "/{voucherId}" })
    public ResponseEntity<VouchersDto> updateVoucher(@PathVariable("voucherId") Long voucherId,
                                                            @Validated @RequestBody VouchersDto vouchersDto, Authentication authentication) {
        return new ResponseEntity<>(vouchersService.updateVouchers(voucherId, vouchersDto, authentication), HttpStatus.OK);
    }

    @GetMapping({ "/{voucherId}" })
    public ResponseEntity<VouchersDto> getVoucher(@PathVariable("voucherId") Long voucherId) {
        return new ResponseEntity<>(vouchersService.getVouchersById(voucherId), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{voucherId}" }) // deleting data
    public ResponseEntity<VouchersDto>  deleteVoucherById(@PathVariable("voucherId") Long voucherId) {
        return new ResponseEntity<>(vouchersService.deleteVouchersById(voucherId), HttpStatus.OK);
    }

    @PutMapping(path = { "/authorise/{voucherId}" })
    public ResponseEntity<VouchersDto> authoriseVoucher(@PathVariable("voucherId") Long voucherId) {
        return new ResponseEntity<>(vouchersService.authoriseVoucher(voucherId), HttpStatus.OK);
    }

    @PostMapping(path = { "/authorise" })
    public ResponseEntity<Boolean> authoriseVoucher( @Validated @RequestBody List<Long> voucherList) {
        return new ResponseEntity<>(vouchersService.authoriseVoucher(voucherList), HttpStatus.OK);
    }
}
