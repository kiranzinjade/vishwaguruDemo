package com.techvg.vks.trading.repository;

import com.techvg.vks.trading.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<Product> findByNameAndIsDeleted(String name,boolean b);
    List<Product> findByisDeleted( boolean b);
    Page<Product> findByisDeleted(Pageable pageable,boolean deleted);
    
//    @Query(value="select s from Product s where s.productVendors.vendor.id=:vendorId")
//    List<Product>findByVendorId(@Param("vendorId") Long vendorId);
  

    @Query(value="SELECT * FROM product p JOIN product_vendor pv ON p.id=pv.product_id JOIN vendor_register v ON pv.vendor_id=v.id WHERE v.id=:vendorId",nativeQuery = true)
    List<Product>findByVendorId(@Param("vendorId") Long vendorId);

}
