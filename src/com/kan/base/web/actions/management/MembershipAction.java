package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.MembershipVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.MembershipService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class MembershipAction extends BaseAction
{
   public final static String accessAction = "HRO_EMPLOYEE_MEMBERSHIP";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final MembershipService membershipService = ( MembershipService ) getService( "membershipService" );
         // ���Action Form
         final MembershipVO membershipVO = ( MembershipVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         membershipVO.setAccountId( getAccountId( request, response ) );
         // ����ɾ������
         if ( membershipVO.getSubAction() != null && membershipVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( membershipVO );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder membershipHolder = new PagedListHolder();
         // ���뵱ǰҳ
         membershipHolder.setPage( page );
         // ���뵱ǰֵ����
         membershipHolder.setObject( membershipVO );
         // ����ҳ���¼����
         membershipHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         membershipService.getMembershipVOsByCondition( membershipHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( membershipHolder, request );
         // Holder��д��Request����
         request.setAttribute( "membershipHolder", membershipHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            return mapping.findForward( "listMembershipTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listMembership" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( MembershipVO ) form ).setStatus( MembershipVO.TRUE );
      ( ( MembershipVO ) form ).setSubAction( CREATE_OBJECT );
      // ��ת���½�����  
      return mapping.findForward( "manageMembership" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final MembershipService membershipService = ( MembershipService ) getService( "membershipService" );
            // ��õ�ǰFORM
            final MembershipVO membershipVO = ( MembershipVO ) form;
            membershipVO.setCreateBy( getUserId( request, response ) );
            membershipVO.setModifyBy( getUserId( request, response ) );
            membershipVO.setAccountId( getAccountId( request, response ) );
            membershipService.insertMembership( membershipVO );

            // ��ʼ�������־ö���
            constantsInit( "initMembership", getAccountId( request, response ) );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, membershipVO, Operate.ADD, membershipVO.getMembershipId(), null );
         }
         // ���Form
         ( ( MembershipVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final MembershipService membershipService = ( MembershipService ) getService( "membershipService" );
         // ������ȡ�����
         String membershipId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "membershipId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( membershipId ) == null )
         {
            membershipId = ( ( MembershipVO ) form ).getMembershipId();
         }
         // ���MembershipVO����                                                                                          
         final MembershipVO membershipVO = membershipService.getMembershipVOByMembershipId( membershipId );
         // ����Add��Update
         membershipVO.setSubAction( VIEW_OBJECT );
         membershipVO.reset( null, request );
         // ��MembershipVO����request����
         request.setAttribute( "membershipForm", membershipVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageMembership" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final MembershipService membershipService = ( MembershipService ) getService( "membershipService" );
            // ������ȡ�����
            final String membershipId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "membershipId" ), "UTF-8" ) );
            // ��ȡMembershipVO����
            final MembershipVO membershipVO = membershipService.getMembershipVOByMembershipId( membershipId );
            // װ�ؽ��洫ֵ
            membershipVO.update( ( MembershipVO ) form );
            // ��ȡ��¼�û�
            membershipVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            membershipService.updateMembership( membershipVO );

            // ��ʼ�������־ö���
            constantsInit( "initMembership", getAccountId( request, response ) );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, membershipVO, Operate.MODIFY, membershipVO.getMembershipId(), null );
         }
         // ���Form
         ( ( MembershipVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final MembershipService membershipService = ( MembershipService ) getService( "membershipService" );
         // ���Action Form
         final MembershipVO membershipVO = ( MembershipVO ) form;
         // ����ѡ�е�ID
         if ( membershipVO.getSelectedIds() != null && !membershipVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : membershipVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               membershipVO.setMembershipId( selectedId );
               membershipVO.setAccountId( getAccountId( request, response ) );
               membershipVO.setModifyBy( getUserId( request, response ) );
               membershipService.deleteMembership( membershipVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initMembership", getAccountId( request, response ) );
            insertlog( request, membershipVO, Operate.DELETE, null, membershipVO.getSelectedIds() );
         }
         // ���Selected IDs����Action
         membershipVO.setSelectedIds( "" );
         membershipVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
