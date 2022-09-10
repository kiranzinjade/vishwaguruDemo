package com.techvg.vks.trading.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.accounts.service.vouchers.PurchaseVoucherService;
import com.techvg.vks.config.TradingConstants;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.trading.domain.Product;
import com.techvg.vks.trading.domain.PurchaseOrder;
import com.techvg.vks.trading.domain.PurchaseRegister;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.domain.SundryCreditor;
import com.techvg.vks.trading.domain.VendorRegister;
import com.techvg.vks.trading.mapper.PurchaseOrderMapper;
import com.techvg.vks.trading.mapper.PurchaseRegisterMapper;
import com.techvg.vks.trading.model.PurchaseOrderDto;
import com.techvg.vks.trading.model.PurchaseOrderPageList;
import com.techvg.vks.trading.model.SundryCreditorDto;
import com.techvg.vks.trading.repository.ProductRepository;
import com.techvg.vks.trading.repository.PurchaseOrderRepository;
import com.techvg.vks.trading.repository.PurchaseRegisterRepository;
import com.techvg.vks.trading.repository.SundryCreditorRepository;
import com.techvg.vks.trading.repository.VendorRegisterRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderMapper mapper;
    private final PurchaseOrderRepository repo;
    private final PurchaseRegisterRepository  purchaseRegisterRepository;
    private final ProductRepository productRepo;
    private final VendorRegisterRepository vendorRepo;
    private final PurchaseRegisterMapper detailsMapper;
    private final StockRegisterService stockService;
    private final SundryCreditorService sundryService;
    private final PurchaseVoucherService purchaseVoucherService;
    private final SundryCreditorRepository sundryCreditorRepo;

    PurchaseOrder purchaseOrder;
    
    
    @Override
    public PurchaseOrderDto addPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
    		 VendorRegister vendor = vendorRepo.findById(purchaseOrderDto.getVendorId())
    	                 .orElseThrow(() -> new NotFoundException("No Vendor found for Id : " +purchaseOrderDto.getVendorId()));
    		
    		 log.debug("REST request to save Purchase Order : {}", purchaseOrderDto);
    		 PurchaseOrder purchaseOrderOptional = repo.findByOrderNo(purchaseOrderDto.getOrderNo());
    			if (purchaseOrderOptional == null) {
         purchaseOrder = mapper.toDomain(purchaseOrderDto);
        purchaseOrder.setVendorRegister(vendor);

        Set<PurchaseRegister> purchaseDetails = new HashSet<>();

        purchaseOrderDto.getPurchaseRegisters().forEach(detailsDto ->{

            Product product = productRepo.findById(detailsDto.getProductId()).orElseThrow(
                    () -> new NotFoundException("No Product Order found for Id : " + detailsDto.getProductId()));
            
            PurchaseRegister details = detailsMapper.toDomain(detailsDto);
            details.setProduct(product);
            purchaseDetails.add(details);

            purchaseOrder.addPurchaseRegister(details);

            //purchaseOrder.addPurchaseRegister(details);
            purchaseRegisterRepository.save(details);
            });
        purchaseOrder.setPurchaseRegisters(purchaseDetails);
        repo.save(purchaseOrder);

        Vouchers purchaseVoucher = purchaseVoucherService.createPurchaseVoucher(purchaseOrder);
        purchaseOrder.setVoucherNo(purchaseVoucher.getVoucherNo());
        repo.updateVoucherNo(purchaseVoucher.getVoucherNo(), purchaseOrder.getId());

        if(purchaseOrder.getBalanceAmount() !=0){
          
        	updateSundryCreditorRegister(purchaseOrder);
        }

        updateStockRegister(purchaseOrder);
        return mapper.toDto(purchaseOrder);
    			}
    			else{
			throw new AlreadyExitsException("Order No already exists : " + purchaseOrderDto.getOrderNo());
		} 
        
    
    }
    private void updateStockRegister(PurchaseOrder purchaseOrder){
    	System.out.println("purchaseOrder-------------------------------------------->"+purchaseOrder.getPurchaseRegisters().toString());
        purchaseOrder.getPurchaseRegisters().forEach(purchaseProd ->{
            StockRegister stockRegister = detailsMapper.toStockRegisterDomain(purchaseProd);
            stockRegister.setTransType(TradingConstants.PURCHASE_TRANS);
            stockRegister.setVoucherId(purchaseOrder.getVoucherNo());
            stockService.addToStockRegister(stockRegister);
        });
    }
    private void updateSundryCreditorRegister(PurchaseOrder purchaseOrder){
    	
    	PurchaseOrderDto purchaseOrderDto = mapper.toDto(purchaseOrder);
    	 VendorRegister vendor = vendorRepo.findById(purchaseOrderDto.getVendorId())
                 .orElseThrow(() -> new NotFoundException("No Vendor found for Id : " +purchaseOrderDto.getVendorId()));
     
            SundryCreditor sundryCreditor = mapper.toSundryCreditorDomain(purchaseOrderDto);
            
              
            sundryCreditor.setParticulars(purchaseOrder.getParticulars());
            sundryCreditor.setDebit(purchaseOrder.getBalanceAmount());
           sundryCreditor.vendor.setId(purchaseOrder.getVendorRegister().getId());
           sundryCreditor.setVendor(vendor);
           SundryCreditorDto sundryCreditorDto = mapper.toDomainToSundryCreditorDto(sundryCreditor);
           sundryCreditorDto.setVendorId(sundryCreditor.getVendor().getId());
           sundryCreditorDto.setOwnerName(sundryCreditor.getVendor().getOwnerName());
           sundryService.addTransactions(sundryCreditorDto);
               
               
        
    }

    @Override
    public PurchaseOrderPageList listPurchaseOrder(Pageable pageable) {

        Page<PurchaseOrder> purchaseOrderPage;
        purchaseOrderPage = repo.findByIsDeleted(pageable,false);

        return new PurchaseOrderPageList(purchaseOrderPage
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(purchaseOrderPage.getPageable().getPageNumber(),
                                purchaseOrderPage.getPageable().getPageSize()),
                purchaseOrderPage.getTotalElements());
    }

    @Override
    public PurchaseOrderDto deletePurchaseOrderById(Long id) {
        PurchaseOrder purchaseOrder = repo.findById(id).orElseThrow(
                () -> new NotFoundException("No Purchase Order found for Id : " +id));
        if (purchaseOrder != null) {
            purchaseOrder.setIsDeleted(true);
            repo.save(purchaseOrder);
        }
        return mapper.toDto(purchaseOrder);
    }

    @Override
    public PurchaseOrderDto getPurchaseOrderById(Long id) {
        PurchaseOrder purchaseOrder = repo.findById(id).orElseThrow(
                () -> new NotFoundException("No Purchase Order found for Id : " +id));
        return mapper.toDto(purchaseOrder);
    }

	@Override
	public PurchaseOrderPageList getPurchaseByVendor(Long vendorId,Pageable pageable) {
		Page<PurchaseOrder> purchaseRegisterPage;
		List<PurchaseOrder> list = repo.findLastRecordsByVendorId(vendorId);
		purchaseRegisterPage =  new PageImpl<>(list,pageable,list.size());
	       
		return new PurchaseOrderPageList(purchaseRegisterPage
										.getContent()
										.stream()
										.map(mapper::toDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(purchaseRegisterPage.getPageable().getPageNumber(),
													purchaseRegisterPage.getPageable().getPageSize()),
											purchaseRegisterPage.getTotalElements());
	}


	@Override
	public PurchaseOrderDto listAllPurchaseOrder(Long orderNo) {
		
			return mapper.toDto(repo.findByOrderNo(orderNo));

		
	}
	@Override
	public List<PurchaseOrderDto> getPurchaseByDesc() {
		List<PurchaseOrder> purchaseOrder = repo.findLastRecords();
		return mapper.toDtoList(purchaseOrder);
	}
}
