package com.kan.base.domain.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�SkillDTO  
* �����������ɼ��ܵ����νṹ��  
* �����ˣ�Jack  
* ����ʱ�䣺2013-7-10 ����07:36:29  
* �޸��ˣ�Jack  
* �޸�ʱ�䣺2013-7-10 ����07:36:29  
* �޸ı�ע��  
* @version   
*   
*/
public class SkillDTO implements Serializable
{


   
   /**  
   * serialVersionUID
   *  
   * @since Ver 1.1  
   */  
   
   private static final long serialVersionUID = 6996669469795350244L;

   // ��ǰSkill����
   private SkillVO skillVO = new SkillVO();

   // ��������Skill��
   private List< SkillDTO > skillDTOs = new ArrayList< SkillDTO >();
   
   // for application
   private String extended;

   public SkillVO getSkillVO()
   {
      return skillVO;
   }

   public void setSkillVO( SkillVO skillVO )
   {
      this.skillVO = skillVO;
   }

   public List< SkillDTO > getSkillDTOs()
   {
      return skillDTOs;
   }

   public void setSkillDTOs( List< SkillDTO > skillDTOs )
   {
      this.skillDTOs = skillDTOs;
   }

   public String getExtended()
   {
      return extended;
   }

   public void setExtended( String extended )
   {
      this.extended = extended;
   }

}
