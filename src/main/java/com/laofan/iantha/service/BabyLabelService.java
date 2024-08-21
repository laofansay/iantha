package com.laofan.iantha.service;

import com.laofan.iantha.domain.BabyLabel;
import com.laofan.iantha.repository.BabyLabelRepository;
import com.laofan.iantha.service.dto.BabyLabelDTO;
import com.laofan.iantha.service.mapper.BabyLabelMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.laofan.iantha.domain.BabyLabel}.
 */
@Service
@Transactional
public class BabyLabelService {

    private static final Logger log = LoggerFactory.getLogger(BabyLabelService.class);

    private final BabyLabelRepository babyLabelRepository;

    private final BabyLabelMapper babyLabelMapper;

    public BabyLabelService(BabyLabelRepository babyLabelRepository, BabyLabelMapper babyLabelMapper) {
        this.babyLabelRepository = babyLabelRepository;
        this.babyLabelMapper = babyLabelMapper;
    }

    /**
     * Save a babyLabel.
     *
     * @param babyLabelDTO the entity to save.
     * @return the persisted entity.
     */
    public BabyLabelDTO save(BabyLabelDTO babyLabelDTO) {
        log.debug("Request to save BabyLabel : {}", babyLabelDTO);
        BabyLabel babyLabel = babyLabelMapper.toEntity(babyLabelDTO);
        babyLabel = babyLabelRepository.save(babyLabel);
        return babyLabelMapper.toDto(babyLabel);
    }

    /**
     * Update a babyLabel.
     *
     * @param babyLabelDTO the entity to save.
     * @return the persisted entity.
     */
    public BabyLabelDTO update(BabyLabelDTO babyLabelDTO) {
        log.debug("Request to update BabyLabel : {}", babyLabelDTO);
        BabyLabel babyLabel = babyLabelMapper.toEntity(babyLabelDTO);
        babyLabel = babyLabelRepository.save(babyLabel);
        return babyLabelMapper.toDto(babyLabel);
    }

    /**
     * Partially update a babyLabel.
     *
     * @param babyLabelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BabyLabelDTO> partialUpdate(BabyLabelDTO babyLabelDTO) {
        log.debug("Request to partially update BabyLabel : {}", babyLabelDTO);

        return babyLabelRepository
            .findById(babyLabelDTO.getId())
            .map(existingBabyLabel -> {
                babyLabelMapper.partialUpdate(existingBabyLabel, babyLabelDTO);

                return existingBabyLabel;
            })
            .map(babyLabelRepository::save)
            .map(babyLabelMapper::toDto);
    }

    /**
     * Get all the babyLabels.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BabyLabelDTO> findAll() {
        log.debug("Request to get all BabyLabels");
        return babyLabelRepository.findAll().stream().map(babyLabelMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one babyLabel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BabyLabelDTO> findOne(Long id) {
        log.debug("Request to get BabyLabel : {}", id);
        return babyLabelRepository.findById(id).map(babyLabelMapper::toDto);
    }

    /**
     * Delete the babyLabel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BabyLabel : {}", id);
        babyLabelRepository.deleteById(id);
    }
}
