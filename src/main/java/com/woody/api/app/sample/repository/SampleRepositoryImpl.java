package com.woody.api.app.sample.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woody.api.app.sample.entity.Sample;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.woody.api.app.sample.entity.QSample.sample;

@Repository
@RequiredArgsConstructor
public class SampleRepositoryImpl implements SampleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Sample> findSamples(String keyword, Pageable pageable) {
        List<Sample> list = jpaQueryFactory
                .selectFrom(sample)
                .where(sample.sampleText.like(keyword))
                .orderBy(sample.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(list, pageable, list.size());
    }
}
