package com.kan.base.web.renders.security;

import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class PositionRender
{
   // 得到整个Position的Tree  树状
   public static String getPositionTree( final HttpServletRequest request ) throws KANException
   {
      // 从Constants中得到当前Account的PositionDTO的列表
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;

      final StringBuffer rs = new StringBuffer();

      if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
      {
         rs.append( "<label style='font-size: 13px;'>“点击职位名”查看或编辑职位信息，点击<img src=\"images/add.png\" />可添加下级职位。" );
         rs.append( "（<font color='#086912'>“绿色字体”</font>表示编制未满，<font color='#782918'>“红色字体”</font>表示超出编制，<span class='deleted'>“红色背景”</span>表示职位已停用。）</label>" );
      }
      else
      {
         rs.append( "<label style='font-size: 13px;'>Click the name of position could check or edit the position information, click <img src=\"images/add.png\" /> the could add the new sub-position." );
         rs.append( "(<font color='#086912'>Green Font</font> means the HC is availiable, <font color='#782918'>Red Font</font> means the HC is full, <span class='deleted'>Red background</span> means the position is cancelled.)</label>" );
      }

      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // 遍历PositionDTO
         rs.append( getPositionNode( request.getLocale(), "", positionDTOs, 1, true, null, null, BaseAction.getCorpId( request, null ) ) );
      }
      else
      {
         // 遍历PositionDTO
         rs.append( getPositionNode( request.getLocale(), "", positionDTOs, 1, true, null, null, null ) );
      }

      return rs.toString();
   }

   /**
    * 架构图版
    * @param request
    * @param positionDTOs
    * @return
    * @throws KANException
    */
   public static String getPositionTree( final HttpServletRequest request, final List< PositionDTO > positionDTOs, final boolean isShoot ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
      {
         rs.append( "<label style='font-size: 13px;'>“点击职位名”查看或编辑职位信息，点击<img src=\"images/add.png\" />可添加下级职位。" );
         rs.append( "（<font color='#086912'>“绿色字体”</font>表示编制未满，<font color='#782918'>“红色字体”</font>表示超出编制，<span class='deleted'>“红色背景”</span>表示职位已停用。）</label>" );
      }
      else
      {
         rs.append( "<label style='font-size: 13px;'>Click the name of position could check or edit the position information, click <img src=\"images/add.png\" /> the could add the new sub-position." );
         rs.append( "(<font color='#086912'>Green Font</font> means the HC is availiable, <font color='#782918'>Red Font</font> means the HC is full, <span class='deleted'>Red background</span> means the position is cancelled.)</label>" );
      }

      final String shootId = request.getParameter( "shootId" );
      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // 遍历PositionDTO
         refreshDTO( positionDTOs, request );
         rs.append( getNode( request.getLocale(), "", positionDTOs, 1, true, null, null, BaseAction.getCorpId( request, null ), BaseAction.getAccountId( request, null ), isShoot, shootId ) );
      }
      else
      {
         // 遍历PositionDTO
         refreshDTO( positionDTOs, request );
         rs.append( getNode( request.getLocale(), "", positionDTOs, 1, true, null, null, null, BaseAction.getAccountId( request, null ), isShoot, shootId ) );
      }

      return rs.toString();
   }

   // 按照用户绑定的职位，得到整个Position的Tree
   public static String getPositionTreeByStaffId( final HttpServletRequest request, final String staffId ) throws KANException
   {
      // 从Constants中得到当前Account的PositionDTO的列表
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;

      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // 遍历PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "STAFF", staffId, BaseAction.getCorpId( request, null ) );
      }
      else
      {
         // 遍历PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "STAFF", staffId, null );
      }

   }

   // 按照职组绑定的职位，得到整个Position的Tree
   public static String getPositionTreeByGroupId( final HttpServletRequest request, final String groupId ) throws KANException
   {
      // 从Constants中得到当前Account的PositionDTO的列表
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;

      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // 遍历PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "GROUP", groupId, BaseAction.getCorpId( request, null ) );
      }
      else
      {
         // 遍历PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "GROUP", groupId, null );
      }

      // 遍历PositionDTO
      // return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "GROUP", groupId, null );
   }

   // 按jason形式字符串，得到整个Position的Tree
   public static String getPositionTreeByJsonString( final HttpServletRequest request, final String jsonString ) throws KANException
   {
      // 从Constants中得到当前Account的PositionDTO的列表
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;
      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // 遍历PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "JSON", jsonString, BaseAction.getCorpId( request, null ) );
      }
      else
      {
         // 遍历PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "JSON", jsonString, null );
      }
      // 遍历PositionDTO
      // return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "JSON", jsonString, null );
   }

   /**
    * 递归方法 - 遍历生成职位树
    * 
    * locale - 语言
    * parentNodeId - 父节点
    * positionDTOs - 职位（已按照层级结构）
    * level - 当前层级
    * full - 是否需要显示全功能树
    * flag - 分辨当前显示STAFF还是GROUP
    * bindId - 当前职位树绑定ID（可以是Staff Id，也可以是Group Id）
    */
   private static String getPositionNode( final Locale locale, final String parentNodeId, final List< PositionDTO > positionDTOs, final int level, final boolean full,
         final String flag, final String bindId, final String corpId ) throws KANException
   {
      if ( hasNode( positionDTOs ) )
      {
         final StringBuffer rs = new StringBuffer();

         for ( PositionDTO positionDTO : positionDTOs )
         {
            // 如果是INhouse
            if ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() ) == null )
            {
               continue;
            }
            // 如果是HRService
            if ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() ) != null )
            {
               continue;
            }
            final String accountId = positionDTO.getPositionVO().getAccountId();
            final String positionId = positionDTO.getPositionVO().getPositionId();
            final String encodedPositionId = positionDTO.getPositionVO().getEncodedId();
            final String nodeId = parentNodeId.concat( "_N" ).concat( positionId );
            final String tempCorpId = positionDTO.getPositionVO().getCorpId();
            String positionName = "";
            Boolean checked = false;
            String tips = "";
            String positionStatus_class = "";
            String checkboxDisplay = "";

            // 按照语言设置取Title
            if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               positionName = positionDTO.getPositionVO().getTitleZH();
            }
            else
            {
               positionName = positionDTO.getPositionVO().getTitleEN();
            }
            // 如果当前的状态为停用
            if ( "2".equals( positionDTO.getPositionVO().getStatus() ) )
            {
               positionStatus_class = "deleted";
            }

            // 按照Flag确定当前节点是否需要选中
            if ( flag != null && flag.trim().equals( "STAFF" ) )
            {
               checked = checkWithStaff( bindId, positionDTO.getPositionStaffRelationVOs() );
            }
            else if ( flag != null && flag.trim().equals( "GROUP" ) )
            {
               checked = checkWithGroup( bindId, positionDTO.getPositionGroupRelationVOs() );
            }
            else if ( flag != null && flag.trim().equals( "JSON" ) )
            {
               checked = checkWithArray( bindId, positionId );
            }
            else if ( flag != null && flag.trim().equals( "EMPLOYEE" ) )
            {
               // TODO
            }

            // 生成职位显示所需的备注信息 - 职位组
            if ( positionDTO.getPositionGroupRelationVOs() != null && positionDTO.getPositionGroupRelationVOs().size() > 0 )
            {
               tips = tips + " - " + positionDTO.getPositionGroupRelationVOs().size() + ( locale.getLanguage().equalsIgnoreCase( "ZH" ) ? "个" : "" )
                     + KANUtil.getProperty( locale, "security.position.group" );
            }

            // 生成职位显示所需的备注信息 - 员工
            final String employeeInfo = KANConstants.getKANAccountConstants( accountId ).get3StaffNamesByPositionId( locale.getLanguage(), positionId );
            if ( KANUtil.filterEmpty( employeeInfo ) != null )
            {
               tips = tips + " - " + employeeInfo;
            }

            if ( tips != null && !tips.isEmpty() )
            {
               if ( full )
               {
                  checkboxDisplay = "style=\"display:none;\"";
               }
            }

            if ( ( corpId == null && KANUtil.filterEmpty( tempCorpId ) == null ) || ( corpId != null && tempCorpId != null && tempCorpId.equals( corpId ) ) )
            {
               final int staffNum = KANConstants.getKANAccountConstants( accountId ).getStaffNumByPositionId( locale.getLanguage(), positionId );
               String fontColor = "";
               if ( KANUtil.filterEmpty( positionDTO.getPositionVO().getIsVacant() ) != null )
               {
                  final int isVacant = Integer.parseInt( positionDTO.getPositionVO().getIsVacant() );
                  if ( isVacant != 0 && staffNum < isVacant )
                  {
                     // 绿色086912 782918
                     fontColor = "style='color:#086912'";
                  }
                  else if ( isVacant != 0 && staffNum > isVacant )
                  {
                     // 红色
                     fontColor = "style='color:#782918'";
                  }
               }
               String liClass = positionDTO.getPositionDTOs().size() > 0 ? "" : "independent";
               // 生成根节点
               rs.append( "<li id=\"" + nodeId + "\" class=\"" + liClass + " " + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
               rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasNode( positionDTO.getPositionDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                     + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\"/> " );
               rs.append( "<input type=\"checkbox\" name=\"positionIdArray\" id=\"positionIdArray\" value=\"" + positionId + "\"" + ( checked ? ( "checked" ) : ( "" ) ) + " "
                     + checkboxDisplay + " /><a onclick=\"managePosition('"

                     + encodedPositionId + "'); \"><span class='" + positionStatus_class + "' " + fontColor + ">" + positionName + "</span></a>" );

               // 如果是职位管理，增加职位管理的按钮
               if ( flag == null )
               {
                  rs.append( "<img src=\"images/add.png\" onclick=\"link('positionAction.do?proc=to_objectNew&tempPositionPrefer='+$('#tempPositionPrefer').val()+'&rootPositionId='+$('#rootPositionId').val()+'&parentPositionId="
                        + encodedPositionId + "');\" /> " );
               }

               rs.append( tips );
               rs.append( "</li>" );

               // 递归遍历存在的节点
               rs.append( getPositionNode( locale, nodeId, positionDTO.getPositionDTOs(), level + 1, full, flag, bindId, corpId ) );
            }
         }

         return rs.toString();
      }
      else
      {
         return "";
      }
   }

   private static String getNode( final Locale locale, final String parentNodeId, final List< PositionDTO > positionDTOs, final int level, final boolean full, final String flag,
         final String bindId, final String corpId, final String accountId, final boolean isShoot, final String shootId ) throws KANException
   {
      if ( hasNode( positionDTOs ) )
      {
         final StringBuffer rs = new StringBuffer();

         for ( PositionDTO positionDTO : positionDTOs )
         {
            final String positionId = positionDTO.getPositionVO().getPositionId();
            final String encodedPositionId = positionDTO.getPositionVO().getEncodedId();
            final String status = positionDTO.getPositionVO().getStatus();
            final String parentId = positionDTO.getPositionVO().getParentPositionId();
            String positionName = "";
            // 预算编制人数
            final int staffsNum = getStaffsNumByPositionId( accountId, positionId );

            final String decodeBranchId = KANUtil.filterEmpty( positionDTO.getPositionVO().getDecodeBranchIdIsTemp() ) == null ? "" : "("
                  + positionDTO.getPositionVO().getDecodeBranchIdIsTemp() + ")";

            if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               positionName = positionDTO.getPositionVO().getTitleZH();
            }
            else
            {
               positionName = positionDTO.getPositionVO().getTitleEN();
            }

            positionName += decodeBranchId;

            String fontColor = "";
            if ( KANUtil.filterEmpty( positionDTO.getPositionVO().getIsVacant() ) != null )
            {
               final int isVacant = Integer.parseInt( positionDTO.getPositionVO().getIsVacant() );
               if ( isVacant != 0 && staffsNum < isVacant )
               {
                  // 绿色086912 782918
                  fontColor = "style='color:#086912'";
               }
               else if ( isVacant != 0 && staffsNum > isVacant )
               {
                  // 红色
                  fontColor = "style='color:#782918'";
               }
            }

            rs.append( "<li>" );
            rs.append( "<div style='padding:0px 5px 0px 5px;'>" );
            if ( !isShoot )
            {
               rs.append( "<a "
                     + fontColor
                     + " href=\"#\" onclick=\"link('positionAction.do?proc=to_objectModify&tempPositionPrefer='+$('#tempPositionPrefer').val()+'&rootPositionId='+$('#rootPositionId').val()+'&positionId="
                     + encodedPositionId + "');\">" );
            }
            else
            {
               rs.append( "<a " + fontColor + " href=\"#\" onclick=\"showHistoryEmployeeNames(" + shootId + ",'position'," + positionId + ");\" >" );
            }

            final String showOneStaffName = getOneStaffName( positionDTO, locale.getLanguage() );

            rs.append( "&nbsp;&nbsp;&nbsp;&nbsp;" + positionName + "<p><image src='images/icons/dot.png' class='isExpanded' style='display:none'/>&nbsp;&nbsp;" + showOneStaffName
                  + "</p>" );

            rs.append( "</a>" );
            rs.append( "</div>" );

            rs.append( "<input type='hidden' name='hiddenPositionId' value='" + positionId + "'/>" );
            rs.append( "<input type='hidden' name='hiddenParentPositionId' value='" + parentId + "'/>" );
            rs.append( "<input type='hidden' name='hiddenStatus' value='" + status + "'/>" );
            if ( !isShoot && status.equals( "1" ) )
            {
               rs.append( "<img class='operaImgAdd' style=\"cursor:pointer;float:left;display:none\" src=\"images/add.png\" onclick=\"link('positionAction.do?proc=to_objectNew&tempPositionPrefer='+$('#tempPositionPrefer').val()+'&rootPositionId='+$('#rootPositionId').val()+'&parentPositionId="
                     + encodedPositionId + "');\" /> " );
            }
            // 如果没有下级职位且职位里面没有员工 (职位模块权限，职位模块规则 是在extend里面记录)
            if ( !isShoot && "2".equals( positionDTO.getPositionVO().getExtended() ) )
            {
               if ( ( positionDTO.getPositionDTOs() == null || ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() == 0 ) )
                     && ( positionDTO.getPositionStaffRelationVOs() == null || ( positionDTO.getPositionStaffRelationVOs() != null && positionDTO.getPositionStaffRelationVOs().size() == 0 ) ) )
               {
                  rs.append( "<img class='operaImgDel' style=\"cursor:pointer;float:right;display:none;\" src=\"images/disable.png\" onclick=\"deletePosition(" + positionId
                        + ")\" />" );
               }
            }
            rs.append( "<ul>" );
            if ( hasNode( positionDTO.getPositionDTOs() ) )
            {
               rs.append( getNode( locale, parentNodeId, positionDTO.getPositionDTOs(), level + 1, full, flag, bindId, corpId, accountId, isShoot, shootId ) );
            }
            rs.append( "</ul>" );
            rs.append( "</li>" );
         }
         return rs.toString();
      }
      else
      {
         return "";
      }
   }

   /**
    * 获取职位第一个人的名字，并显示人数总数
    * @param positionDTO 
    * @param language 
    * @param tempStaffsNum 
    * @return
    */
   private static String getOneStaffName( final PositionDTO positionDTO, String language )
   {
      String result = "";
      if ( positionDTO != null )
      {
         final List< PositionStaffRelationVO > relationVOs = positionDTO.getPositionStaffRelationVOs();
         if ( relationVOs != null && relationVOs.size() > 0 )
         {
            for ( PositionStaffRelationVO vo : relationVOs )
            {
               final StaffDTO staffDTO = KANConstants.getKANAccountConstants( positionDTO.getPositionVO().getAccountId() ).getStaffDTOByStaffId( vo.getStaffId() );
               if ( KANUtil.filterEmpty( result ) == null )
               {
                  result = ( "zh".equalsIgnoreCase( language ) ? staffDTO.getStaffVO().getNameZH() : staffDTO.getStaffVO().getNameZH() );
               }
               else
               {
                  result = result + "、" + ( "zh".equalsIgnoreCase( language ) ? staffDTO.getStaffVO().getNameZH() : staffDTO.getStaffVO().getNameZH() );
               }
            }

            result = result + ( "zh".equalsIgnoreCase( language ) ? "(共:" + relationVOs.size() + " 人)" : "total " + relationVOs.size() + " persons)" );
         }
         else
         {
            result = "zh".equalsIgnoreCase( language ) ? "该职位空缺" : "Job Vacancy";
         }
      }

      return result;
   }

   // 判断当前节点是否已经绑定员工，如有绑定则选中
   private static boolean checkWithStaff( final String value, final List< PositionStaffRelationVO > positionStaffRelationVOs )
   {
      if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
      {
         for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelationVOs )
         {
            if ( positionStaffRelationVO.getStaffId() != null && positionStaffRelationVO.getStaffId().trim().equals( value ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   // 判断当前节点是否已经绑定职位组，如有绑定则选中
   private static boolean checkWithGroup( final String value, final List< PositionGroupRelationVO > positionGroupRelationVOs )
   {
      if ( positionGroupRelationVOs != null && positionGroupRelationVOs.size() > 0 )
      {
         for ( PositionGroupRelationVO positionGroupRelationVO : positionGroupRelationVOs )
         {
            if ( positionGroupRelationVO.getGroupId() != null && positionGroupRelationVO.getGroupId().trim().equals( value ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   // 判断当前节点是否存在于数组
   private static boolean checkWithArray( final String jsonString, final String positionId )
   {
      if ( jsonString != null && !"".equals( jsonString ) && KANUtil.jasonArrayToStringArray( jsonString ).length > 0 )
      {
         for ( String temp : KANUtil.jasonArrayToStringArray( jsonString ) )
         {
            if ( positionId.equals( temp ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   // 判断是否存在子节点
   private static boolean hasNode( final List< PositionDTO > positionDTOs )
   {
      if ( positionDTOs != null && positionDTOs.size() > 0 )
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

      String className = "";
      switch ( level )
      {
         case 1:
            className = "firstlevel";
            break;
         case 2:
            className = "secondlevel";
            break;
         case 3:
            className = "thirdlevel";
            break;
         case 4:
            className = "fourthlevel";
            break;
         case 5:
            className = "fifthlevel";
            break;
         case 6:
            className = "sixthlevel";
            break;
         case 7:
            className = "seventhlevel";
            break;
         case 8:
            className = "eighthlevel";
            break;
         default:
            break;
      }
      return className;
   }

   public static String getPositionThinkingCombo( final HttpServletRequest request )
   {
      final StringBuffer rs = new StringBuffer();

      // 添加联想框
      rs.append( "<li>" );
      rs.append( "<input type=\"text\" id=\"positionName\" name=\"positionName\" class=\"thinking_positionName\"><input type=\"hidden\" id=\"positionId\" name=\"positionId\" class=\"thinking_positionId\">" );
      rs.append( " " );
      rs.append( KANUtil.getSelectHTML( KANUtil.getMappings( request.getLocale(), "security.position.staff.relation.staff.type" ), "staffType", "thinking_staffType", "1", "changeStaffType();", "width: auto;" ) );
      rs.append( " " );
      rs.append( "<input type=\"text\" class=\"Wdate wdate_beginTime small\" id=\"beginTime\" name=\"beginTime\" readonly=\"readonly\" value=\"\" onFocus=\"WdatePicker({minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\\'endTime\\')}'});\" style=\"display: none;\"/>" );
      rs.append( " " );
      rs.append( "<input type=\"text\" class=\"Wdate wdate_endTime small\" id=\"endTime\" name=\"endTime\" readonly=\"readonly\" value=\"\" onFocus=\"WdatePicker({minDate:'%y-%M-%d'&&'#F{$dp.$D(\\'beginTime\\')}'});\" style=\"display: none;\"/>" );
      rs.append( " " );
      rs.append( "<input type=\"button\" class=\"addbutton\" name=\"btnAddStaff\" id=\"btnAddStaff\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.add" )
            + "\" onclick=\"addPosition();\"/> " );
      rs.append( "</li>" );

      return rs.toString();
   }

   public static String getPositionsByStaffId( final HttpServletRequest request, final String staffId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      if ( KANUtil.filterEmpty( staffId ) != null )
      {
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
         StaffDTO staffDTO = accountConstants.getStaffDTOByStaffId( staffId );
         if ( staffDTO != null )
         {
            List< PositionStaffRelationVO > positionStaffRelations = staffDTO.getPositionStaffRelationVOs();
            if ( positionStaffRelations != null && positionStaffRelations.size() > 0 )
            {
               for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelations )
               {
                  PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( positionStaffRelationVO.getPositionId() );
                  final BranchVO branchVO = accountConstants.getBranchVOByBranchId( positionDTO.getPositionVO().getBranchId() );
                  // 读取Title
                  final String positionName = positionDTO.getPositionVO().getTitleZH() + " - " + positionDTO.getPositionVO().getTitleEN();
                  // 读取branchName
                  String branchName = "";
                  String agent = "";
                  String agentStart = "";
                  String agentEnd = "";
                  if ( positionStaffRelationVO.getStaffType() != null && positionStaffRelationVO.getStaffType().trim().equals( "2" ) )
                  {
                     // 按照语言设置取代理字符串
                     if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                     {
                        agent = "（代理）";
                     }
                     else
                     {
                        agent = "(Agent)";
                     }
                     // 格式化代理开始时间
                     agentStart = KANUtil.formatDate( positionStaffRelationVO.getAgentStart(), accountConstants.OPTIONS_DATE_FORMAT );
                     // 格式化代理结束时间
                     agentEnd = KANUtil.formatDate( positionStaffRelationVO.getAgentStart(), accountConstants.OPTIONS_DATE_FORMAT );

                     // 拼装代理字符串
                     agent = agent + " &nbsp;&nbsp; " + agentStart + " - " + agentEnd;
                  }
                  if ( branchVO != null )
                  {
                     branchName = "[" + ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? branchVO.getNameZH() : branchVO.getNameEN() ) + "]";
                  }

                  rs.append( "<li id=\"thinking_position_" + positionStaffRelationVO.getPositionId() + "\">" );
                  rs.append( "<input type=\"hidden\" id=\"positionIdArray\" name=\"positionIdArray\" class=\"thinking_positionIds\" value=\""
                        + positionStaffRelationVO.getPositionId() + '_' + positionStaffRelationVO.getStaffType() + '_' + positionStaffRelationVO.getAgentStart() + '_'
                        + positionStaffRelationVO.getAgentEnd() + "\">" );
                  rs.append( "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\"><img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" style=\"display: none;\" onclick=\"removePosition('thinking_position_"
                        + positionStaffRelationVO.getPositionId() + "');\"/>" );
                  rs.append( " &nbsp;&nbsp; " + branchName + "&nbsp;" + positionName + " &nbsp;&nbsp; " + agent );
                  rs.append( "</li>" );
               }
            }
         }
      }
      return rs.toString();
   }

   // 得到一个没有checkbox的positionTree 联系人用
   public static String getBasePositionTree( final HttpServletRequest request ) throws KANException
   {
      // 从Constants中得到当前Account的PositionDTO的列表
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;

      final StringBuffer rs = new StringBuffer();
      rs.append( "<li>" + KANUtil.getProperty( request.getLocale(), "message.click.position.name" ) + "</li>" );
      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // 遍历PositionDTO
         rs.append( getBasePositionNode( request.getLocale(), "", positionDTOs, 1, true, null, null, BaseAction.getCorpId( request, null ) ) );
      }
      else
      {
         // 遍历PositionDTO
         rs.append( getBasePositionNode( request.getLocale(), "", positionDTOs, 1, true, null, null, null ) );
      }

      return rs.toString();
   }

   // for basePositionTree
   private static String getBasePositionNode( final Locale locale, final String parentNodeId, final List< PositionDTO > positionDTOs, final int level, final boolean full,
         final String flag, final String bindId, final String corpId ) throws KANException
   {
      if ( hasNode( positionDTOs ) )
      {
         final StringBuffer rs = new StringBuffer();

         for ( PositionDTO positionDTO : positionDTOs )
         {
            final String accountId = positionDTO.getPositionVO().getAccountId();
            final String positionId = positionDTO.getPositionVO().getPositionId();
            final String nodeId = parentNodeId.concat( "_N" ).concat( positionId );
            final String tempCorpId = positionDTO.getPositionVO().getCorpId();
            String positionName = "";
            String tips = "";
            String positionStatus_class = "";

            // 按照语言设置取Title
            if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               positionName = positionDTO.getPositionVO().getTitleZH();
            }
            else
            {
               positionName = positionDTO.getPositionVO().getTitleEN();
            }
            // 如果当前的状态为停用
            if ( "2".equals( positionDTO.getPositionVO().getStatus() ) )
            {
               positionStatus_class = "deleted";
            }

            // 生成职位显示所需的备注信息 - 员工
            tips = KANConstants.getKANAccountConstants( accountId ).getStaffNamesByPositionId( locale.getLanguage(), positionId );

            // 生成职位显示所需的备注信息 - 职位组
            if ( positionDTO.getPositionGroupRelationVOs() != null && positionDTO.getPositionGroupRelationVOs().size() > 0 )
            {
               tips = tips + " - " + positionDTO.getPositionGroupRelationVOs().size() + " 个职位组";
            }

            if ( tips != null && !tips.isEmpty() )
            {
               tips = " - " + tips;
            }

            if ( ( corpId == null && KANUtil.filterEmpty( tempCorpId ) == null ) || ( corpId != null && tempCorpId != null && tempCorpId.equals( corpId ) ) )
            {
               // 生成根节点
               rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
               rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasNode( positionDTO.getPositionDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                     + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\"/> " );
               rs.append( "<a style=\"cursor:pointer\" onclick=\"addContactPosition('" + positionId + "','" + positionName + "'); \"><span class='" + positionStatus_class + "'>"
                     + positionName + "</span></a>" );
               rs.append( tips );
               rs.append( "</li>" );

               // 递归遍历存在的节点
               rs.append( getBasePositionNode( locale, nodeId, positionDTO.getPositionDTOs(), level + 1, full, flag, bindId, corpId ) );
            }
         }

         return rs.toString();
      }
      else
      {
         return "";
      }
   }

   private static void refreshDTO( List< PositionDTO > positionDTOs, final HttpServletRequest request )
   {
      for ( PositionDTO positionDTO : positionDTOs )
      {
         positionDTO.getPositionVO().reset( null, request );
         if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
         {
            refreshDTO( positionDTO.getPositionDTOs(), request );
         }
      }
   }

   public static int getStaffsNumByPositionId( final String accountId, final String positionId ) throws KANException
   {
      int sum = 0;
      List< PositionStaffRelationVO > positionStaffRelationVOsByPositionId = null;

      if ( positionId != null )
      {
         final PositionDTO positionDTO = KANConstants.getKANAccountConstants( accountId ).getPositionDTOByPositionId( positionId );

         if ( positionDTO != null )
         {
            positionStaffRelationVOsByPositionId = positionDTO.getPositionStaffRelationVOs();
         }
      }

      if ( positionStaffRelationVOsByPositionId != null && positionStaffRelationVOsByPositionId.size() > 0 )
      {
         for ( Object relationVOObject : positionStaffRelationVOsByPositionId )
         {
            final PositionStaffRelationVO positionStaffRelationVO = ( PositionStaffRelationVO ) relationVOObject;

            if ( positionStaffRelationVO != null
                  && KANConstants.getKANAccountConstants( positionStaffRelationVO.getAccountId() ).getStaffBaseViewByStaffId( positionStaffRelationVO.getStaffId() ) != null )
            {
               sum++;
            }
         }
      }

      return sum;
   }

   // 生成下拉框，父节点为“0”的
   public static String getSelectHtml( final HttpServletRequest request )
   {
      StringBuffer sb = new StringBuffer();
      try
      {
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
         final List< PositionDTO > positionDTOs = accountConstants.getIndependentDisplayPositionDTOs( BaseAction.getCorpId( request, null ) );
         // 按照职级排序
         orderPositionDTOs( positionDTOs, accountConstants, BaseAction.getCorpId( request, null ) );
         String rootPositionId = "";
         if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "rootPositionId" ) ) != null )
         {
            rootPositionId = URLDecoder.decode( URLDecoder.decode( ( String ) request.getAttribute( "rootPositionId" ), "utf-8" ), "utf-8" );
         }
         sb.append( "<select id='orgChartSel'>" );
         for ( PositionDTO positionDTO : positionDTOs )
         {
            //如果职位的职位组为系统管理组，则不显示
            final List< PositionGroupRelationVO > groupRelationVOs = positionDTO.getPositionGroupRelationVOs();
            boolean isSystemGroup = false;
            if ( groupRelationVOs != null && groupRelationVOs.size() > 0 )
            {
               for ( PositionGroupRelationVO groupRelationVO : groupRelationVOs )
               {
                  final GroupDTO groupDTO = KANConstants.getKANAccountConstants( positionDTO.getPositionVO().getAccountId() ).getGroupDTOByGroupId( groupRelationVO.getGroupId() );
                  if ( groupDTO != null && groupDTO.getGroupVO().getNameZH().contains( "系统管理组" ) )
                  {
                     isSystemGroup = true;
                     break;
                  }
               }
            }
            // 如果是系统管理组，则跳过
            if ( isSystemGroup )
            {
               continue;
            }
            positionDTO.getPositionVO().reset( null, request );
            String tempPositionName = "";
            if ( "ZH".equalsIgnoreCase( request.getLocale().getLanguage() ) )
            {
               tempPositionName = positionDTO.getPositionVO().getTitleZH();
            }
            else
            {
               tempPositionName = positionDTO.getPositionVO().getTitleEN();
            }
            if ( positionDTO.getPositionVO().getPositionId().equals( rootPositionId ) )
            {
               sb.append( "<option selected value=\"" + positionDTO.getPositionVO().getPositionId() + "\">" + tempPositionName + "</option>" );
            }
            else
            {
               sb.append( "<option value=\"" + positionDTO.getPositionVO().getPositionId() + "\">" + tempPositionName + "</option>" );
            }
         }
         sb.append( "</select>&nbsp;&nbsp;" );

         if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            sb.append( "<label style='font-size: 13px;'><font color='#086912'>“绿色字体”</font>表示编制未满，<font color='#782918'>“红色字体”</font>表示超出编制，<span class='deleted'>“红色背景”</span>表示职位已停用。</label>" );
         }
         else
         {
            sb.append( "<label style='font-size: 13px;'><font color='#086912'>Green Font</font> means the HC is availiable, <font color='#782918'>Red Font</font> means the HC is full, <span class='deleted'>Red background</span> means the position is cancelled.</label>" );
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return sb.toString();
   }

   private static void orderPositionDTOs( final List< PositionDTO > positionDTOs, final KANAccountConstants accountConstants, final String corpId )
   {
      List< MappingVO > positionGradesMappngVOs = accountConstants.getPositionGrades( null, corpId );
      for ( int i = 0; i < positionDTOs.size(); i++ )
      {
         for ( int j = i + 1; j < positionDTOs.size(); j++ )
         {
            // 
            String weightI = "";
            String weightJ = "";
            for ( MappingVO mappingVO : positionGradesMappngVOs )
            {
               if ( mappingVO.getMappingId().equals( positionDTOs.get( i ).getPositionVO().getPositionGradeId() ) )
               {
                  weightI = mappingVO.getMappingTemp();
               }
               if ( mappingVO.getMappingId().equals( positionDTOs.get( j ).getPositionVO().getPositionGradeId() ) )
               {
                  weightJ = mappingVO.getMappingTemp();
               }
            }
            if ( KANUtil.filterEmpty( weightI ) == null )
            {
               weightI = "0";
            }
            if ( KANUtil.filterEmpty( weightJ ) == null )
            {
               weightJ = "0";
            }
            if ( Integer.parseInt( weightI ) < Integer.parseInt( weightJ ) )
            {
               // 如果I 的 权重小于J，则换位置
               final PositionDTO tempDTO = positionDTOs.get( i );
               positionDTOs.set( i, positionDTOs.get( j ) );
               positionDTOs.set( j, tempDTO );
            }
         }
      }

   }

   public static String getPositionTree( final HttpServletRequest request, final boolean hasCheckbox ) throws KANException
   {

      // 系统模块树
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;

      // 遍历ModuleDTO
      return getPositionNode( request, "", "", positionDTOs, null, 1, hasCheckbox );
   }

   private static String getPositionNode( final HttpServletRequest request, final String ownerObjectId, final String parentNodeId, final List< PositionDTO > positionDTOs,
         final List< PositionVO > selectedPositionVOs, final int level, final boolean hasCheckbox ) throws KANException
   {
      try
      {
         if ( hasNode( positionDTOs ) )
         {
            final StringBuffer rs = new StringBuffer();

            for ( PositionDTO positionDTO : positionDTOs )
            {
               if ( positionDTO.getPositionVO() != null && StringUtils.equals( positionDTO.getPositionVO().getCorpId(), BaseAction.getCorpId( request, null ) ) )
               {
                  final String positionId = positionDTO.getPositionVO().getPositionId();
                  final String nodeId = parentNodeId.concat( "_N_P" ).concat( positionId );
                  String positionName = "";

                  // 按照语言设置取Title
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     positionName = positionDTO.getPositionVO().getTitleZH();
                  }
                  else
                  {
                     positionName = positionDTO.getPositionVO().getTitleEN();
                  }

                  // 生成根节点
                  rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\">" );
                  rs.append( "<img id=\"IMG" + nodeId + "\" src=\"images/minus.gif\" onclick=\"kantreeNodeClick('" + nodeId + "');\" /> " );

                  // 需要使用Checkbox
                  if ( hasCheckbox )
                  {
                     rs.append( "<input type=\"checkbox\" name=\"positionIds\" id=\"positionIds\" value=\"" + positionId + "\""
                           + ( checkedSelected( positionId, selectedPositionVOs ) ? "checked" : "" ) + ">" );
                  }
                  // 不使用Checkbox的情况下，改用图片说明当前Module是否已有设置
                  else
                  {
                     if ( checkedSelected( positionId, selectedPositionVOs ) )
                     {
                        rs.append( "<img id=\"" + positionId + "\" src=\"images/enable.png\" /> " );
                     }
                     else
                     {
                        rs.append( "<img id=\"" + positionId + "\" src=\"images/empty.png\" /> " );
                     }
                  }

                  rs.append( "<a id=\"managePositon\" class=\"kanhandle\">" );
                  rs.append( positionName );
                  rs.append( "</a>" );
                  if ( hasCheckbox )
                  {
                     rs.append( "</input>" );
                  }
                  rs.append( "</li>" );

                  // 递归遍历存在的节点
                  rs.append( getPositionNode( request, ownerObjectId, nodeId, positionDTO.getPositionDTOs(), selectedPositionVOs, level + 1, hasCheckbox ) );
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

   // 判断当前节点是否需要选中
   private static boolean checkedSelected( final String postionId, final List< PositionVO > selectedPositionVOs )
   {
      if ( selectedPositionVOs != null )
      {
         for ( PositionVO positionVO : selectedPositionVOs )
         {
            if ( positionVO != null && positionVO.getBranchId() != null && positionVO.getBranchId().trim().equals( postionId ) )
            {
               return true;
            }
         }
      }
      return false;
   }
}
