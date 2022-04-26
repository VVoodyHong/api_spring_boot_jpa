package com.woody.api.app.sample.entity;

import com.woody.api.app.sample.dto.SampleDTO;
import com.woody.api.common.model.DateAudit;
import lombok.Getter;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "sample")
public class Sample extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String sampleText;
    private Integer sampleInt;

    public SampleDTO.Detail toSampleDetailDTO() {
        return new ModelMapper().map(this, SampleDTO.Detail.class);
    }
}
