package com.chirag.licensedemo.config;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

public class LicenseManagerHolder {
	
	private static LicenseManager licenseManager;

	public static LicenseManager getInstance(LicenseParam param) {
		if(param != null) {
			licenseManager = new CustomLicenseManager(param);
		}
		return licenseManager;
	}
}
