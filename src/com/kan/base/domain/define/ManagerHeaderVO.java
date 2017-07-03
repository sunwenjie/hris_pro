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

public class ManagerHeaderVO extends BaseVO
{

   /**  
   * SerialVersionUID
   *  
   */
   private static final long serialVersionUID = 7944831352139745188L;

   /**
    * For DB
    */

   // 页面ID
   private String managerHeaderId;

   // 数据库表或视图
   private String tableId;

   // 页面名称（中文）
   private String nameZH;

   // 页面名称（英文）
   private String nameEN;

   // 页面备注
   private String comments;

   // 描述
   private String description;

   /**
    * For Appliction
    */
   @JsonIgnore
   // 表 - 视图 
   private List< MappingVO > tables = new ArrayList< MappingVO >();

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.tables = KANConstants.getKANAccountConstants( getAccountId() ).getTables( request.getLocale().getLanguage(), getRole(), TableVO.MANAGER );
      if ( this.tables != null )
      {
         this.tables.add( 0, super.getEmptyMappingVO() );
      }
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( managerHeaderId );
   }

   @Override
   public void reset() throws KANException
   {
      this.tableId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.comments = "";
      this.description = "";
      super.setStatus( "0" );
      super.setCorpId( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ManagerHeaderVO managerHeaderVO = ( ManagerHeaderVO ) object;
      this.tableId = managerHeaderVO.getTableId();
      this.nameZH = managerHeaderVO.getNameZH();
      this.nameEN = managerHeaderVO.getNameEN();
      this.comments = managerHeaderVO.getComments();
      this.description = managerHeaderVO.getDescription();
      super.setStatus( managerHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setCorpId( managerHeaderVO.getCorpId() );
   }

   // 解译表 - 视图
   public String getDecodeTable()
   {
      return decodeField( tableId, tables );
   }

   public String getManagerHeaderId()
   {
      return managerHeaderId;
   }

   public void setManagerHeaderId( String managerHeaderId )
   {
      this.managerHeaderId = managerHeaderId;
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

   public String getComments()
   {
      return comments;
   }

   public void setComments( String comments )
   {
      this.comments = comments;
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

}
