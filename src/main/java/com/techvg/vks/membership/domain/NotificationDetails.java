package com.techvg.vks.membership.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.society.domain.Notification;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification_details")
public class NotificationDetails extends BaseEntity<String> {

	@Column(name = "notification_date")
	Date notificationDate ;
	
	@Column(name = "notification_status")
	String notificationStatus;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Member member;
	
	@ManyToOne
	@JoinColumn(name = "notification_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Notification notification;

}
