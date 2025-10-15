package com.willianac.video_analyzer.repository;

import org.springframework.data.repository.CrudRepository;

import com.willianac.video_analyzer.model.SummaryRequest;

public interface SummaryRequestRepository extends CrudRepository<SummaryRequest, Long> {
    
}
