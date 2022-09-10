package com.techvg.vks.share.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "shares_certificate_history")
public class ShareHistory extends BaseEntity<String> {
    @Column(name = "allocation_date")
    Date allocationDate;
    @Column(name = "issued_date")
    Date issuedDate;
    @Column(name = "amount")
    Double shareTotalAmount;
    @Column(name = "printed_date")
    Date printedDate;
    @Column(name = "no_of_shares")
    Integer noOfShares;
    @Column(name = "share_id_from")
    Integer sharesIdFrom;
    @Column(name = "share_id_to")
    Integer sharesIdTo;
    @Column(name = "share_certificate_no")
    Integer shareCertificateNo;
    @Column(name = "member_unique_id")
    private String memeberUniqueId;

}
