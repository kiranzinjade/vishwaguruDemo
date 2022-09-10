package com.techvg.vks.idgenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "seq_voucher")
public class VoucherSeq {

    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voucher_seq")
    private Long voucherNo;
}
