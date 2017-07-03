package com.kan.base.web.renders.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.security.GroupVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class GroupRender
{

   // 生成Group的多选框，根据json字符串判断是否选中
   public static String getGroupMultipleChoiceByJsonString( final HttpServletRequest request, final String jsonString ) throws KANException
   {
      return getGroupMultipleChoice( request, null, jsonString );
   }

   // 生成Group的多选框，根据positionId字符串判断是否选中
   public static String getGroupMultipleChoiceByPositionId( final HttpServletRequest request, final String positionId ) throws KANException
   {
      if ( positionId != null )
      {
         final PositionDTO positionDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionDTOByPositionId( positionId );
         return getGroupMultipleChoice( request, positionDTO, null );
      }
      return getGroupMultipleChoice( request, null, null );
   }

   // 生成Group的多选框
   private static String getGroupMultipleChoice( final HttpServletRequest request, final PositionDTO positionDTO, final String jsonString ) throws KANException
   {
      // 获得当前AccountId职位组
      final List< GroupVO > positionGroupVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionGroupVOs( BaseAction.getCorpId( request, null ) );

      // 初始化职级 - 职位组关系列表
      List< PositionGroupRelationVO > relationVOsByPositionId = null;

      // 初始化positionGroupArray
      String[] positionGroupArray = null;

      if ( positionDTO != null )
      {
         relationVOsByPositionId = positionDTO.getPositionGroupRelationVOs();
      }

      if ( jsonString != null && !"".equals( jsonString ) )
      {
         positionGroupArray = KANUtil.jasonArrayToStringArray( jsonString );
      }

      final StringBuffer rs = new StringBuffer();

      if ( positionGroupVOs != null && positionGroupVOs.size() > 0 )
      {
         for ( GroupVO positionGroupVO : positionGroupVOs )
         {
            // 只显示启用状态的数据
            if ( positionGroupVO != null && "1".equals( positionGroupVO.getStatus() ) )
            {
               String tempGroupId = positionGroupVO.getGroupId();
               String groupName = "";
               String checked = "";

               // 根据positionId或jsonString判断是否需要选中
               if ( relationVOsByPositionId != null && relationVOsByPositionId.size() > 0 )
               {
                  checked = checked( tempGroupId, relationVOsByPositionId ) ? "checked" : "";
               }
               else if ( positionGroupArray != null && positionGroupArray.length > 0 )
               {
                  checked = checked( tempGroupId, positionGroupArray ) ? "checked" : "";
               }

               // 按照语言设置取Title
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  groupName = positionGroupVO.getNameZH();
               }
               else
               {
                  groupName = positionGroupVO.getNameEN();
               }

               rs.append( "<li>" );
               rs.append( "<input type=\"checkbox\" id=\"groupIdArray\" name=\"groupIdArray\" value=\"" + positionGroupVO.getGroupId() + "\" " + checked + " />" + groupName );
               rs.append( "</li>" );
            }
         }

      }

      return rs.toString();
   }

   // 判断当前节点是否需要选中
   private static boolean checked( final String groupId, final List< PositionGroupRelationVO > relationVOsByPositionId )
   {
      for ( PositionGroupRelationVO relationVO : relationVOsByPositionId )
      {
         if ( relationVO.getGroupId() != null && relationVO.getGroupId().trim().equals( groupId ) )
         {
            return true;
         }
      }

      return false;
   }

   // 判断当前节点是否需要选中
   private static boolean checked( final String groupId, final String[] positionGroupArray )
   {
      for ( String temp : positionGroupArray )
      {
         if ( temp.trim().equals( groupId ) )
         {
            return true;
         }
      }

      return false;
   }

   public static String getBaseGroup( final HttpServletRequest request, final String jsonString ) throws KANException
   {
      return getBaseGroupHtml( request, null, jsonString );
   }

   // 生成Group的列表
   private static String getBaseGroupHtml( final HttpServletRequest request, final PositionDTO positionDTO, final String jsonString ) throws KANException
   {
      // 获得当前AccountId职位组
      final List< GroupVO > positionGroupVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionGroupVOs( BaseAction.getCorpId( request, null ) );

      final StringBuffer rs = new StringBuffer();
      rs.append( "<li>" + KANUtil.getProperty( request.getLocale(), "message.click.position.group.name" ) + "</li>" );
      if ( positionGroupVOs != null && positionGroupVOs.size() > 0 )
      {
         for ( GroupVO positionGroupVO : positionGroupVOs )
         {
            // 只显示启用状态的数据
            if ( positionGroupVO != null && "1".equals( positionGroupVO.getStatus() ) )
            {
               String groupName = "";

               // 按照语言设置取Title
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  groupName = positionGroupVO.getNameZH();
               }
               else
               {
                  groupName = positionGroupVO.getNameEN();
               }

               rs.append( "<li style=\"width:33%;float:left;margin-top:10px\" >" );
               rs.append( "<a style=\"cursor:pointer\" onclick=\"addContactGroup('" + positionGroupVO.getGroupId() + "','" + groupName + "'); \"><span>" + groupName
                     + "</span></a>" );
               rs.append( "</li>" );
            }
         }

      }

      return rs.toString();
   }

}
