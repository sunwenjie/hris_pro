package com.kan.hro.domain.biz.cb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kan.hro.domain.biz.sb.SpecialDTO;

public class CBDTO implements Serializable, SpecialDTO< Object, List< ? > >
{

   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = 3857246051424897409L;

   // Commercial Benefit Header
   private CBHeaderVO cbHeaderVO;

   // Commercial Benefit Detail
   private List< CBDetailVO > cbDetailVOs = new ArrayList< CBDetailVO >();

   public CBHeaderVO getCbHeaderVO()
   {
      return cbHeaderVO;
   }

   public void setCbHeaderVO( CBHeaderVO cbHeaderVO )
   {
      this.cbHeaderVO = cbHeaderVO;
   }

   public List< CBDetailVO > getCbDetailVOs()
   {
      return cbDetailVOs;
   }

   public void setCbDetailVOs( List< CBDetailVO > cbDetailVOs )
   {
      this.cbDetailVOs = cbDetailVOs;
   }

   @Override
   public Object getHeaderVO()
   {
      return cbHeaderVO;
   }

   @Override
   public List< ? > getDetailVOs()
   {
      return cbDetailVOs;
   }

   @Override
   public Map< ?, ? > getFlags()
   {
      return null;
   }

}
