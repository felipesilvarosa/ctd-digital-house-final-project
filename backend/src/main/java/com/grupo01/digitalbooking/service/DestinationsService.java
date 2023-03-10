package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.Destination;
import com.grupo01.digitalbooking.dto.DestinationDTO;
import com.grupo01.digitalbooking.repository.DestinationRepository;
import com.grupo01.digitalbooking.service.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DestinationsService {

    private final DestinationRepository destinationRepository;

    public List<DestinationDTO> getDestinations() {
        List<Destination> destinations = destinationRepository.findAll();
        return destinations.stream().map(DestinationDTO::new).collect(Collectors.toList());
    }

    public DestinationDTO createDestination(DestinationDTO destinationDTO) {
        Destination destination = new Destination(destinationDTO);

        destinationRepository.findByCityAndCountry(destinationDTO.getCity(),destinationDTO.getCountry())
                .ifPresent((d)->{
                    throw new ConflictException("Destino " +
                            destinationDTO.getCity() +", "+destinationDTO.getCountry()+
                            " já cadastrado");
                });

        destinationRepository.save(destination);
        return new DestinationDTO(destination);
    }
}
