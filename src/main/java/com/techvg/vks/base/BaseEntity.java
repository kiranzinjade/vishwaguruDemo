package com.techvg.vks.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})

@NoArgsConstructor
@Data
public abstract class BaseEntity<U> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected Long id;

    @CreatedDate
    protected LocalDateTime created;

    @CreatedBy
    protected String createdBy;

    @LastModifiedDate
    @Column(name = "last_modified", columnDefinition="DATETIME(6)")
    protected LocalDateTime lastModified;

    @LastModifiedBy
    protected String lastModifiedBy;

    protected Boolean isDeleted=false;

}
