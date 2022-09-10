package com.techvg.vks.share.service;

import com.techvg.vks.config.ShareConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.share.repository.SharesAllocationRepository;
import com.techvg.vks.society.domain.SocietyConfiguration;
import com.techvg.vks.society.mapper.SocietyConfigHistoryMapper;
import com.techvg.vks.society.repository.SocietyConfigHistoryRepository;
import com.techvg.vks.society.repository.SocietyConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShareDividendServiceImpl implements ShareDividendService {
    private final SocietyConfigurationRepository societyRepo;
    private final SocietyConfigHistoryRepository societyHistoryRepo;
    private final SharesAllocationRepository shareallocrepo;
    private final SocietyConfigHistoryMapper societyconfighistotymapper;
    private int totalshares = 0;
    private int totalSharesAmt;

    @Override
    public Double getShareCapital(int year) {

        Double capital = 5000000d;
        return capital;
    }

    @Override
    public Double getTotalShareDividend() {

        SocietyConfiguration societyconfig = societyRepo.findByConfigName(ShareConstants.DIVIDENDTOKEN).orElseThrow(() -> new NotFoundException("Dividend Percentage Not Specified"));
        return societyconfig.getValue();
    }

    @Override
    public ResponseEntity<?> calculateShareDividend() {

        SocietyConfiguration societyconfig = societyRepo.findByConfigName(ShareConstants.DIVIDENDTOKEN).orElseThrow(() -> new NotFoundException("Dividend Percentage Not Specified"));

        Double shareCapital = getShareCapital(societyconfig.getYear() - 1);

        Double dividendpercent = societyconfig.getValue();

        Double totaldividend = shareCapital * (dividendpercent / 100);

        saveShareDividend(totaldividend, societyconfig.getYear());

        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Share calculated for the year " + societyconfig.getYear() + " successfully !!!");
    }

    @Override
    public ResponseEntity approveShareDividend() {

        shareallocrepo.findAll().forEach(action -> {
            totalshares = totalshares + action.getNoOfShares();
        });

        SocietyConfiguration privtotaldiv = societyRepo.findByConfigName(ShareConstants.TOTALDIVIDENDAMT).orElseThrow(() -> new NotFoundException("Total Dividend Not Found"));
        double pershareamt = (privtotaldiv.getValue() / totalshares);
        saveShareSinglePrice(pershareamt, privtotaldiv.getYear());
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Share calculation approved for the year " + privtotaldiv.getYear() + " successfully !!! \n total dividend = " + privtotaldiv.getValue() + " \n Per Share Price = " + pershareamt);

    }


    @Override
    public void saveShareDividend(double dividend, int year) {

        Optional<SocietyConfiguration> privtotaldiv = societyRepo.findByConfigName(ShareConstants.TOTALDIVIDENDAMT);
        if (privtotaldiv.isEmpty()) {
            SocietyConfiguration societycconf = new SocietyConfiguration();
            societycconf.setConfigName(ShareConstants.TOTALDIVIDENDAMT);
            societycconf.setValue(dividend);
            societycconf.setConfigType(ShareConstants.CONFIG_TYPE_SHAREDIVIDEND);
            societycconf.setYear(year);
            societyRepo.save(societycconf);
        } else {
            SocietyConfiguration ccn = privtotaldiv.get();
            societyHistoryRepo.save(societyconfighistotymapper.toHistory(ccn));
            ccn.setValue(dividend);
            ccn.setYear(year);
            societyRepo.save(ccn);
        }
    }

    @Override
    public void saveShareSinglePrice(double price, int year) {

        Optional<SocietyConfiguration> soctyconfig = societyRepo.findByConfigName(ShareConstants.DIVIDEND_AMT_PERSHARE);
        if (soctyconfig.isEmpty()) {
            SocietyConfiguration societycconf = new SocietyConfiguration();
            societycconf.setConfigName(ShareConstants.DIVIDEND_AMT_PERSHARE);
            societycconf.setValue(price);
            societycconf.setConfigType(ShareConstants.CONFIG_TYPE_SHAREDIVIDEND);
            societycconf.setYear(year);
            societyRepo.save(societycconf);

        } else {

            SocietyConfiguration ccn = soctyconfig.get();
            societyHistoryRepo.save(societyconfighistotymapper.toHistory(ccn));
            ccn.setValue(price);
            ccn.setYear(year);
            societyRepo.save(ccn);

        }
    }

    @Override
    public Double getPerSharePrice() {
        return societyRepo.findByConfigName(ShareConstants.SINGLE_SHARE_PRICE).orElseThrow(
                () -> new NotFoundException("Total Dividend Not Found")).getValue();
    }

    @Override
    public Integer getTotalNoOfShares() {
        totalSharesAmt = 0;
        shareallocrepo.findAll().forEach(action -> {
            totalSharesAmt += action.getNoOfShares();
        });
        return totalSharesAmt;
    }
}
