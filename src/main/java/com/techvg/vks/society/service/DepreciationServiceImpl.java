package com.techvg.vks.society.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.config.TradingConstants;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.AssetsRegistration;
import com.techvg.vks.society.domain.Depreciation;
import com.techvg.vks.society.mapper.DepreciationMapper;
import com.techvg.vks.society.model.DepreciationDto;
import com.techvg.vks.society.repository.AssetsRegistrationRepository;
import com.techvg.vks.society.repository.DepreciationRepository;
import com.techvg.vks.trading.domain.PurchaseRegister;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepreciationServiceImpl implements DepreciationService {
	
	private final AssetsRegistrationRepository assetsRegistrationRepository;
	private final DepreciationRepository depreciationRepository;
	private final DepreciationMapper depreciationMapper;
	@Override
	public DepreciationDto addDepreciaton(Long assetId) {
		
		List<AssetsRegistration> assets=assetsRegistrationRepository.findByAssetId(assetId);
		
	    assets.forEach(action->{
	    	
	    	AssetsRegistration assetReg=assetsRegistrationRepository.findById(action.getId()).orElseThrow(NotFoundException::new);
	    	
	    	Depreciation depreciation = new Depreciation();
	    	depreciation.setAssetsReg(assetReg);
	    	
	    	Integer year=action.getDate().getYear()+1900;
			String tranType=action.getTransactionType();
 	    	
    		double deprate=action.getAssets().getDepreciation();
    		double depreciation1=(action.getQuantity() * action.getCost()) * deprate/100;
    		
	    	depreciation.setDepreciation(depreciation1);
	    	depreciation.setYear(year);
	    	depreciation.setBookValue(assetReg.getCost() * assetReg.getQuantity());
	    	depreciation.setWdvSoldAsset(0.0);
	    	depreciation.setWdv(0.0);
	    		
	    			if(tranType.equals(TradingConstants.SALES_TRANS)) 
	    			{
	    				int soldyear= action.getDate().getYear()+1900; 
	    				int quantity=assetReg.getQuantity();
	    				double cost=assetReg.getCost();
	    				depreciation.setBookValue(cost-depreciation1);
	    				depreciation.setWdv(cost-depreciation1);
	    				depreciation.setWdvSoldAsset(cost-depreciation1);
	    				depreciation.setYear(soldyear);

	    			}
		    depreciationRepository.save(depreciation);

    	});
		 return null;
	}
	
}
