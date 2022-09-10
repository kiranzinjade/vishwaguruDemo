package com.techvg.vks.trading.repository;

import com.techvg.vks.trading.domain.Product;
import com.techvg.vks.trading.domain.ProductVendor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductVendorRepository extends JpaRepository<ProductVendor, Long> {

	@Modifying
    @Query(value="delete from ProductVendor pv where pv.product.id=:productId and pv.vendor.id=:vendorId" )
    void removeProductVendor(Long vendorId, Long productId);
	
	List<ProductVendor>findByProductId(Long Id);
	
	Optional<ProductVendor> findByVendorIdAndProductId(Long vendorId, Long productId);
}
