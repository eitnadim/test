package com.eit.gateway.dataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.LoggingEvent;
import com.eit.gateway.repository.LoggingEventRepository;

@Service
public class LoggingEventService {

	@Autowired
	LoggingEventRepository loggingEventRepository;

	public void saveLoggingEvent(LoggingEvent loggingEvent) {

		loggingEventRepository.save(loggingEvent);

	}

}
