package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.dto.PolicyDTO;
import com.grupo01.digitalbooking.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository repository;

    @Transactional(readOnly = true)
    public List<PolicyDTO> getAllPolicies(){
        return repository.findAll()
                .stream()
                .map(PolicyDTO::new)
                .collect(Collectors.toList());
    }

}
