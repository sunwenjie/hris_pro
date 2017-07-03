package com.kan.hro.domain.biz.payment;

import java.util.Date;
import java.util.List;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class CommonBatchVO extends BaseVO
{
   // serialVersionUID
   private static final long serialVersionUID = 607778465078507895L;

   //批次Id
   private String batchId;

   private String importExcelName;

   private String description;

   // 当前页面状态status 标记Id(preview, confirm, submit)
   private String statusFlag;

   // 当前页面类型标记(batch, contract, header, detail)
   private String pageFlag;

   //for application
   private List< String > inList;
   private List< String > notInList;

   public List< String > getInList()
   {
      return inList;
   }

   public void setInList( List< String > inList )
   {
      this.inList = inList;
   }

   public List< String > getNotInList()
   {
      return notInList;
   }

   public void setNotInList( List< String > notInList )
   {
      this.notInList = notInList;
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getImportExcelName()
   {
      return importExcelName;
   }

   public void setImportExcelName( String importExcelName )
   {
      this.importExcelName = importExcelName;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( batchId );
   }

   public String getSubStrDescription() throws KANException
   {
      String result = "";
      if ( KANUtil.filterEmpty( this.description ) != null )
      {
         if ( description.length() > 15 )
         {
            result = description.substring( 0, 20 ) + "...";
         }
         else
         {
            result = description;
         }
      }
      return result;
   }

   @Override
   public void reset() throws KANException
   {
      this.description = "";
      this.importExcelName = "";
      super.setStatus( "" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final CommonBatchVO commonBatchVO = ( CommonBatchVO ) object;
      this.setDescription( commonBatchVO.getDescription() );
      this.setImportExcelName( commonBatchVO.getImportExcelName() );
      super.setStatus( "0" );
      super.setModifyDate( new Date() );
   }

   public String getStatusFlag()
   {
      return statusFlag;
   }

   public void setStatusFlag( String statusFlag )
   {
      this.statusFlag = statusFlag;
   }

   public String getPageFlag()
   {
      return pageFlag;
   }

   public void setPageFlag( String pageFlag )
   {
      this.pageFlag = pageFlag;
   }

}
