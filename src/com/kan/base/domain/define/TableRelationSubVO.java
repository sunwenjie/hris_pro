package com.kan.base.domain.define;



public class TableRelationSubVO{

	private String tableRelationId;

	//����
	private String masterTableId;
	//�ӱ�
	private String slaveTableId;
	
	//�ӱ�����
	private String slaveTableNameZH;
	
	private String slaveTableNameEN;
	//��������
	private String joinOn;
	
	private String joinType;
   //�ӱ�join on �ֶ�
   private String slaveColumn;
   //����join on �ֶ�
   private String masterColumn;
   
	
	//����ı��ϵid �����ж������������޸ģ�
	private String reportRelationId;
	
	
	public String getJoinType()
   {
      return joinType;
   }
   public void setJoinType( String joinType )
   {
      this.joinType = joinType;
   }
   public String getSlaveColumn()
   {
      return slaveColumn;
   }
   public void setSlaveColumn( String slaveColumn )
   {
      this.slaveColumn = slaveColumn;
   }
   public String getMasterColumn()
   {
      return masterColumn;
   }
   public void setMasterColumn( String masterColumn )
   {
      this.masterColumn = masterColumn;
   }
   public String getTableRelationId() {
		return tableRelationId;
	}
	public void setTableRelationId(String tableRelationId) {
		this.tableRelationId = tableRelationId;
	}
	public String getMasterTableId() {
		return masterTableId;
	}
	public void setMasterTableId(String masterTableId) {
		this.masterTableId = masterTableId;
	}
	public String getSlaveTableId() {
		return slaveTableId;
	}
	public void setSlaveTableId(String slaveTableId) {
		this.slaveTableId = slaveTableId;
	}
	public String getSlaveTableNameZH() {
		return slaveTableNameZH;
	}
	public void setSlaveTableNameZH(String slaveTableNameZH) {
		this.slaveTableNameZH = slaveTableNameZH;
	}
	public String getSlaveTableNameEN() {
		return slaveTableNameEN;
	}
	public void setSlaveTableNameEN(String slaveTableNameEN) {
		this.slaveTableNameEN = slaveTableNameEN;
	}
	public String getJoinOn() {
		return joinOn;
	}
	public void setJoinOn(String joinOn) {
		this.joinOn = joinOn;
	}
	public String getReportRelationId() {
		return reportRelationId;
	}
	public void setReportRelationId(String reportRelationId) {
		this.reportRelationId = reportRelationId;
	}
	
	

}
