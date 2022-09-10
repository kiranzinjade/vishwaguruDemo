package com.techvg.vks.share.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor

public class ShareTransferRequest {

@NotNull	
@Getter
private final Long member;
@NotNull
@Getter
private final Long nominee;
	
	
}
