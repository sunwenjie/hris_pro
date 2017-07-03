package com.kan.base.domain.management;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�SkillVO  
* ��������  ְλ����
* �����ˣ�Jack  
* ����ʱ�䣺2013-7-11 ����07:49:54  
* �޸��ˣ�Jack  
* �޸�ʱ�䣺2013-7-11 ����07:49:54  
* �޸ı�ע��  
* @version   
*   
*/
public class SkillVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 7620945611419188691L;
   // ����Id
   private String skillId;

   // �������ƣ����ģ�
   private String skillNameZH;

   // �������ƣ�Ӣ�ģ�
   private String skillNameEN;

   // ����
   private String description;

   // ������Id
   private String parentSkillId;

   /**
    * for app
    */
   // ���ڵ�����
   private String parentSkillName;

   private String[] skillIdArray;

   @Override
   public void update( final Object object )
   {
      final SkillVO skillVO = ( SkillVO ) object;
      this.skillNameZH = skillVO.getSkillNameZH();
      this.skillNameEN = skillVO.getSkillNameEN();
      this.description = skillVO.getDescription();
      this.parentSkillId = skillVO.getParentSkillId();
      super.setStatus( skillVO.getStatus() );
      super.setModifyBy( skillVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.skillNameZH = "";
      this.skillNameEN = "";
      this.description = "";
      this.parentSkillId = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( skillId );
   }

   public String getSkillId()
   {
      return skillId;
   }

   public void setSkillId( String skillId )
   {
      this.skillId = skillId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getParentSkillId()
   {
      return KANUtil.filterEmpty( parentSkillId ) == null ? "0" : parentSkillId;
   }

   public void setParentSkillId( String parentSkillId )
   {
      this.parentSkillId = parentSkillId;
   }

   public String getSkillNameZH()
   {
      return skillNameZH;
   }

   public void setSkillNameZH( String skillNameZH )
   {
      this.skillNameZH = skillNameZH;
   }

   public String getSkillNameEN()
   {
      return skillNameEN;
   }

   public void setSkillNameEN( String skillNameEN )
   {
      this.skillNameEN = skillNameEN;
   }

   public String getParentSkillName()
   {
      return parentSkillName;
   }

   public void setParentSkillName( String parentSkillName )
   {
      this.parentSkillName = parentSkillName;
   }

   public String[] getSkillIdArray()
   {
      return skillIdArray;
   }

   public void setSkillIdArray( String[] skillIdArray )
   {
      this.skillIdArray = skillIdArray;
   }

}
