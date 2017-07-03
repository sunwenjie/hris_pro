package com.kan.base.web.renders.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class StaffRender
{
   // 生成Staff联想框
   public static String getStaffThinkingCombo( final HttpServletRequest request )
   {
      final StringBuffer rs = new StringBuffer();

      // 添加联想框
      rs.append( "<li>" );
      rs.append( "<input type=\"text\" id=\"staffName\" name=\"staffName\" class=\"thinking_staffName\"><input type=\"hidden\" id=\"staffId\" name=\"staffId\" class=\"thinking_staffId\">" );
      rs.append( " " );
      rs.append( KANUtil.getSelectHTML( KANUtil.getMappings( request.getLocale(), "security.position.staff.relation.staff.type" ), "staffType", "thinking_staffType", "1", "changeStaffType();", "width: auto;" ) );
      rs.append( " " );
      rs.append( "<input type=\"text\" class=\"Wdate wdate_beginTime small\" id=\"beginTime\" name=\"beginTime\" readonly=\"readonly\" value=\"\" onFocus=\"WdatePicker({minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\\'endTime\\')}'});\" style=\"display: none;\"/>" );
      rs.append( " " );
      rs.append( "<input type=\"text\" class=\"Wdate wdate_endTime small\" id=\"endTime\" name=\"endTime\" readonly=\"readonly\" value=\"\" onFocus=\"WdatePicker({minDate:'%y-%M-%d'&&'#F{$dp.$D(\\'beginTime\\')}'});\" style=\"display: none;\"/>" );
      rs.append( " " );
      rs.append( "<input type=\"button\" class=\"addbutton\" name=\"btnAddStaff\" id=\"btnAddStaff\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.add" )
            + "\" onclick=\"addStaff();\"/> " );
      rs.append( "</li>" );

      return rs.toString();
   }

   // 生成Staff管理列表
   public static String getStaffsByPositionId( final HttpServletRequest request, final String positionId ) throws KANException
   {
      List< PositionStaffRelationVO > positionStaffRelationVOsByPositionId = null;

      if ( positionId != null )
      {
         final PositionDTO positionDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionDTOByPositionId( positionId );

         if ( positionDTO != null )
         {
            positionStaffRelationVOsByPositionId = positionDTO.getPositionStaffRelationVOs();
         }
      }

      final StringBuffer rs = new StringBuffer();

      if ( positionStaffRelationVOsByPositionId != null && positionStaffRelationVOsByPositionId.size() > 0 )
      {
         for ( Object relationVOObject : positionStaffRelationVOsByPositionId )
         {
            final PositionStaffRelationVO positionStaffRelationVO = ( PositionStaffRelationVO ) relationVOObject;

            if ( positionStaffRelationVO != null
                  && KANConstants.getKANAccountConstants( positionStaffRelationVO.getAccountId() ).getStaffBaseViewByStaffId( positionStaffRelationVO.getStaffId() ) != null )
            {
               // 读取Title
               String staffName = KANConstants.getKANAccountConstants( positionStaffRelationVO.getAccountId() ).getStaffBaseViewByStaffId( positionStaffRelationVO.getStaffId() ).getNameZH();

               final PositionDTO positionDTO = KANConstants.getKANAccountConstants( positionStaffRelationVO.getAccountId() ).getPositionDTOByPositionId( positionId );
               if ( positionDTO != null && positionDTO.getPositionVO() != null )
               {
                  final PositionDTO parentPositionDTO = KANConstants.getKANAccountConstants( positionStaffRelationVO.getAccountId() ).getPositionDTOByPositionId( positionDTO.getPositionVO().getParentPositionId() );
                  if ( parentPositionDTO != null && parentPositionDTO.getPositionVO() != null )
                  {
                     parentPositionDTO.getPositionVO().reset( null, request );
                     staffName = staffName + " - " + parentPositionDTO.getPositionVO().getDecodeBranchId();
                  }
               }

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
                  if ( positionStaffRelationVO.getAgentStart() != null )
                  {
                     agentStart = KANUtil.formatDate( positionStaffRelationVO.getAgentStart(), KANConstants.getKANAccountConstants( positionStaffRelationVO.getAccountId() ).OPTIONS_DATE_FORMAT );
                  }
                  // 格式化代理结束时间
                  if ( positionStaffRelationVO.getAgentEnd() != null )
                  {
                     agentEnd = KANUtil.formatDate( positionStaffRelationVO.getAgentEnd(), KANConstants.getKANAccountConstants( positionStaffRelationVO.getAccountId() ).OPTIONS_DATE_FORMAT );
                  }

                  // 拼装代理字符串
                  if ( positionStaffRelationVO.getAgentStart() == null && positionStaffRelationVO.getAgentEnd() == null )
                  {
                     agent = "";
                  }
                  else
                  {
                     agent = agent + " &nbsp;&nbsp; " + agentStart + " - " + agentEnd;
                  }
               }

               rs.append( "<li id=\"thinking_staff_" + positionStaffRelationVO.getStaffId() + "\">" );
               rs.append( "<input type=\"hidden\" id=\"staffIdArray\" name=\"staffIdArray\" class=\"thinking_staffIds\" value=\"" + positionStaffRelationVO.getStaffId() + "_"
                     + positionStaffRelationVO.getStaffType() + "_" + positionStaffRelationVO.getAgentStart() + "_" + positionStaffRelationVO.getAgentEnd() + "\">" );
               rs.append( "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\"><img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" style=\"display: none;\" onclick=\"removeStaff('thinking_staff_"
                     + positionStaffRelationVO.getStaffId() + "');\"/>" );
               rs.append( " &nbsp;&nbsp; " + staffName + " &nbsp;&nbsp; " + agent );
               rs.append( "</li>" );
            }
         }
      }

      return rs.toString();
   }
}
