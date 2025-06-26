package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "fuel_consumption", schema = "mvt")
//@Getter 
//@Setter 
public class FuelConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "vin", length = 135)
    private String vin;

    @Column(name = "eventtimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventTimestamp;

    @Column(name = "mode", length = 135)
    private String mode;

    @Column(name = "current_fuel", length = 135)
    private String currentFuel;

    @Column(name = "total_fuel", length = 135)
    private String totalFuel;

    @Column(name = "fuel_consumption", length = 135)
    private String fuelConsumption;

    @Column(name = "current_flowrate", length = 135)
    private String currentFlowRate;

    @Column(name = "current_temp", length = 135)
    private String currentTemp;

    @Column(name = "lasthour_fuel", length = 135)
    private String lastHourFuel;

    @Column(name = "min_temp", length = 135)
    private String minTemp;

    @Column(name = "max_temp", length = 135)
    private String maxTemp;

    @Column(name = "min_flowrate", length = 135)
    private String minFlowRate;

    @Column(name = "max_flowrate", length = 135)
    private String maxFlowRate;

    @Column(name = "oveall_fuel", length = 135)
    private String overallFuel;

    @Column(name = "today_fuel", length = 135)
    private String todayFuel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Date getEventTimestamp() {
		return eventTimestamp;
	}

	public void setEventTimestamp(Date eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCurrentFuel() {
		return currentFuel;
	}

	public void setCurrentFuel(String currentFuel) {
		this.currentFuel = currentFuel;
	}

	public String getTotalFuel() {
		return totalFuel;
	}

	public void setTotalFuel(String totalFuel) {
		this.totalFuel = totalFuel;
	}

	public String getFuelConsumption() {
		return fuelConsumption;
	}

	public void setFuelConsumption(String fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	public String getCurrentFlowRate() {
		return currentFlowRate;
	}

	public void setCurrentFlowRate(String currentFlowRate) {
		this.currentFlowRate = currentFlowRate;
	}

	public String getCurrentTemp() {
		return currentTemp;
	}

	public void setCurrentTemp(String currentTemp) {
		this.currentTemp = currentTemp;
	}

	public String getLastHourFuel() {
		return lastHourFuel;
	}

	public void setLastHourFuel(String lastHourFuel) {
		this.lastHourFuel = lastHourFuel;
	}

	public String getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(String minTemp) {
		this.minTemp = minTemp;
	}

	public String getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(String maxTemp) {
		this.maxTemp = maxTemp;
	}

	public String getMinFlowRate() {
		return minFlowRate;
	}

	public void setMinFlowRate(String minFlowRate) {
		this.minFlowRate = minFlowRate;
	}

	public String getMaxFlowRate() {
		return maxFlowRate;
	}

	public void setMaxFlowRate(String maxFlowRate) {
		this.maxFlowRate = maxFlowRate;
	}

	public String getOverallFuel() {
		return overallFuel;
	}

	public void setOverallFuel(String overallFuel) {
		this.overallFuel = overallFuel;
	}

	public String getTodayFuel() {
		return todayFuel;
	}

	public void setTodayFuel(String todayFuel) {
		this.todayFuel = todayFuel;
	}
    
    public FuelConsumption() {
		// TODO Auto-generated constructor stub
	}

	public FuelConsumption(Long id, String vin, Date eventTimestamp, String mode, String currentFuel, String totalFuel,
			String fuelConsumption, String currentFlowRate, String currentTemp, String lastHourFuel, String minTemp,
			String maxTemp, String minFlowRate, String maxFlowRate, String overallFuel, String todayFuel) {
		super();
		this.id = id;
		this.vin = vin;
		this.eventTimestamp = eventTimestamp;
		this.mode = mode;
		this.currentFuel = currentFuel;
		this.totalFuel = totalFuel;
		this.fuelConsumption = fuelConsumption;
		this.currentFlowRate = currentFlowRate;
		this.currentTemp = currentTemp;
		this.lastHourFuel = lastHourFuel;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		this.minFlowRate = minFlowRate;
		this.maxFlowRate = maxFlowRate;
		this.overallFuel = overallFuel;
		this.todayFuel = todayFuel;
	}	
    
    
    
}
