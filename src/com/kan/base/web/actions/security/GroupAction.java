package com.kan.base.web.actions.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.GroupModuleRuleRelationVO;
import com.kan.base.domain.security.GroupVO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.GroupService;
import com.kan.base.service.inf.security.PositionService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class GroupAction extends BaseAction
{

   public static String accessAction = "HRO_SEC_POSITIONGROUP";

   /**
    * List Group
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final GroupService groupService = ( GroupService ) getService( "groupService" );
         // ���Action Form
         final GroupVO groupVO = ( GroupVO ) form;

         // ����subAction
         dealSubAction( groupVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder groupHolder = new PagedListHolder();

         // ���뵱ǰҳ
         groupHolder.setPage( page );
         // ���뵱ǰֵ����
         groupHolder.setObject( groupVO );
         // ����ҳ���¼����
         groupHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         groupService.getGroupVOsByCondition( groupHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( groupHolder, request );
         // Holder��д��Request����
         request.setAttribute( "groupHolder", groupHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax��������ת
            return mapping.findForward( "listGroupTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listGroup" );
   }

   /**
    * To Group Create
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( GroupVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( GroupVO ) form ).setStatus( "1" );

      // ��ʼ������Tab Number
      request.setAttribute( "positionCount", 0 );

      // ��ת���½�����
      return mapping.findForward( "manageGroup" );
   }

   /**
    * To Group Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final GroupService groupService = ( GroupService ) getService( "groupService" );
         // ��õ�ǰ����
         String groupId = KANUtil.decodeString( request.getParameter( "groupId" ) );
         if ( KANUtil.filterEmpty( groupId ) == null )
         {
            groupId = ( ( GroupVO ) form ).getGroupId();
         }
         // ���������Ӧ����
         final GroupVO groupVO = groupService.getGroupVOByGroupId( groupId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         groupVO.reset( null, request );
         groupVO.setSubAction( VIEW_OBJECT );

         // Checkbox����
         groupVO.setHrFunction( ( groupVO.getHrFunction() != null && groupVO.getHrFunction().equalsIgnoreCase( GroupVO.TRUE ) ) ? "on" : "" );

         // ���Position Group Count����Tab Number��ʾ

         int positionCount = 0;
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
         if ( accountConstants != null )
         {
            final GroupDTO groupDTO = accountConstants.getGroupDTOByGroupId( groupId );
            if ( groupDTO != null && groupDTO.getPositionGroupRelationVOs() != null )
            {
               positionCount = groupDTO.getPositionGroupRelationVOs().size();
            }
         }
         request.setAttribute( "positionCount", positionCount );

         request.setAttribute( "groupForm", groupVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageGroup" );
   }

   /**
    * Add Group
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final GroupService groupService = ( GroupService ) getService( "groupService" );
            final StaffService staffService = ( StaffService ) getService( "staffService" );

            // ���ActionForm
            final GroupVO groupVO = ( GroupVO ) form;

            // ��ȡ��¼�û�
            groupVO.setAccountId( getAccountId( request, response ) );
            groupVO.setCreateBy( getUserId( request, response ) );
            groupVO.setModifyBy( getUserId( request, response ) );

            // HrServiceĬ��ΪHrFunction
            if ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) )
            {
               groupVO.setHrFunction( GroupVO.TRUE );
            }
            else
            {
               // Checkbox����
               groupVO.setHrFunction( ( groupVO.getHrFunction() != null && groupVO.getHrFunction().equalsIgnoreCase( "on" ) ) ? GroupVO.TRUE : GroupVO.FALSE );
            }

            // �½�����
            groupService.insertGroup( groupVO );

            // ��ʼ��StaffId�ַ����б�
            final List< String > staffIds = new ArrayList< String >();

            if ( groupVO.getPositionIdArray() != null && groupVO.getPositionIdArray().length > 0 )
            {
               for ( String positionId : groupVO.getPositionIdArray() )
               {
                  String tempPositionId = "";

                  if ( positionId.contains( "_" ) )
                  {
                     tempPositionId = positionId.split( "_" )[ 0 ];
                  }

                  final List< Object > positionStaffRelationVOs = staffService.getPositionStaffRelationVOsByPositionId( tempPositionId );

                  // װ�ظ���ǰ��StaffIds
                  if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
                  {
                     for ( Object object : positionStaffRelationVOs )
                     {
                        if ( !staffIds.contains( ( ( PositionStaffRelationVO ) object ).getStaffId() ) )
                        {
                           staffIds.add( ( ( PositionStaffRelationVO ) object ).getStaffId() );
                        }
                     }
                  }
               }
            }

            insertlog( request, groupVO, Operate.ADD, groupVO.getGroupId(), "ְλ��staffIds: " + KANUtil.stringListToJasonArray( staffIds ) );
            // ���¼��س����е�PositionGroup��Position��Staff
            constantsInit( "initPositionGroup", getAccountId( request, response ) );
            constantsInit( "initPosition", getAccountId( request, response ) );

            // ���¼���Ӱ���Staff
            if ( staffIds != null && staffIds.size() > 0 )
            {
               for ( String staffId : staffIds )
               {
                  constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffId } );
               }
            }

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }
         else
         {
            // ���Form
            ( ( GroupVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify group
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final GroupService groupService = ( GroupService ) getService( "groupService" );
            final PositionService positionService = ( PositionService ) getService( "positionService" );
            final StaffService staffService = ( StaffService ) getService( "staffService" );

            // ��õ�ǰ����
            final String groupId = KANUtil.decodeString( request.getParameter( "groupId" ) );

            // ���������Ӧ����
            final GroupVO groupVO = groupService.getGroupVOByGroupId( groupId );

            // ��ʼ��PositionGroupRelationVO�б�����ǰ��
            final List< Object > updateBeforePositionGroupRelationVOs = positionService.getRelationVOsByGroupId( groupId );

            // ��ʼ��StaffId�ַ����б�
            final List< String > staffIds = new ArrayList< String >();

            if ( updateBeforePositionGroupRelationVOs != null && updateBeforePositionGroupRelationVOs.size() > 0 )
            {
               for ( Object positionGroupRelationVOObject : updateBeforePositionGroupRelationVOs )
               {
                  // ��ʼ��PositionVO
                  final List< Object > positionStaffRelationVOs = staffService.getPositionStaffRelationVOsByPositionId( ( ( PositionGroupRelationVO ) positionGroupRelationVOObject ).getPositionId() );

                  // װ�ظ���ǰ��StaffIds
                  if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
                  {
                     for ( Object positionStaffRelationVOObject : positionStaffRelationVOs )
                     {
                        if ( !staffIds.contains( ( ( PositionStaffRelationVO ) positionStaffRelationVOObject ).getStaffId() ) )
                        {
                           staffIds.add( ( ( PositionStaffRelationVO ) positionStaffRelationVOObject ).getStaffId() );
                        }
                     }
                  }
               }
            }

            // ��ȡ��¼�û�
            groupVO.update( ( GroupVO ) form );
            groupVO.setModifyBy( getUserId( request, response ) );

            // HrServiceĬ��ΪHrFunction
            if ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) )
            {
               groupVO.setHrFunction( GroupVO.TRUE );
            }
            else
            {
               // Checkbox����
               groupVO.setHrFunction( ( groupVO.getHrFunction() != null && groupVO.getHrFunction().equalsIgnoreCase( "on" ) ) ? GroupVO.TRUE : GroupVO.FALSE );
            }

            // �޸Ķ���
            groupService.updateGroup( groupVO );

            // ��ʼ��PositionGroupRelationVO�б����º�
            final List< Object > updateAfterPositionGroupRelationVOs = positionService.getRelationVOsByGroupId( groupId );

            if ( updateAfterPositionGroupRelationVOs != null && updateAfterPositionGroupRelationVOs.size() > 0 )
            {
               for ( Object positionGroupRelationVOObject : updateAfterPositionGroupRelationVOs )
               {
                  // ��ʼ��PositionVO
                  final List< Object > positionStaffRelationVOs = staffService.getPositionStaffRelationVOsByPositionId( ( ( PositionGroupRelationVO ) positionGroupRelationVOObject ).getPositionId() );

                  // װ�ظ���ǰ��StaffIds
                  if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
                  {
                     for ( Object positionStaffRelationVOObject : positionStaffRelationVOs )
                     {
                        if ( !staffIds.contains( ( ( PositionStaffRelationVO ) positionStaffRelationVOObject ).getStaffId() ) )
                        {
                           staffIds.add( ( ( PositionStaffRelationVO ) positionStaffRelationVOObject ).getStaffId() );
                        }
                     }
                  }
               }
            }

            insertlog( request, groupVO, Operate.MODIFY, groupVO.getGroupId(), "ְλ��staffIds: " + KANUtil.stringListToJasonArray( staffIds ) );
            // ���¼��س����е�PositionGroup��Position
            constantsInit( "initPositionGroup", getAccountId( request, response ) );
            constantsInit( "initPosition", getAccountId( request, response ) );

            // ���¼���Ӱ���Staff
            if ( staffIds != null && staffIds.size() > 0 )
            {
               for ( String staffId : staffIds )
               {
                  constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffId } );
               }
            }

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Form����
         ( ( GroupVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת����ѯ
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Group Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final GroupService groupService = ( GroupService ) getService( "groupService" );

         // ��õ�ǰ����
         final String encodeGroupId = request.getParameter( "groupId" );
         String groupId = "";
         GroupVO groupVO = new GroupVO();

         // ���GroupId��Ϊ�յ�����������ݿ����GroupVO����
         if ( encodeGroupId != null && !encodeGroupId.equals( "" ) )
         {
            groupId = KANUtil.decodeStringFromAjax( encodeGroupId );
            groupVO = groupService.getGroupVOByGroupId( groupId );
         }

         // �����ύ����������
         groupVO.update( ( GroupVO ) form );
         // ����
         groupVO.setNameEN( URLDecoder.decode( groupVO.getNameEN(), "UTF-8" ) );

         groupVO.setAccountId( getAccountId( request, response ) );
         groupVO.setModifyBy( getUserId( request, response ) );

         // ���GroupId��Ϊ�յ�������޸�GroupModule���󣬷�֮����GroupVOһ���½�
         if ( encodeGroupId != null && !encodeGroupId.equals( "" ) )
         {
            // �޸�GroupModule����
            groupService.updateGroupModule( groupVO );
         }
         else
         {
            // �½�Position����
            groupService.insertGroup( groupVO );
         }

         // ���ر༭�ɹ����
         success( request, MESSAGE_TYPE_UPDATE );

         insertlog( request, groupVO, Operate.MODIFY, groupVO.getGroupId(), "modify_object_ajax" );

         // ���Form����
         ( ( GroupVO ) form ).reset();

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // Send to client
         out.println( URLEncoder.encode( Cryptogram.encodeString( groupVO.getGroupId() ), "UTF-8" ) );
         out.flush();
         out.close();

         // ���¼��س����е�Group
         constantsInit( "initPositionGroup", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax���ã���ת���հ�ҳ��
      return mapping.findForward( "" );
   }

   /**  
    * Modify Object Ajax Popup
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward modify_object_ajax_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final GroupService groupService = ( GroupService ) getService( "groupService" );

         // ���GroupId�� moduleId
         final String groupId = KANUtil.decodeStringFromAjax( request.getParameter( "groupId" ) );
         final String moduleId = request.getParameter( "moduleId" );

         final GroupVO groupVO = groupService.getGroupVOByGroupId( groupId );

         // ����GroupVO �� RightIdArray��RuleIdArray
         groupVO.setRightIdArray( ( ( GroupVO ) form ).getRightIdArray() );
         groupVO.setRuleIdArray( ( ( GroupVO ) form ).getRuleIdArray() );

         groupVO.setAccountId( getAccountId( request, response ) );
         groupVO.setModifyBy( getUserId( request, response ) );

         // �������ݿ�
         groupService.updateGroupModuleRelationPopup( groupVO, moduleId );

         // ���ر༭�ɹ����
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

         insertlog( request, groupVO, Operate.MODIFY, groupVO.getGroupId(), "modify_object_ajax_popup" );

         // ���Form����
         ( ( GroupVO ) form ).reset();

         // ���¼��س����е�Group
         constantsInit( "initPositionGroup", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax���ã���ת���հ�ҳ��
      return mapping.findForward( "" );
   }

   /**
    * Delete Group
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final GroupService groupService = ( GroupService ) getService( "groupService" );

         final GroupVO groupVO = new GroupVO();
         // ��õ�ǰ����
         final String groupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "groupId" ), "UTF-8" ) );

         // ɾ��������Ӧ����
         groupVO.setGroupId( groupId );
         groupVO.setModifyBy( getUserId( request, response ) );

         groupService.deleteGroup( groupVO );

         insertlog( request, groupVO, Operate.DELETE, groupVO.getGroupId(), null );

         // ���¼��س����е�group
         constantsInit( "initPositionGroup", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Group List
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final GroupService groupService = ( GroupService ) getService( "groupService" );

         // ���Action Form
         GroupVO groupVO = ( GroupVO ) form;
         // ����ѡ�е�ID
         if ( groupVO.getSelectedIds() != null && !groupVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : groupVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               groupVO.setGroupId( selectedId );
               groupVO.setModifyBy( getUserId( request, response ) );
               groupService.deleteGroup( groupVO );
            }

            insertlog( request, groupVO, Operate.DELETE, null, groupVO.getSelectedIds() );
         }

         // ���¼��س����е�group
         constantsInit( "initPositionGroup", getAccountId( request, response ) );

         // ���Selected IDs����Action
         groupVO.setSelectedIds( "" );
         groupVO.setSubAction( "" );
         groupVO.setGroupId( "" );
         groupVO.reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getRulesByGroupId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      response.setContentType( "application/json;charset=UTF-8" );
      response.setCharacterEncoding( "UTF-8" );
      String groupId = request.getParameter( "groupId" );
      if ( groupId != null && !groupId.equals( "" ) )
      {
         groupId = KANUtil.decodeStringFromAjax( groupId );
      }
      GroupDTO groupDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getGroupDTOByGroupId( groupId );
      List< GroupModuleRuleRelationVO > groupModuleRuleRelationVOs = groupDTO.getGroupModuleRuleRelationVOs();

      List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
      for ( GroupModuleRuleRelationVO groupModuleRuleRelationVO : groupModuleRuleRelationVOs )
      {
         Map< String, String > map = new HashMap< String, String >();
         map.put( "ruleId", groupModuleRuleRelationVO.getRuleId() );
         map.put( "remark1", StringUtils.isEmpty( groupModuleRuleRelationVO.getRemark1() ) ? "" : groupModuleRuleRelationVO.getRemark1() );
         listReturn.add( map );
      }

      JSONArray json = JSONArray.fromObject( listReturn );
      try
      {
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
   }
}
