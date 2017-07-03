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

public class SickLeaveSalaryHeaderVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -4720817302773353960L;

   /**
    * For DB
    */
   // 病假工资主表ID
   private String headerId;

   // 名称（中文）
   private String nameZH;

   // 名称（英文）
   private String nameEN;

   // 科目ID
   private String itemId;

   // 参考日期
   private String baseOn;

   // 描述
   private String description;

   @JsonIgnore
   // 基于列表
   private List< MappingVO > baseOns = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > itemIds = new ArrayList< MappingVO >();

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.itemId = "0";
      this.baseOn = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.baseOns = KANUtil.getMappings( request.getLocale(), "sickleavesalarydetail.baseon.type" );
      this.itemIds = KANConstants.getKANAccountConstants( super.getAccountId() ).getLeaveItems( request.getLocale().getLanguage(), super.getCorpId() );
      this.itemIds.add( 0, super.getEmptyMappingVO() );

      // 只留下“病假 - 非全薪”
      if ( itemIds != null && itemIds.size() > 0 )
      {
         for ( int i = itemIds.size() - 1; i >= 0; i-- )
         {
            final MappingVO mappingVO = itemIds.get( i );

            if ( !mappingVO.getMappingId().equals( "0" ) && !mappingVO.getMappingId().equals( "10253" ) )
            {
               itemIds.remove( i );
            }
         }
      }
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = ( SickLeaveSalaryHeaderVO ) object;
      this.nameZH = sickLeaveSalaryHeaderVO.getNameZH();
      this.nameEN = sickLeaveSalaryHeaderVO.getNameEN();
      this.itemId = sickLeaveSalaryHeaderVO.getItemId();
      this.baseOn = sickLeaveSalaryHeaderVO.getBaseOn();
      this.description = sickLeaveSalaryHeaderVO.getDescription();
      super.setStatus( sickLeaveSalaryHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodebaseOn()
   {
      return decodeField( baseOn, baseOns );
   }

   public String getDecodeitemId()
   {
      return decodeField( itemId, itemIds );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
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

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getBaseOn()
   {
      return baseOn;
   }

   public void setBaseOn( String baseOn )
   {
      this.baseOn = baseOn;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getBaseOns()
   {
      return baseOns;
   }

   public void setBaseOns( List< MappingVO > baseOns )
   {
      this.baseOns = baseOns;
   }

   public List< MappingVO > getItemIds()
   {
      return itemIds;
   }

   public void setItemIds( List< MappingVO > itemIds )
   {
      this.itemIds = itemIds;
   }

}
