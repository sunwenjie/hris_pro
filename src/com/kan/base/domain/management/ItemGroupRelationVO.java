package com.kan.base.domain.management;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class ItemGroupRelationVO extends BaseVO
{

   /**
    * Serial Version UID
    */

   private static final long serialVersionUID = -4888074886186581315L;

   /**
    * For DB
    */

   // ��Ŀ - �������ID
   private String relationId;
   // ��Ŀ����ID
   private String itemGroupId;
   // ��ĿID
   private String itemId;
   // ����
   private String description;

   @Override
   public void reset() throws KANException
   {
    
   }

   @Override
   public void update( Object object ) throws KANException
   {

   }
   public String getRelationId()
   {
      return relationId;
   }

   public void setRelationId( String relationId )
   {
      this.relationId = relationId;
   }

   public String getItemGroupId()
   {
      return itemGroupId;
   }

   public void setItemGroupId( String itemGroupId )
   {
      this.itemGroupId = itemGroupId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
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
      return encodedField( relationId );
   }

}
