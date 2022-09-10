package com.techvg.vks.accounts.controller;

import com.techvg.vks.accounts.model.VoucherTypeDto;
import com.techvg.vks.accounts.model.VoucherTypePageList;
import com.techvg.vks.accounts.service.VoucherTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
@RequestMapping("/api/voucherType")
public class VoucherTypeController {
    private final VoucherTypeService voucherTypeService;

    @GetMapping
    public ResponseEntity<VoucherTypePageList> listAllVoucherTypes(Pageable pageable) {
        VoucherTypePageList voucherTypePageList = voucherTypeService.listVoucherType(pageable);
        return new ResponseEntity<>(voucherTypePageList, HttpStatus.OK);
    }
    @GetMapping( "/list/voucherTypeList")
   public ResponseEntity<List<VoucherTypeDto>> listVoucherTypes() {
        return new ResponseEntity<>(voucherTypeService.listVouchers(), HttpStatus.OK);
    }

    @GetMapping( "/list/genVoucherTypeList")
    public ResponseEntity<List<VoucherTypeDto>> listGeneralVoucherTypes() {
        return new ResponseEntity<>(voucherTypeService.listGeneralVouchers(), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<VoucherTypeDto> addVoucherType(@Validated @RequestBody VoucherTypeDto voucherTypeDto) {
        return new ResponseEntity<>( voucherTypeService.addVoucherType(voucherTypeDto), HttpStatus.CREATED);
    }

    @PutMapping(path = { "/{voucherTypeId}" })
    public ResponseEntity<VoucherTypeDto> updateVoucherType(@PathVariable("voucherTypeId") Long voucherTypeId,
                                                     @Validated @RequestBody VoucherTypeDto voucherTypeDto, Authentication authentication) {
        return new ResponseEntity<>(voucherTypeService.updateVoucherType(voucherTypeId, voucherTypeDto, authentication), HttpStatus.OK);
    }

    @GetMapping({ "/{voucherTypeId}" })
    public ResponseEntity<VoucherTypeDto> getVoucherType(@PathVariable("voucherTypeId") Long voucherTypeId) {
        return new ResponseEntity<>(voucherTypeService.getVoucherTypeById(voucherTypeId), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{voucherTypeId}" }) // deleting data
    public ResponseEntity<VoucherTypeDto>  deleteVoucherTypeById(@PathVariable("voucherTypeId") Long voucherTypeId) {
        return new ResponseEntity<>(voucherTypeService.deleteVoucherTypeById(voucherTypeId), HttpStatus.OK);
    }

}
