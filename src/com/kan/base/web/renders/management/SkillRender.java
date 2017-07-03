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
   // �õ�����Skill��Tree
   public static String getSkillTree( final HttpServletRequest request ) throws KANException
   {
      // ��request�л�ȡaccountId
      final String accountId = ( String ) request.getAttribute( "accountId" );

      // ����accountId��ȡ��ǰaccount��Ӧ��skillDTOs
      List< SkillDTO > skillDTOs = KANConstants.getKANAccountConstants( accountId ).getSkillDTOsByParentSkillId( "0", BaseAction.getCorpId( request, null ) );

      final StringBuffer rs = new StringBuffer();
      rs.append( KANUtil.getProperty( request.getLocale(), "management.skill.tips" ) + "��" );

      // ����SkillDTO
      String corpId = null;
      if ( BaseAction.getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         corpId = BaseAction.getCorpId( request, null );
      }
      rs.append( getSkillNode( request.getLocale(), "", skillDTOs, 1, true, null, null, corpId ) );

      return rs.toString();
   }

   /**
    * �ݹ鷽�� - ��������ְλ��
    * 
    * locale - ����
    * parentNodeId - ���ڵ�
    * skillDTOs - ְλ���Ѱ��ղ㼶�ṹ��
    * level - ��ǰ�㼶
    * full - �Ƿ���Ҫ��ʾȫ������
    * flag - �ֱ浱ǰ��ʾSTAFF����GROUP
    * bindId - ��ǰְλ����ID��������Staff Id��Ҳ������Group Id��
    * CorpId - �����Ϊ������INHOUSE
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

               // ������������ȡSkillName
               if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  skillName = skillDTO.getSkillVO().getSkillNameZH();
               }
               else
               {
                  skillName = skillDTO.getSkillVO().getSkillNameEN();
               }

               // ���ɸ��ڵ�
               rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
               rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasNode( skillDTO.getSkillDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                     + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\"/> " );
               // ��������ڹ�Ա���ܣ�����ɾ��
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

               // �ݹ�������ڵĽڵ�
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

   // �ж��Ƿ�����ӽڵ�
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

   // ����LevelId�õ�CSS��ʽ
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
    
   * getSkillNameCombo(��ʾְλ��Ӧ���Ѵ��ڵļ�����Ϣ)  
    
   * @param   name  
    
   * @param  @return    �趨�ļ�  
    
   * @return String    DOM����  
    
   * @throws KANException 
    
   * @Exception �쳣����  
    
   * @since  CodingExample��Ver(���뷶���鿴) 1.1  
    
   */
   public static String getSkillNameCombo( final HttpServletRequest request, final String[] skillIdList ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      rs.append( "<ol id=\"mannageSkill_ol\" class=\"auto\">" );
      // �ӳ����л�ȡSkillView
      final List< SkillBaseView > skillBaseViews = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).SKILL_BASEVIEW;
      String skillName = "";

      // ���SkillId List��Ϊ��
      if ( skillIdList != null && skillIdList.length > 0 )
      {
         // ����skillIdList���Ҷ�Ӧ��name
         for ( String skillId : skillIdList )
         {
            for ( SkillBaseView skillBaseView : skillBaseViews )
            {
               if ( skillId.equals( skillBaseView.getId() ) )
               {
                  // ���SkillID����ֵ
                  final String encodedSkillId = KANUtil.encodeStringWithCryptogram( skillId );
                  // ���skillId��Ӧ��skillName
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
    
   * getSkillListOrder(����ԵĻ��skill list)  
    
   * @param   name  
    
   * @param  @return    �趨�ļ�  
    
   * @return String    DOM����  
   
   * @throws KANException 
    
   * @Exception �쳣����  
    
   * @since  CodingExample��Ver(���뷶���鿴) 1.1  
    
   */
   public static String getSkillListOrder( final HttpServletRequest request, final String parentSkillId, final String[] skillIdArray ) throws KANException
   {

      final StringBuffer rs = new StringBuffer();
      // �ӳ����л��skillDTOs
      final List< SkillDTO > skillDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSkillDTOsByParentSkillId( parentSkillId, BaseAction.getCorpId( request, null ) );

      rs.append( "<ol class=\"auto\">" );
      // ����skillDTOs
      for ( SkillDTO skillDTO : skillDTOs )
      {
         if ( !BaseAction.getAccountId( request, null ).equals( skillDTO.getSkillVO().getAccountId() ) )
         {
            continue;
         }

         final String skillId = skillDTO.getSkillVO().getSkillId();
         String skillName = "";
         Boolean existFlag = false;

         // ������������ȡSkillName
         if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            skillName = skillDTO.getSkillVO().getSkillNameZH();
         }
         else
         {
            skillName = skillDTO.getSkillVO().getSkillNameEN();
         }

         // �ж�Skill�Ƿ����
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
    
   * getBtnDiv(������������btn����)  
    
   * @param   name  
    
   * @param  @return    �趨�ļ�  
    
   * @return String    DOM����  
   
   * @throws KANException 
    
   * @Exception �쳣����  
    
   * @since  CodingExample��Ver(���뷶���鿴) 1.1  
    
   */
   public static String getBtnDiv( final HttpServletRequest request, final String parentSkillId ) throws KANException
   {

      final StringBuffer rs = new StringBuffer();

      // ������Ǹ��ڵ���ʾ��������һ�㡱
      if ( !"0".equals( parentSkillId ) )
      {
         rs.append( "<ol class=\"static\"><li><img src=\"images/back.png\" onclick=\"pageBack();\"> <a id=\"btn_back_lv\" onclick=\"pageBack();\">������һ��</a></li></ol>" );
      }

      return rs.toString();
   }

   public static String getSkillListOrderDiv( final HttpServletRequest request, final String parentSkillId, final String[] skillIdArray ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      // ����btn����
      rs.append( getBtnDiv( request, parentSkillId ) );

      // ����skill list����
      rs.append( getSkillListOrder( request, parentSkillId, skillIdArray ) );

      return rs.toString();
   }

}
