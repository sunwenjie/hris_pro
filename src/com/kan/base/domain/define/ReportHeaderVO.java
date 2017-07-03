package com.kan.base.domain.define;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ReportHeaderVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 5848558636849221995L;

   /**
    * For DB
    */

   // ����ID
   private String reportHeaderId;

   // �����ID
   private String tableId;

   // �����������ģ�
   private String nameZH;

   // ��������Ӣ�ģ�
   private String nameEN;

   // �����
   private String clicks;

   // �Ƿ��ҳ
   private String usePagination;

   // ÿҳ��ʾ������
   private String pageSize;

   // Ԥ��ҳ��
   private String loadPages;

   // �������ȣ�
   private String isSearchFirst;

   // �����ֶ�
   private String sortColumns;

   // �����ֶ�
   private String groupColumns;

   // ͳ���ֶ�
   private String statisticsColumns;

   // ����Excal��ʽ
   private String exportExcelType;

   // �Ƿ񵼳�Pdf
   private String isExportPDF;

   // ������ָ��ģ��
   private String moduleType;

   // �Ƿ񹫹�
   private String isPublic;

   // ����ְλ
   private String positionIds;

   // ����ְ��
   private String positionGradeIds;

   // ����ְλ��
   private String positionGroupIds;

   // �����˲���
   private String branch;

   // ������
   private String owner;

   // ����
   private String description;

   /**
    * 
    * For Application
    */
   @JsonIgnore
   // �Զ���Table
   private List< MappingVO > tables;
   @JsonIgnore
   // ����Excel��ʽ
   private List< MappingVO > exportExcelTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��������
   private List< MappingVO > sortColumnses = new ArrayList< MappingVO >();
   @JsonIgnore
   // �ۺϺ���
   private List< MappingVO > statisticsColumnses = new ArrayList< MappingVO >();
   @JsonIgnore
   // �Ƿ񹫿�
   private List< MappingVO > isPublics = new ArrayList< MappingVO >();
   @JsonIgnore
   // ����ģ��
   private List< MappingVO > moduleTypes = new ArrayList< MappingVO >();

   private String selectTablesJson = "";

   private String unSelectTablesJson = "";

   private String selectColumnsJson = "";

   private String unSelectColumnsJson = "";

   private ReportColumnTempVO reportColumnTempVO;

   // ְ������
   private String[] positionIdArray = new String[] {};

   // ְλ����
   private String[] positionGradeIdArray = new String[] {};

   // ְλ������
   private String[] groupIdArray = new String[] {};

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.tables = KANConstants.getKANAccountConstants( getAccountId() ).getTables( request.getLocale().getLanguage(), getRole(), TableVO.REPORT );
      if ( this.tables != null )
      {
         this.tables.add( 0, super.getEmptyMappingVO() );
      }
      this.sortColumnses = KANUtil.getMappings( getLocale(), "def.report.header.sort.column" );
      this.statisticsColumnses = KANUtil.getMappings( request.getLocale(), "def.report.header.statistics.column" );
      this.isPublics = KANUtil.getMappings( request.getLocale(), "def.report.header.public" );
      this.moduleTypes = KANUtil.getMappings( request.getLocale(), "sys.module.types" );
      this.exportExcelTypes = KANUtil.getMappings( request.getLocale(), "def.report.header.export.excel.type" );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "def.report.header.status" ) );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.usePagination = "0";
      this.pageSize = "";
      this.loadPages = "";
      this.isSearchFirst = "0";
      this.sortColumns = "";
      this.groupColumns = "";
      this.statisticsColumns = "";
      this.exportExcelType = "0";
      this.isExportPDF = "0";
      this.description = "";
      super.setStatus( "0" );
      super.setCorpId( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ReportHeaderVO reportHeaderVO = ( ReportHeaderVO ) object;
      //this.tableId = reportHeaderVO.getTableId();
      this.nameZH = reportHeaderVO.getNameZH();
      this.nameEN = reportHeaderVO.getNameEN();
      this.usePagination = reportHeaderVO.getUsePagination();
      this.pageSize = reportHeaderVO.getPageSize();
      this.loadPages = reportHeaderVO.getLoadPages();
      this.isSearchFirst = reportHeaderVO.getIsSearchFirst();
      this.exportExcelType = reportHeaderVO.getExportExcelType();
      this.isExportPDF = reportHeaderVO.getIsExportPDF();
      this.description = reportHeaderVO.getDescription();
      this.selectTablesJson = reportHeaderVO.getSelectTablesJson();
      super.setStatus( reportHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setCorpId( reportHeaderVO.getCorpId() );
   }

   // ����� - ��ͼ
   public String getDecodeTable()
   {
      return decodeField( tableId, tables );
   }

   // ��������ʽ
   public String getDecodeSortColumns()
   {
      if ( KANUtil.filterEmpty( sortColumns, "0" ) != null )
      {
         return decodeField( sortColumns, sortColumnses );
      }
      return null;
   }

   // �����Ƿ��ҳ
   public String getDecodeUsePagination()
   {
      return decodeField( usePagination, super.getFlags() );
   }

   // ����ͳ�ƺ���
   public String getDecodeStatisticsColumns()
   {
      if ( KANUtil.filterEmpty( statisticsColumns, "0" ) != null )
      {
         return decodeField( statisticsColumns, statisticsColumnses );
      }
      return null;
   }

   public String getExportExcelType()
   {
      return exportExcelType;
   }

   public void setExportExcelType( String exportExcelType )
   {
      this.exportExcelType = exportExcelType;
   }

   public String getReportHeaderId()
   {
      return reportHeaderId;
   }

   public void setReportHeaderId( String reportHeaderId )
   {
      this.reportHeaderId = reportHeaderId;
   }

   public String getTableId()
   {
      return tableId;
   }

   public void setTableId( String tableId )
   {
      this.tableId = tableId;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getClicks()
   {
      return KANUtil.filterEmpty( clicks );
   }

   public void setClicks( String clicks )
   {
      this.clicks = clicks;
   }

   public String getUsePagination()
   {
      return usePagination;
   }

   public void setUsePagination( String usePagination )
   {
      this.usePagination = usePagination;
   }

   public String getPageSize()
   {
      return pageSize;
   }

   public void setPageSize( String pageSize )
   {
      this.pageSize = pageSize;
   }

   public String getLoadPages()
   {
      return loadPages;
   }

   public void setLoadPages( String loadPages )
   {
      this.loadPages = loadPages;
   }

   public String getIsSearchFirst()
   {
      return isSearchFirst;
   }

   public void setIsSearchFirst( String isSearchFirst )
   {
      this.isSearchFirst = isSearchFirst;
   }

   public String getSortColumns()
   {
      return sortColumns;
   }

   public void setSortColumns( String sortColumns )
   {
      this.sortColumns = sortColumns;
   }

   public String getGroupColumns()
   {
      return groupColumns;
   }

   public void setGroupColumns( String groupColumns )
   {
      this.groupColumns = groupColumns;
   }

   public String getStatisticsColumns()
   {
      return statisticsColumns;
   }

   public void setStatisticsColumns( String statisticsColumns )
   {
      this.statisticsColumns = statisticsColumns;
   }

   public String getIsExportPDF()
   {
      return isExportPDF;
   }

   public void setIsExportPDF( String isExportPDF )
   {
      this.isExportPDF = isExportPDF;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getTables()
   {
      return tables;
   }

   public void setTables( List< MappingVO > tables )
   {
      this.tables = tables;
   }

   public List< MappingVO > getSortColumnses()
   {
      return sortColumnses;
   }

   public void setSortColumnses( List< MappingVO > sortColumnses )
   {
      this.sortColumnses = sortColumnses;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( reportHeaderId );
   }

   public List< MappingVO > getStatisticsColumnses()
   {
      return statisticsColumnses;
   }

   public void setStatisticsColumnses( List< MappingVO > statisticsColumnses )
   {
      this.statisticsColumnses = statisticsColumnses;
   }

   public List< MappingVO > getIsPublics()
   {
      return isPublics;
   }

   public void setIsPublics( List< MappingVO > isPublics )
   {
      this.isPublics = isPublics;
   }

   public String getModuleType()
   {
      return moduleType;
   }

   public void setModuleType( String moduleType )
   {
      this.moduleType = moduleType;
   }

   public String getPositionIds()
   {
      return positionIds;
   }

   public void setPositionIds( String positionIds )
   {
      this.positionIds = positionIds;
   }

   public String getPositionGradeIds()
   {
      return positionGradeIds;
   }

   public void setPositionGradeIds( String positionGradeIds )
   {
      this.positionGradeIds = positionGradeIds;
   }

   public String getPositionGroupIds()
   {
      return positionGroupIds;
   }

   public void setPositionGroupIds( String positionGroupIds )
   {
      this.positionGroupIds = positionGroupIds;
   }

   public void setIsPublic( String isPublic )
   {
      this.isPublic = isPublic;
   }

   public String getIsPublic()
   {
      return isPublic;
   }

   public String[] getPositionIdArray()
   {
      return positionIdArray;
   }

   public void setPositionIdArray( String[] positionIdArray )
   {
      this.positionIdArray = positionIdArray;
   }

   public String[] getPositionGradeIdArray()
   {
      return positionGradeIdArray;
   }

   public void setPositionGradeIdArray( String[] positionGradeIdArray )
   {
      this.positionGradeIdArray = positionGradeIdArray;
   }

   public String[] getGroupIdArray()
   {
      return groupIdArray;
   }

   public void setGroupIdArray( String[] groupIdArray )
   {
      this.groupIdArray = groupIdArray;
   }

   public String getBranch()
   {
      return branch;
   }

   public void setBranch( String branch )
   {
      this.branch = branch;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public List< MappingVO > getExportExcelTypes()
   {
      return exportExcelTypes;
   }

   public void setExportExcelTypes( List< MappingVO > exportExcelTypes )
   {
      this.exportExcelTypes = exportExcelTypes;
   }

   public List< MappingVO > getModuleTypes()
   {
      return moduleTypes;
   }

   public void setModuleTypes( List< MappingVO > moduleTypes )
   {
      this.moduleTypes = moduleTypes;
   }

   public String getSelectTablesJson()
   {
      return selectTablesJson;
   }

   public void setSelectTablesJson( String selectTablesJson )
   {
      this.selectTablesJson = selectTablesJson;
   }

   public String getUnSelectTablesJson()
   {
      return unSelectTablesJson;
   }

   public void setUnSelectTablesJson( String unSelectTablesJson )
   {
      this.unSelectTablesJson = unSelectTablesJson;
   }

   public String getSelectColumnsJson()
   {
      return selectColumnsJson;
   }

   public void setSelectColumnsJson( String selectColumnsJson )
   {
      this.selectColumnsJson = selectColumnsJson;
   }

   public String getUnSelectColumnsJson()
   {
      return unSelectColumnsJson;
   }

   public void setUnSelectColumnsJson( String unSelectColumnsJson )
   {
      this.unSelectColumnsJson = unSelectColumnsJson;
   }

   public ReportColumnTempVO getReportColumnTempVO()
   {
      return reportColumnTempVO;
   }

   public void setReportColumnTempVO( ReportColumnTempVO reportColumnTempVO )
   {
      this.reportColumnTempVO = reportColumnTempVO;
   }

}
