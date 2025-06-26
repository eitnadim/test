package com.eit.gateway.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eit.gateway.entity.VehicleEvent;

@Repository
public interface VehicleEventRepository extends JpaRepository<VehicleEvent, Long> {

}
