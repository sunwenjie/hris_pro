package com.kan.base.domain.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**  
*   
* 项目名称：HRO_V1  
* 类名称：SkillDTO  
* 类描述：生成技能的树形结构用  
* 创建人：Jack  
* 创建时间：2013-7-10 下午07:36:29  
* 修改人：Jack  
* 修改时间：2013-7-10 下午07:36:29  
* 修改备注：  
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

   // 当前Skill对象
   private SkillVO skillVO = new SkillVO();

   // 用于生成Skill树
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
