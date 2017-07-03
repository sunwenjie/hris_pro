package com.kan.base.domain.management;

import java.io.Serializable;

public class ContractTemplateClientVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String laborContractTemplateId;
	
	private String clientId;
	
	private String clientName;

	public String getLaborContractTemplateId() {
		return laborContractTemplateId;
	}

	public void setLaborContractTemplateId(String laborContractTemplateId) {
		this.laborContractTemplateId = laborContractTemplateId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}
