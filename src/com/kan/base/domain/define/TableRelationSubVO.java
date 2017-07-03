package com.kan.base.domain.define;



public class TableRelationSubVO{

	private String tableRelationId;

	//主表
	private String masterTableId;
	//从表
	private String slaveTableId;
	
	//从表名称
	private String slaveTableNameZH;
	
	private String slaveTableNameEN;
	//关联条件
	private String joinOn;
	
	private String joinType;
   //子表join on 字段
   private String slaveColumn;
   //主表join on 字段
   private String masterColumn;
   
	
	//报表的表关系id 用于判断是新增还是修改；
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
