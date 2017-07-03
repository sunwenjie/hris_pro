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
 * ����Right��ؿؼ��ڴ�����
 * 
 * @author Kevin
 */
public class RightRender
{
   /**
    * ����account����Ȩ�޶�Ӧ�Ķ�ѡ��
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRightMultipleChoice( final HttpServletRequest request, final String moduleId, final boolean full ) throws KANException
   {
      // ϵͳ�趨��Ȩ�� - Super�趨Module��������ЩȨ��
      List< RightVO > baseRightVOs = KANConstants.getRightVOs();
      // ϵͳ�Ѿ�ѡ���Ȩ��
      List< RightVO > selectedRightVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // ��ʼ��selectedRightVOs
         if ( KANConstants.getModuleDTOByModuleId( moduleId ) != null && KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO() != null )
         {
            selectedRightVOs = KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO().getSuperSelectedRightVOs();
         }
      }

      return getRightMultipleChoice( request, baseRightVOs, null, selectedRightVOs, full, null );
   }

   /**
    * ����account����Ȩ�޶�Ӧ�Ķ�ѡ��
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRightMultipleChoiceByAccountId( final HttpServletRequest request, final String accountId, final String moduleId, final boolean full )
         throws KANException
   {
      // ϵͳ�趨��Ȩ�� - Super�趨Module��������ЩȨ��
      List< RightVO > baseRightVOs = KANConstants.getRightVOs();
      // �û����Accountȫ���趨��Ȩ��
      List< RightVO > accountRightVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // ��ʼ��baseRightVOs
         if ( KANConstants.getModuleDTOByModuleId( moduleId ) != null && KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO() != null )
         {
            baseRightVOs = KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO().getRightVOs();
         }
         // ��ʼ��accountRightVOs
         if ( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ) != null )
         {
            accountRightVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ).getAccountRightVOs();
         }
      }

      return getRightMultipleChoice( request, baseRightVOs, null, accountRightVOs, full, moduleId );
   }

   /**
    * ����ְλ����Ȩ�޶�Ӧ�Ķ�ѡ��
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRightMultipleChoiceByPositionId( final HttpServletRequest request, final String positionId, final String moduleId, final boolean full )
         throws KANException
   {
      // ϵͳ�趨��Ȩ�� - Super�趨Module��������ЩȨ��
      List< RightVO > baseRightVOs = null;
      // �û����Accountȫ���趨��Ȩ��
      List< RightVO > accountRightVOs = null;
      // ��Ե�ǰPosition�趨��Ȩ��
      List< RightVO > selectedRightVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // ��ʼ��baseRightVOs
         if ( KANConstants.getModuleDTOByModuleId( moduleId ) != null && KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO() != null )
         {
            baseRightVOs = KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO().getRightVOs();
         }
         // ��ʼ��accountRightVOs
         if ( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ) != null )
         {
            accountRightVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ).getAccountRightVOs();
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
                  selectedRightVOs = positionModuleDTO.getSelectedRightVOs();
               }
            }

         }
      }

      return getRightMultipleChoice( request, baseRightVOs, accountRightVOs, selectedRightVOs, full, null );
   }

   /**
    * ����ְλ������Ȩ�޶�Ӧ�Ķ�ѡ��
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRightMultipleChoiceByGroupId( final HttpServletRequest request, final String groupId, final String moduleId, final boolean full ) throws KANException
   {
      // ϵͳ�趨��Ȩ�� - Super�趨Module��������ЩȨ��
      List< RightVO > baseRightVOs = null;
      // �û����Accountȫ���趨��Ȩ��
      List< RightVO > accountRightVOs = null;
      // ��Ե�ǰPosition�趨��Ȩ��
      List< RightVO > selectedRightVOs = null;

      if ( moduleId != null && !moduleId.trim().equals( "" ) )
      {
         // ��ʼ��baseRightVOs
         if ( KANConstants.getModuleDTOByModuleId( moduleId ) != null && KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO() != null )
         {
            baseRightVOs = KANConstants.getModuleDTOByModuleId( moduleId ).getModuleVO().getRightVOs();
         }
         // ��ʼ��accountRightVOs
         if ( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ) != null )
         {
            accountRightVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getAccountModuleDTOByModuleId( moduleId ).getAccountRightVOs();
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
                  selectedRightVOs = groupModuleDTO.getSelectedRightVOs();
               }
            }
         }
      }

      return getRightMultipleChoice( request, baseRightVOs, accountRightVOs, selectedRightVOs, full, null );
   }

   /**
    * ����Ȩ�޶�Ӧ�Ķ�ѡ��
    * 
    * HttpServletRequest request
    * List< RightVO > baseRightVOs - ϵͳ�趨��Ȩ��
    * List< RightVO > accountRightVOs - Accountȫ���趨��Ȩ��
    * List< RightVO > selectedRightVOs - Ŀ������趨��Ȩ��
    * boolean full - ���ɼ�����������������
    */
   // Code reviewed by Kevin at 2013-06-26
   public static String getRightMultipleChoice( final HttpServletRequest request, final List< RightVO > baseRightVOs, final List< RightVO > accountRightVOs,
         final List< RightVO > selectedRightVOs, final boolean full, final String moduleId ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // ����Right Tree
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

               // ������������ȡRight������
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
               // �����ǰѡ�е�moduleId��ȫ�ֹ���������༭Ȩ��
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

   // �жϵ�ǰ�ڵ��Ƿ���Accountȫ���趨
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

   // �жϵ�ǰ�ڵ��Ƿ���Ҫѡ��
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
         //�����ѡ��
         if ( hasContain( selectRightIds, rightVO.getRightId() ) )
         {
            sb.append( "checked=\"checked\" " );
         }
         sb.append( "/>" );// end input
         String rightName = "";

         // ������������ȡRight������
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
            //�����ѡ��
            if ( rightVO != null && hasContain( selectRightIds, rightId ) )
            {
               sb.append( "checked=\"checked\" " );
            }
            sb.append( "/>" );// end input
            String rightName = "";

            // ������������ȡRight������
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
         sb.append( "<font color=red >�޷��ϵ�Ȩ��</font>" );

      }

      return sb.toString();

   }

   /***
    * �ж�list ���������Ƿ���targate ����, �ȽϷ�����equals()
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
