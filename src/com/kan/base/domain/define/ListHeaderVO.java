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

public class ListHeaderVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -2948575542385388878L;

   /**
    * For DB
    */

   private String listHeaderId;

   private String tableId;

   // 关联主表ID
   private String parentId;

   // 使用JAVA对象？
   private String useJavaObject;

   // JAVA对象限定名
   private String javaObjectName;

   private String searchId;

   private String nameZH;

   private String nameEN;

   private String pageSize;

   private String loadPages;

   private String usePagination;

   private String isSearchFirst;

   private String exportExcel;

   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   private List< MappingVO > tables = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > searchHeaders = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > parents = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      // 获得系统自定义的Table
      this.tables = KANConstants.getKANAccountConstants( getAccountId() ).getTables( request.getLocale().getLanguage(), getRole(), TableVO.LIST );
      if ( this.tables != null )
      {
         this.tables.add( 0, super.getEmptyMappingVO() );
      }
      // 获得账户自定义的SearchHeader
      this.searchHeaders = KANConstants.getKANAccountConstants( super.getAccountId() ).getSearchHeadersByTableId( tableId, super.getCorpId(), request.getLocale().getLanguage() );
      if ( this.searchHeaders != null )
      {
         this.searchHeaders.add( 0, super.getEmptyMappingVO() );
      }
      this.parents = KANConstants.getKANAccountConstants( super.getAccountId() ).getLists( request.getLocale().getLanguage(), true, getCorpId() );
      if ( parents != null && parents.size() > 0 )
      {
         parents.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.tableId = "";
      this.parentId = "0";
      this.useJavaObject = "0";
      this.javaObjectName = "";
      this.searchId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.pageSize = "";
      this.loadPages = "";
      this.usePagination = "";
      this.isSearchFirst = "";
      this.exportExcel = "";
      this.description = "";
      super.setStatus( "" );
      super.setCorpId( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ListHeaderVO listHeaderVO = ( ListHeaderVO ) object;
      this.parentId = listHeaderVO.getParentId();
      this.useJavaObject = listHeaderVO.getUseJavaObject();
      this.javaObjectName = listHeaderVO.getJavaObjectName();
      this.searchId = listHeaderVO.getSearchId();
      this.nameZH = listHeaderVO.getNameZH();
      this.nameEN = listHeaderVO.getNameEN();
      this.usePagination = listHeaderVO.getUsePagination();
      this.pageSize = listHeaderVO.getPageSize();
      this.loadPages = listHeaderVO.getLoadPages();
      this.isSearchFirst = listHeaderVO.getIsSearchFirst();
      this.exportExcel = listHeaderVO.getExportExcel();
      this.description = listHeaderVO.getDescription();
      super.setStatus( listHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setCorpId( listHeaderVO.getCorpId() );
   }

   public String getDecodeTable()
   {
      return decodeField( tableId, tables );
   }

   public String getDecodeSearchHeader()
   {
      return decodeField( searchId, searchHeaders );
   }

   public String getDecodeIsSearchFirst()
   {
      return decodeField( isSearchFirst, super.getFlags() );
   }

   public String getDecodeUsePagination()
   {
      return decodeField( usePagination, super.getFlags() );
   }

   public String getDecodeExportExcel()
   {
      return decodeField( exportExcel, super.getFlags() );
   }

   public String getListHeaderId()
   {
      return listHeaderId;
   }

   public void setListHeaderId( String listHeaderId )
   {
      this.listHeaderId = listHeaderId;
   }

   public String getTableId()
   {
      return tableId;
   }

   public void setTableId( String tableId )
   {
      this.tableId = tableId;
   }

   public String getParentId()
   {
      return parentId;
   }

   public void setParentId( String parentId )
   {
      this.parentId = parentId;
   }

   public String getUseJavaObject()
   {
      return useJavaObject;
   }

   public void setUseJavaObject( String useJavaObject )
   {
      this.useJavaObject = useJavaObject;
   }

   public String getJavaObjectName()
   {
      return javaObjectName;
   }

   public void setJavaObjectName( String javaObjectName )
   {
      this.javaObjectName = javaObjectName;
   }

   public String getSearchId()
   {
      return searchId;
   }

   public void setSearchId( String searchId )
   {
      this.searchId = searchId;
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

   public String getUsePagination()
   {
      return usePagination;
   }

   public void setUsePagination( String usePagination )
   {
      this.usePagination = usePagination;
   }

   public String getIsSearchFirst()
   {
      return isSearchFirst;
   }

   public void setIsSearchFirst( String isSearchFirst )
   {
      this.isSearchFirst = isSearchFirst;
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

   public List< MappingVO > getSearchHeaders()
   {
      return searchHeaders;
   }

   public void setSearchHeaders( List< MappingVO > searchHeaders )
   {
      this.searchHeaders = searchHeaders;
   }

   public List< MappingVO > getParents()
   {
      return parents;
   }

   public void setParents( List< MappingVO > parents )
   {
      this.parents = parents;
   }

   public String getExportExcel()
   {
      return exportExcel;
   }

   public void setExportExcel( String exportExcel )
   {
      this.exportExcel = exportExcel;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( listHeaderId );
   }

}
