package com.eit.gateway.device.meitrack;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class MeitrackP88LPacket {
    
    // Original fields
    private String messageId;
    private String imei;
    private String command;
    private byte[] rawData;
    private Date timestamp;
    private GPSInfo gpsInfo;
    
    // Additional fields for Meitrack P88L protocol
    private int eventCode;
    private long gsmSignalStrength;
    private int inputPortStatus;
    private double externalPowerVoltage;
    private long mileage;
    private long runTime;
    private int stepCount;
    private int batteryPercentage;
    
    // System flag components
    private boolean eep2Modified;
    private boolean accOn;
    private boolean antiTheftArmed;
    private boolean vibrating;
    private boolean moving;
    private boolean externalPowerConnected;
    private boolean charging;
    private boolean sleepModeEnabled;
    
    // Additional status fields
    private String deviceStatus;
    private String eventType;
    private List<String> alarmTypes;
    private Map<String, Object> additionalData;

    // Constructors
    public MeitrackP88LPacket() {
        this.timestamp = new Date();
        this.alarmTypes = new ArrayList<>();
        this.additionalData = new HashMap<>();
    }

    // Original getters and setters
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public String getImei() { return imei; }
    public void setImei(String imei) { this.imei = imei; }

    public String getCommand() { return command; }
    public void setCommand(String command) { this.command = command; }

    public byte[] getRawData() { return rawData; }
    public void setRawData(byte[] rawData) { this.rawData = rawData; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public GPSInfo getGpsInfo() { return gpsInfo; }
    public void setGpsInfo(GPSInfo gpsInfo) { this.gpsInfo = gpsInfo; }

    // New getters and setters
    public int getEventCode() { return eventCode; }
    public void setEventCode(int eventCode) { this.eventCode = eventCode; }

    public long getGsmSignalStrength() { return gsmSignalStrength; }
    public void setGsmSignalStrength(long value) { this.gsmSignalStrength = value; }

    public int getInputPortStatus() { return inputPortStatus; }
    public void setInputPortStatus(int inputPortStatus) { this.inputPortStatus = inputPortStatus; }

    public double getExternalPowerVoltage() { return externalPowerVoltage; }
    public void setExternalPowerVoltage(double externalPowerVoltage) { this.externalPowerVoltage = externalPowerVoltage; }

    public long getMileage() { return mileage; }
    public void setMileage(long mileage) { this.mileage = mileage; }

    public long getRunTime() { return runTime; }
    public void setRunTime(long runTime) { this.runTime = runTime; }

    public int getStepCount() { return stepCount; }
    public void setStepCount(int stepCount) { this.stepCount = stepCount; }

    public int getBatteryPercentage() { return batteryPercentage; }
    public void setBatteryPercentage(int batteryPercentage) { this.batteryPercentage = batteryPercentage; }

    public boolean isEep2Modified() { return eep2Modified; }
    public void setEep2Modified(boolean eep2Modified) { this.eep2Modified = eep2Modified; }

    public boolean isAccOn() { return accOn; }
    public void setAccOn(boolean accOn) { this.accOn = accOn; }

    public boolean isAntiTheftArmed() { return antiTheftArmed; }
    public void setAntiTheftArmed(boolean antiTheftArmed) { this.antiTheftArmed = antiTheftArmed; }

    public boolean isVibrating() { return vibrating; }
    public void setVibrating(boolean vibrating) { this.vibrating = vibrating; }

    public boolean isMoving() { return moving; }
    public void setMoving(boolean moving) { this.moving = moving; }

    public boolean isExternalPowerConnected() { return externalPowerConnected; }
    public void setExternalPowerConnected(boolean externalPowerConnected) { this.externalPowerConnected = externalPowerConnected; }

    public boolean isCharging() { return charging; }
    public void setCharging(boolean charging) { this.charging = charging; }

    public boolean isSleepModeEnabled() { return sleepModeEnabled; }
    public void setSleepModeEnabled(boolean sleepModeEnabled) { this.sleepModeEnabled = sleepModeEnabled; }

    public String getDeviceStatus() { return deviceStatus; }
    public void setDeviceStatus(String deviceStatus) { this.deviceStatus = deviceStatus; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public List<String> getAlarmTypes() { return alarmTypes; }
    public void setAlarmTypes(List<String> alarmTypes) { this.alarmTypes = alarmTypes; }

    public Map<String, Object> getAdditionalData() { return additionalData; }
    public void setAdditionalData(Map<String, Object> additionalData) { this.additionalData = additionalData; }

    // Utility methods
    public String getEventDescription() {
        switch (eventCode) {
            case 1: return "SOS Pressed";
            case 17: return "Low Battery";
            case 19: return "Speeding";
            case 20: return "Enter Geo-fence";
            case 21: return "Exit Geo-fence";
            case 24: return "GPS Signal Lost";
            case 25: return "GPS Signal Recovery";
            case 26: return "Enter Sleep";
            case 27: return "Exit Sleep";
            case 29: return "Device Reboot";
            case 31: return "Heartbeat";
            case 32: return "Cornering";
            case 33: return "Track By Distance";
            case 34: return "Reply Current (Passive)";
            case 35: return "Track By Time Interval";
            case 36: return "Tow";
            case 40: return "Power Off";
            case 79: return "Fall";
            case 111: return "Call Record";
            case 127: return "Alarm Clock Info";
            case 152: return "Start Trip";
            case 153: return "End Trip";
            case 154: return "Reset Step";
            case 157: return "Lost";
            case 158: return "Lost Recovery";
            default: return "Unknown Event (" + eventCode + ")";
        }
    }

    public String getGsmSignalQuality() {
        if (gsmSignalStrength >= 20) return "Excellent";
        else if (gsmSignalStrength >= 15) return "Good";
        else if (gsmSignalStrength >= 10) return "Fair";
        else if (gsmSignalStrength >= 5) return "Poor";
        else return "Very Poor";
    }

    public boolean isEmergencyEvent() {
        return eventCode == 1 || eventCode == 79 || eventCode == 157; // SOS, Fall, Lost
    }

    public String getPowerStatus() {
        if (charging) return "Charging (" + batteryPercentage + "%)";
        else if (externalPowerConnected) return "External Power (" + batteryPercentage + "%)";
        else return "Battery (" + batteryPercentage + "%)";
    }

    public boolean isLowBattery() {
        return batteryPercentage < 20;
    }

    public String getDeviceState() {
        StringBuilder state = new StringBuilder();
        
        if (moving) state.append("Moving, ");
        else state.append("Stationary, ");
        
        if (vibrating) state.append("Vibrating, ");
        if (accOn) state.append("ACC On, ");
        if (antiTheftArmed) state.append("Armed, ");
        if (sleepModeEnabled) state.append("Sleep Mode, ");
        
        String result = state.toString();
        return result.isEmpty() ? "Normal" : result.substring(0, result.length() - 2);
    }

    public double getMileageInKm() {
        return mileage / 1000.0; // Convert meters to kilometers
    }

    public double getMileageInMiles() {
        return (mileage / 1000.0) * 0.621371; // Convert to miles
    }

    public String getFormattedRunTime() {
        long hours = runTime / 3600;
        long minutes = (runTime % 3600) / 60;
        long seconds = runTime % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void addAlarmType(String alarmType) {
        if (alarmTypes == null) {
            alarmTypes = new ArrayList<>();
        }
        if (!alarmTypes.contains(alarmType)) {
            alarmTypes.add(alarmType);
        }
    }

    public void addAdditionalData(String key, Object value) {
        if (additionalData == null) {
            additionalData = new HashMap<>();
        }
        additionalData.put(key, value);
    }

    public boolean hasGPSData() {
        return gpsInfo != null && gpsInfo.isValid();
    }

    public boolean isHealthy() {
        return !isLowBattery() && 
               gsmSignalStrength > 5 && 
               (hasGPSData() || gpsInfo != null);
    }

    @Override
    public String toString() {
        return "MeitrackP88LPacket{" +
                "imei='" + imei + '\'' +
                ", command='" + command + '\'' +
                ", event='" + getEventDescription() + '\'' +
                ", gsmSignal=" + gsmSignalStrength + " (" + getGsmSignalQuality() + ")" +
                ", battery=" + batteryPercentage + "%" +
                ", steps=" + stepCount +
                ", mileage=" + String.format("%.1f", getMileageInKm()) + "km" +
                ", runTime=" + getFormattedRunTime() +
                ", deviceState='" + getDeviceState() + '\'' +
                ", powerStatus='" + getPowerStatus() + '\'' +
                ", hasGPS=" + hasGPSData() +
                ", timestamp=" + timestamp +
                '}';
    }

    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Meitrack P88L Packet Details ===\n");
        sb.append("IMEI: ").append(imei).append("\n");
        sb.append("Message ID: ").append(messageId).append("\n");
        sb.append("Command: ").append(command).append("\n");
        sb.append("Event: ").append(getEventDescription()).append(" (").append(eventCode).append(")\n");
        sb.append("Timestamp: ").append(timestamp).append("\n");
        
        sb.append("\n--- Device Status ---\n");
        sb.append("GSM Signal: ").append(gsmSignalStrength).append(" (").append(getGsmSignalQuality()).append(")\n");
        sb.append("Battery: ").append(batteryPercentage).append("%\n");
        sb.append("Power Status: ").append(getPowerStatus()).append("\n");
        sb.append("Device State: ").append(getDeviceState()).append("\n");
        
        sb.append("\n--- Activity Data ---\n");
        sb.append("Steps: ").append(stepCount).append("\n");
        sb.append("Mileage: ").append(String.format("%.1f", getMileageInKm())).append(" km\n");
        sb.append("Run Time: ").append(getFormattedRunTime()).append("\n");
        
        if (hasGPSData()) {
            sb.append("\n--- GPS Data ---\n");
            sb.append(gpsInfo.toString()).append("\n");
        }
        
        if (!alarmTypes.isEmpty()) {
            sb.append("\n--- Alarms ---\n");
            for (String alarm : alarmTypes) {
                sb.append("- ").append(alarm).append("\n");
            }
        }
        
        return sb.toString();
    }
}