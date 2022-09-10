package com.techvg.vks.accounts.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "journal")
public class Journal extends BaseEntity<String> {

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;
}
