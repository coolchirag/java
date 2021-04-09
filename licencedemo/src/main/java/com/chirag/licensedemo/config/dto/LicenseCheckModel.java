package com.chirag.licensedemo.config.dto;

import java.io.Serializable;
import java.util.List;

public class LicenseCheckModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * IP addresses that can be allowed
     */
    private List<String> ipAddress;

    /**
     * Allowable MAC address
     */
    private List<String> macAddress;

    /**
     * Allowable CPU serial number
     */
    private String cpuSerial;

    /**
     * Allowed motherboard serial number
     */
    private String mainBoardSerial;

    

    public List<String> getIpAddress() {
		return ipAddress;
	}



	public void setIpAddress(List<String> ipAddress) {
		this.ipAddress = ipAddress;
	}



	public List<String> getMacAddress() {
		return macAddress;
	}



	public void setMacAddress(List<String> macAddress) {
		this.macAddress = macAddress;
	}



	public String getCpuSerial() {
		return cpuSerial;
	}



	public void setCpuSerial(String cpuSerial) {
		this.cpuSerial = cpuSerial;
	}



	public String getMainBoardSerial() {
		return mainBoardSerial;
	}



	public void setMainBoardSerial(String mainBoardSerial) {
		this.mainBoardSerial = mainBoardSerial;
	}



	@Override
    public String toString() {
        return "LicenseCheckModel{" +
                "ipAddress=" + ipAddress +
                ", macAddress=" + macAddress +
                ", cpuSerial='" + cpuSerial + '\'' +
                ", mainBoardSerial='" + mainBoardSerial + '\'' +
                '}';
    }

}
