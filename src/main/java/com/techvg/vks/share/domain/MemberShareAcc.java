package com.techvg.vks.share.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member_share_account")
public class MemberShareAcc extends BaseEntity<String> {

    @Column(name="share_acc_number")
    Long shareAccNumber;

    @Column(name = "share_acc_name")
    String shareAccName;

    @Column(name = "parent_acc_head_code")
    String parentAccHeadCode;

    @Column(name = "parent_acc_id")
    Long parentAccId; // Id of ledger account parent

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;
}
