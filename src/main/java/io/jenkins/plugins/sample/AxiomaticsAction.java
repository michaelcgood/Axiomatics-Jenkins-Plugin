package io.jenkins.plugins.sample;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.axiomatics.asm.admin.client.AsmAccessDenied_Exception;
import com.axiomatics.asm.admin.client.AsmWebServiceFault_Exception;

import hudson.model.Run;
import jenkins.model.RunAction2;


public class AxiomaticsAction implements RunAction2 {
	
	private transient Run run;
	
    @Override
    public void onAttached(Run<?, ?> run) {
        this.run = run; 
    }

    @Override
    public void onLoad(Run<?, ?> run) {
        this.run = run; 
    }

    public Run getRun() { 
        return run;
    }
    
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AxiomaticsAction(String name, String asmURL, String wsdlURL, String asmUser, String asmPassword, String trustStore,
			String trustStoreType, String trustStorePassword, String domainName, String projectName) {
		this.name = name;
		ConvertALFA convertALFA = new ConvertALFA();
		File mainPolicy = new File("/Users/mikegood/Documents/ALFA-Jenkins-Repo/Tutorial/src-gen/tutorial.main.xml");
		File policyFolder = new File("/Users/mikegood/Documents/ALFA-Jenkins-Repo/Tutorial/src-gen");
		String destPackage = "/Users/mikegood/Documents/ALFA-Jenkins-Repo/policy.zip";
		File policyPackage = convertALFA.doThePackaging(mainPolicy, policyFolder, destPackage);
		UploadPolicy uploadPolicy = new UploadPolicy();
		try {
			uploadPolicy.setParameters(policyPackage.getAbsolutePath(), asmURL, wsdlURL, asmUser, asmPassword, trustStore,
					trustStoreType, trustStorePassword, domainName, projectName);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (AsmAccessDenied_Exception e) {
			e.printStackTrace();
		} catch (AsmWebServiceFault_Exception e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @Override
    public String getIconFileName() {
    	return "document.png";
    }

    @Override
    public String getDisplayName() {
    	return "Axiomatics";
    }

    @Override
    public String getUrlName() {
    	return "axiomatics";
    }

}
