package com.kan.base.domain.define;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class ReportRelationVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 807105249177532932L;

	/**
	 * 
	 */

	private String reportRelationId;
	
	private String reportHeaderId;
	
	private String slaveTableId;
	

    // √Ë ˆ
    private String description;


	@Override
	public String getEncodedId() throws KANException {
		return encodedField(reportRelationId);
	}

	public void reset(final ActionMapping mapping,
			final HttpServletRequest request) {
		super.reset(mapping, request);
	}

	@Override
	public void reset() {
		this.reportHeaderId = "";
		this.slaveTableId = "";
		super.setStatus("0");
	}

	@Override
	public void update(final Object object) {
	
	}

	public String getReportRelationId() {
		return reportRelationId;
	}

	public void setReportRelationId(String reportRelationId) {
		this.reportRelationId = reportRelationId;
	}

	public String getReportHeaderId() {
		return reportHeaderId;
	}

	public void setReportHeaderId(String reportHeaderId) {
		this.reportHeaderId = reportHeaderId;
	}

	public String getSlaveTableId() {
		return slaveTableId;
	}

	public void setSlaveTableId(String slaveTableId) {
		this.slaveTableId = slaveTableId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	


}
