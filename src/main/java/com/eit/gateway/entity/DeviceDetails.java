/**
 * 
 */
package com.eit.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "devicedetails", schema = "mvt")
public class DeviceDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "device_type", nullable = false)
	private String deviceType;
	
	@Column(name = "listening_port", nullable = false, unique = true)
	private Integer listeningPort;
	
	@Column(name = "packet_size", nullable = false)
	private Integer packetSize;
	
	@Column(name = "read_timeout_in_seconds", nullable = false)
	private Integer readTimeoutInSeconds;
	
	@Column(name = "validate_first_packet", nullable = false)
	private String validateFirstPacket;
	
	@Column(name = "push_data_to_test_server", nullable = false)
	private String pushDataToTestServer;
	
	@Column(name = "test_server_ip")
	private String testServerIp;
	
	@Column(name = "test_server_port")
	private Integer testServerPort;
	
	public DeviceDetails() {}
	
	public DeviceDetails(Long id, String deviceType, Integer listeningPort, 
			Integer packetSize, Integer readTimeoutInSeconds,String validateFirstPacket,
			String pushDataToTestServer,String testServerIp,Integer testServerPort ) {
		super();
		this.id = id;
		this.deviceType = deviceType;
		this.listeningPort = listeningPort;
		this.packetSize = packetSize;
		this.readTimeoutInSeconds = readTimeoutInSeconds;
		this.validateFirstPacket = validateFirstPacket;
		this.pushDataToTestServer = pushDataToTestServer;
		this.testServerIp = testServerIp;
		this.testServerPort = testServerPort;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}
	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	/**
	 * @return the listeningPort
	 */
	public Integer getListeningPort() {
		return listeningPort;
	}
	/**
	 * @param listeningPort the listeningPort to set
	 */
	public void setListeningPort(Integer listeningPort) {
		this.listeningPort = listeningPort;
	}
	/**
	 * @return the packetSize
	 */
	public Integer getPacketSize() {
		return packetSize;
	}
	/**
	 * @param packetSize the packetSize to set
	 */
	public void setPacketSize(Integer packetSize) {
		this.packetSize = packetSize;
	}

	/**
	 * @return the readTimeoutInSeconds
	 */
	public Integer getReadTimeoutInSeconds() {
		return readTimeoutInSeconds;
	}

	/**
	 * @param readTimeoutInSeconds the readTimeoutInSeconds to set
	 */
	public void setReadTimeoutInSeconds(Integer readTimeoutInSeconds) {
		this.readTimeoutInSeconds = readTimeoutInSeconds;
	}

	/**
	 * @return the testServerIp
	 */
	public String getTestServerIp() {
		return testServerIp;
	}

	/**
	 * @param testServerIp the testServerIp to set
	 */
	public void setTestServerIp(String testServerIp) {
		this.testServerIp = testServerIp;
	}

	/**
	 * @return the testServerPort
	 */
	public Integer getTestServerPort() {
		return testServerPort;
	}

	/**
	 * @param testServerPort the testServerPort to set
	 */
	public void setTestServerPort(Integer testServerPort) {
		this.testServerPort = testServerPort;
	}

	/**
	 * @return the pushDataToTestServer
	 */
	public String getPushDataToTestServer() {
		return pushDataToTestServer;
	}

	/**
	 * @param pushDataToTestServer the pushDataToTestServer to set
	 */
	public void setPushDataToTestServer(String pushDataToTestServer) {
		this.pushDataToTestServer = pushDataToTestServer;
	}

	/**
	 * @return the validateFirstPacket
	 */
	public String getValidateFirstPacket() {
		return validateFirstPacket;
	}

	/**
	 * @param validateFirstPacket the validateFirstPacket to set
	 */
	public void setValidateFirstPacket(String validateFirstPacket) {
		this.validateFirstPacket = validateFirstPacket;
	}

	
}
