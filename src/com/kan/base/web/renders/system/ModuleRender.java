package com.kan.base.web.renders.system;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.GroupModuleDTO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionModuleDTO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.system.AccountModuleDTO;
import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.domain.system.RuleVO;
import com.kan.base.service.inf.system.ModuleService;
import com.kan.base.tag.AuthConstants;
import com.kan.base.util.CachedUtil;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class ModuleRender
{
   // 得到整个Module的Tree - 按照登录，用于全局规则设置
   // Code reviewed by Kevin at 2013-06-26
   public static String getModuleTree( final HttpServletRequest request, final boolean hasCheckbox ) throws KANException
   {
      // 系统模块树
      final List< ModuleDTO > moduleDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getModuleDTOs();
      // 针对当前Position设定的权限
      final List< ModuleVO > selectedModuleVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSelectedModuleVOs();

      // 遍历ModuleDTO
      return getModuleNode( request, "", "", moduleDTOs, selectedModuleVOs, 1, hasCheckbox );
   }

   // 得到整个Module的Tree - 按照已选择的Module
   public static String getModuleTreeBySelectedModules( final HttpServletRequest request, final boolean hasCheckbox, final List< ModuleVO > selectedModuleVOs ) throws KANException
   {
      // 系统模块树
      final List< ModuleDTO > moduleDTOs = KANConstants.MODULE_DTO;

      // 遍历ModuleDTO
      return getModuleNode( request, "", "", moduleDTOs, selectedModuleVOs, 1, hasCheckbox );
   }

   // 得到整个Module的Tree - 按照AccountId
   // Code reviewed by Kevin at 2013-06-26
   public static String getModuleTreeByAccountId( final HttpServletRequest request, final boolean hasCheckbox, final String accountId ) throws KANException
   {
      // 系统模块树
      final List< ModuleDTO > moduleDTOs = KANConstants.MODULE_DTO;
      // 针对当前Position设定的权限
      final List< ModuleVO > selectedModuleVOs = ( accountId != null && KANConstants.getKANAccountConstants( accountId ) != null ? KANConstants.getKANAccountConstants( accountId ).getModuleVOs()
            : null );

      // 遍历ModuleDTO
      return getModuleNode( request, "", "", moduleDTOs, selectedModuleVOs, 1, hasCheckbox );
   }

   // 得到整个Module的Tree - 可以设定是否有Checkbox
   // Code reviewed by Kevin at 2013-06-26
   public static String getModuleTreeByPositionId( final HttpServletRequest request, final boolean hasCheckbox, final String positionId ) throws KANException
   {
      // 获得整棵有效的Module树
      final List< ModuleDTO > moduleDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getModuleDTOs();

      // 获得全局设定过的Module列表
      List< ModuleVO > moduleVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSelectedModuleVOs();

      // 按照PositionId获得PositionDTO
      final PositionDTO positionDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionDTOByPositionId( positionId );

      // 获得Position已设定过的Module列表
      if ( positionDTO != null && positionDTO.getModuleVOs() != null && positionDTO.getModuleVOs().size() > 0 )
      {
         if ( moduleVOs == null )
         {
            moduleVOs = new ArrayList< ModuleVO >();
         }

         moduleVOs.addAll( positionDTO.getModuleVOs() );
      }

      // 遍历ModuleDTO
      return getModuleNode( request, positionId, "", moduleDTOs, moduleVOs, 1, hasCheckbox );
   }

   // 得到整个Module的Tree - 可以设定是否有Checkbox
   // Code reviewed by Kevin at 2013-06-26
   public static String getModuleTreeByGroupId( final HttpServletRequest request, final boolean hasCheckbox, final String groupId ) throws KANException
   {
      // 获得整棵有效的Module树
      final List< ModuleDTO > moduleDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getModuleDTOs();

      // 获得全局设定过的Module列表
      List< ModuleVO > moduleVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSelectedModuleVOs();

      // 按照GroupId获得GroupDTO
      final GroupDTO groupDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getGroupDTOByGroupId( groupId );

      // 获得Group已设定过的Module列表
      if ( groupDTO != null && groupDTO.getModuleVOs() != null && groupDTO.getModuleVOs().size() > 0 )
      {
         if ( moduleVOs == null )
         {
            moduleVOs = new ArrayList< ModuleVO >();
         }
         moduleVOs.addAll( groupDTO.getModuleVOs() );
      }

      // 遍历ModuleDTO
      return getModuleNode( request, groupId, "", moduleDTOs, moduleVOs, 1, hasCheckbox );
   }

   /**
    * 递归方法 - 遍历生成模块树
    * 
    * request - HttpServletRequest
    * ownerObjectId - 当前Module Ownwe的ID
    * parentNodeId - 父节点
    * moduleDTOs - 模块（已按照层级结构）
    * selectedModuleVOs - 当前需要默认选中的模块
    * level - 当前层级
    * hasCheckbox - 是否需要有Checkbox
    */
   // Code reviewed by Kevin at 2013-06-26
   private static String getModuleNode( final HttpServletRequest request, final String ownerObjectId, final String parentNodeId, final List< ModuleDTO > moduleDTOs,
         final List< ModuleVO > selectedModuleVOs, final int level, final boolean hasCheckbox ) throws KANException
   {
      try
      {
         if ( hasTreeNode( moduleDTOs ) )
         {
            final StringBuffer rs = new StringBuffer();

            for ( ModuleDTO moduleDTO : moduleDTOs )
            {

               // 如果是HRO环境
               if ( BaseAction.getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) )
               {
                  // 如果是HRM菜单不显示
                  if ( moduleDTO.getModuleVO().getRole().equals( KANConstants.ROLE_IN_HOUSE ) && !( BaseAction.getAccountId( request, null ) ).equals( "1" ) )
                  {
                     continue;
                  }

                  final String moduleId = moduleDTO.getModuleVO().getModuleId();
                  final String nodeId = parentNodeId.concat( "_N" ).concat( moduleId );
                  String moduleName = "";

                  // 按照语言设置取Title
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     moduleName = moduleDTO.getModuleVO().getNameZH();
                  }
                  else
                  {
                     moduleName = moduleDTO.getModuleVO().getNameEN();
                  }

                  // 生成根节点
                  rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
                  rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasTreeNode( moduleDTO.getModuleDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                        + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\" /> " );

                  // 需要使用Checkbox
                  if ( hasCheckbox )
                  {
                     rs.append( "<input type=\"checkbox\" name=\"moduleIdArray\" id=\"moduleIdArray\" value=\"" + moduleId + "\" onclick=\"manageModule(this, '"
                           + ( ownerObjectId == null ? "" : ownerObjectId ) + "','" + moduleId + "');\" " + ( checkedSelected( moduleId, selectedModuleVOs ) ? "checked" : "" )
                           + ">" );
                  }
                  // 不使用Checkbox的情况下，改用图片说明当前Module是否已有设置
                  else
                  {
                     if ( checkedSelected( moduleId, selectedModuleVOs ) )
                     {
                        rs.append( "<img id=\"" + moduleId + "\" src=\"images/enable.png\" /> " );
                     }
                     else
                     {
                        rs.append( "<img id=\"" + moduleId + "\" src=\"images/empty.png\" /> " );
                     }
                  }

                  rs.append( "<a id=\"manageModule\" onclick=\"manageModule(this, '"
                        + ( ownerObjectId == null || ownerObjectId.trim().equals( "" ) ? "" : URLEncoder.encode( Cryptogram.encodeString( ownerObjectId ), "UTF-8" ) ) + "','"
                        + moduleId + "');\" class=\"kanhandle\">" );
                  rs.append( moduleName );
                  rs.append( "</a>" );
                  if ( hasCheckbox )
                  {
                     rs.append( "</input>" );
                  }
                  rs.append( "</li>" );

                  // 递归遍历存在的节点
                  rs.append( getModuleNode( request, ownerObjectId, nodeId, moduleDTO.getModuleDTOs(), selectedModuleVOs, level + 1, hasCheckbox ) );
               }
               // 如果是HRM环境
               else if ( BaseAction.getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
               {
                  // 如果是HRO菜单不显示
                  if ( moduleDTO.getModuleVO().getRole().equals( KANConstants.ROLE_HR_SERVICE ) )
                  {
                     continue;
                  }

                  final String moduleId = moduleDTO.getModuleVO().getModuleId();
                  final String nodeId = parentNodeId.concat( "_N" ).concat( moduleId );
                  String moduleName = "";

                  // 按照语言设置取Title
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     moduleName = moduleDTO.getModuleVO().getTitleZH();
                  }
                  else
                  {
                     moduleName = moduleDTO.getModuleVO().getTitleEN();
                  }

                  // 生成根节点
                  rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
                  rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasTreeNode( moduleDTO.getModuleDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                        + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\" /> " );

                  // 需要使用Checkbox
                  if ( hasCheckbox )
                  {
                     rs.append( "<input type=\"checkbox\" name=\"moduleIdArray\" id=\"moduleIdArray\" value=\"" + moduleId + "\" onclick=\"manageModule(this, '"
                           + ( ownerObjectId == null ? "" : ownerObjectId ) + "','" + moduleId + "');\" " + ( checkedSelected( moduleId, selectedModuleVOs ) ? "checked" : "" )
                           + ">" );
                  }
                  // 不使用Checkbox的情况下，改用图片说明当前Module是否已有设置
                  else
                  {
                     if ( checkedSelected( moduleId, selectedModuleVOs ) )
                     {
                        rs.append( "<img id=\"" + moduleId + "\" src=\"images/enable.png\" /> " );
                     }
                     else
                     {
                        rs.append( "<img id=\"" + moduleId + "\" src=\"images/empty.png\" /> " );
                     }
                  }

                  rs.append( "<a id=\"manageModule\" onclick=\"manageModule(this, '"
                        + ( ownerObjectId == null || ownerObjectId.trim().equals( "" ) ? "" : URLEncoder.encode( Cryptogram.encodeString( ownerObjectId ), "UTF-8" ) ) + "','"
                        + moduleId + "');\" class=\"kanhandle\">" );
                  rs.append( moduleName );
                  rs.append( "</a>" );
                  if ( hasCheckbox )
                  {
                     rs.append( "</input>" );
                  }
                  rs.append( "</li>" );

                  // 递归遍历存在的节点
                  rs.append( getModuleNode( request, ownerObjectId, nodeId, moduleDTO.getModuleDTOs(), selectedModuleVOs, level + 1, hasCheckbox ) );
               }

            }

            return rs.toString();
         }
         else
         {
            return "";
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 判断是否存在子节点 - Tree
   private static boolean hasTreeNode( final List< ModuleDTO > moduleDTOs )
   {
      if ( moduleDTOs != null && moduleDTOs.size() > 0 )
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   // 判断是否存在子节点 - Menu
   private static boolean hasMenuNode( final List< ModuleDTO > moduleDTOs )
   {
      if ( moduleDTOs != null && moduleDTOs.size() > 0 )
      {
         for ( ModuleDTO moduleDTO : moduleDTOs )
         {
            if ( moduleDTO.getModuleVO() != null && KANUtil.filterEmpty( moduleDTO.getModuleVO().getModuleFlag() ) != null && moduleDTO.getModuleVO().getModuleFlag() == 1 )
            {
               return true;
            }
         }
      }
      else
      {
         return false;
      }

      return false;
   }

   private static boolean hasClientNode( final List< ModuleDTO > moduleDTOs, final List< ModuleVO > clientModuleVO )
   {
      if ( moduleDTOs != null && moduleDTOs.size() > 0 )
      {
         for ( ModuleDTO moduleDTO : moduleDTOs )
         {
            if ( moduleDTO.getModuleVO() != null && moduleDTO.getModuleVO().getModuleId() != null )
            {
               for ( ModuleVO moduleVO : clientModuleVO )
               {
                  if ( StringUtils.equals( moduleVO.getModuleId(), moduleDTO.getModuleVO().getModuleId() ) )
                  {
                     return true;
                  }
               }
            }
         }
      }
      return false;
   }

   // 判断当前节点是否需要选中
   private static boolean checkedSelected( final String moduleId, final List< ModuleVO > selectedModuleVOs )
   {
      if ( selectedModuleVOs != null )
      {
         for ( ModuleVO moduleVO : selectedModuleVOs )
         {
            if ( moduleVO != null && moduleVO.getModuleId() != null && moduleVO.getModuleId().trim().equals( moduleId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   // 按照LevelId得到CSS样式
   private static String getClassNameByLevel( final int level )
   {
      if ( level == 1 )
      {
         return "firstlevel";
      }
      else if ( level == 2 )
      {
         return "secondlevel";
      }
      else if ( level == 3 )
      {
         return "thirdlevel";
      }
      else if ( level == 4 )
      {
         return "fourthlevel";
      }
      else if ( level == 5 )
      {
         return "fifthlevel";
      }
      else if ( level == 6 )
      {
         return "sixthlevel";
      }
      else if ( level == 7 )
      {
         return "seventhlevel";
      }
      else if ( level == 8 )
      {
         return "eighthlevel";
      }
      else
      {
         return "";
      }
   }

   // 根据PositionId得到整个Right和Rule的Combo
   // Code reviewed by Kevin at 2013-06-26
   public static String getAuthorityComboByPositionId( final HttpServletRequest request, final String positionId, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // 用户针对Account全局设定的规则
      List< RuleVO > accountRuleVOs = null;

      // 针对当前Position设定的规则
      List< RuleVO > selectedRuleVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // 初始化accountRuleVOs
         if ( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ) != null )
         {
            accountRuleVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ).getAccountRuleVOs();
         }
         // 初始化selectRightVOs
         if ( positionId != null && !positionId.trim().equals( "" ) )
         {
            final PositionDTO positionDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionDTOByPositionId( positionId );

            if ( positionDTO != null )
            {
               final PositionModuleDTO positionModuleDTO = positionDTO.getPositionModuleDTOByModuleId( moduleId );

               if ( positionModuleDTO != null )
               {
                  selectedRuleVOs = positionModuleDTO.getSelectedRuleVOs();
               }

            }

         }
      }

      rs.append( RightRender.getRightMultipleChoiceByPositionId( request, positionId, moduleId, false ) );
      rs.append( "<br>" );
//      rs.append( RuleRender.getRuleComboByModuleId( request, accountRuleVOs, selectedRuleVOs, moduleId ) );
//      rs.append( "<ol id=\"divSaveRightRule\" class=\"static\" style=\"display: none;\"><li style=\"margin: 8px 2px 0px 0px;\"><input type=\"button\" class=\"addbutton\" name=\"btnSaveRightRule\" id=\"btnSaveRightRule\" value=\"保存权限\" onclick=\"btnSubmit(true);\"/></li></ol>" );

      return rs.toString();
   }

   // 根据groupId得到整个Right和Rule的Combo
   // Code reviewed by Kevin at 2013-06-26
   public static String getAuthorityComboByGroupId( final HttpServletRequest request, final String groupId, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
/*
     // 用户针对Account全局设定的规则
      List< RuleVO > accountRuleVOs = null;

      // 针对当前group设定的规则
      List< RuleVO > selectedRuleVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // 初始化accountRuleVOs
         if ( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ) != null )
         {
            accountRuleVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ).getAccountRuleVOs();
         }
         // 初始化selectRightVOs
         if ( groupId != null && !groupId.trim().equals( "" ) )
         {
            final GroupDTO groupDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getGroupDTOByGroupId( groupId );

            if ( groupDTO != null )
            {
               final GroupModuleDTO groupModuleDTO = groupDTO.getGroupModuleDTOByModuleId( moduleId );

               if ( groupModuleDTO != null )
               {
                  selectedRuleVOs = groupModuleDTO.getSelectedRuleVOs();
               }
            }
         }
      }*/

      rs.append( RightRender.getRightMultipleChoiceByGroupId( request, groupId, moduleId, false ) );
      rs.append( "<br>" );
//      rs.append( RuleRender.getRuleComboByModuleId( request, accountRuleVOs, selectedRuleVOs, moduleId ) );
//      rs.append( "<ol id=\"divSaveRightRule\" class=\"static\" style=\"display: none;\"><li style=\"margin: 8px 2px 0px 0px;\"><input type=\"button\" class=\"addbutton\" name=\"btnSaveRightRule\" id=\"btnSaveRightRule\" value=\"保存权限\" onclick=\"btnSubmit(true);\"/></li></ol>" );

      return rs.toString();
   }

   // 根据AccountId得到整个Right和Rule的Combo
   // Code reviewed by Kevin at 2013-06-26
   public static String getAuthorityComboByAccountId( final HttpServletRequest request, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // 用户针对Account全局设定的规则
      List< RuleVO > accountRuleVOs = null;

      // 针对当前account设定的规则
      List< RuleVO > selectedRuleVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // 初始化selectRightVOs
         final AccountModuleDTO accountModuleDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId );

         if ( accountModuleDTO != null )
         {
            selectedRuleVOs = accountModuleDTO.getAccountRuleVOs();
         }
      }

      rs.append( RightRender.getRightMultipleChoiceByAccountId( request, null, moduleId, false ) );
      rs.append( "<br>" );
      rs.append( RuleRender.getRuleComboByModuleId( request, accountRuleVOs, selectedRuleVOs, moduleId ) );
      rs.append( "<ol id=\"divSaveRightRule\" class=\"static\" style=\"display: none;\"><li style=\"margin: 8px 2px 0px 0px;\"><input type=\"button\" class=\"addbutton\" name=\"btnSaveRightRule\" id=\"btnSaveRightRule\" value=\"保存权限\" onclick=\"btnSubmit(true);\"/></li></ol>" );

      return rs.toString();
   }

   // 得到整个Module的Menu
   // Code reviewed by Kevin at 2013-06-25
   public static String getModuleMenu( final HttpServletRequest request ) throws KANException
   {
      final StaffDTO staffDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getStaffDTOByStaffId( BaseAction.getStaffId( request, null ) );

      // 遍历ModuleDTO
      return getModuleMenu( request, staffDTO.getModuleDTOs() );
   }

   /**
    * 递归方法 - 遍历生成模块菜单
    * 
    * request - HttpServletRequest
    * moduleDTOs - 模块（已按照层级结构）
    */
   // Updated by Siuxia at 2013-12-24
   // Code reviewed by Kevin at 2013-06-25
   private static String getModuleMenu( final HttpServletRequest request, final List< ModuleDTO > moduleDTOs ) throws KANException
   {
      if ( hasMenuNode( moduleDTOs ) )
      {
         final StringBuffer rs = new StringBuffer();

         final String accountId = BaseAction.getAccountId( request, null );
         final UserVO user = BaseAction.getUserVOFromClient( request, null );
         KANAccountConstants kanAccountConstants = KANConstants.getKANAccountConstants( accountId );

         for ( ModuleDTO moduleDTO : moduleDTOs )
         {
            if ( moduleDTO == null || moduleDTO.getModuleVO() == null )
            {
               continue;
            }

            if ( moduleDTO.getModuleVO().getModuleFlag() == 1
                  && ( kanAccountConstants.hasMenuAuthority( user, moduleDTO, AuthConstants.RIGHT_VIEW ) || KANUtil.filterEmpty( moduleDTO.getModuleVO().getModuleId() ) == null ) )
            {

               String moduleName = "";
               String className = "";
               String defaultAction = "";
               String onclick = "";

               // 按照语言设置取Title
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  if ( BaseAction.getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) )
                  {
                     moduleName = moduleDTO.getModuleVO().getNameZH();
                  }
                  else
                  {
                     moduleName = moduleDTO.getModuleVO().getTitleZH();
                  }
               }
               else
               {
                  if ( BaseAction.getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) )
                  {
                     moduleName = moduleDTO.getModuleVO().getNameEN();
                  }
                  else
                  {
                     moduleName = moduleDTO.getModuleVO().getTitleEN();
                  }
               }

               // 设置当前菜单的样式
               className = hasMenuNode( moduleDTO.getModuleDTOs() ) ? "class=\"arrow\"" : "";

               // 如果模块ID为空则是报表
               if ( KANUtil.filterEmpty( moduleDTO.getModuleVO().getModuleId() ) == null )
               {
                  if ( KANUtil.filterEmpty( moduleDTO.getModuleVO().getDefaultAction() ) != null )
                  {
                     final JSONObject jsonObject = JSONObject.fromObject( moduleDTO.getModuleVO().getDefaultAction() );
                     rs.append( "<li id=\"" + moduleDTO.getModuleVO().getModuleName() + "\">" );
                     onclick = "onclick=\"link('" + getModuleLink( jsonObject ) + "');\"";
                     rs.append( "<a href=\"#\" " + className + " " + onclick + ">" + moduleName + "</a>" );
                     rs.append( "</li>" );
                  }
               }
               else
               {
                  // 设置当前菜单的默认Action
                  defaultAction = moduleDTO.getModuleVO().getDefaultAction() != null && !moduleDTO.getModuleVO().getDefaultAction().trim().equals( "" ) ? "onclick=\"link('"
                        + moduleDTO.getModuleVO().getDefaultAction() + "');\"" : "";

                  final ModuleVO moduleVO = moduleDTO.getModuleVO();
                  rs.append( "<li id=\"" + moduleVO.getModuleName() + "\"><a href=\"#\" " + className + " " + defaultAction + ">" + moduleName + "</a>" );
               }

               // 递归遍历存在的节点
               if ( hasMenuNode( moduleDTO.getModuleDTOs() ) )
               {
                  rs.append( "<ul>" );
                  rs.append( getModuleMenu( request, moduleDTO.getModuleDTOs() ) );
                  rs.append( "</ul>" );
               }
               rs.append( "</li>" );

            }
         }

         return rs.toString();
      }
      else
      {
         return "";
      }
   }

   private static String getModuleLink( final JSONObject jsonObject )
   {
      // 初始化报表链接
      String reportLink = "reportHeaderAction.do?proc=execute_object";

      if ( jsonObject != null )
      {
         for ( Object key : jsonObject.keySet() )
         {
            reportLink = reportLink + "&" + String.valueOf( key ) + "=" + jsonObject.get( key );
         }
      }

      return reportLink;
   }

   public static String getClientModuleMenu( final HttpServletRequest request ) throws KANException
   {
      String clientId = BaseAction.getClientId( request, null );
      String accountId = BaseAction.getAccountId( request, null );
      String role = BaseAction.getRole( request, null );
      List< ModuleDTO > golbDTO = new ArrayList< ModuleDTO >();
      List< ModuleVO > clientModule = new ArrayList< ModuleVO >();
      if ( KANConstants.ROLE_CLIENT.equals( role ) )
      {
         golbDTO = KANConstants.CLIENT_MODULE_DTO;
         clientModule = ( List< ModuleVO > ) CachedUtil.get( request, "CLIENT_MODULE_" + clientId );
      }
      if ( KANConstants.ROLE_EMPLOYEE.equals( role ) )
      {
         golbDTO = KANConstants.EMPLOYEE_CLIENT_MODULE_DTO;
         clientModule = ( List< ModuleVO > ) CachedUtil.get( request, "EMPLOYEE_CLIENT_MODULE_" + clientId );
      }
      if ( clientModule == null )
      {
         final ModuleService moduleService = ( ModuleService ) BaseAction.getService( "moduleService" );
         clientModule = moduleService.getClientModuleVOs( accountId, clientId, role );
         if ( clientModule != null )
         {
            if ( KANConstants.ROLE_CLIENT.equals( role ) )
            {
               BaseAction.setObjectToSession( request, "CLIENT_MODULE_" + clientId, clientModule );
            }
            if ( KANConstants.ROLE_EMPLOYEE.equals( role ) )
            {
               BaseAction.setObjectToSession( request, "EMPLOYEE_CLIENT_MODULE_" + clientId, clientModule );
            }
         }
      }
      return getClientMenuNode( request, golbDTO, clientModule, clientId );
   }

   public static String getClientTree( final HttpServletRequest request, final HttpServletResponse response, boolean globFlag, final String role, final String clientId )
         throws KANException
   {

      String accountId = BaseAction.getAccountId( request, response );
      final ModuleService moduleService = ( ModuleService ) BaseAction.getService( "moduleService" );
      List< ModuleDTO > golbDTO = new ArrayList< ModuleDTO >();
      List< ModuleVO > clientModule = new ArrayList< ModuleVO >();
      if ( KANConstants.ROLE_CLIENT.equals( role ) )
      {
         golbDTO = KANConstants.CLIENT_MODULE_DTO;
         clientModule = moduleService.getClientModuleVOs( accountId, clientId, role );
      }
      if ( KANConstants.ROLE_EMPLOYEE.equals( role ) )
      {
         golbDTO = KANConstants.EMPLOYEE_CLIENT_MODULE_DTO;
         clientModule = moduleService.getClientModuleVOs( accountId, clientId, role );
      }
      return getClientTreeNode( request, "", golbDTO, clientModule, 1, globFlag, role );
   }

   private static String getClientMenuNode( final HttpServletRequest request, final List< ModuleDTO > moduleDTOs, final List< ModuleVO > clientModuleVO, final String clientId )
         throws KANException
   {
      if ( clientModuleVO == null || ( clientModuleVO != null && clientModuleVO.size() == 0 ) )
      {
         return "";
      }
      if ( hasClientNode( moduleDTOs, clientModuleVO ) )
      {
         final StringBuffer rs = new StringBuffer();
         for ( ModuleDTO moduleDTO : moduleDTOs )
         {
            if ( moduleDTO == null || moduleDTO.getModuleVO() == null )
            {
               continue;
            }

            String voClientId = getCheckedSelectModuleClientId( moduleDTO.getModuleVO().getModuleId(), clientModuleVO );
            if ( !( "".equals( voClientId ) || StringUtils.equals( voClientId, clientId ) ) )
            {
               continue;
            }

            String moduleName = "";
            String className = "";
            String defaultAction = "";
            String onclick = "";

            // 按照语言设置取Title
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               moduleName = moduleDTO.getModuleVO().getNameZH();
            }
            else
            {
               moduleName = moduleDTO.getModuleVO().getTitleEN();
            }

            // 设置当前菜单的样式
            className = hasClientNode( moduleDTO.getModuleDTOs(), clientModuleVO ) ? "class=\"arrow\"" : "";

            // 如果模块ID为空则是报表
            if ( KANUtil.filterEmpty( moduleDTO.getModuleVO().getModuleId() ) == null )
            {
               if ( KANUtil.filterEmpty( moduleDTO.getModuleVO().getDefaultAction() ) != null )
               {
                  final JSONObject jsonObject = JSONObject.fromObject( moduleDTO.getModuleVO().getDefaultAction() );
                  rs.append( "<li id=\"" + moduleDTO.getModuleVO().getModuleName() + "\">" );
                  onclick = "onclick=\"link('" + getModuleLink( jsonObject ) + "');\"";
                  rs.append( "<a href=\"#\" " + className + " " + onclick + ">" + moduleName + "</a>" );
                  rs.append( "</li>" );
               }
            }
            else
            {
               // 设置当前菜单的默认Action
               defaultAction = moduleDTO.getModuleVO().getDefaultAction() != null && !moduleDTO.getModuleVO().getDefaultAction().trim().equals( "" ) ? "onclick=\"link('"
                     + moduleDTO.getModuleVO().getDefaultAction() + "');\"" : "";

               final ModuleVO moduleVO = moduleDTO.getModuleVO();
               rs.append( "<li id=\"" + moduleVO.getModuleName() + "\"><a href=\"#\" " + className + " " + defaultAction + ">" + moduleName + "</a>" );
            }

            // 递归遍历存在的节点
            if ( hasClientNode( moduleDTO.getModuleDTOs(), clientModuleVO ) )
            {
               rs.append( "<ul>" );
               rs.append( getClientMenuNode( request, moduleDTO.getModuleDTOs(), clientModuleVO, clientId ) );
               rs.append( "</ul>" );
            }
            rs.append( "</li>" );
         }

         return rs.toString();
      }
      else
      {
         return "";
      }
   }

   private static String getClientTreeNode( final HttpServletRequest request, final String parentNodeId, final List< ModuleDTO > moduleDTOs,
         final List< ModuleVO > selectedModuleVOs, final int level, final boolean globFlag, final String role ) throws KANException
   {
      try
      {
         if ( hasTreeNode( moduleDTOs ) )
         {
            final StringBuffer rs = new StringBuffer();
            String fileName = KANConstants.ROLE_EMPLOYEE.equals( role ) ? "employeeModuleIdArray" : "moduleIdArray";

            for ( ModuleDTO moduleDTO : moduleDTOs )
            {
               final String moduleId = moduleDTO.getModuleVO().getModuleId();
               final String nodeId = parentNodeId.concat( "_N" ).concat( moduleId );
               String moduleName = "";

               // 按照语言设置取Title
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  moduleName = moduleDTO.getModuleVO().getNameZH();
               }
               else
               {
                  moduleName = moduleDTO.getModuleVO().getNameEN();
               }

               // 生成根节点
               rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
               rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasTreeNode( moduleDTO.getModuleDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                     + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\" /> " );

               //判断是否选择
               if ( getCheckedSelectModuleClientId( moduleId, selectedModuleVOs ) != null )
               {
                  //如果是全局设定的checkbox
                  String voClientId = getCheckedSelectModuleClientId( moduleId, selectedModuleVOs );
                  if ( "".equals( voClientId ) )
                  {
                     //针对于client页面
                     if ( !globFlag )
                     {
                        rs.append( "<img src=\"images/selected.png\">" );
                     }
                     else
                     {
                        rs.append( "<input type=\"checkbox\" name=\""+fileName+"\" id=\""+fileName+"\" value=\"" + moduleId + "\" onclick=\"manageModule(this, '','" + moduleId
                              + "');\" checked>" );
                     }

                  }
                  else
                  //如果是局部设定的checkbox
                  {
                     //针对于client页面
                     if ( !globFlag )
                     {
                        rs.append( "<input type=\"checkbox\" name=\""+fileName+"\" id=\""+fileName+"\" value=\"" + moduleId + "\" onclick=\"manageModule(this, '','" + moduleId
                              + "');\" checked>" );
                     }
                     else
                     {
                        rs.append( "<input type=\"checkbox\" name=\""+fileName+"\" id=\""+fileName+"\" value=\"" + moduleId + "\" onclick=\"manageModule(this, '','" + moduleId
                              + "');\">" );
                     }
                  }

               }
               else
               {
                  rs.append( "<input type=\"checkbox\" name=\""+fileName+"\" id=\""+fileName+"\" value=\"" + moduleId + "\" onclick=\"manageModule(this, '','" + moduleId
                        + "');\">" );
               }

               rs.append( "<a id=\"manageModule\" onclick=\"manageModule(this, '','" + moduleId + "');\" class=\"kanhandle\">" );
               rs.append( moduleName );
               rs.append( "</a>" );
               rs.append( "</input>" );
               rs.append( "</li>" );

               // 递归遍历存在的节点
               rs.append( getClientTreeNode( request, nodeId, moduleDTO.getModuleDTOs(), selectedModuleVOs, level + 1, globFlag, role ) );

            }
            return rs.toString();
         }
         else
         {
            return "";
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   private static String getCheckedSelectModuleClientId( final String moduleId, final List< ModuleVO > selectedModuleVOs )
   {
      if ( selectedModuleVOs != null )
      {
         for ( ModuleVO moduleVO : selectedModuleVOs )
         {
            if ( moduleVO != null && moduleVO.getModuleId() != null && moduleVO.getModuleId().trim().equals( moduleId ) )
            {
               if ( StringUtils.isEmpty( moduleVO.getClientId() ) )
               {
                  return "";
               }
               else
               {
                  return moduleVO.getClientId();
               }
            }
         }
      }

      return null;
   }
}
