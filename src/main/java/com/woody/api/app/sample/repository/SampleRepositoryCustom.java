package com.woody.api.app.sample.repository;

import com.woody.api.app.sample.entity.Sample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SampleRepositoryCustom {
    Page<Sample> findSamples(String keyword, Pageable pageable);
}
