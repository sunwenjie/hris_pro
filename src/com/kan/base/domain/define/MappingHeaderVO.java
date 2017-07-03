package com.kan.base.domain.define;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：MappingHeaderVO  
* 类描述：  
* 创建人：Siuvan  
* 创建时间：2014-3-3 下午02:52:01  
* 修改人：Siuvan  
* 修改时间：2014-3-3 下午02:52:01  
* 修改备注：  
* @version   
*   
*/
public class MappingHeaderVO extends BaseVO
{
   // Serial Version UID
   private static final long serialVersionUID = 11654555648545L;

   /**
    * For DB
    */
   // 匹配ID
   private String mappingHeaderId;

   // 报表ID
   private String reportId;

   // 导入ID
   private String importId;

   // 列表ID
   private String listId;

   // 匹配名称（中文）
   private String nameZH;

   // 匹配名称（英文）
   private String nameEN;

   // 描述
   private String description;

   /**
    * For Application
    */
   private String clientName;

   // 导入 MappingVO List
   private List< MappingVO > importIds = new ArrayList< MappingVO >();

   // 导出 MappingVO List
   private List< MappingVO > reportIds = new ArrayList< MappingVO >();

   // 列表 MappingVO List
   private List< MappingVO > listIds = new ArrayList< MappingVO >();

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      try
      {
         this.importIds = KANConstants.getKANAccountConstants( super.getAccountId() ).getImportHeaders( super.getLocale().getLanguage(), KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) ) );
      }
      catch ( KANException e )
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      importIds.add( 0, getEmptyMappingVO() );
      this.reportIds = KANConstants.getKANAccountConstants( super.getAccountId() ).getReportHeaders( super.getLocale().getLanguage() );
      reportIds.add( 0, getEmptyMappingVO() );
      this.listIds = KANConstants.getKANAccountConstants( super.getAccountId() ).getLists( super.getLocale().getLanguage(), false, getCorpId() );
      listIds.add( 0, getEmptyMappingVO() );
   }

   @Override
   public void reset() throws KANException
   {
      super.setClientId( "" );
      super.setCorpId( "" );
      this.reportId = "";
      this.importId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.description = "";
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final MappingHeaderVO mappingHeader = ( MappingHeaderVO ) object;
      super.setCorpId( mappingHeader.getCorpId() );
      this.reportId = mappingHeader.getReportId();
      this.importId = mappingHeader.getImportId();
      this.nameZH = mappingHeader.getNameZH();
      this.nameEN = mappingHeader.getNameEN();
      super.setStatus( mappingHeader.getStatus() );
      this.description = mappingHeader.getDescription();
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( mappingHeaderId );
   }

   public String getEncodedCorpId() throws KANException
   {
      return encodedField( super.getCorpId() );
   }

   public String getMappingHeaderId()
   {
      return mappingHeaderId;
   }

   public void setMappingHeaderId( String mappingHeaderId )
   {
      this.mappingHeaderId = mappingHeaderId;
   }

   public String getReportId()
   {
      return reportId;
   }

   public void setReportId( String reportId )
   {
      this.reportId = reportId;
   }

   public String getImportId()
   {
      return importId;
   }

   public void setImportId( String importId )
   {
      this.importId = importId;
   }

   public String getListId()
   {
      return listId;
   }

   public void setListId( String listId )
   {
      this.listId = listId;
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

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getClientName()
   {
      return clientName;
   }

   public void setClientName( String clientName )
   {
      this.clientName = clientName;
   }

   public List< MappingVO > getImportIds()
   {
      return importIds;
   }

   public void setImportIds( List< MappingVO > importIds )
   {
      this.importIds = importIds;
   }

   public List< MappingVO > getReportIds()
   {
      return reportIds;
   }

   public void setReportIds( List< MappingVO > reportIds )
   {
      this.reportIds = reportIds;
   }

   public List< MappingVO > getListIds()
   {
      return listIds;
   }

   public void setListIds( List< MappingVO > listIds )
   {
      this.listIds = listIds;
   }

}
