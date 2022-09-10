package com.techvg.vks.accounts.repository;

import com.techvg.vks.accounts.domain.DayBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DayBookRepository extends JpaRepository<DayBook, Long> {

    @Query(value=" select c from DayBook c where date(c.transDate) = :transDate and c.isGLCreated=false order by c.lastModified ASC")
    List<DayBook> findAllByTransDateAndGL(@Param("transDate") Date transDate);

    @Query(value=" select c from DayBook c where date(c.transDate) = :transDate")
    List<DayBook> findAllByTransDate(@Param("transDate") Date transDate);

    @Query(value=" select c from DayBook c where date(c.transDate) = :transDate and c.particulars = :particulars and c.transType = :type order by c.id ASC")
    Optional<DayBook> findByParticularsAndTransDateAndTransTypeOrderByIdAsc(@Param("particulars") String particulars, @Param("transDate") Date transDate, @Param("type") String type);

    Optional<DayBook> findByTransTypeOrderByIdAsc(String type);
    
    @Query(value="select c from DayBook c where date(c.transDate)= subdate(:transDate, 1) and c.lastModified = (select max(c.lastModified) from  DayBook c  where date(c.transDate)= subdate(:transDate, 1))")
    DayBook findByLastRecord(@Param("transDate")Date transDate);
   
    @Query(value="select c from DayBook c where date(c.transDate)=:transDate and c.id = (select max(c.id) from  DayBook c  where date(c.transDate)=:transDate)")
    DayBook findByLastRecordOfTheDay(@Param("transDate")Date transDate);

}
