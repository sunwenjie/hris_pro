package com.kan.hro.domain.biz.sb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SBDTO implements Serializable, SpecialDTO< Object, List< ? > >
{
   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = 3857246051424897409L;

   // Social Benefit Header
   private SBHeaderVO sbHeaderVO;

   // Social Benefit Detail
   private List< SBDetailVO > sbDetailVOs = new ArrayList< SBDetailVO >();

   public SBHeaderVO getSbHeaderVO()
   {
      return sbHeaderVO;
   }

   public void setSbHeaderVO( SBHeaderVO sbHeaderVO )
   {
      this.sbHeaderVO = sbHeaderVO;
   }

   public List< SBDetailVO > getSbDetailVOs()
   {
      return sbDetailVOs;
   }

   public void setSbDetailVOs( List< SBDetailVO > sbDetailVOs )
   {
      this.sbDetailVOs = sbDetailVOs;
   }

   @Override
   public Object getHeaderVO()
   {
      return getSbHeaderVO();
   }

   @Override
   public List< ? > getDetailVOs()
   {
      return getSbDetailVOs();
   }

   @Override
   public Map< ?, ? > getFlags()
   {
      return null;
   }
}
