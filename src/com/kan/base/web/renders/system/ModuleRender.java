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
   // �õ�����Module��Tree - ���յ�¼������ȫ�ֹ�������
   // Code reviewed by Kevin at 2013-06-26
   public static String getModuleTree( final HttpServletRequest request, final boolean hasCheckbox ) throws KANException
   {
      // ϵͳģ����
      final List< ModuleDTO > moduleDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getModuleDTOs();
      // ��Ե�ǰPosition�趨��Ȩ��
      final List< ModuleVO > selectedModuleVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSelectedModuleVOs();

      // ����ModuleDTO
      return getModuleNode( request, "", "", moduleDTOs, selectedModuleVOs, 1, hasCheckbox );
   }

   // �õ�����Module��Tree - ������ѡ���Module
   public static String getModuleTreeBySelectedModules( final HttpServletRequest request, final boolean hasCheckbox, final List< ModuleVO > selectedModuleVOs ) throws KANException
   {
      // ϵͳģ����
      final List< ModuleDTO > moduleDTOs = KANConstants.MODULE_DTO;

      // ����ModuleDTO
      return getModuleNode( request, "", "", moduleDTOs, selectedModuleVOs, 1, hasCheckbox );
   }

   // �õ�����Module��Tree - ����AccountId
   // Code reviewed by Kevin at 2013-06-26
   public static String getModuleTreeByAccountId( final HttpServletRequest request, final boolean hasCheckbox, final String accountId ) throws KANException
   {
      // ϵͳģ����
      final List< ModuleDTO > moduleDTOs = KANConstants.MODULE_DTO;
      // ��Ե�ǰPosition�趨��Ȩ��
      final List< ModuleVO > selectedModuleVOs = ( accountId != null && KANConstants.getKANAccountConstants( accountId ) != null ? KANConstants.getKANAccountConstants( accountId ).getModuleVOs()
            : null );

      // ����ModuleDTO
      return getModuleNode( request, "", "", moduleDTOs, selectedModuleVOs, 1, hasCheckbox );
   }

   // �õ�����Module��Tree - �����趨�Ƿ���Checkbox
   // Code reviewed by Kevin at 2013-06-26
   public static String getModuleTreeByPositionId( final HttpServletRequest request, final boolean hasCheckbox, final String positionId ) throws KANException
   {
      // ���������Ч��Module��
      final List< ModuleDTO > moduleDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getModuleDTOs();

      // ���ȫ���趨����Module�б�
      List< ModuleVO > moduleVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSelectedModuleVOs();

      // ����PositionId���PositionDTO
      final PositionDTO positionDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionDTOByPositionId( positionId );

      // ���Position���趨����Module�б�
      if ( positionDTO != null && positionDTO.getModuleVOs() != null && positionDTO.getModuleVOs().size() > 0 )
      {
         if ( moduleVOs == null )
         {
            moduleVOs = new ArrayList< ModuleVO >();
         }

         moduleVOs.addAll( positionDTO.getModuleVOs() );
      }

      // ����ModuleDTO
      return getModuleNode( request, positionId, "", moduleDTOs, moduleVOs, 1, hasCheckbox );
   }

   // �õ�����Module��Tree - �����趨�Ƿ���Checkbox
   // Code reviewed by Kevin at 2013-06-26
   public static String getModuleTreeByGroupId( final HttpServletRequest request, final boolean hasCheckbox, final String groupId ) throws KANException
   {
      // ���������Ч��Module��
      final List< ModuleDTO > moduleDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getModuleDTOs();

      // ���ȫ���趨����Module�б�
      List< ModuleVO > moduleVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSelectedModuleVOs();

      // ����GroupId���GroupDTO
      final GroupDTO groupDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getGroupDTOByGroupId( groupId );

      // ���Group���趨����Module�б�
      if ( groupDTO != null && groupDTO.getModuleVOs() != null && groupDTO.getModuleVOs().size() > 0 )
      {
         if ( moduleVOs == null )
         {
            moduleVOs = new ArrayList< ModuleVO >();
         }
         moduleVOs.addAll( groupDTO.getModuleVOs() );
      }

      // ����ModuleDTO
      return getModuleNode( request, groupId, "", moduleDTOs, moduleVOs, 1, hasCheckbox );
   }

   /**
    * �ݹ鷽�� - ��������ģ����
    * 
    * request - HttpServletRequest
    * ownerObjectId - ��ǰModule Ownwe��ID
    * parentNodeId - ���ڵ�
    * moduleDTOs - ģ�飨�Ѱ��ղ㼶�ṹ��
    * selectedModuleVOs - ��ǰ��ҪĬ��ѡ�е�ģ��
    * level - ��ǰ�㼶
    * hasCheckbox - �Ƿ���Ҫ��Checkbox
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

               // �����HRO����
               if ( BaseAction.getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) )
               {
                  // �����HRM�˵�����ʾ
                  if ( moduleDTO.getModuleVO().getRole().equals( KANConstants.ROLE_IN_HOUSE ) && !( BaseAction.getAccountId( request, null ) ).equals( "1" ) )
                  {
                     continue;
                  }

                  final String moduleId = moduleDTO.getModuleVO().getModuleId();
                  final String nodeId = parentNodeId.concat( "_N" ).concat( moduleId );
                  String moduleName = "";

                  // ������������ȡTitle
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     moduleName = moduleDTO.getModuleVO().getNameZH();
                  }
                  else
                  {
                     moduleName = moduleDTO.getModuleVO().getNameEN();
                  }

                  // ���ɸ��ڵ�
                  rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
                  rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasTreeNode( moduleDTO.getModuleDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                        + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\" /> " );

                  // ��Ҫʹ��Checkbox
                  if ( hasCheckbox )
                  {
                     rs.append( "<input type=\"checkbox\" name=\"moduleIdArray\" id=\"moduleIdArray\" value=\"" + moduleId + "\" onclick=\"manageModule(this, '"
                           + ( ownerObjectId == null ? "" : ownerObjectId ) + "','" + moduleId + "');\" " + ( checkedSelected( moduleId, selectedModuleVOs ) ? "checked" : "" )
                           + ">" );
                  }
                  // ��ʹ��Checkbox������£�����ͼƬ˵����ǰModule�Ƿ���������
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

                  // �ݹ�������ڵĽڵ�
                  rs.append( getModuleNode( request, ownerObjectId, nodeId, moduleDTO.getModuleDTOs(), selectedModuleVOs, level + 1, hasCheckbox ) );
               }
               // �����HRM����
               else if ( BaseAction.getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
               {
                  // �����HRO�˵�����ʾ
                  if ( moduleDTO.getModuleVO().getRole().equals( KANConstants.ROLE_HR_SERVICE ) )
                  {
                     continue;
                  }

                  final String moduleId = moduleDTO.getModuleVO().getModuleId();
                  final String nodeId = parentNodeId.concat( "_N" ).concat( moduleId );
                  String moduleName = "";

                  // ������������ȡTitle
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     moduleName = moduleDTO.getModuleVO().getTitleZH();
                  }
                  else
                  {
                     moduleName = moduleDTO.getModuleVO().getTitleEN();
                  }

                  // ���ɸ��ڵ�
                  rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
                  rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasTreeNode( moduleDTO.getModuleDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                        + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\" /> " );

                  // ��Ҫʹ��Checkbox
                  if ( hasCheckbox )
                  {
                     rs.append( "<input type=\"checkbox\" name=\"moduleIdArray\" id=\"moduleIdArray\" value=\"" + moduleId + "\" onclick=\"manageModule(this, '"
                           + ( ownerObjectId == null ? "" : ownerObjectId ) + "','" + moduleId + "');\" " + ( checkedSelected( moduleId, selectedModuleVOs ) ? "checked" : "" )
                           + ">" );
                  }
                  // ��ʹ��Checkbox������£�����ͼƬ˵����ǰModule�Ƿ���������
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

                  // �ݹ�������ڵĽڵ�
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

   // �ж��Ƿ�����ӽڵ� - Tree
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

   // �ж��Ƿ�����ӽڵ� - Menu
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

   // �жϵ�ǰ�ڵ��Ƿ���Ҫѡ��
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

   // ����LevelId�õ�CSS��ʽ
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

   // ����PositionId�õ�����Right��Rule��Combo
   // Code reviewed by Kevin at 2013-06-26
   public static String getAuthorityComboByPositionId( final HttpServletRequest request, final String positionId, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // �û����Accountȫ���趨�Ĺ���
      List< RuleVO > accountRuleVOs = null;

      // ��Ե�ǰPosition�趨�Ĺ���
      List< RuleVO > selectedRuleVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // ��ʼ��accountRuleVOs
         if ( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ) != null )
         {
            accountRuleVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ).getAccountRuleVOs();
         }
         // ��ʼ��selectRightVOs
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
//      rs.append( "<ol id=\"divSaveRightRule\" class=\"static\" style=\"display: none;\"><li style=\"margin: 8px 2px 0px 0px;\"><input type=\"button\" class=\"addbutton\" name=\"btnSaveRightRule\" id=\"btnSaveRightRule\" value=\"����Ȩ��\" onclick=\"btnSubmit(true);\"/></li></ol>" );

      return rs.toString();
   }

   // ����groupId�õ�����Right��Rule��Combo
   // Code reviewed by Kevin at 2013-06-26
   public static String getAuthorityComboByGroupId( final HttpServletRequest request, final String groupId, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
/*
     // �û����Accountȫ���趨�Ĺ���
      List< RuleVO > accountRuleVOs = null;

      // ��Ե�ǰgroup�趨�Ĺ���
      List< RuleVO > selectedRuleVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // ��ʼ��accountRuleVOs
         if ( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ) != null )
         {
            accountRuleVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ).getAccountRuleVOs();
         }
         // ��ʼ��selectRightVOs
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
//      rs.append( "<ol id=\"divSaveRightRule\" class=\"static\" style=\"display: none;\"><li style=\"margin: 8px 2px 0px 0px;\"><input type=\"button\" class=\"addbutton\" name=\"btnSaveRightRule\" id=\"btnSaveRightRule\" value=\"����Ȩ��\" onclick=\"btnSubmit(true);\"/></li></ol>" );

      return rs.toString();
   }

   // ����AccountId�õ�����Right��Rule��Combo
   // Code reviewed by Kevin at 2013-06-26
   public static String getAuthorityComboByAccountId( final HttpServletRequest request, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // �û����Accountȫ���趨�Ĺ���
      List< RuleVO > accountRuleVOs = null;

      // ��Ե�ǰaccount�趨�Ĺ���
      List< RuleVO > selectedRuleVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // ��ʼ��selectRightVOs
         final AccountModuleDTO accountModuleDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId );

         if ( accountModuleDTO != null )
         {
            selectedRuleVOs = accountModuleDTO.getAccountRuleVOs();
         }
      }

      rs.append( RightRender.getRightMultipleChoiceByAccountId( request, null, moduleId, false ) );
      rs.append( "<br>" );
      rs.append( RuleRender.getRuleComboByModuleId( request, accountRuleVOs, selectedRuleVOs, moduleId ) );
      rs.append( "<ol id=\"divSaveRightRule\" class=\"static\" style=\"display: none;\"><li style=\"margin: 8px 2px 0px 0px;\"><input type=\"button\" class=\"addbutton\" name=\"btnSaveRightRule\" id=\"btnSaveRightRule\" value=\"����Ȩ��\" onclick=\"btnSubmit(true);\"/></li></ol>" );

      return rs.toString();
   }

   // �õ�����Module��Menu
   // Code reviewed by Kevin at 2013-06-25
   public static String getModuleMenu( final HttpServletRequest request ) throws KANException
   {
      final StaffDTO staffDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getStaffDTOByStaffId( BaseAction.getStaffId( request, null ) );

      // ����ModuleDTO
      return getModuleMenu( request, staffDTO.getModuleDTOs() );
   }

   /**
    * �ݹ鷽�� - ��������ģ��˵�
    * 
    * request - HttpServletRequest
    * moduleDTOs - ģ�飨�Ѱ��ղ㼶�ṹ��
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

               // ������������ȡTitle
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

               // ���õ�ǰ�˵�����ʽ
               className = hasMenuNode( moduleDTO.getModuleDTOs() ) ? "class=\"arrow\"" : "";

               // ���ģ��IDΪ�����Ǳ���
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
                  // ���õ�ǰ�˵���Ĭ��Action
                  defaultAction = moduleDTO.getModuleVO().getDefaultAction() != null && !moduleDTO.getModuleVO().getDefaultAction().trim().equals( "" ) ? "onclick=\"link('"
                        + moduleDTO.getModuleVO().getDefaultAction() + "');\"" : "";

                  final ModuleVO moduleVO = moduleDTO.getModuleVO();
                  rs.append( "<li id=\"" + moduleVO.getModuleName() + "\"><a href=\"#\" " + className + " " + defaultAction + ">" + moduleName + "</a>" );
               }

               // �ݹ�������ڵĽڵ�
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
      // ��ʼ����������
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

            // ������������ȡTitle
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               moduleName = moduleDTO.getModuleVO().getNameZH();
            }
            else
            {
               moduleName = moduleDTO.getModuleVO().getTitleEN();
            }

            // ���õ�ǰ�˵�����ʽ
            className = hasClientNode( moduleDTO.getModuleDTOs(), clientModuleVO ) ? "class=\"arrow\"" : "";

            // ���ģ��IDΪ�����Ǳ���
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
               // ���õ�ǰ�˵���Ĭ��Action
               defaultAction = moduleDTO.getModuleVO().getDefaultAction() != null && !moduleDTO.getModuleVO().getDefaultAction().trim().equals( "" ) ? "onclick=\"link('"
                     + moduleDTO.getModuleVO().getDefaultAction() + "');\"" : "";

               final ModuleVO moduleVO = moduleDTO.getModuleVO();
               rs.append( "<li id=\"" + moduleVO.getModuleName() + "\"><a href=\"#\" " + className + " " + defaultAction + ">" + moduleName + "</a>" );
            }

            // �ݹ�������ڵĽڵ�
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

               // ������������ȡTitle
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  moduleName = moduleDTO.getModuleVO().getNameZH();
               }
               else
               {
                  moduleName = moduleDTO.getModuleVO().getNameEN();
               }

               // ���ɸ��ڵ�
               rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
               rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasTreeNode( moduleDTO.getModuleDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                     + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\" /> " );

               //�ж��Ƿ�ѡ��
               if ( getCheckedSelectModuleClientId( moduleId, selectedModuleVOs ) != null )
               {
                  //�����ȫ���趨��checkbox
                  String voClientId = getCheckedSelectModuleClientId( moduleId, selectedModuleVOs );
                  if ( "".equals( voClientId ) )
                  {
                     //�����clientҳ��
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
                  //����Ǿֲ��趨��checkbox
                  {
                     //�����clientҳ��
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

               // �ݹ�������ڵĽڵ�
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
