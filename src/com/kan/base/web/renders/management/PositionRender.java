package com.kan.base.web.renders.management;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.management.PositionDTO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class PositionRender
{
   // 得到整个Position的Tree
   public static String getPositionTree( final HttpServletRequest request ) throws KANException
   {
      final String accountId = ( String ) request.getAttribute( "accountId" );
      // 从常量中得到当前Account的PositionDTO的列表
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( accountId ).EMPLOYEE_POSITION_DTO;

      final StringBuffer rs = new StringBuffer();
      rs.append( "“点击职位名”查看或编辑职位信息，点击" );
      rs.append( " <img src=\"images/add.png\" /> " );
      rs.append( "可添加下级职位。" );

      // 遍历PositionDTO
      rs.append( getPositionNode( request.getLocale(), "", positionDTOs, 1, true, null, null ) );

      return rs.toString();
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

            // 按照语言设置取Title
            if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               positionName = positionDTO.getPositionVO().getTitleZH();
               employeeContractNumInfo = "（" + positionDTO.getEmployeeContractNumber() + "）";
            }
            else
            {
               positionName = positionDTO.getPositionVO().getTitleEN();
               employeeContractNumInfo = "(" + positionDTO.getEmployeeContractNumber() + ")";
            }

            // 生成根节点
            rs.append( "<li id=\"" + nodeId + "\" class=\"" + getClassNameByLevel( level ) + "\" " + ( level > 2 ? "style=\"display: none\"" : "" ) + ">" );
            rs.append( "<img id=\"IMG" + nodeId + "\" src=\"" + ( level == 2 && hasNode( positionDTO.getPositionDTOs() ) ? "images/plus.gif" : "images/minus.gif" )
                  + "\" onclick=\"kantreeNodeClick('" + nodeId + "');\"/> " );
            rs.append( "<input type=\"checkbox\" "+(!"（0）".equals( employeeContractNumInfo)?"disabled=\"disabled\"":"")+" name=\"positionIdArray\" id=\"positionIdArray\" value=\"" + positionId + "\"" + checked
                  + "/><a href=\"#\" id=\"positionName\" onclick=\"managePosition('" + encodedPositionId + "');\">" + positionName + employeeContractNumInfo + "</a>" );
            rs.append( "<img src=\"images/add.png\" onclick=\"addChildPosition('" + encodedPositionId + "');\" />" );
            rs.append( tips );
            rs.append( "</li>" );

            // 递归遍历存在的节点
            rs.append( getPositionNode( locale, nodeId, positionDTO.getPositionDTOs(), level + 1, full, flag, bindId ) );
         }

         return rs.toString();
      }
      else
      {
         return "";
      }
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
