package com.grupo01.digitalbooking.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tb_policy_description")
public class PolicyDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String icon;
    private String description;
    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

}
