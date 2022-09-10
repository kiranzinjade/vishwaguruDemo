package com.techvg.vks.idgenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "seq_mem_share_acc")
public class MemShareAccSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mem_share_acc_seq")
    private Long memShareAccNo;
}
