package com.kan.base.web.renders.management;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.management.PositionDTO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class PositionRender
{
   // �õ�����Position��Tree
   public static String getPositionTree( final HttpServletRequest request ) throws KANException
   {
      final String accountId = ( String ) request.getAttribute( "accountId" );
      // �ӳ����еõ���ǰAccount��PositionDTO���б�
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( accountId ).EMPLOYEE_POSITION_DTO;

      final StringBuffer rs = new StringBuffer();
      rs.append( "�����ְλ�����鿴��༭ְλ��Ϣ�����" );
      rs.append( " <img src=\"images/add.png\" /> " );
      rs.append( "������¼�ְλ��" );

      // ����PositionDTO
      rs.append( getPositionNode( request.getLocale(), "", positionDTOs, 1, true, null, null ) );

      return rs.toString();
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
         final String flag, final String bindId ) throws KANException
   {
      if ( hasNode( positionDTOs ) )
      {
         final StringBuffer rs = new StringBuffer();

         for ( PositionDTO positionDTO : positionDTOs )
         {
            final String positionId = positionDTO.getPositionVO().getPositionId();
            final String encodedPositionId = positionDTO.getPositionVO().getEncodedId();
            final String nodeId = parentNodeId.concat( "_N" ).concat( positionId );
            String positionName = "";
            String employeeContractNumInfo = "0";
            String checked = "";
            String tips = "";

            // ������������ȡTitle
            if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               positionName = positionDTO.getPositionVO().getTitleZH();
               employeeContractNumInfo = "��" + positionDTO.getEmployeeContractNumber() + "��";
            }
            else
            {
               positionName = positionDTO.getPositionVO().getTitleEN();
               employeeContractNumInfo = "(" + positionDTO.getEmployeeContractNumber() + ")";
            }

            // ���ɸ��ڵ�
            rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
            rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasNode( positionDTO.getPositionDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                  + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\"/> " );
            rs.append( "<input type=\"checkbox\" "+(!"��0��".equals( employeeContractNumInfo)?"disabled=\"disabled\"":"")+" name=\"positionIdArray\" id=\"positionIdArray\" value=\"" + positionId + "\"" + checked
                  + "/><a href=\"#\" id=\"positionName\" onclick=\"managePosition('" + encodedPositionId + "');\">" + positionName + employeeContractNumInfo + "</a>" );
            rs.append( "<img src=\"images/add.png\" onclick=\"addChildPosition('" + encodedPositionId + "');\" />" );
            rs.append( tips );
            rs.append( "</li>" );

            // �ݹ�������ڵĽڵ�
            rs.append( getPositionNode( locale, nodeId, positionDTO.getPositionDTOs(), level + 1, full, flag, bindId ) );
         }

         return rs.toString();
      }
      else
      {
         return "";
      }
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
