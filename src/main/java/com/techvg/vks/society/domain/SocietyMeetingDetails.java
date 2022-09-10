package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Society_meeting_details")

public class SocietyMeetingDetails extends BaseEntity<String> {

    @Column(name = "tharav_no")
    String tharavNo;

    @Column(name = "subject")
    String subject;

    @Column(name = "tharav_date")
    Date tharavDate;

    @Lob
    @Column(name = "tharav_details_file")
    private byte[] tharavDetailsFile;

    @Column(name = "tharav_file_name")
    String tharavFileName;
    
    @Column(name = "type")
    String type;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public SocietyMeeting societyMeeting;

}
