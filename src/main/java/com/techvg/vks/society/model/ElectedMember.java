package com.techvg.vks.society.model;

import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ElectedMember {

	private final Long memberId;

	private final String designation;
}
