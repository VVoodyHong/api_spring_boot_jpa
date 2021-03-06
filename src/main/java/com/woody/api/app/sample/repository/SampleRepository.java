package com.woody.api.app.sample.repository;

import com.woody.api.app.sample.entity.Sample;
import org.springframework.data.repository.CrudRepository;

public interface SampleRepository extends CrudRepository<Sample, Long>, SampleRepositoryCustom {
    Sample findSampleByIdx(Long SampleIdx);
}
