package com.techvg.vks.society.mapper;

import com.techvg.vks.society.domain.LoanProduct;
import com.techvg.vks.society.model.LoanProductDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel="spring")
public interface LoanProductMapper {

	LoanProduct toLoanProduct(LoanProductDto loanProductDto);
	LoanProductDto toLoanProductDto(LoanProduct loanProduct);
	List<LoanProductDto> domainToDtoList(List<LoanProduct> domainList);

//	@AfterMapping
//	default void updateProduct(LoanProduct loanProduct , @MappingTarget LoanProductDto loanProductDto ) {
//		if(loanProduct.getCropLoanDemand() !=null){
//			loanProductDto.setCropLoanDemandName(loanProduct.getCropLoanDemand().getCrop().getCropName());
//			loanProductDto.setCropLoanDemandId(loanProduct.getCropLoanDemand().getId());
//		}
//	}
}
