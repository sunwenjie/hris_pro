package com.kan.hro.domain.biz.sb;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.kan.base.domain.BaseDTO;
import com.kan.base.util.KANUtil;

public class SBBillDTO extends BaseDTO implements SpecialDTO< Object, List< ? > >, Comparator< Object >
{

   /**  
   * Serial Version UID 
   */
   private static final long serialVersionUID = -7494909744444836941L;

   // 社保单主
   private SBBillHeaderView sbBillHeaderView;

   // 社保单从
   private List< SBBillDetailView > sbBillDetailViews = new ArrayList< SBBillDetailView >();

   // 标识为空科目
   private Map< String, Integer > flagMap = new HashMap< String, Integer >();

   // 数值字段
   private final static String[] NUMBER_FILED = new String[] { "amountCompany", "amountPersonal" };

   public SBBillHeaderView getSbBillHeaderView()
   {
      return sbBillHeaderView;
   }

   public void setSbBillHeaderView( SBBillHeaderView sbBillHeaderView )
   {
      this.sbBillHeaderView = sbBillHeaderView;
   }

   public List< SBBillDetailView > getSbBillDetailViews()
   {
      return sbBillDetailViews;
   }

   public void setSbBillDetailViews( List< SBBillDetailView > sbBillDetailViews )
   {
      this.sbBillDetailViews = sbBillDetailViews;
   }

   public Map< String, Integer > getFlagMap()
   {
      return flagMap;
   }

   public void setFlagMap( Map< String, Integer > flagMap )
   {
      this.flagMap = flagMap;
   }

   @Override
   public Object getHeaderVO()
   {
      return sbBillHeaderView;
   }

   @Override
   public List< ? > getDetailVOs()
   {
      return sbBillDetailViews;
   }

   @Override
   public Map< ?, ? > getFlags()
   {
      return flagMap;
   }

   @Override
   public int compare( Object o1, Object o2 )
   {
      try
      {
         final SBBillDTO dto1 = ( SBBillDTO ) o1;
         final SBBillDTO dto2 = ( SBBillDTO ) o2;

         final String field1 = ( ( String ) KANUtil.getValue( dto1.getSbBillHeaderView(), sortColumn ) );
         final String field2 = ( ( String ) KANUtil.getValue( dto2.getSbBillHeaderView(), sortColumn ) );

         if ( KANUtil.filterEmpty( field1 ) != null && KANUtil.filterEmpty( field2 ) != null )
         {
            if ( isNumberFiled() )
            {
               if ( sortOrder.equals( "asc" ) )
               {
                  return -( Double.valueOf( field1 ).compareTo( Double.valueOf( field2 ) ) );
               }
               else
               {
                  return ( Double.valueOf( field1 ).compareTo( Double.valueOf( field2 ) ) );
               }
            }
            if ( sortOrder.equals( "asc" ) )
            {
               return -field1.compareTo( field2 );
            }
            else
            {
               return field1.compareTo( field2 );
            }
         }

         return 0;
      }
      catch ( Exception e )
      {
      }
      return 0;
   }

   private boolean isNumberFiled()
   {
      return ArrayUtils.contains( NUMBER_FILED, sortColumn );
   }

}