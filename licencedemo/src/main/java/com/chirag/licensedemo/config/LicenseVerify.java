package com.chirag.licensedemo.config;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chirag.licensedemo.config.dto.LicenseVerifyParam;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

public class LicenseVerify {

	private static Logger logger = LogManager.getLogger(LicenseVerify.class);

	public synchronized LicenseContent install(LicenseVerifyParam param){
        LicenseContent result = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //1. Installation certificate
        try{
            LicenseManager licenseManager = LicenseManagerHolder.getInstance(initLicenseParam(param));
            licenseManager.uninstall();

            result = licenseManager.install(new File(param.getLicensePath()));
            logger.info(MessageFormat.format("Certificate installation succeeded, certificate validity:{0} - {1}",format.format(result.getNotBefore()),format.format(result.getNotAfter())));
        }catch (Exception e){
            logger.error("Certificate installation failed!",e);
        }

        return result;
    }

    /**
     * Verify License certificate
     * @author zifangsky
     * @date 2018/4/20 16:26
     * @since 1.0.0
     * @return boolean
     */
    public boolean verify(){
        LicenseManager licenseManager = LicenseManagerHolder.getInstance(null);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //2. Verification certificate
        try {
            LicenseContent licenseContent = licenseManager.verify();
//            System.out.println(licenseContent.getSubject());

            logger.info(MessageFormat.format("The certificate has passed the verification, and the validity period of the certificate is:{0} - {1}",format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter())));
            return true;
        }catch (Exception e){
            logger.error("Certificate verification failed!",e);
            return false;
        }
    }

    /**
     * Initialize certificate generation parameters
     * @author zifangsky
     * @date 2018/4/20 10:56
     * @since 1.0.0
     * @param param License Parameters required for verification class
     * @return de.schlichtherle.license.LicenseParam
     */
    private LicenseParam initLicenseParam(LicenseVerifyParam param){
        Preferences preferences = Preferences.userNodeForPackage(LicenseVerify.class);

        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam publicStoreParam = new DefaultKeyStoreParam(LicenseVerify.class
                ,param.getPublicKeysStorePath()
                ,param.getPublicAlias()
                ,param.getStorePass()
                ,null);

        return new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,publicStoreParam
                ,cipherParam);
    }

}
