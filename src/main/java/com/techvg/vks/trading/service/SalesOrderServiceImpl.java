package com.techvg.vks.trading.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.accounts.service.vouchers.SalesVoucherService;
import com.techvg.vks.config.TradingConstants;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.trading.domain.Product;
import com.techvg.vks.trading.domain.PurchaseOrder;
import com.techvg.vks.trading.domain.SalesOrder;
import com.techvg.vks.trading.domain.SalesRegister;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.domain.SundryCreditor;
import com.techvg.vks.trading.domain.SundryDebtor;
import com.techvg.vks.trading.domain.VendorRegister;
import com.techvg.vks.trading.mapper.SalesOrderMapper;
import com.techvg.vks.trading.mapper.SalesRegisterMapper;
import com.techvg.vks.trading.model.PurchaseOrderDto;
import com.techvg.vks.trading.model.SalesOrderDto;
import com.techvg.vks.trading.model.SalesOrderPageList;
import com.techvg.vks.trading.model.SundryCreditorDto;
import com.techvg.vks.trading.model.SundryDebtorDto;
import com.techvg.vks.trading.repository.ProductRepository;
import com.techvg.vks.trading.repository.SalesOrderRepository;
import com.techvg.vks.trading.repository.SalesRegisterRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesOrderServiceImpl implements SalesOrderService {

    private final SalesOrderMapper mapper;
    private final SalesOrderRepository repo;

    private final ProductRepository productRepo;
    private final MemberRepository memRepo;

    private final SalesRegisterMapper detailsMapper;
    private final SalesRegisterRepository salesRegisterRepository;

    private final SalesVoucherService salesVoucherService;

    private final StockRegisterService stockService;
    private final SundryDebtorService sundryDebtorService;

    Boolean flag = false;
    
    @Override
    public SalesOrderDto addSalesOrder(SalesOrderDto salesOrderDto) {
    	
    	 Member member = memRepo.findById(salesOrderDto.getMemberId())
                 .orElseThrow(() -> new NotFoundException("No Member found for Id : " +salesOrderDto.getMemberId()));
    
    	 log.debug("REST request to save Purchase Order : {}", salesOrderDto);
		 SalesOrder salesOrderOptional = repo.findByOrderNo(salesOrderDto.getOrderNo());
			if (salesOrderOptional == null) {
       
        SalesOrder salesOrder = mapper.toDomain(salesOrderDto);
        salesOrder.setMember(member);

        Set<SalesRegister> salesDetails = new HashSet<>();

        salesOrderDto.getSalesRegisters().forEach(detailsDto ->{

            Product product = productRepo.findById(detailsDto.getProductId()).orElseThrow(
                    () -> new NotFoundException("No Product Order found for Id : " + detailsDto.getProductId()));

            SalesRegister details = detailsMapper.toDomain(detailsDto);
            details.setProduct(product);
            salesDetails.add(details);
           // salesOrder.addSalesRegister(details);
            details.setSalesOrder(salesOrder);
            salesRegisterRepository.save(details);
        });

        salesOrder.setSalesRegisters(salesDetails);
        repo.save(salesOrder);

        Vouchers purchaseVoucher = salesVoucherService.createSalesVoucher(salesOrder);
        salesOrder.setVoucherNo(purchaseVoucher.getVoucherNo());
        repo.updateVoucherNo(salesOrder.getVoucherNo(), salesOrder.getId());

        if(salesOrder.getBalanceAmount() !=0){
           updateSundryDebtorRegister(salesOrder);
        }

        updateStockRegister(salesOrder);
        return mapper.toDto(salesOrder);
    }else {
    	throw new AlreadyExitsException("Order No already exists : " + salesOrderDto.getOrderNo());
    	
    }
    }

    private void updateStockRegister(SalesOrder salesOrder){
        salesOrder.getSalesRegisters().forEach(salesProd ->{
            StockRegister stockRegister = detailsMapper.toStockRegisterDomain(salesProd);
            stockRegister.setTransType(TradingConstants.SALES_TRANS);
            stockService.addToStockRegister(stockRegister);
        });
    }
 private void updateSundryDebtorRegister(SalesOrder salesOrder){
    	
	 SalesOrderDto salesOrderDto = mapper.toDto(salesOrder);
    	System.out.println("Purchase Order Dto--------------"+salesOrderDto);
    	Member member = memRepo.findById(salesOrderDto.getMemberId())
                 .orElseThrow(() -> new NotFoundException("No Vendor found for Id : " +salesOrderDto.getMemberId()));
     
            SundryDebtor sundryDebtor = mapper.toSundryDebtorDomain(salesOrderDto);
            
              
            sundryDebtor.setParticulars(salesOrder.getParticulars());
            sundryDebtor.setCredit(salesOrder.getBalanceAmount());
            sundryDebtor.member.setId(salesOrder.getMember().getId());
            sundryDebtor.setMember(member);
            SundryDebtorDto sundryDebtorDto = mapper.toDomainToSundryDebtorDto(sundryDebtor);
            sundryDebtorDto.setMemberId(sundryDebtor.getMember().getId());
            sundryDebtorDto.setFullName(sundryDebtor.getMember().getFirstName()+" " +sundryDebtor.getMember().getMiddleName()+ ""+sundryDebtor.getMember().getLastName());
            Authentication authentication = null;
            sundryDebtorService.addSundryDebitor(sundryDebtorDto, authentication);
               
               
        
    }


    @Override
    public SalesOrderPageList listSalesOrder(Pageable pageable) {

        Page<SalesOrder> salesOrderPage = repo.findByIsDeleted(pageable,false);

        return new SalesOrderPageList(salesOrderPage
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(salesOrderPage.getPageable().getPageNumber(),
                                salesOrderPage.getPageable().getPageSize()),
                salesOrderPage.getTotalElements());
    }

    @Override
    public SalesOrderDto deleteSalesOrderById(Long id) {
        SalesOrder salesOrder = repo.findById(id).orElseThrow(
                () -> new NotFoundException("No Sales Order found for Id : " +id));
        if (salesOrder != null) {
            salesOrder.setIsDeleted(true);
            repo.save(salesOrder);
        }
        return mapper.toDto(salesOrder);
    }

    @Override
    public SalesOrderDto getSalesOrderById(Long id) {
        SalesOrder salesOrder = repo.findById(id).orElseThrow(
                () -> new NotFoundException("No Sales Order found for Id : " +id));
        return mapper.toDto(salesOrder);
    }

//	@Override
//	public List<SalesOrderDto> getSalesByMember(Long memberId) {
//		List<SalesOrder> salesOrder = repo.findLastRecordsByMemberId(memberId);
//		return mapper.toDtoList(salesOrder);
//	}

	@Override
	public SalesOrderDto listAllSalesOrder(Long orderNo) {
		return mapper.toDto(repo.findByOrderNo(orderNo));

	}

	@Override
	public List listAllSales() {
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);

		Integer year = cal.get(Calendar.YEAR);
		ArrayList<Integer> salesList=new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			String monthid = String.format("%02d", i);
			String monthdate = year + "-" + monthid;
			
			Integer month = Integer.parseInt(monthid);
		   Integer sales=repo.getSalesCount(month, year);
			salesList.add(sales);
		}
		return salesList;
	}

	@Override
	public List<SalesOrderDto> getSalesByDesc() {
		List<SalesOrder> salesOrder = repo.findLastRecords();
		return mapper.toDtoList(salesOrder);
	}

	@Override
	public SalesOrderPageList getSalesByMember(Long memberId, Pageable pageable) {
		Page<SalesOrder> salesRegisterPage;
		List<SalesOrder> list = repo.findLastRecordsByMemberId(memberId);
		salesRegisterPage =  new PageImpl<>(list,pageable,list.size());
	       
		return new SalesOrderPageList(salesRegisterPage
										.getContent()
										.stream()
										.map(mapper::toDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(salesRegisterPage.getPageable().getPageNumber(),
													salesRegisterPage.getPageable().getPageSize()),
											salesRegisterPage.getTotalElements());
	}
    
    
}
