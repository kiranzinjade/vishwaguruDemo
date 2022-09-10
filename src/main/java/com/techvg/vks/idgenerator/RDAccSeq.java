package com.techvg.vks.idgenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "seq_rd_acc")
public class RDAccSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rd_acc_seq")
    private Long rdAccNo;
}
