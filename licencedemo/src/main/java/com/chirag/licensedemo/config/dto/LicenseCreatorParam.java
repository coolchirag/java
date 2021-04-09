package com.chirag.licensedemo.config.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LicenseCreatorParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Certificate subject
     */
    private String subject;

    /**
     * Key alias
     */
    private String privateAlias;

    /**
     * Key and password (it needs to be kept properly and cannot be known by the user)
     */
    private String keyPass;

    /**
     * Password to access the secret key library
     */
    private String storePass;

    /**
     * Certificate generation path
     */
    private String licensePath;

    /**
     * Keystore store path
     */
    private String privateKeysStorePath;

    /**
     * Certificate effective time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issuedTime = new Date();

    /**
     * Certificate expiration time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiryTime;

    /**
     * customer type
     */
    private String consumerType = "user";

    /**
     * Number of users
     */
    private Integer consumerAmount = 1;

    /**
     * Description information
     */
    private String description = "";

    /**
     * Additional server hardware verification information
     */
    private LicenseCheckModel licenseCheckModel;

    

    public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getPrivateAlias() {
		return privateAlias;
	}



	public void setPrivateAlias(String privateAlias) {
		this.privateAlias = privateAlias;
	}



	public String getKeyPass() {
		return keyPass;
	}



	public void setKeyPass(String keyPass) {
		this.keyPass = keyPass;
	}



	public String getStorePass() {
		return storePass;
	}



	public void setStorePass(String storePass) {
		this.storePass = storePass;
	}



	public String getLicensePath() {
		return licensePath;
	}



	public void setLicensePath(String licensePath) {
		this.licensePath = licensePath;
	}



	public String getPrivateKeysStorePath() {
		return privateKeysStorePath;
	}



	public void setPrivateKeysStorePath(String privateKeysStorePath) {
		this.privateKeysStorePath = privateKeysStorePath;
	}



	public Date getIssuedTime() {
		return issuedTime;
	}



	public void setIssuedTime(Date issuedTime) {
		this.issuedTime = issuedTime;
	}



	public Date getExpiryTime() {
		return expiryTime;
	}



	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}



	public String getConsumerType() {
		return consumerType;
	}



	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}



	public Integer getConsumerAmount() {
		return consumerAmount;
	}



	public void setConsumerAmount(Integer consumerAmount) {
		this.consumerAmount = consumerAmount;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public LicenseCheckModel getLicenseCheckModel() {
		return licenseCheckModel;
	}



	public void setLicenseCheckModel(LicenseCheckModel licenseCheckModel) {
		this.licenseCheckModel = licenseCheckModel;
	}



	@Override
    public String toString() {
        return "LicenseCreatorParam{" +
                "subject='" + subject + '\'' +
                ", privateAlias='" + privateAlias + '\'' +
                ", keyPass='" + keyPass + '\'' +
                ", storePass='" + storePass + '\'' +
                ", licensePath='" + licensePath + '\'' +
                ", privateKeysStorePath='" + privateKeysStorePath + '\'' +
                ", issuedTime=" + issuedTime +
                ", expiryTime=" + expiryTime +
                ", consumerType='" + consumerType + '\'' +
                ", consumerAmount=" + consumerAmount +
                ", description='" + description + '\'' +
                ", licenseCheckModel=" + licenseCheckModel +
                '}';
    }

}
