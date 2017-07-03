package com.kan.hro.domain.biz.sb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SBDTOTemp implements Serializable, SpecialDTO< Object, List< ? > >
{

   // serialVersionUID
   private static final long serialVersionUID = 123675302496923762L;

   // Social Benefit HeaderTemp
   private SBHeaderTempVO sbHeaderTempVO;

   // Social Benefit DetailTemp
   private List< SBDetailTempVO > sbDetailTempVOs = new ArrayList< SBDetailTempVO >();

   public SBHeaderTempVO getSbHeaderTempVO()
   {
      return sbHeaderTempVO;
   }

   public void setSbHeaderTempVO( SBHeaderTempVO sbHeaderTempVO )
   {
      this.sbHeaderTempVO = sbHeaderTempVO;
   }

   public List< SBDetailTempVO > getSbDetailTempVOs()
   {
      return sbDetailTempVOs;
   }

   public void setSbDetailTempVOs( List< SBDetailTempVO > sbDetailTempVOs )
   {
      this.sbDetailTempVOs = sbDetailTempVOs;
   }

   @Override
   public Object getHeaderVO()
   {
      return getSbHeaderTempVO();
   }

   @Override
   public List< ? > getDetailVOs()
   {
      return getSbDetailTempVOs();
   }

   @Override
   public Map< ?, ? > getFlags()
   {
      return null;
   }
}
