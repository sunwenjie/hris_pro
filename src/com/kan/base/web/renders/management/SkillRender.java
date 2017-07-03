package com.kan.base.web.renders.management;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.management.SkillBaseView;
import com.kan.base.domain.management.SkillDTO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class SkillRender
{
   // 得到整个Skill的Tree
   public static String getSkillTree( final HttpServletRequest request ) throws KANException
   {
      // 从request中获取accountId
      final String accountId = ( String ) request.getAttribute( "accountId" );

      // 根据accountId获取当前account对应的skillDTOs
      List< SkillDTO > skillDTOs = KANConstants.getKANAccountConstants( accountId ).getSkillDTOsByParentSkillId( "0", BaseAction.getCorpId( request, null ) );

      final StringBuffer rs = new StringBuffer();
      rs.append( KANUtil.getProperty( request.getLocale(), "management.skill.tips" ) + "：" );

      // 遍历SkillDTO
      String corpId = null;
      if ( BaseAction.getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         corpId = BaseAction.getCorpId( request, null );
      }
      rs.append( getSkillNode( request.getLocale(), "", skillDTOs, 1, true, null, null, corpId ) );

      return rs.toString();
   }

   /**
    * 递归方法 - 遍历生成职位树
    * 
    * locale - 语言
    * parentNodeId - 父节点
    * skillDTOs - 职位（已按照层级结构）
    * level - 当前层级
    * full - 是否需要显示全功能树
    * flag - 分辨当前显示STAFF还是GROUP
    * bindId - 当前职位树绑定ID（可以是Staff Id，也可以是Group Id）
    * CorpId - 如果不为空则是INHOUSE
    */
   private static String getSkillNode( final Locale locale, final String parentNodeId, final List< SkillDTO > skillDTOs, final int level, final boolean full, final String flag,
         final String bindId, final String corpId ) throws KANException
   {
      if ( hasNode( skillDTOs ) )
      {
         final StringBuffer rs = new StringBuffer();

         for ( SkillDTO skillDTO : skillDTOs )
         {

            if ( KANUtil.filterEmpty( corpId ) == null
                  || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( skillDTO.getSkillVO().getCorpId() ) != null && skillDTO.getSkillVO().getCorpId().equals( corpId ) ) )
            {
               final String skillId = skillDTO.getSkillVO().getSkillId();
               final String encodedSkillId = skillDTO.getSkillVO().getEncodedId();
               final String nodeId = parentNodeId.concat( "_N" ).concat( skillId );
               String skillName = "";
               String checked = "";

               // 按照语言设置取SkillName
               if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  skillName = skillDTO.getSkillVO().getSkillNameZH();
               }
               else
               {
                  skillName = skillDTO.getSkillVO().getSkillNameEN();
               }

               // 生成根节点
               rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
               rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasNode( skillDTO.getSkillDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                     + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\"/> " );
               // 如果存在于雇员技能，则不能删除
               String disabled = "";
               String onClick = "onclick=\"manageSkill('" + encodedSkillId + "');\"";
               if ( "1".equals( skillDTO.getExtended() ) )
               {
                  disabled = "disabled";
               }
               rs.append( "<input " + disabled + " type=\"checkbox\" name=\"skillIdArray\" id=\"skillIdArray\" value=\"" + skillId + "\"" + checked + "/><a href=\"#\" " + onClick
                     + " >" + skillName + "</a>" );
               rs.append( "<img style=\"cursor:pointer\" src=\"images/add.png\" onclick=\"addChildSkill('" + encodedSkillId + "');\" /> " );
               rs.append( "</li>" );

               // 递归遍历存在的节点
               rs.append( getSkillNode( locale, nodeId, skillDTO.getSkillDTOs(), level + 1, full, flag, bindId, corpId ) );
            }
         }
         return rs.toString();
      }
      else
      {
         return "";
      }
   }

   // 判断是否存在子节点
   private static boolean hasNode( final List< SkillDTO > skillDTOs )
   {
      if ( skillDTOs != null && skillDTOs.size() > 0 )
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   // 按照LevelId得到CSS样式
   private static String getClassNameByLevel( final int level )
   {

      switch ( level )
      {
         case 1:
            return "firstlevel";
         case 2:
            return "secondlevel";
         case 3:
            return "thirdlevel";
         case 4:
            return "fourthlevel";
         case 5:
            return "fifthlevel";
         case 6:
            return "sixthlevel";
         case 7:
            return "seventhlevel";
         case 8:
            return "eighthlevel";

         default:
            return "";
      }
   }

   /**  
    
   * getSkillNameCombo(显示职位对应的已存在的技能信息)  
    
   * @param   name  
    
   * @param  @return    设定文件  
    
   * @return String    DOM对象  
    
   * @throws KANException 
    
   * @Exception 异常对象  
    
   * @since  CodingExample　Ver(编码范例查看) 1.1  
    
   */
   public static String getSkillNameCombo( final HttpServletRequest request, final String[] skillIdList ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      rs.append( "<ol id=\"mannageSkill_ol\" class=\"auto\">" );
      // 从常量中获取SkillView
      final List< SkillBaseView > skillBaseViews = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).SKILL_BASEVIEW;
      String skillName = "";

      // 如果SkillId List不为空
      if ( skillIdList != null && skillIdList.length > 0 )
      {
         // 遍历skillIdList查找对应的name
         for ( String skillId : skillIdList )
         {
            for ( SkillBaseView skillBaseView : skillBaseViews )
            {
               if ( skillId.equals( skillBaseView.getId() ) )
               {
                  // 获得SkillID编码值
                  final String encodedSkillId = KANUtil.encodeStringWithCryptogram( skillId );
                  // 获得skillId对应的skillName
                  skillName = skillBaseView.getName();
                  rs.append( "<li id=\"mannageSkill_"
                        + skillId
                        + "\" style=\"margin: 2px 0px;\">"
                        + "<input type=\"hidden\" id=\"skillIdArray\" name=\"skillIdArray\" value=\""
                        + skillId
                        + "\">"
                        + "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">"
                        + "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" style=\"display: none;\" onClick=\"removeSkill('mannageSkill_"
                        + skillId + "');\"/>" + "&nbsp;&nbsp;" + "<a onclick=link('skillAction.do?proc=to_objectModify&skillId=" + encodedSkillId + "') >" + skillName
                        + "</a></li>" );
               }
            }
         }
         rs.append( "</ol>" );
      }
      return rs.toString();
   }

   /**  
    
   * getSkillListOrder(层次性的获得skill list)  
    
   * @param   name  
    
   * @param  @return    设定文件  
    
   * @return String    DOM对象  
   
   * @throws KANException 
    
   * @Exception 异常对象  
    
   * @since  CodingExample　Ver(编码范例查看) 1.1  
    
   */
   public static String getSkillListOrder( final HttpServletRequest request, final String parentSkillId, final String[] skillIdArray ) throws KANException
   {

      final StringBuffer rs = new StringBuffer();
      // 从常量中获得skillDTOs
      final List< SkillDTO > skillDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSkillDTOsByParentSkillId( parentSkillId, BaseAction.getCorpId( request, null ) );

      rs.append( "<ol class=\"auto\">" );
      // 遍历skillDTOs
      for ( SkillDTO skillDTO : skillDTOs )
      {
         if ( !BaseAction.getAccountId( request, null ).equals( skillDTO.getSkillVO().getAccountId() ) )
         {
            continue;
         }

         final String skillId = skillDTO.getSkillVO().getSkillId();
         String skillName = "";
         Boolean existFlag = false;

         // 按照语言设置取SkillName
         if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            skillName = skillDTO.getSkillVO().getSkillNameZH();
         }
         else
         {
            skillName = skillDTO.getSkillVO().getSkillNameEN();
         }

         // 判断Skill是否存在
         if ( skillIdArray != null && skillIdArray.length > 0 )
         {
            for ( String tempSkillId : skillIdArray )
            {
               if ( skillId.equals( tempSkillId ) )
               {
                  existFlag = true;
                  break;
               }
            }
         }

         rs.append( "<li>" );
         rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + skillId + "\" name=\"manageSkill_skillIdArray" + "\" value=\"" + skillId + "\""
               + ( existFlag ? " checked=\"checked\" disabled=\"disabled\"" : "" ) + "/>&nbsp&nbsp&nbsp<a href=\"#\" onclick=\"showNextSkillListLvl(" + skillId + ")\" >"
               + skillName + "</a>" );
         rs.append( "</li>" );
      }
      rs.append( "<input type=\"hidden\" id=\"skill_lv_body_parentId\" name=\"skill_lv_body_parentId\" value=\"" + parentSkillId + "\" />" );
      rs.append( "</ol>" );

      return rs.toString();
   }

   /**  
    
   * getBtnDiv(根据条件生成btn按键)  
    
   * @param   name  
    
   * @param  @return    设定文件  
    
   * @return String    DOM对象  
   
   * @throws KANException 
    
   * @Exception 异常对象  
    
   * @since  CodingExample　Ver(编码范例查看) 1.1  
    
   */
   public static String getBtnDiv( final HttpServletRequest request, final String parentSkillId ) throws KANException
   {

      final StringBuffer rs = new StringBuffer();

      // 如果不是根节点显示“返回上一层”
      if ( !"0".equals( parentSkillId ) )
      {
         rs.append( "<ol class=\"static\"><li><img src=\"images/back.png\" onclick=\"pageBack();\"> <a id=\"btn_back_lv\" onclick=\"pageBack();\">返回上一层</a></li></ol>" );
      }

      return rs.toString();
   }

   public static String getSkillListOrderDiv( final HttpServletRequest request, final String parentSkillId, final String[] skillIdArray ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      // 生成btn区域
      rs.append( getBtnDiv( request, parentSkillId ) );

      // 生成skill list区域
      rs.append( getSkillListOrder( request, parentSkillId, skillIdArray ) );

      return rs.toString();
   }

}
