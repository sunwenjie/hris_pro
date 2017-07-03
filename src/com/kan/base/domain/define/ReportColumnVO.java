package com.kan.base.domain.define;

import com.kan.base.util.KANException;

public class ReportColumnVO {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 2332740723271229739L;

	/**
	 * For DB
	 */

	// 报表从表ID
	private String reportDetailId;

	// 主表ID
	private String reportHeaderId;

	// 字段ID
	private String columnId;

	// 字段名（中文）
	private String nameZH;

	// 字段名（英文）
	private String nameEN;
	
	// 字段名（中文）
	private String orgNameZH;

	// 字段名（英文）
	private String orgNameEN;

	// 字段宽度
	private String columnWidth;

	// 宽度类型
	private String columnWidthType;

	// 顺序
	private String columnIndex;

	// 字体大小
	private String fontSize;

	// 是否转译？
	private String isDecoded;

	// 是否链接？
	private String isLinked;

	// 连接Action
	private String linkedAction;

	// 链接目标方式
	private String linkedTarget;

	// 日期格式化？
	private String datetimeFormat;

	// 保留小数位
	private String accuracy;

	// 截取方式
	private String round;

	// 对齐方式
	private String align;

	// 是否排序
	private String sort;

	// 是否显示
	private String display;

	// 描述
	private String description;

	// 值类型
	private String valueType;
	
	//页面显引
	private String displayStyle="";

	/**
	 * For Application
	 */
	// Action中进行初始化

	// decoded字段
	private String decodeColumnId;

	// 使用聚合函数
	private String statisticsColumns;
	
	private String status;
	
	//原始状态
	private String initStatus = "def";//修改-modify，新增-insert，默认-def，删除-delete
	//操作后状态
	private String operStatus = "def";//修改-modify，新增-insert，默认-def，删除-delete
	
	private String tableId;

	// 解译字段

	private String deleted;
	
	//统计函数
	private String statisticsFun;
	
	public void setValue(ColumnVO columnVO) throws KANException {
		this.columnId = columnVO.getColumnId();
		this.nameEN = columnVO.getNameEN();
		this.nameZH = columnVO.getNameZH();
		//原始列名用于显示
		this.orgNameEN = columnVO.getNameEN();
		this.orgNameZH = columnVO.getNameZH();
		this.valueType = columnVO.getValueType();
		this.tableId = columnVO.getTableId();
	  this.reportHeaderId = "";
      this.columnWidth = "";
      this.columnWidthType = "1";
      this.columnIndex = "0";
      this.fontSize = "13";
      this.isDecoded = "0";
      this.isLinked = "0";
      this.linkedAction = "";
      this.linkedTarget = "0";
      this.datetimeFormat = "0";
      this.accuracy = "0";
      this.round = "0";
      this.align = "1";
      this.sort = "1";
      this.display = "1";
      this.description = "";
      this.status = "1";
      this.initStatus = "insert";
      this.operStatus = "def";
      this.deleted = "1";
      this.statisticsFun = "0";
	}
	
	public void setValue(ReportDetailVO reportDetailVO) throws KANException {
		this.nameEN = reportDetailVO.getNameEN();
		this.nameZH = reportDetailVO.getNameZH();
	  this.reportDetailId = reportDetailVO.getReportDetailId();
	  this.reportHeaderId = reportDetailVO.getReportHeaderId();
      this.columnWidth = reportDetailVO.getColumnWidth();
      this.columnWidthType = reportDetailVO.getColumnWidthType();
      this.columnIndex = reportDetailVO.getColumnIndex();
      this.fontSize = reportDetailVO.getFontSize();
      this.isDecoded = reportDetailVO.getIsDecoded();
      this.isLinked = reportDetailVO.getIsLinked();
      this.linkedAction = reportDetailVO.getLinkedAction();
      this.linkedTarget = reportDetailVO.getLinkedTarget();
      this.datetimeFormat = reportDetailVO.getDatetimeFormat();
      this.accuracy = reportDetailVO.getAccuracy();
      this.round = reportDetailVO.getRound();
      this.align = reportDetailVO.getAlign();
      this.sort = reportDetailVO.getSort();
      this.display = reportDetailVO.getDisplay();
      this.description = reportDetailVO.getDescription();
      this.status = reportDetailVO.getStatus();
      this.deleted = reportDetailVO.getDeleted();
      this.statisticsFun = reportDetailVO.getStatisticsFun();
      this.initStatus = "def";
      this.operStatus = "def";
      this.tableId = reportDetailVO.getTableId();
	}

	public String getTableId() {
		return tableId;
	}

	
	public String getStatisticsFun() {
		return statisticsFun;
	}

	public void setStatisticsFun(String statisticsFun) {
		this.statisticsFun = statisticsFun;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getDisplayStyle() {
		return displayStyle;
	}

	public void setDisplayStyle(String displayStyle) {
		this.displayStyle = displayStyle;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getReportDetailId() {
		return reportDetailId;
	}


	public void setReportDetailId(String reportDetailId) {
		this.reportDetailId = reportDetailId;
	}


	public String getReportHeaderId() {
		return reportHeaderId;
	}


	public void setReportHeaderId(String reportHeaderId) {
		this.reportHeaderId = reportHeaderId;
	}


	public String getColumnId() {
		return columnId;
	}


	public void setColumnId(String columnId) {
		this.columnId = columnId;
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


	public String getColumnWidth() {
		return columnWidth;
	}


	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}


	public String getColumnWidthType() {
		return columnWidthType;
	}


	public void setColumnWidthType(String columnWidthType) {
		this.columnWidthType = columnWidthType;
	}


	public String getColumnIndex() {
		return columnIndex;
	}


	public void setColumnIndex(String columnIndex) {
		this.columnIndex = columnIndex;
	}


	public String getFontSize() {
		return fontSize;
	}


	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}


	public String getIsDecoded() {
		return isDecoded;
	}


	public void setIsDecoded(String isDecoded) {
		this.isDecoded = isDecoded;
	}


	public String getIsLinked() {
		return isLinked;
	}


	public void setIsLinked(String isLinked) {
		this.isLinked = isLinked;
	}


	public String getLinkedAction() {
		return linkedAction;
	}


	public void setLinkedAction(String linkedAction) {
		this.linkedAction = linkedAction;
	}


	public String getLinkedTarget() {
		return linkedTarget;
	}


	public void setLinkedTarget(String linkedTarget) {
		this.linkedTarget = linkedTarget;
	}


	public String getDatetimeFormat() {
		return datetimeFormat;
	}


	public void setDatetimeFormat(String datetimeFormat) {
		this.datetimeFormat = datetimeFormat;
	}


	public String getAccuracy() {
		return accuracy;
	}


	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}


	public String getRound() {
		return round;
	}


	public void setRound(String round) {
		this.round = round;
	}


	public String getAlign() {
		return align;
	}


	public void setAlign(String align) {
		this.align = align;
	}


	public String getSort() {
		return sort;
	}


	public void setSort(String sort) {
		this.sort = sort;
	}


	public String getDisplay() {
		return display;
	}


	public void setDisplay(String display) {
		this.display = display;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getValueType() {
		return valueType;
	}


	public void setValueType(String valueType) {
		this.valueType = valueType;
	}


	public String getDecodeColumnId() {
		return decodeColumnId;
	}


	public void setDecodeColumnId(String decodeColumnId) {
		this.decodeColumnId = decodeColumnId;
	}


	public String getStatisticsColumns() {
		return statisticsColumns;
	}


	public void setStatisticsColumns(String statisticsColumns) {
		this.statisticsColumns = statisticsColumns;
	}

	public String getInitStatus() {
		return initStatus;
	}

	public void setInitStatus(String initStatus) {
		this.initStatus = initStatus;
	}

	public String getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getOrgNameZH() {
		return orgNameZH;
	}

	public void setOrgNameZH(String orgNameZH) {
		this.orgNameZH = orgNameZH;
	}

	public String getOrgNameEN() {
		return orgNameEN;
	}

	public void setOrgNameEN(String orgNameEN) {
		this.orgNameEN = orgNameEN;
	}

	
	
}
