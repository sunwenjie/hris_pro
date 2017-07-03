package com.kan.base.domain.define;

import java.util.ArrayList;
import java.util.List;



public class TableColumnSubVO{


	//����
	private String tableId;
	
	//�ӱ�����
	private String nameZH;
	
	private String nameEN;
	
	private List<ReportColumnVO> reportColumnVOList = new ArrayList<ReportColumnVO>();
	

	private String displayStyle="";
	
	
	//�Ƿ�����������ҳ����ʾ
	private int isMasterTable = 0;

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getNameZH() {
		return nameZH;
	}

	public void setNameZH(String nameZH) {
		this.nameZH = nameZH;
	}

	public String getNameEN() {
		return nameEN;
	}

	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}

	public List<ReportColumnVO> getReportColumnVOList() {
		return reportColumnVOList;
	}

	public void setReportColumnVOList(List<ReportColumnVO> reportColumnVOList) {
		this.reportColumnVOList = reportColumnVOList;
	}

	public int getIsMasterTable() {
		return isMasterTable;
	}

	public void setIsMasterTable(int isMasterTable) {
		this.isMasterTable = isMasterTable;
	}

	public String getDisplayStyle() {
		return displayStyle;
	}

	public void setDisplayStyle(String displayStyle) {
		this.displayStyle = displayStyle;
	}

	
}
