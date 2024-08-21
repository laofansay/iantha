package com.laofan.iantha.service;

import com.laofan.iantha.domain.BabySpec;
import com.laofan.iantha.repository.BabySpecRepository;
import com.laofan.iantha.service.dto.BabySpecDTO;
import com.laofan.iantha.service.mapper.BabySpecMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.laofan.iantha.domain.BabySpec}.
 */
@Service
@Transactional
public class BabySpecService {

    private static final Logger log = LoggerFactory.getLogger(BabySpecService.class);

    private final BabySpecRepository babySpecRepository;

    private final BabySpecMapper babySpecMapper;

    public BabySpecService(BabySpecRepository babySpecRepository, BabySpecMapper babySpecMapper) {
        this.babySpecRepository = babySpecRepository;
        this.babySpecMapper = babySpecMapper;
    }

    /**
     * Save a babySpec.
     *
     * @param babySpecDTO the entity to save.
     * @return the persisted entity.
     */
    public BabySpecDTO save(BabySpecDTO babySpecDTO) {
        log.debug("Request to save BabySpec : {}", babySpecDTO);
        BabySpec babySpec = babySpecMapper.toEntity(babySpecDTO);
        babySpec = babySpecRepository.save(babySpec);
        return babySpecMapper.toDto(babySpec);
    }

    /**
     * Update a babySpec.
     *
     * @param babySpecDTO the entity to save.
     * @return the persisted entity.
     */
    public BabySpecDTO update(BabySpecDTO babySpecDTO) {
        log.debug("Request to update BabySpec : {}", babySpecDTO);
        BabySpec babySpec = babySpecMapper.toEntity(babySpecDTO);
        babySpec = babySpecRepository.save(babySpec);
        return babySpecMapper.toDto(babySpec);
    }

    /**
     * Partially update a babySpec.
     *
     * @param babySpecDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BabySpecDTO> partialUpdate(BabySpecDTO babySpecDTO) {
        log.debug("Request to partially update BabySpec : {}", babySpecDTO);

        return babySpecRepository
            .findById(babySpecDTO.getId())
            .map(existingBabySpec -> {
                babySpecMapper.partialUpdate(existingBabySpec, babySpecDTO);

                return existingBabySpec;
            })
            .map(babySpecRepository::save)
            .map(babySpecMapper::toDto);
    }

    /**
     * Get all the babySpecs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BabySpecDTO> findAll() {
        log.debug("Request to get all BabySpecs");
        return babySpecRepository.findAll().stream().map(babySpecMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one babySpec by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BabySpecDTO> findOne(Long id) {
        log.debug("Request to get BabySpec : {}", id);
        return babySpecRepository.findById(id).map(babySpecMapper::toDto);
    }

    /**
     * Delete the babySpec by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BabySpec : {}", id);
        babySpecRepository.deleteById(id);
    }
}
