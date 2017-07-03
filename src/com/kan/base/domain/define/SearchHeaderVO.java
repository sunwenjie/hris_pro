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

public class SearchHeaderVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 1336397445673358135L;

   /**
    * For DB
    */
   // 主键Id
   private String searchHeaderId;

   // 对应的tableId
   private String tableId;

   // 使用JAVA对象？
   private String useJavaObject;

   // JAVA对象限定名
   private String javaObjectName;

   // 搜索中文名
   private String nameZH;

   // 搜索英文名
   private String nameEN;

   // 是否需要先输入条件
   private String isSearchFirst;

   // 描述信息
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // 表 - 视图 MappingVO
   private List< MappingVO > tables = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      // 获得系统自定义的Table
      this.tables = KANConstants.getKANAccountConstants( getAccountId() ).getTables( request.getLocale().getLanguage(), getRole(), TableVO.SEARCH );
      if ( this.tables != null )
      {
         this.tables.add( 0, super.getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.tableId = "";
      this.useJavaObject = "";
      this.javaObjectName = "";
      this.nameZH = "";
      this.nameEN = "";
      this.isSearchFirst = "";
      this.description = "";
      super.setStatus( "" );
      super.setCorpId( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final SearchHeaderVO searchHeaderVO = ( SearchHeaderVO ) object;
      this.useJavaObject = searchHeaderVO.getUseJavaObject();
      this.javaObjectName = searchHeaderVO.getJavaObjectName();
      this.nameZH = searchHeaderVO.getNameZH();
      this.nameEN = searchHeaderVO.getNameEN();
      this.isSearchFirst = searchHeaderVO.getIsSearchFirst();
      this.description = searchHeaderVO.getDescription();
      super.setStatus( searchHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setCorpId( searchHeaderVO.getCorpId() );
   }

   // 解译表 - 视图
   public String getDecodeTable()
   {
      return decodeField( tableId, tables );
   }

   // 解译是否搜索优先
   public String getDecodeIsSearchFirst()
   {
      return decodeField( isSearchFirst, super.getFlags() );
   }

   public String getSearchHeaderId()
   {
      return searchHeaderId;
   }

   public void setSearchHeaderId( String searchHeaderId )
   {
      this.searchHeaderId = searchHeaderId;
   }

   public String getTableId()
   {
      return tableId;
   }

   public void setTableId( String tableId )
   {
      this.tableId = tableId;
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

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( searchHeaderId );
   }

}
