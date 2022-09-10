package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.NotificationDetails;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="notification")
public class Notification extends BaseEntity<String>{
	
	
	@Column(name = "notification_name")
	String notificationName;
	
	@Column(name = "notification_type")
	String notificationType;
	
	@Column(name = "notification_message")
	String notificationMessage;
	
	@Column(name = "notification_Period")
	Integer notificationPeriod;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
	private Set<NotificationDetails> notificationDetails;
	
}
