package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.PolicyDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "tb_policies")
@NoArgsConstructor
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToMany(mappedBy = "policy")
    private List<PolicyDescription> policyDescriptions;

    public Policy(String type,PolicyDTO dto){
        this.type = type;
        this.title = dto.getTitle();
        this.policyDescriptions = dto.getDescriptions()
                .stream()
                .map(PolicyDescription::new)
                .collect(Collectors.toList());
    }

}
