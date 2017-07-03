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
   // ����Id
   private String searchHeaderId;

   // ��Ӧ��tableId
   private String tableId;

   // ʹ��JAVA����
   private String useJavaObject;

   // JAVA�����޶���
   private String javaObjectName;

   // ����������
   private String nameZH;

   // ����Ӣ����
   private String nameEN;

   // �Ƿ���Ҫ����������
   private String isSearchFirst;

   // ������Ϣ
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // �� - ��ͼ MappingVO
   private List< MappingVO > tables = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      // ���ϵͳ�Զ����Table
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

   // ����� - ��ͼ
   public String getDecodeTable()
   {
      return decodeField( tableId, tables );
   }

   // �����Ƿ���������
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
