package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MappingDTO implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -473183923728072396L;

   // ƥ�䵼�������� Header
   private MappingHeaderVO mappingHeaderVO = new MappingHeaderVO();

   // ƥ�䵼�������� Details
   private List< MappingDetailVO > mappingDetailVOs = new ArrayList< MappingDetailVO >();

   public MappingHeaderVO getMappingHeaderVO()
   {
      return mappingHeaderVO;
   }

   public void setMappingHeaderVO( MappingHeaderVO mappingHeaderVO )
   {
      this.mappingHeaderVO = mappingHeaderVO;
   }

   public List< MappingDetailVO > getMappingDetailVOs()
   {
      return mappingDetailVOs;
   }

   public void setMappingDetailVOs( List< MappingDetailVO > mappingDetailVOs )
   {
      this.mappingDetailVOs = mappingDetailVOs;
   }
}
