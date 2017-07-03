package com.kan.base.web.renders.security;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.kan.base.domain.security.BranchDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class BranchRender
{
   // 得到整个Branch的Tree
   public static String getBranchTree( final HttpServletRequest request, final List< BranchDTO > branchDTOs, final boolean isShoot ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      final String shootId = request.getParameter( "shootId" );
      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // 遍历PositionDTO
         rs.append( getBranchNode( request.getLocale(), branchDTOs, 1, true, null, null, BaseAction.getCorpId( request, null ), BaseAction.getAccountId( request, null ), isShoot, shootId ) );
      }
      else
      {
         // 遍历PositionDTO
         rs.append( getBranchNode( request.getLocale(), branchDTOs, 1, true, null, null, null, BaseAction.getAccountId( request, null ), isShoot, shootId ) );
      }

      return rs.toString();
   }

   /**
    * 递归方法 - 遍历生成关系图
    * 
    */
   private static String getBranchNode( final Locale locale, final List< BranchDTO > branchDTOs, final int level, final boolean full, final String flag, final String bindId,
         final String corpId, final String accountId, final boolean isShoot, final String shootId ) throws KANException
   {
      if ( hasNode( branchDTOs ) )
      {
         final StringBuffer rs = new StringBuffer();

         for ( BranchDTO branchDTO : branchDTOs )
         {
            // 如果是INhouse
            if ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) == null )
            {
               continue;
            }
            // 如果是HRService
            if ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) != null )
            {
               continue;
            }
            final String branchId = branchDTO.getBranchVO().getBranchId();
            final String encodedBranchId = branchDTO.getBranchVO().getEncodedId();
            final String branchCode = branchDTO.getBranchVO().getBranchCode();
            final String branchNameEN = branchDTO.getBranchVO().getNameEN();
            final String parentId = branchDTO.getBranchVO().getParentBranchId();
            final String status = branchDTO.getBranchVO().getStatus();
            String branchName = "";
            final int num = staffNumInBranch( branchDTO.getBranchVO().getBranchId(), accountId, KANConstants.getKANAccountConstants( accountId ).OPTIONS_ISSUMSUBBRANCHHC );

            // 按照语言设置取Title
            if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               branchName = branchDTO.getBranchVO().getNameZH() + "（" + num + " 人）";
            }
            else
            {
               branchName = branchDTO.getBranchVO().getNameEN() + "(" + num + " people)";
            }
            rs.append( "<li>" );
            rs.append( "<div style='padding:0px 5px 0px 5px;'>" );
            if ( !isShoot )
            {
               rs.append( "<a href=\"#\" onclick=\"link('branchAction.do?proc=to_objectModify&rootBranchId='+$('#rootBranchId').val()+'&id=" + encodedBranchId + "');\">" );
            }
            else
            {
               rs.append( "<a href=\"#\" onclick=\"showHistoryEmployeeNames(" + shootId + ",'branch'," + branchId + ");\">" );
            }
            if ( KANUtil.filterEmpty( branchNameEN ) == null )
            {
               rs.append( " &nbsp;&nbsp;&nbsp;&nbsp;" + branchName + "<p><image src='images/icons/dot.png' class='isExpanded' style='display:none'/>&nbsp;&nbsp;" + branchCode
                     + "</p>" );
            }
            else
            {
               rs.append( " &nbsp;&nbsp;&nbsp;&nbsp;" + branchName + "<p><image src='images/icons/dot.png' class='isExpanded' style='display:none'/>&nbsp;&nbsp;" + branchNameEN
                     + "-" + branchCode + "</p>" );
            }
            rs.append( "</a>" );
            rs.append( "</div>" );
            rs.append( "<input type='hidden' name='hiddenBranchId' value='" + branchId + "'/>" );
            rs.append( "<input type='hidden' name='hiddenParentBranchId' value='" + parentId + "'/>" );
            rs.append( "<input type='hidden' name='hiddenStatus' value='" + status + "'/>" );
            if ( !isShoot
                  && status.equals( "1" )
                  && ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) != null ) ) )
            {
               rs.append( "<img class='operaImgAdd' style=\"cursor:pointer;float:left;display:none\" src=\"images/add.png\" onclick=\"link('branchAction.do?proc=to_objectNew&rootBranchId='+$('#rootBranchId').val()+'&parentBranchId="
                     + encodedBranchId + "');\" />" );
            }
            if ( !isShoot && "2".equals( branchDTO.getBranchVO().getExtended() )
                  && ( branchDTO.getBranchDTOs() == null || ( branchDTO.getBranchDTOs() != null && branchDTO.getBranchDTOs().size() == 0 ) ) )
            {
               rs.append( "<img class='operaImgDel' style=\"cursor:pointer;float:right;display:none;\" src=\"images/disable.png\" onclick=\"deleteBranch(" + branchId + ")\" />" );
            }

            rs.append( "<ul>" );
            if ( hasNode( branchDTO.getBranchDTOs() ) )
            {
               rs.append( getBranchNode( locale, branchDTO.getBranchDTOs(), level + 1, full, flag, bindId, corpId, accountId, isShoot, shootId ) );
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

   // 判断是否存在子节点
   private static boolean hasNode( final List< BranchDTO > branchDTOs )
   {
      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   private static int staffNumInBranch( final String branchId, final String accountId, final String isSumSubBranchHC )
   {
      // 1统计 0不统计
      if ( "1".equals( isSumSubBranchHC ) )
      // 职位里面的staff
      {
         return staffNumInBranch( branchId, accountId ).size();
      }
      else
      {
         final BranchVO branchVO = KANConstants.getKANAccountConstants( accountId ).getBranchVOByBranchId( branchId );
         return branchVO == null ? 0 : branchVO.getStaffIdsInBranch().size();
      }

   }

   private static List< String > staffNumInBranch( final String branchId, final String accountId )
   {
      final List< String > staffIds = new ArrayList< String >();
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
      final BranchDTO branchDTO = accountConstants.getBranchDTOByBranchId( branchId );
      if ( branchDTO != null )
      {
         staffIds.addAll( branchDTO.getBranchVO().getStaffIdsInBranch() );
      }
      if ( branchDTO != null && branchDTO.getBranchDTOs() != null && branchDTO.getBranchDTOs().size() > 0 )
      {
         for ( BranchDTO tempDTO : branchDTO.getBranchDTOs() )
         {
            staffIds.addAll( staffNumInBranch( tempDTO.getBranchVO().getBranchId(), accountId ) );
         }
      }
      return KANUtil.getDistinctList( staffIds );
   }

   private static List< StaffVO > getDistinctStaffVOs( List< StaffVO > staffVOsInBranch )
   {
      final List< StaffVO > targetStaffVOs = new ArrayList< StaffVO >();
      for ( StaffVO staffVO : staffVOsInBranch )
      {
         boolean flag = false;
         for ( StaffVO tempStaffVO : targetStaffVOs )
         {
            if ( staffVO.getStaffId().equals( tempStaffVO.getStaffId() ) )
            {
               flag = true;
               break;
            }
         }
         if ( !flag )
         {
            targetStaffVOs.add( staffVO );
         }
      }
      return targetStaffVOs;
   }

   public static String getSelectHtml( final HttpServletRequest request )
   {
      StringBuffer sb = new StringBuffer();
      try
      {
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
         final List< BranchDTO > branchDTOs = accountConstants.BRANCH_DTO;
         String rootBranchId = "";
         if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "rootBranchId" ) ) != null )
         {
            rootBranchId = URLDecoder.decode( URLDecoder.decode( ( String ) request.getAttribute( "rootBranchId" ), "utf-8" ), "utf-8" );
         }
         sb.append( "<select id='orgChartSel'>" );
         // 1是法务实体架构图，3是部门架构图
         final boolean showAsEntity = "1".equals( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_BRANCH_PREFER );

         final List< BranchDTO > targetBranchDTOs = new ArrayList< BranchDTO >();
         for ( BranchDTO branchDTO : branchDTOs )
         {
            if ( "历史".equals( branchDTO.getBranchVO().getNameZH() ) )
            {
               targetBranchDTOs.add( branchDTO );
            }
            else
            {
               targetBranchDTOs.add( 0, branchDTO );
            }
         }

         for ( BranchDTO branchDTO : targetBranchDTOs )
         {
            // 如果是INhouse
            if ( KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) ) != null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) == null )
            {
               continue;
            }
            // 如果是HRService
            if ( KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) ) == null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) != null )
            {
               continue;
            }
            branchDTO.getBranchVO().reset( null, request );
            String showName = "";
            if ( "zh".equalsIgnoreCase( request.getLocale().getLanguage() ) )
            {
               showName = showAsEntity ? branchDTO.getBranchVO().getDecodeEntityId() : branchDTO.getBranchVO().getNameZH();
            }
            else
            {
               showName = showAsEntity ? branchDTO.getBranchVO().getDecodeEntityId() : branchDTO.getBranchVO().getNameEN();
            }
            if ( branchDTO.getBranchVO().getBranchId().equals( rootBranchId ) )
            {
               sb.append( "<option selected value=\"" + branchDTO.getBranchVO().getBranchId() + "\">" + showName + "</option>" );
            }
            else
            {
               sb.append( "<option value=\"" + branchDTO.getBranchVO().getBranchId() + "\">" + showName + "</option>" );
            }
         }
         sb.append( "</select>" );
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return sb.toString();
   }

   @SuppressWarnings("unchecked")
   public static String getOrgShootSelectHtml( final HttpServletRequest request )
   {
      StringBuffer sb = new StringBuffer();
      try
      {
         final List< BranchDTO > branchDTOs = ( List< BranchDTO > ) request.getAttribute( "branchDTOs" );
         String rootBranchId = "";
         if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "rootBranchId" ) ) != null )
         {
            rootBranchId = URLDecoder.decode( URLDecoder.decode( ( String ) request.getAttribute( "rootBranchId" ), "utf-8" ), "utf-8" );
         }
         sb.append( "<select id='orgChartSel'>" );
         for ( BranchDTO branchDTO : branchDTOs )
         {
            branchDTO.getBranchVO().reset( null, request );
            if ( branchDTO.getBranchVO().getBranchId().equals( rootBranchId ) )
            {
               sb.append( "<option selected value=\"" + branchDTO.getBranchVO().getBranchId() + "\">" + branchDTO.getBranchVO().getDecodeEntityId() + "</option>" );
            }
            else
            {
               sb.append( "<option value=\"" + branchDTO.getBranchVO().getBranchId() + "\">" + branchDTO.getBranchVO().getDecodeEntityId() + "</option>" );
            }
         }
         sb.append( "</select>" );
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return sb.toString();
   }

   public static String getBranchTree( final HttpServletRequest request, final boolean hasCheckbox ) throws KANException
   {

      // 系统模块树
      final List< BranchDTO > branchDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).BRANCH_DTO;

      // 遍历ModuleDTO
      return getBranchNode( request, "", "", branchDTOs, null, 1, hasCheckbox );
   }

   private static String getBranchNode( final HttpServletRequest request, final String ownerObjectId, final String parentNodeId, final List< BranchDTO > branchDTOs,
         final List< BranchVO > selectedBranchVOs, final int level, final boolean hasCheckbox ) throws KANException
   {
      try
      {
         if ( hasNode( branchDTOs ) )
         {
            final StringBuffer rs = new StringBuffer();

            for ( BranchDTO branchDTO : branchDTOs )
            {
               if ( branchDTO.getBranchVO() != null && StringUtils.equals( branchDTO.getBranchVO().getCorpId(), BaseAction.getCorpId( request, null ) ) )
               {
                  final String branchId = branchDTO.getBranchVO().getBranchId();
                  final String nodeId = parentNodeId.concat( "_N_B" ).concat( branchId );
                  String branchName = "";

                  // 按照语言设置取Title
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     branchName = branchDTO.getBranchVO().getNameZH();
                  }
                  else
                  {
                     branchName = branchDTO.getBranchVO().getNameEN();
                  }

                  // 生成根节点
                  rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\">" );
                  rs.append( "<img id=\"IMG" + nodeId + "\" src=\"images/minus.gif\" onclick=\"kantreeNodeClick('" + nodeId + "');\" /> " );

                  // 需要使用Checkbox
                  if ( hasCheckbox )
                  {
                     rs.append( "<input type=\"checkbox\" name=\"branchIds\" id=\"branchIds\" value=\"" + branchId + "\""
                           + ( checkedSelected( branchId, selectedBranchVOs ) ? "checked" : "" ) + ">" );
                  }
                  // 不使用Checkbox的情况下，改用图片说明当前Module是否已有设置
                  else
                  {
                     if ( checkedSelected( branchId, selectedBranchVOs ) )
                     {
                        rs.append( "<img id=\"" + branchId + "\" src=\"images/enable.png\" /> " );
                     }
                     else
                     {
                        rs.append( "<img id=\"" + branchId + "\" src=\"images/empty.png\" /> " );
                     }
                  }

                  rs.append( "<a id=\"manageBranch\" class=\"kanhandle\">" );
                  rs.append( branchName );
                  rs.append( "</a>" );
                  if ( hasCheckbox )
                  {
                     rs.append( "</input>" );
                  }
                  rs.append( "</li>" );

                  // 递归遍历存在的节点
                  rs.append( getBranchNode( request, ownerObjectId, nodeId, branchDTO.getBranchDTOs(), selectedBranchVOs, level + 1, hasCheckbox ) );
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
   private static boolean checkedSelected( final String branchId, final List< BranchVO > selectedBranchVOs )
   {
      if ( selectedBranchVOs != null )
      {
         for ( BranchVO branchVO : selectedBranchVOs )
         {
            if ( branchVO != null && branchVO.getBranchId() != null && branchVO.getBranchId().trim().equals( branchId ) )
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
}
