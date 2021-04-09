package com.chirag.licensedemo.config;

import java.io.File;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

import javax.security.auth.x500.X500Principal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chirag.licensedemo.config.dto.LicenseCreatorParam;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

public class LicenseCreator {
	private static Logger logger = LogManager.getLogger(LicenseCreator.class);
    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER = new X500Principal("CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN");
    private LicenseCreatorParam param;

    public LicenseCreator(LicenseCreatorParam param) {
        this.param = param;
    }

    /**
     * Generate License certificate
     * @author zifangsky
     * @date 2018/4/20 10:58
     * @since 1.0.0
     * @return boolean
     */
    public boolean generateLicense(){
        try {
            LicenseManager licenseManager = new CustomLicenseManager(initLicenseParam());
            LicenseContent licenseContent = initLicenseContent();

            licenseManager.store(licenseContent,new File(param.getLicensePath()));

            return true;
        }catch (Exception e){
            logger.error(MessageFormat.format("Certificate generation failed:{0}",param),e);
            return false;
        }
    }

    /**
     * Initialize certificate generation parameters
     * @author zifangsky
     * @date 2018/4/20 10:56
     * @since 1.0.0
     * @return de.schlichtherle.license.LicenseParam
     */
    private LicenseParam initLicenseParam(){
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);

        //Set the secret key to encrypt the certificate content
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(LicenseCreator.class
                ,param.getPrivateKeysStorePath()
                ,param.getPrivateAlias()
                ,param.getStorePass()
                ,param.getKeyPass());

        LicenseParam licenseParam = new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,privateStoreParam
                ,cipherParam);

        return licenseParam;
    }

    /**
     * Set certificate generation body information
     * @author zifangsky
     * @date 2018/4/20 10:57
     * @since 1.0.0
     * @return de.schlichtherle.license.LicenseContent
     */
    private LicenseContent initLicenseContent(){
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        licenseContent.setSubject(param.getSubject());
        licenseContent.setIssued(param.getIssuedTime());
        licenseContent.setNotBefore(param.getIssuedTime());
        licenseContent.setNotAfter(param.getExpiryTime());
        licenseContent.setConsumerType(param.getConsumerType());
        licenseContent.setConsumerAmount(param.getConsumerAmount());
        licenseContent.setInfo(param.getDescription());

        //Extended verification server hardware information
        licenseContent.setExtra(param.getLicenseCheckModel());

        return licenseContent;
    }
}
