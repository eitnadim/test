package com.eit.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eit.gateway.entity.LoggingEvent;

public interface LoggingEventRepository extends JpaRepository<LoggingEvent, Long> {

}
