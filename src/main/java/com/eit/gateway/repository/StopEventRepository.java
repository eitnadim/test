package com.eit.gateway.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eit.gateway.entity.StopEvents;

public interface StopEventRepository extends JpaRepository<StopEvents,Long > {

	List<StopEvents> findByVinAndTripId(String vin,Long tripId);

}
