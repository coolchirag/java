package com.chirag.licensedemo.config.dto;

public class LicenseVerifyParam {
	/**
     * Certificate subject
     */
    private String subject;

    /**
     * Public key nickname
     */
    private String publicAlias;

    /**
     * Password to access public key library
     */
    private String storePass;

    /**
     * Certificate generation path
     */
    private String licensePath;

    /**
     * Keystore store path
     */
    private String publicKeysStorePath;

    public LicenseVerifyParam() {

    }

    public LicenseVerifyParam(String subject, String publicAlias, String storePass, String licensePath, String publicKeysStorePath) {
        this.subject = subject;
        this.publicAlias = publicAlias;
        this.storePass = storePass;
        this.licensePath = licensePath;
        this.publicKeysStorePath = publicKeysStorePath;
    }

    

    public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPublicAlias() {
		return publicAlias;
	}

	public void setPublicAlias(String publicAlias) {
		this.publicAlias = publicAlias;
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

	public String getPublicKeysStorePath() {
		return publicKeysStorePath;
	}

	public void setPublicKeysStorePath(String publicKeysStorePath) {
		this.publicKeysStorePath = publicKeysStorePath;
	}

	@Override
    public String toString() {
        return "LicenseVerifyParam{" +
                "subject='" + subject + '\'' +
                ", publicAlias='" + publicAlias + '\'' +
                ", storePass='" + storePass + '\'' +
                ", licensePath='" + licensePath + '\'' +
                ", publicKeysStorePath='" + publicKeysStorePath + '\'' +
                '}';
    }
}
