package com.kan.base.web.renders.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.domain.system.RuleVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * ����Rule��صĿؼ��ڴ�����
 * 
 * @author Kevin
 */
public class RuleRender
{
   /**
    * ����Ȩ�޶�Ӧ�Ķ�ѡ��
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRuleMultipleChoice( final HttpServletRequest request, final String moduleId ) throws KANException
   {
      // ϵͳ�趨��Ȩ�� - Super�趨Module��������Щ����
      final List< RuleVO > baseRuleVOs = KANConstants.getRuleVOs();
      // �û����Accountȫ���趨�Ĺ���
      List< RuleVO > accountRuleVOs = null;
      // ��Ե�ǰPosition�趨�Ĺ���
      List< RuleVO > selectedRuleVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // ��ʼ��baseRightVOs
         if ( KANConstants.getModuleDTOByModuleId( moduleId ) != null && KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO() != null )
         {
            selectedRuleVOs = KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO().getSuperSelectedRuleVOs();
         }
      }

      return getRuleMultipleChoice( request, baseRuleVOs, accountRuleVOs, selectedRuleVOs );
   }

   /**
    * ���ɹ����Ӧ�Ķ�ѡ��
    * HttpServletRequest request
    * List< RightVO > baseRightVOs - ϵͳ�趨��Ȩ��
    * List< RightVO > accountRightVOs - Accountȫ���趨��Ȩ��
    * List< RightVO > selectedRightVOs - Ŀ������趨��Ȩ��
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRuleMultipleChoice( final HttpServletRequest request, final List< RuleVO > baseRuleVOs, final List< RuleVO > accountRuleVOs,
         final List< RuleVO > selectedRuleVOs ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      if ( baseRuleVOs != null && baseRuleVOs.size() > 0 )
      {
         rs.append( "<ol id=\"combo_rule\" class=\"auto\">" );
         for ( RuleVO ruleVO : baseRuleVOs )
         {
            ruleVO.reset( null, request );

            String ruleName = "";

            // ������������ȡRule������
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               ruleName = ruleVO.getNameZH();
            }
            else
            {
               ruleName = ruleVO.getNameEN();
            }

            rs.append( "<li style=\"margin: 2px 0px;\">" );
            rs.append( "<input type=\"checkbox\" id=\"ruleIdArray\" name=\"ruleIdArray\" value=\"" + ruleVO.getRuleId() + "\" "
                  + ( find( ruleVO.getRuleId(), selectedRuleVOs ) ? "checked" : "" ) + " />" + ruleVO.getDecodeRuleType() + " - " + ruleName );
            rs.append( "</li>" );
         }
         rs.append( "</ol>" );
      }

      return rs.toString();
   }

   // �жϵ�ǰ�ڵ��Ƿ���Ҫѡ��
   private static boolean find( final String ruleId, final List< RuleVO > selectedRuleVOs )
   {
      if ( selectedRuleVOs != null )
      {
         for ( RuleVO ruleVO : selectedRuleVOs )
         {
            if ( ruleVO != null && ruleVO.getRuleId() != null && ruleVO.getRuleId().trim().equals( ruleId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   // ����RuleType��Ӧ�Ŀؼ�
   // Code reviewed by Kevin at 2013-06-26
   public static String getRuleTypeComboByModuleId( final HttpServletRequest request, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // ����ModuleId���Constants�е�DTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByModuleId( moduleId );

      rs.append( "<ol class=\"static\">" );
      rs.append( "<li>" );
      rs.append( KANUtil.getSelectHTML( getMatchedMappings( KANUtil.getMappings( request.getLocale(), "sys.rule.types" ), KANUtil.jasonArrayToStringArray( moduleDTO.getModuleVO().getRuleIds() ) ), "ruleTypeId", "combo_ruleTypeId", "0", "changeRuleType('"
            + moduleId + "');", "width: auto;" ) );
      rs.append( " " );
      rs.append( "<span class=\"rulecombo\"></span>" );
      rs.append( "&nbsp;&nbsp;&nbsp;<input type=\"button\" class=\"addbutton\" name=\"btnAddRule\" id=\"btnAddRule\" value=\""
            + KANUtil.getProperty( request.getLocale(), "button.add" ) + "\" onclick=\"addRule();\"/> " );
      rs.append( "</li>" );
      rs.append( "</ol>" );

      return rs.toString();
   }

   // Rule Type��ѡ��ֻ����Super���趨��Χ��
   // Code reviewed by Kevin at 2013-06-26
   private static List< MappingVO > getMatchedMappings( final List< MappingVO > sourceMappings, final String[] ids )
   {
      final List< MappingVO > returnMappings = new ArrayList< MappingVO >();

      if ( ids == null )
      {
         return sourceMappings;
      }
      else
      {
         for ( MappingVO mappingVO : sourceMappings )
         {
            if ( mappingVO.getMappingId() != null && mappingVO.getMappingId().equals( "0" ) )
            {
               returnMappings.add( mappingVO );
            }
            else
            {
               for ( String id : ids )
               {
                  final RuleVO ruleVO = KANConstants.getRuleVOByRuleId( id );
                  if ( mappingVO.getMappingId() != null && ruleVO != null && ruleVO.getRuleType() != null && mappingVO.getMappingId().trim().equals( ruleVO.getRuleType().trim() ) )
                  {
                     returnMappings.add( mappingVO );
                     break;
                  }
               }
            }
         }
      }

      return returnMappings;
   }

   // ����Rule��Ӧ�Ŀؼ�
   // Code reviewed by Kevin at 2013-06-26
   public static String getRuleSelectByModuleId( final Locale locale, final String ruleType, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      rs.append( KANUtil.getSelectHTML( KANConstants.getRules( locale.getLanguage(), ruleType, moduleId ), "ruleId", "combo_ruleId", "0", null, "width: auto;" ) );

      return rs.toString();
   }

   /**
    * ����Ȩ�޶�Ӧ�Ķ�ѡ��
    * HttpServletRequest request
    * List< RuleVO > accountRightVOs - Accountȫ���趨��Ȩ��
    * List< RuleVO > selectedRightVOs - Ŀ������趨��Ȩ��
    * String moduleId - ģ��ID
    */
   public static String getRuleComboByModuleId( final HttpServletRequest request, final List< RuleVO > accountRuleVOs, final List< RuleVO > selectedRuleVOs, final String moduleId )
         throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      rs.append( "<span>" + KANUtil.getProperty( request.getLocale(), "security.position.group.set.rule.tips" ) + "</span>" );
      rs.append( RuleRender.getRuleTypeComboByModuleId( request, moduleId ) );
      rs.append( "<ol id=\"combo_rule\" class=\"static\">" );

      // ��ǰ�˻�ȫ�ֱ������õĸ�Module��Rule��ϵ
      if ( accountRuleVOs != null && accountRuleVOs.size() > 0 )
      {
         for ( RuleVO ruleVO : accountRuleVOs )
         {
            // ��ʼ���������
            ruleVO.reset( null, request );
            final String decodedRuleType = ruleVO.getDecodeRuleType();
            String decodeRuleName = "";

            // ������������ȡTitle
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               decodeRuleName = ruleVO.getNameZH();
            }
            else
            {
               decodeRuleName = ruleVO.getNameEN();
            }

            rs.append( "<li id=\"rule_" + ruleVO.getRuleId()
                  + "\" style=\"margin: 2px 0px;\"><input type=\"hidden\" id=\"ruleIdArray_account\" name=\"ruleIdArray_account\" value=\"" + ruleVO.getRuleType() + "_"
                  + ruleVO.getRuleId() + "\"><img src=\"images/selected.png\" /> &nbsp;&nbsp; " + decodedRuleType + " - " + decodeRuleName + "</li>" );
         }
      }

      // ���ȫ���Ѿ����ã����Ƴ�Ŀ������е��б�
      if ( selectedRuleVOs != null && selectedRuleVOs.size() > 0 )
      {
         for ( int i = selectedRuleVOs.size() - 1; i >= 0; i-- )
         {
            if ( find( selectedRuleVOs.get( i ).getRuleId(), accountRuleVOs ) )
            {
               selectedRuleVOs.remove( i );
            }
         }
      }

      // ��ǰĿ��������õĸ�Module��Rule��ϵ
      if ( selectedRuleVOs != null && selectedRuleVOs.size() > 0 )
      {
         for ( RuleVO ruleVO : selectedRuleVOs )
         {
            // ��ʼ���������
            ruleVO.reset( null, request );
            final String decodedRuleType = ruleVO.getDecodeRuleType();
            String decodeRuleName = "";

            // ������������ȡTitle
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               decodeRuleName = ruleVO.getNameZH();
            }
            else
            {
               decodeRuleName = ruleVO.getNameEN();
            }

            rs.append( "<li id=\"rule_" + ruleVO.getRuleId() + "\" style=\"margin: 2px 0px;\"><input type=\"hidden\" id=\"ruleIdArray\" name=\"ruleIdArray\" value=\""
                  + ruleVO.getRuleType() + "_" + ruleVO.getRuleId() + "\"><img src=\"images/warning-btn-s.png\" onclick=\"removeRule('rule_" + ruleVO.getRuleId()
                  + "');\"/> &nbsp;&nbsp; " + decodedRuleType + " - " + decodeRuleName + "</li>" );
         }
      }

      rs.append( "</ol>" );

      return rs.toString();
   }
}
