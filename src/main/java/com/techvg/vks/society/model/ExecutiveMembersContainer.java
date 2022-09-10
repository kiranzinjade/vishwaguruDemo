package com.techvg.vks.society.model;

import java.util.List;

import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ConstructorBinding
public class ExecutiveMembersContainer {

	@Getter
	private  final java.util.Date electeddate;
	@Getter
    private final List<ElectedMember> electedmemberslist;
	
}
