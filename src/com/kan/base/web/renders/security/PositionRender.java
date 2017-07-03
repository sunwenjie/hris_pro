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
   // �õ�����Position��Tree  ��״
   public static String getPositionTree( final HttpServletRequest request ) throws KANException
   {
      // ��Constants�еõ���ǰAccount��PositionDTO���б�
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;

      final StringBuffer rs = new StringBuffer();

      if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
      {
         rs.append( "<label style='font-size: 13px;'>�����ְλ�����鿴��༭ְλ��Ϣ�����<img src=\"images/add.png\" />������¼�ְλ��" );
         rs.append( "��<font color='#086912'>����ɫ���塱</font>��ʾ����δ����<font color='#782918'>����ɫ���塱</font>��ʾ�������ƣ�<span class='deleted'>����ɫ������</span>��ʾְλ��ͣ�á���</label>" );
      }
      else
      {
         rs.append( "<label style='font-size: 13px;'>Click the name of position could check or edit the position information, click <img src=\"images/add.png\" /> the could add the new sub-position." );
         rs.append( "(<font color='#086912'>Green Font</font> means the HC is availiable, <font color='#782918'>Red Font</font> means the HC is full, <span class='deleted'>Red background</span> means the position is cancelled.)</label>" );
      }

      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // ����PositionDTO
         rs.append( getPositionNode( request.getLocale(), "", positionDTOs, 1, true, null, null, BaseAction.getCorpId( request, null ) ) );
      }
      else
      {
         // ����PositionDTO
         rs.append( getPositionNode( request.getLocale(), "", positionDTOs, 1, true, null, null, null ) );
      }

      return rs.toString();
   }

   /**
    * �ܹ�ͼ��
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
         rs.append( "<label style='font-size: 13px;'>�����ְλ�����鿴��༭ְλ��Ϣ�����<img src=\"images/add.png\" />������¼�ְλ��" );
         rs.append( "��<font color='#086912'>����ɫ���塱</font>��ʾ����δ����<font color='#782918'>����ɫ���塱</font>��ʾ�������ƣ�<span class='deleted'>����ɫ������</span>��ʾְλ��ͣ�á���</label>" );
      }
      else
      {
         rs.append( "<label style='font-size: 13px;'>Click the name of position could check or edit the position information, click <img src=\"images/add.png\" /> the could add the new sub-position." );
         rs.append( "(<font color='#086912'>Green Font</font> means the HC is availiable, <font color='#782918'>Red Font</font> means the HC is full, <span class='deleted'>Red background</span> means the position is cancelled.)</label>" );
      }

      final String shootId = request.getParameter( "shootId" );
      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // ����PositionDTO
         refreshDTO( positionDTOs, request );
         rs.append( getNode( request.getLocale(), "", positionDTOs, 1, true, null, null, BaseAction.getCorpId( request, null ), BaseAction.getAccountId( request, null ), isShoot, shootId ) );
      }
      else
      {
         // ����PositionDTO
         refreshDTO( positionDTOs, request );
         rs.append( getNode( request.getLocale(), "", positionDTOs, 1, true, null, null, null, BaseAction.getAccountId( request, null ), isShoot, shootId ) );
      }

      return rs.toString();
   }

   // �����û��󶨵�ְλ���õ�����Position��Tree
   public static String getPositionTreeByStaffId( final HttpServletRequest request, final String staffId ) throws KANException
   {
      // ��Constants�еõ���ǰAccount��PositionDTO���б�
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;

      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // ����PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "STAFF", staffId, BaseAction.getCorpId( request, null ) );
      }
      else
      {
         // ����PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "STAFF", staffId, null );
      }

   }

   // ����ְ��󶨵�ְλ���õ�����Position��Tree
   public static String getPositionTreeByGroupId( final HttpServletRequest request, final String groupId ) throws KANException
   {
      // ��Constants�еõ���ǰAccount��PositionDTO���б�
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;

      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // ����PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "GROUP", groupId, BaseAction.getCorpId( request, null ) );
      }
      else
      {
         // ����PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "GROUP", groupId, null );
      }

      // ����PositionDTO
      // return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "GROUP", groupId, null );
   }

   // ��jason��ʽ�ַ������õ�����Position��Tree
   public static String getPositionTreeByJsonString( final HttpServletRequest request, final String jsonString ) throws KANException
   {
      // ��Constants�еõ���ǰAccount��PositionDTO���б�
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;
      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // ����PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "JSON", jsonString, BaseAction.getCorpId( request, null ) );
      }
      else
      {
         // ����PositionDTO
         return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "JSON", jsonString, null );
      }
      // ����PositionDTO
      // return getPositionNode( request.getLocale(), "", positionDTOs, 1, false, "JSON", jsonString, null );
   }

   /**
    * �ݹ鷽�� - ��������ְλ��
    * 
    * locale - ����
    * parentNodeId - ���ڵ�
    * positionDTOs - ְλ���Ѱ��ղ㼶�ṹ��
    * level - ��ǰ�㼶
    * full - �Ƿ���Ҫ��ʾȫ������
    * flag - �ֱ浱ǰ��ʾSTAFF����GROUP
    * bindId - ��ǰְλ����ID��������Staff Id��Ҳ������Group Id��
    */
   private static String getPositionNode( final Locale locale, final String parentNodeId, final List< PositionDTO > positionDTOs, final int level, final boolean full,
         final String flag, final String bindId, final String corpId ) throws KANException
   {
      if ( hasNode( positionDTOs ) )
      {
         final StringBuffer rs = new StringBuffer();

         for ( PositionDTO positionDTO : positionDTOs )
         {
            // �����INhouse
            if ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getCorpId() ) == null )
            {
               continue;
            }
            // �����HRService
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

            // ������������ȡTitle
            if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               positionName = positionDTO.getPositionVO().getTitleZH();
            }
            else
            {
               positionName = positionDTO.getPositionVO().getTitleEN();
            }
            // �����ǰ��״̬Ϊͣ��
            if ( "2".equals( positionDTO.getPositionVO().getStatus() ) )
            {
               positionStatus_class = "deleted";
            }

            // ����Flagȷ����ǰ�ڵ��Ƿ���Ҫѡ��
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

            // ����ְλ��ʾ����ı�ע��Ϣ - ְλ��
            if ( positionDTO.getPositionGroupRelationVOs() != null && positionDTO.getPositionGroupRelationVOs().size() > 0 )
            {
               tips = tips + " - " + positionDTO.getPositionGroupRelationVOs().size() + ( locale.getLanguage().equalsIgnoreCase( "ZH" ) ? "��" : "" )
                     + KANUtil.getProperty( locale, "security.position.group" );
            }

            // ����ְλ��ʾ����ı�ע��Ϣ - Ա��
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
                     // ��ɫ086912 782918
                     fontColor = "style='color:#086912'";
                  }
                  else if ( isVacant != 0 && staffNum > isVacant )
                  {
                     // ��ɫ
                     fontColor = "style='color:#782918'";
                  }
               }
               String liClass = positionDTO.getPositionDTOs().size() > 0 ? "" : "independent";
               // ���ɸ��ڵ�
               rs.append( "<li id=\"" + nodeId + "\" class=\"" + liClass + " " + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
               rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasNode( positionDTO.getPositionDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                     + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\"/> " );
               rs.append( "<input type=\"checkbox\" name=\"positionIdArray\" id=\"positionIdArray\" value=\"" + positionId + "\"" + ( checked ? ( "checked" ) : ( "" ) ) + " "
                     + checkboxDisplay + " /><a onclick=\"managePosition('"

                     + encodedPositionId + "'); \"><span class='" + positionStatus_class + "' " + fontColor + ">" + positionName + "</span></a>" );

               // �����ְλ��������ְλ����İ�ť
               if ( flag == null )
               {
                  rs.append( "<img src=\"images/add.png\" onclick=\"link('positionAction.do?proc=to_objectNew&tempPositionPrefer='+$('#tempPositionPrefer').val()+'&rootPositionId='+$('#rootPositionId').val()+'&parentPositionId="
                        + encodedPositionId + "');\" /> " );
               }

               rs.append( tips );
               rs.append( "</li>" );

               // �ݹ�������ڵĽڵ�
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
            // Ԥ���������
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
                  // ��ɫ086912 782918
                  fontColor = "style='color:#086912'";
               }
               else if ( isVacant != 0 && staffsNum > isVacant )
               {
                  // ��ɫ
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
            // ���û���¼�ְλ��ְλ����û��Ա�� (ְλģ��Ȩ�ޣ�ְλģ����� ����extend�����¼)
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
    * ��ȡְλ��һ���˵����֣�����ʾ��������
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
                  result = result + "��" + ( "zh".equalsIgnoreCase( language ) ? staffDTO.getStaffVO().getNameZH() : staffDTO.getStaffVO().getNameZH() );
               }
            }

            result = result + ( "zh".equalsIgnoreCase( language ) ? "(��:" + relationVOs.size() + " ��)" : "total " + relationVOs.size() + " persons)" );
         }
         else
         {
            result = "zh".equalsIgnoreCase( language ) ? "��ְλ��ȱ" : "Job Vacancy";
         }
      }

      return result;
   }

   // �жϵ�ǰ�ڵ��Ƿ��Ѿ���Ա�������а���ѡ��
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

   // �жϵ�ǰ�ڵ��Ƿ��Ѿ���ְλ�飬���а���ѡ��
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

   // �жϵ�ǰ�ڵ��Ƿ����������
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

   // �ж��Ƿ�����ӽڵ�
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

   // ����LevelId�õ�CSS��ʽ
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

      // ��������
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
                  // ��ȡTitle
                  final String positionName = positionDTO.getPositionVO().getTitleZH() + " - " + positionDTO.getPositionVO().getTitleEN();
                  // ��ȡbranchName
                  String branchName = "";
                  String agent = "";
                  String agentStart = "";
                  String agentEnd = "";
                  if ( positionStaffRelationVO.getStaffType() != null && positionStaffRelationVO.getStaffType().trim().equals( "2" ) )
                  {
                     // ������������ȡ�����ַ���
                     if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                     {
                        agent = "������";
                     }
                     else
                     {
                        agent = "(Agent)";
                     }
                     // ��ʽ������ʼʱ��
                     agentStart = KANUtil.formatDate( positionStaffRelationVO.getAgentStart(), accountConstants.OPTIONS_DATE_FORMAT );
                     // ��ʽ���������ʱ��
                     agentEnd = KANUtil.formatDate( positionStaffRelationVO.getAgentStart(), accountConstants.OPTIONS_DATE_FORMAT );

                     // ƴװ�����ַ���
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

   // �õ�һ��û��checkbox��positionTree ��ϵ����
   public static String getBasePositionTree( final HttpServletRequest request ) throws KANException
   {
      // ��Constants�еõ���ǰAccount��PositionDTO���б�
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;

      final StringBuffer rs = new StringBuffer();
      rs.append( "<li>" + KANUtil.getProperty( request.getLocale(), "message.click.position.name" ) + "</li>" );
      if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
      {
         // ����PositionDTO
         rs.append( getBasePositionNode( request.getLocale(), "", positionDTOs, 1, true, null, null, BaseAction.getCorpId( request, null ) ) );
      }
      else
      {
         // ����PositionDTO
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

            // ������������ȡTitle
            if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               positionName = positionDTO.getPositionVO().getTitleZH();
            }
            else
            {
               positionName = positionDTO.getPositionVO().getTitleEN();
            }
            // �����ǰ��״̬Ϊͣ��
            if ( "2".equals( positionDTO.getPositionVO().getStatus() ) )
            {
               positionStatus_class = "deleted";
            }

            // ����ְλ��ʾ����ı�ע��Ϣ - Ա��
            tips = KANConstants.getKANAccountConstants( accountId ).getStaffNamesByPositionId( locale.getLanguage(), positionId );

            // ����ְλ��ʾ����ı�ע��Ϣ - ְλ��
            if ( positionDTO.getPositionGroupRelationVOs() != null && positionDTO.getPositionGroupRelationVOs().size() > 0 )
            {
               tips = tips + " - " + positionDTO.getPositionGroupRelationVOs().size() + " ��ְλ��";
            }

            if ( tips != null && !tips.isEmpty() )
            {
               tips = " - " + tips;
            }

            if ( ( corpId == null && KANUtil.filterEmpty( tempCorpId ) == null ) || ( corpId != null && tempCorpId != null && tempCorpId.equals( corpId ) ) )
            {
               // ���ɸ��ڵ�
               rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
               rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasNode( positionDTO.getPositionDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                     + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\"/> " );
               rs.append( "<a style=\"cursor:pointer\" onclick=\"addContactPosition('" + positionId + "','" + positionName + "'); \"><span class='" + positionStatus_class + "'>"
                     + positionName + "</span></a>" );
               rs.append( tips );
               rs.append( "</li>" );

               // �ݹ�������ڵĽڵ�
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

   // ���������򣬸��ڵ�Ϊ��0����
   public static String getSelectHtml( final HttpServletRequest request )
   {
      StringBuffer sb = new StringBuffer();
      try
      {
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
         final List< PositionDTO > positionDTOs = accountConstants.getIndependentDisplayPositionDTOs( BaseAction.getCorpId( request, null ) );
         // ����ְ������
         orderPositionDTOs( positionDTOs, accountConstants, BaseAction.getCorpId( request, null ) );
         String rootPositionId = "";
         if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "rootPositionId" ) ) != null )
         {
            rootPositionId = URLDecoder.decode( URLDecoder.decode( ( String ) request.getAttribute( "rootPositionId" ), "utf-8" ), "utf-8" );
         }
         sb.append( "<select id='orgChartSel'>" );
         for ( PositionDTO positionDTO : positionDTOs )
         {
            //���ְλ��ְλ��Ϊϵͳ�����飬����ʾ
            final List< PositionGroupRelationVO > groupRelationVOs = positionDTO.getPositionGroupRelationVOs();
            boolean isSystemGroup = false;
            if ( groupRelationVOs != null && groupRelationVOs.size() > 0 )
            {
               for ( PositionGroupRelationVO groupRelationVO : groupRelationVOs )
               {
                  final GroupDTO groupDTO = KANConstants.getKANAccountConstants( positionDTO.getPositionVO().getAccountId() ).getGroupDTOByGroupId( groupRelationVO.getGroupId() );
                  if ( groupDTO != null && groupDTO.getGroupVO().getNameZH().contains( "ϵͳ������" ) )
                  {
                     isSystemGroup = true;
                     break;
                  }
               }
            }
            // �����ϵͳ�����飬������
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
            sb.append( "<label style='font-size: 13px;'><font color='#086912'>����ɫ���塱</font>��ʾ����δ����<font color='#782918'>����ɫ���塱</font>��ʾ�������ƣ�<span class='deleted'>����ɫ������</span>��ʾְλ��ͣ�á�</label>" );
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
               // ���I �� Ȩ��С��J����λ��
               final PositionDTO tempDTO = positionDTOs.get( i );
               positionDTOs.set( i, positionDTOs.get( j ) );
               positionDTOs.set( j, tempDTO );
            }
         }
      }

   }

   public static String getPositionTree( final HttpServletRequest request, final boolean hasCheckbox ) throws KANException
   {

      // ϵͳģ����
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_DTO;

      // ����ModuleDTO
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

                  // ������������ȡTitle
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     positionName = positionDTO.getPositionVO().getTitleZH();
                  }
                  else
                  {
                     positionName = positionDTO.getPositionVO().getTitleEN();
                  }

                  // ���ɸ��ڵ�
                  rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\">" );
                  rs.append( "<img id=\"IMG" + nodeId + "\" src=\"images/minus.gif\" onclick=\"kantreeNodeClick('" + nodeId + "');\" /> " );

                  // ��Ҫʹ��Checkbox
                  if ( hasCheckbox )
                  {
                     rs.append( "<input type=\"checkbox\" name=\"positionIds\" id=\"positionIds\" value=\"" + positionId + "\""
                           + ( checkedSelected( positionId, selectedPositionVOs ) ? "checked" : "" ) + ">" );
                  }
                  // ��ʹ��Checkbox������£�����ͼƬ˵����ǰModule�Ƿ���������
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

                  // �ݹ�������ڵĽڵ�
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

   // �жϵ�ǰ�ڵ��Ƿ���Ҫѡ��
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
