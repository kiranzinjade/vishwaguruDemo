package com.techvg.vks.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techvg.vks.society.domain.MeasuringUnit;
import com.techvg.vks.trading.domain.VendorRegister;

import java.util.List;
import java.util.Optional;

public interface VendorRegisterRepository extends JpaRepository<VendorRegister,  Long>{

    Optional<VendorRegister> findByGstNumber(String gstNumber);
    
    List<VendorRegister> findByisDeleted( boolean b);
}
