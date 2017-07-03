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
 * 所有Rule相关的控件在此生成
 * 
 * @author Kevin
 */
public class RuleRender
{
   /**
    * 生成权限对应的多选框
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRuleMultipleChoice( final HttpServletRequest request, final String moduleId ) throws KANException
   {
      // 系统设定的权限 - Super设定Module可以有哪些规则
      final List< RuleVO > baseRuleVOs = KANConstants.getRuleVOs();
      // 用户针对Account全局设定的规则
      List< RuleVO > accountRuleVOs = null;
      // 针对当前Position设定的规则
      List< RuleVO > selectedRuleVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // 初始化baseRightVOs
         if ( KANConstants.getModuleDTOByModuleId( moduleId ) != null && KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO() != null )
         {
            selectedRuleVOs = KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO().getSuperSelectedRuleVOs();
         }
      }

      return getRuleMultipleChoice( request, baseRuleVOs, accountRuleVOs, selectedRuleVOs );
   }

   /**
    * 生成规则对应的多选框
    * HttpServletRequest request
    * List< RightVO > baseRightVOs - 系统设定的权限
    * List< RightVO > accountRightVOs - Account全局设定的权限
    * List< RightVO > selectedRightVOs - 目标对象设定的权限
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

            // 按照语言设置取Rule的名称
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

   // 判断当前节点是否需要选中
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

   // 生成RuleType对应的控件
   // Code reviewed by Kevin at 2013-06-26
   public static String getRuleTypeComboByModuleId( final HttpServletRequest request, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // 按照ModuleId获得Constants中的DTO
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

   // Rule Type的选择只能在Super的设定范围内
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

   // 生成Rule对应的控件
   // Code reviewed by Kevin at 2013-06-26
   public static String getRuleSelectByModuleId( final Locale locale, final String ruleType, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      rs.append( KANUtil.getSelectHTML( KANConstants.getRules( locale.getLanguage(), ruleType, moduleId ), "ruleId", "combo_ruleId", "0", null, "width: auto;" ) );

      return rs.toString();
   }

   /**
    * 生成权限对应的多选框
    * HttpServletRequest request
    * List< RuleVO > accountRightVOs - Account全局设定的权限
    * List< RuleVO > selectedRightVOs - 目标对象设定的权限
    * String moduleId - 模块ID
    */
   public static String getRuleComboByModuleId( final HttpServletRequest request, final List< RuleVO > accountRuleVOs, final List< RuleVO > selectedRuleVOs, final String moduleId )
         throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      rs.append( "<span>" + KANUtil.getProperty( request.getLocale(), "security.position.group.set.rule.tips" ) + "</span>" );
      rs.append( RuleRender.getRuleTypeComboByModuleId( request, moduleId ) );
      rs.append( "<ol id=\"combo_rule\" class=\"static\">" );

      // 当前账户全局变量设置的跟Module、Rule关系
      if ( accountRuleVOs != null && accountRuleVOs.size() > 0 )
      {
         for ( RuleVO ruleVO : accountRuleVOs )
         {
            // 初始化规则对象
            ruleVO.reset( null, request );
            final String decodedRuleType = ruleVO.getDecodeRuleType();
            String decodeRuleName = "";

            // 按照语言设置取Title
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

      // 如果全局已经设置，则移除目标对象中的列表
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

      // 当前目标对象设置的跟Module、Rule关系
      if ( selectedRuleVOs != null && selectedRuleVOs.size() > 0 )
      {
         for ( RuleVO ruleVO : selectedRuleVOs )
         {
            // 初始化规则对象
            ruleVO.reset( null, request );
            final String decodedRuleType = ruleVO.getDecodeRuleType();
            String decodeRuleName = "";

            // 按照语言设置取Title
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
