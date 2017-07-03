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
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�MappingHeaderVO  
* ��������  
* �����ˣ�Siuvan  
* ����ʱ�䣺2014-3-3 ����02:52:01  
* �޸��ˣ�Siuvan  
* �޸�ʱ�䣺2014-3-3 ����02:52:01  
* �޸ı�ע��  
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
   // ƥ��ID
   private String mappingHeaderId;

   // ����ID
   private String reportId;

   // ����ID
   private String importId;

   // �б�ID
   private String listId;

   // ƥ�����ƣ����ģ�
   private String nameZH;

   // ƥ�����ƣ�Ӣ�ģ�
   private String nameEN;

   // ����
   private String description;

   /**
    * For Application
    */
   private String clientName;

   // ���� MappingVO List
   private List< MappingVO > importIds = new ArrayList< MappingVO >();

   // ���� MappingVO List
   private List< MappingVO > reportIds = new ArrayList< MappingVO >();

   // �б� MappingVO List
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
