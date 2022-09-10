package com.techvg.vks.idgenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "seq_fd_acc")
public class FDAccSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fd_acc_seq")
    private Long fdAccNo;
}
