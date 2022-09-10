package com.techvg.vks.share.reports.ShareCapitalLedger;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareCapitalLedgerWrapper {

	private Date allocationDate;
	private Integer sharesIdFrom;
	private Integer sharesIdTo;
	private Integer sharesIdFrom1;
	private Integer sharesIdTo1;
	private Integer shareCertificateNo;
	private Integer shareCertificateNo1;
	private Integer noOfShares;
	private Integer noOfShares1;
	private Integer noOfShares2;
	private Double value;
	private Double value1;
	private Double value2;
	private Long memberId;
	private Long shareId;
	private String name;
	private String sharesAllocationStatus;
	List<Integer> totalShareCertificates;
	private Integer noOfDays;
	private Double product;
	List<String> distinctiveShares;
	private String particulars;
	private String noAndDateOfBoardResolution;
	String certificate;
	String shareCertificates;
}
