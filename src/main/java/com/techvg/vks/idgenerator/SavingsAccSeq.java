package com.techvg.vks.idgenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "seq_savings_acc")
public class SavingsAccSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "saving_acc_seq")
    private Long savingsAccNo;
}
