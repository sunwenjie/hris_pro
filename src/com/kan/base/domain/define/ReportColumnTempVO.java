package com.kan.base.domain.define;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANUtil;

public class ReportColumnTempVO extends ActionForm
{
   /**
	 * 
	 */
	private static final long serialVersionUID = -5023764959896387871L;

// 字体大小Mapping
   private List< MappingVO > fontSizes = new ArrayList< MappingVO >();

   // 连接目标Mapping
   private List< MappingVO > linkedTargets = new ArrayList< MappingVO >();

   // 日期格式Mapping
   private List< MappingVO > datetimeFormats = new ArrayList< MappingVO >();

   // 对齐方式Mapping
   private List< MappingVO > aligns = new ArrayList< MappingVO >();

   // 保留小数Mapping
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();

   // 截取位数Mapping
   private List< MappingVO > rounds = new ArrayList< MappingVO >();

   // 宽度类型Mapping
   private List< MappingVO > columnWidthTypies = new ArrayList< MappingVO >();

   // 聚合函数
   private List< MappingVO > statisticsColumnses = new ArrayList< MappingVO >();



public ReportColumnTempVO(final HttpServletRequest request ) {
	 this.fontSizes = KANUtil.getMappings( request.getLocale(), "def.list.detail.font.size" );
     this.linkedTargets = KANUtil.getMappings( request.getLocale(), "def.list.detail.link.target" );
     this.datetimeFormats = KANUtil.getMappings( request.getLocale(), "options.dateformat" );
     this.aligns = KANUtil.getMappings( request.getLocale(), "def.list.detail.align" );
     this.accuracys = KANUtil.getMappings( request.getLocale(), "def.list.detail.accuracy" );
     this.rounds = KANUtil.getMappings( request.getLocale(), "def.list.detail.round" );
     this.columnWidthTypies = KANUtil.getMappings( request.getLocale(), "def.list.detail.column.width.type" );
     this.statisticsColumnses = KANUtil.getMappings( request.getLocale(), "def.report.header.statistics.column" );
}


public List<MappingVO> getFontSizes() {
	return fontSizes;
}


public void setFontSizes(List<MappingVO> fontSizes) {
	this.fontSizes = fontSizes;
}


public List<MappingVO> getLinkedTargets() {
	return linkedTargets;
}


public void setLinkedTargets(List<MappingVO> linkedTargets) {
	this.linkedTargets = linkedTargets;
}


public List<MappingVO> getDatetimeFormats() {
	return datetimeFormats;
}


public void setDatetimeFormats(List<MappingVO> datetimeFormats) {
	this.datetimeFormats = datetimeFormats;
}


public List<MappingVO> getAligns() {
	return aligns;
}


public void setAligns(List<MappingVO> aligns) {
	this.aligns = aligns;
}


public List<MappingVO> getAccuracys() {
	return accuracys;
}


public void setAccuracys(List<MappingVO> accuracys) {
	this.accuracys = accuracys;
}


public List<MappingVO> getRounds() {
	return rounds;
}


public void setRounds(List<MappingVO> rounds) {
	this.rounds = rounds;
}


public List<MappingVO> getColumnWidthTypies() {
	return columnWidthTypies;
}


public void setColumnWidthTypies(List<MappingVO> columnWidthTypies) {
	this.columnWidthTypies = columnWidthTypies;
}


public List<MappingVO> getStatisticsColumnses() {
	return statisticsColumnses;
}


public void setStatisticsColumnses(List<MappingVO> statisticsColumnses) {
	this.statisticsColumnses = statisticsColumnses;
}

 

}
