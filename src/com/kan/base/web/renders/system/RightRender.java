package com.kan.base.web.renders.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.GroupModuleDTO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionModuleDTO;
import com.kan.base.domain.system.RightVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

/**
 * 所有Right相关控件在此生成
 * 
 * @author Kevin
 */
public class RightRender
{
   /**
    * 根据account生成权限对应的多选框
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRightMultipleChoice( final HttpServletRequest request, final String moduleId, final boolean full ) throws KANException
   {
      // 系统设定的权限 - Super设定Module可以有哪些权限
      List< RightVO > baseRightVOs = KANConstants.getRightVOs();
      // 系统已经选择的权限
      List< RightVO > selectedRightVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // 初始化selectedRightVOs
         if ( KANConstants.getModuleDTOByModuleId( moduleId ) != null && KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO() != null )
         {
            selectedRightVOs = KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO().getSuperSelectedRightVOs();
         }
      }

      return getRightMultipleChoice( request, baseRightVOs, null, selectedRightVOs, full, null );
   }

   /**
    * 根据account生成权限对应的多选框
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRightMultipleChoiceByAccountId( final HttpServletRequest request, final String accountId, final String moduleId, final boolean full )
         throws KANException
   {
      // 系统设定的权限 - Super设定Module可以有哪些权限
      List< RightVO > baseRightVOs = KANConstants.getRightVOs();
      // 用户针对Account全局设定的权限
      List< RightVO > accountRightVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // 初始化baseRightVOs
         if ( KANConstants.getModuleDTOByModuleId( moduleId ) != null && KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO() != null )
         {
            baseRightVOs = KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO().getRightVOs();
         }
         // 初始化accountRightVOs
         if ( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ) != null )
         {
            accountRightVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ).getAccountRightVOs();
         }
      }

      return getRightMultipleChoice( request, baseRightVOs, null, accountRightVOs, full, moduleId );
   }

   /**
    * 根据职位生成权限对应的多选框
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRightMultipleChoiceByPositionId( final HttpServletRequest request, final String positionId, final String moduleId, final boolean full )
         throws KANException
   {
      // 系统设定的权限 - Super设定Module可以有哪些权限
      List< RightVO > baseRightVOs = null;
      // 用户针对Account全局设定的权限
      List< RightVO > accountRightVOs = null;
      // 针对当前Position设定的权限
      List< RightVO > selectedRightVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // 初始化baseRightVOs
         if ( KANConstants.getModuleDTOByModuleId( moduleId ) != null && KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO() != null )
         {
            baseRightVOs = KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO().getRightVOs();
         }
         // 初始化accountRightVOs
         if ( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ) != null )
         {
            accountRightVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ).getAccountRightVOs();
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
                  selectedRightVOs = positionModuleDTO.getSelectedRightVOs();
               }
            }

         }
      }

      return getRightMultipleChoice( request, baseRightVOs, accountRightVOs, selectedRightVOs, full, null );
   }

   /**
    * 根据职位组生成权限对应的多选框
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRightMultipleChoiceByGroupId( final HttpServletRequest request, final String groupId, final String moduleId, final boolean full ) throws KANException
   {
      // 系统设定的权限 - Super设定Module可以有哪些权限
      List< RightVO > baseRightVOs = null;
      // 用户针对Account全局设定的权限
      List< RightVO > accountRightVOs = null;
      // 针对当前Position设定的权限
      List< RightVO > selectedRightVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // 初始化baseRightVOs
         if ( KANConstants.getModuleDTOByModuleId( moduleId ) != null && KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO() != null )
         {
            baseRightVOs = KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO().getRightVOs();
         }
         // 初始化accountRightVOs
         if ( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ) != null )
         {
            accountRightVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ).getAccountRightVOs();
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
                  selectedRightVOs = groupModuleDTO.getSelectedRightVOs();
               }
            }
         }
      }

      return getRightMultipleChoice( request, baseRightVOs, accountRightVOs, selectedRightVOs, full, null );
   }

   /**
    * 生成权限对应的多选框
    * 
    * HttpServletRequest request
    * List< RightVO > baseRightVOs - 系统设定的权限
    * List< RightVO > accountRightVOs - Account全局设定的权限
    * List< RightVO > selectedRightVOs - 目标对象设定的权限
    * boolean full - 生成简洁的树还是完整的树
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRightMultipleChoice( final HttpServletRequest request, final List< RightVO > baseRightVOs, final List< RightVO > accountRightVOs,
         final List< RightVO > selectedRightVOs, final boolean full, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // 生成Right Tree
      if ( baseRightVOs != null && baseRightVOs.size() > 0 )
      {
         if ( !full )
         {
            rs.append( "<span>" + KANUtil.getProperty( request.getLocale(), "security.position.group.set.right.tips" ) + "</span>" );
         }
         rs.append( "<ol class=\"auto\">" );
         if ( !full )
         {
            rs.append( "<li style=\"margin: 2px 0px;\">" );
         }
         for ( RightVO rightVO : baseRightVOs )
         {
            if ( rightVO.getRightType() != null && rightVO.getRightType().trim().equals( "1" ) )
            {
               String rightName = "";

               // 按照语言设置取Right的名称
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  rightName = rightVO.getNameZH();
               }
               else
               {
                  rightName = rightVO.getNameEN();
               }

               if ( full )
               {
                  rs.append( "<li style=\"margin: 2px 0px;\">" );
               }

               if ( checkedAccount( rightVO.getRightId(), accountRightVOs ) )
               {
                  rs.append( "<img src=\"images/selected.png\" />" + rightName );
               }
               // 如果当前选中的moduleId是全局规则则不允许编辑权限
               else if ( checkedSelected( rightVO.getRightId(), selectedRightVOs ) && "22".equals( moduleId ) )
               {
                  rs.append( "<img src=\"images/selected.png\" />" + rightName );
                  rs.append( "<input type=\"checkbox\" id=\"rightIdArray\" name=\"rightIdArray\" value=\"" + rightVO.getRightId() + "\" " + "checked" + " style='display:none'"
                        + " />" );
               }
               else
               {
                  rs.append( "<input type=\"checkbox\" id=\"rightIdArray\" name=\"rightIdArray\" value=\"" + rightVO.getRightId() + "\" "
                        + ( checkedSelected( rightVO.getRightId(), selectedRightVOs ) ? "checked" : "" ) + " />" + rightName );
               }

               if ( full )
               {
                  rs.append( "</li>" );
               }
               else
               {
                  rs.append( "&nbsp;&nbsp;&nbsp;&nbsp;" );
               }
            }
         }
         if ( !full )
         {
            rs.append( "</li>" );
         }
         rs.append( "</ol>" );
      }

      return rs.toString();
   }

   // 判断当前节点是否由Account全局设定
   private static boolean checkedAccount( final String rightId, final List< RightVO > accountRightVOs )
   {
      if ( accountRightVOs != null )
      {
         for ( RightVO rightVO : accountRightVOs )
         {
            if ( rightVO != null && rightVO.getRightId() != null && rightVO.getRightId().trim().equals( rightId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   // 判断当前节点是否需要选中
   private static boolean checkedSelected( final String rightId, final List< RightVO > selectedRightVOs )
   {
      if ( selectedRightVOs != null )
      {
         for ( RightVO rightVO : selectedRightVOs )
         {
            if ( rightVO != null && rightVO.getRightId() != null && rightVO.getRightId().trim().equals( rightId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   public static String getRightHorizontalMultipleChoice( final HttpServletRequest request, final List< RightVO > rightVOs, final String checkBoxName, final String[] selectRightIds )
   {
      final StringBuilder sb = new StringBuilder();

      for ( RightVO rightVO : rightVOs )
      {
         sb.append( "<label class='auto serviceType_3'><input type=\"checkbox\" name= \"" ).append( checkBoxName ).append( "\" value=\"" );
         sb.append( rightVO.getRightId() ).append( "\"" );
         //如果被选中
         if ( hasContain( selectRightIds, rightVO.getRightId() ) )
         {
            sb.append( "checked=\"checked\" " );
         }
         sb.append( "/>" );// end input
         String rightName = "";

         // 按照语言设置取Right的名称
         if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            rightName = rightVO.getNameZH();
         }
         else
         {
            rightName = rightVO.getNameEN();
         }
         sb.append( rightName );
         sb.append( "</label>" );
      }
      return sb.toString();

   }

   public static String getRightHorizontalMultipleChoice( final HttpServletRequest request, final String[] rightIds, final String checkBoxName, final String[] selectRightIds )
   {

      final StringBuilder sb = new StringBuilder();
      if ( rightIds != null )
      {
         for ( String rightId : rightIds )
         {
            RightVO rightVO = KANConstants.getRightVOByRightId( rightId );
            //<label class='auto serviceType_3'><input type="checkbox" name="ck" id="kanList_chkSelectRecord_23" name="chkSelectRow[]" value="4" />1</label>
            sb.append( "<label class='auto serviceType_3'><input type=\"checkbox\" name= \"" ).append( checkBoxName ).append( "\" value=\"" );
            sb.append( rightVO.getRightId() ).append( "\"" );
            //如果被选中
            if ( rightVO != null && hasContain( selectRightIds, rightId ) )
            {
               sb.append( "checked=\"checked\" " );
            }
            sb.append( "/>" );// end input
            String rightName = "";

            // 按照语言设置取Right的名称
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               rightName = rightVO.getNameZH();
            }
            else
            {
               rightName = rightVO.getNameEN();
            }
            sb.append( rightName );
            sb.append( "</label>" );

         }
      }
      else
      {
         sb.append( "<font color=red >无符合的权限</font>" );

      }

      return sb.toString();

   }

   /***
    * 判断list 数组里面是否有targate 对象, 比较方法是equals()
    * @param list
    * @param targate
    * @return
    */

   public static boolean hasContain( Object[] list, Object targate )
   {
      if ( list == null )
         return false;
      for ( Object obj : list )
      {
         if ( obj.equals( targate ) )
         {
            return true;
         }
      }
      return false;
   }
}
