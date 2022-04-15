package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.PolicyDescriptionDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tb_policies_descriptions")
@NoArgsConstructor
public class PolicyDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String icon;
    private String description;
    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

    public PolicyDescription(PolicyDescriptionDTO dto){
        this.icon = dto.getIcon();
        this.description = dto.getDescription();
    }

}
