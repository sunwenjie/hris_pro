package com.kan.base.domain.management;

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

public class ItemGroupVO extends BaseVO
{

   /**  
    *  Serial Version UID
    */
   private static final long serialVersionUID = 6001637614808400704L;

   /**
    * For DB
    */
   // ��Ŀ����ID
   private String itemGroupId;

   // ��Ŀ�������ƣ����ģ�
   private String nameZH;

   // ��Ŀ�������ƣ�Ӣ�ģ�
   private String nameEN;

   // �󶨿�ĿID
   private String bindItemId;

   // �Ƿ���Ҫ���տ�Ŀ����ϲ����б�
   private String listMerge;

   // �Ƿ���Ҫ���տ�Ŀ����ϲ�������
   private String reportMerge;

   // �Ƿ���Ҫ���տ�Ŀ����ϲ�����Ʊ��
   private String invoiceMerge;

   // ��Ŀ������ 
   private String description;

   //��Ŀ��������
   private String itemGroupType;

   /**
    * For Application
    */
   @JsonIgnore
   // ��Ŀ
   private List< MappingVO > bindItems = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��Ŀ��������
   private List< MappingVO > itemGroupTypes = new ArrayList< MappingVO >();
   // �󶨿�Ŀ����
   private String[] itemIdArray;

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.bindItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getItems( request.getLocale().getLanguage(), getCorpId() );
      this.itemGroupTypes = KANUtil.getMappings( request.getLocale(), "item.group.type" );
      if ( bindItems != null )
      {
         bindItems.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.itemGroupId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.bindItemId = "";
      this.listMerge = "0";
      this.reportMerge = "0";
      this.invoiceMerge = "0";
      this.description = "";
      this.itemGroupType = "";
      this.itemIdArray = null;
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ItemGroupVO itemGroupVO = ( ItemGroupVO ) object;
      this.nameZH = itemGroupVO.getNameZH();
      this.nameEN = itemGroupVO.getNameEN();
      this.bindItemId = itemGroupVO.getBindItemId();
      this.listMerge = itemGroupVO.getListMerge();
      this.reportMerge = itemGroupVO.getReportMerge();
      this.invoiceMerge = itemGroupVO.getInvoiceMerge();
      this.description = itemGroupVO.getDescription();
      this.itemGroupType = itemGroupVO.getItemGroupType();
      super.setStatus( itemGroupVO.getStatus() );
      super.setModifyBy( itemGroupVO.getModifyBy() );
      super.setModifyDate( new Date() );
      this.itemIdArray = itemGroupVO.getItemIdArray();
   }

   public String getDecodeBindItemId()
   {
      return decodeField( bindItemId, bindItems );
   }

   public String getDecodeListMerge()
   {
      if ( super.getFlags() != null )
      {
         for ( MappingVO mappingVO : super.getFlags() )
         {
            if ( mappingVO.getMappingId().equals( listMerge ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public String getDecodeReportMerge()
   {
      if ( super.getFlags() != null )
      {
         for ( MappingVO mappingVO : super.getFlags() )
         {
            if ( mappingVO.getMappingId().equals( reportMerge ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public String getDecodeInvoiceMerge()
   {
      if ( super.getFlags() != null )
      {
         for ( MappingVO mappingVO : super.getFlags() )
         {
            if ( mappingVO.getMappingId().equals( invoiceMerge ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public String getItemGroupId()
   {
      return itemGroupId;
   }

   public void setItemGroupId( String itemGroupId )
   {
      this.itemGroupId = itemGroupId;
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

   public final String getBindItemId()
   {
      return bindItemId;
   }

   public final void setBindItemId( String bindItemId )
   {
      this.bindItemId = bindItemId;
   }

   public String getListMerge()
   {
      return listMerge;
   }

   public void setListMerge( String listMerge )
   {
      this.listMerge = listMerge;
   }

   public String getReportMerge()
   {
      return reportMerge;
   }

   public void setReportMerge( String reportMerge )
   {
      this.reportMerge = reportMerge;
   }

   public String getInvoiceMerge()
   {
      return invoiceMerge;
   }

   public void setInvoiceMerge( String invoiceMerge )
   {
      this.invoiceMerge = invoiceMerge;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( itemGroupId );
   }

   public final List< MappingVO > getBindItems()
   {
      return bindItems;
   }

   public final void setBindItems( List< MappingVO > bindItems )
   {
      this.bindItems = bindItems;
   }

   public String[] getItemIdArray()
   {
      return itemIdArray;
   }

   public void setItemIdArray( String[] itemIdArray )
   {
      this.itemIdArray = itemIdArray;
   }

   public String getItemGroupType()
   {
      return itemGroupType;
   }

   public void setItemGroupType( String itemGroupType )
   {
      this.itemGroupType = itemGroupType;
   }

   public List< MappingVO > getItemGroupTypes()
   {
      return itemGroupTypes;
   }

   public void setItemGroupTypes( List< MappingVO > itemGroupTypes )
   {
      this.itemGroupTypes = itemGroupTypes;
   }

}
