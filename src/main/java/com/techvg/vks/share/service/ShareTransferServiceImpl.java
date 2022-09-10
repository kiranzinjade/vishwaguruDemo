package com.techvg.vks.share.service;

import com.techvg.vks.config.ShareConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.domain.Nominee;
import com.techvg.vks.membership.mapper.NomineeMapper;
import com.techvg.vks.membership.model.NomineeDto;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.membership.repository.NomineeRepository;
import com.techvg.vks.share.domain.Shares;
import com.techvg.vks.share.repository.SharesAllocationRepository;
import com.techvg.vks.share.repository.SharesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ShareTransferServiceImpl implements ShareTransferService {

    private final SharesRepository shareMasterRepository;
    private final NomineeRepository nomineeRepo;
    private final MemberRepository memberRepo;
    private final SharesAllocationRepository shareAllocRepo;
    private final NomineeMapper nomineeMapper;
    private Boolean isgenuine;
    private Member member;
    private Nominee nomineeobj;
    private boolean isNomineeMember;
    private Member membernominee;
    private int noOfShares;
    private double totalAmount;
    private double shareprice;

    @Override
    public Boolean isMemberHasNominee(Long memberid, Long nomineeid) {
        isgenuine = false;
        member = null;
        Optional<Member> memberopt = memberRepo.findById(memberid);

        memberopt.ifPresent(action -> {

            member = action;
        });

        List<NomineeDto> nomlist = nomineeMapper.domainToDtoList(nomineeRepo.findByMember(member));
        nomlist.forEach(item -> {
            if (item.getId() == Integer.parseInt(nomineeid.toString())) {
                nomineeobj = nomineeMapper.nomineeDtoToNominee(item);
                isgenuine = true;
            }
        });
        return isgenuine;
    }

    @Override
    public Boolean isNomineeMember(Long nomineeid) {
        isNomineeMember = false;
        membernominee = null;
        Optional<Member> membernom = this.memberRepo.findByAadharCard(nomineeobj.getAadharCard());
        membernom.ifPresent(action -> {
            membernominee = action;
            isNomineeMember = true;
        });
        return isNomineeMember;
    }

    @Override
    public ResponseEntity<?> shareTransfer(Long memberid, Long nomineeid) {

        this.shareprice = 0;
        this.noOfShares = 0;
        this.totalAmount = 0;


        if (isMemberHasNominee(memberid, nomineeid)) {

            if (isNomineeMember(memberid)) {


                //======================= Share transfer application for nominee happens ========================
                this.shareAllocRepo.findByMember(member).forEach(action -> {

                     noOfShares = noOfShares + (action.getSharesIdTo() - action.getSharesIdFrom());
                     shareprice = action.getShares().getSharePrice();
                     totalAmount = totalAmount + (action.getSharesIdTo() - action.getSharesIdFrom()) * shareprice;
                     
                });

                Shares shareApplication = new Shares();
                shareApplication.setApplicationDate(new java.util.Date());
                shareApplication.setApplicationType(ShareConstants.APPS_TYPE_TRANSFER);
                shareApplication.setMember(membernominee);
                shareApplication.setNumberOfShares(noOfShares);
                shareApplication.setShareAmount(totalAmount);
                shareApplication.setSharePrice(shareprice);
                shareApplication.setFromMember(memberRepo.findById(memberid).orElseThrow(NotFoundException::new));
                shareApplication.setShareApplicationStatus(ShareConstants.APPLICATION_STATUS_PENDING);
                this.shareMasterRepository.save(shareApplication);

                //======================= Share Allocation happens ========================
                shareAllocRepo.findByMember(member).forEach(action -> {
                    action.setMember(membernominee);
                    action.setParticulars(ShareConstants.ALLOC_PARTICULARS_TRANSFER);
                    action.setSharesAllocationStatus(ShareConstants.ALLOCATION_STATUS_TRANSFER);
                    shareAllocRepo.save(action);
                });

                return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Share Transfer Successfull");

            } else {
            	  throw new NotFoundException("Nominee "+nomineeobj.getFirstName()+" "+nomineeobj.getMiddleName()+" "+nomineeobj.getLastName()+" is not member yet !!!");
            }

        } else {
            throw new NotFoundException("Nominee is not member yet !!!");

        }

    }

}
