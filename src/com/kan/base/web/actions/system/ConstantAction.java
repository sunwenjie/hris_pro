package com.kan.base.web.actions.system;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.ConstantService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

public class ConstantAction extends BaseAction
{

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
         final ConstantService constantService = ( ConstantService ) getService( "constantService" );
         // ���Action Form
         final ConstantVO constantVO = ( ConstantVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         constantVO.setAccountId( getAccountId( request, response ) );

         // ����ɾ������
         if ( constantVO.getSubAction() != null && constantVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( constantVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder constantHolder = new PagedListHolder();
         // ���뵱ǰҳ
         constantHolder.setPage( page );
         // ���뵱ǰֵ����
         constantHolder.setObject( constantVO );
         // ����ҳ���¼����
         constantHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         constantService.getConstantVOsByCondition( constantHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( constantHolder, request );

         // Holder��д��Request����
         request.setAttribute( "constantHolder", constantHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Constant JSP
            return mapping.findForward( "listConstantTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listConstant" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( ConstantVO ) form ).setStatus( BaseVO.TRUE );
      ( ( ConstantVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����  
      return mapping.findForward( "manageConstant" );
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
            final ConstantService constantService = ( ConstantService ) getService( "constantService" );

            // ��õ�ǰFORM
            final ConstantVO constantVO = ( ConstantVO ) form;
            constantVO.setCreateBy( getUserId( request, response ) );
            constantVO.setModifyBy( getUserId( request, response ) );
            constantVO.setAccountId( getAccountId( request, response ) );
            constantService.insertConstant( constantVO );

            // ��ʼ�������־ö���
            constantsInit( "initConstant", getAccountId( request, response ) );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Action Form
         ( ( ConstantVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
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
         final ConstantService constantService = ( ConstantService ) getService( "constantService" );
         // ������ȡ�����
         final String constantId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "constantId" ), "UTF-8" ) );
         // ���ConstantVO����
         final ConstantVO constantVO = constantService.getConstantVOByConstantId( constantId );
         // ����Add��Update
         constantVO.setSubAction( BaseAction.VIEW_OBJECT );
         constantVO.reset( null, request );
         // ��ConstantVO����request����
         request.setAttribute( "constantForm", constantVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageConstant" );
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
            final ConstantService constantService = ( ConstantService ) getService( "constantService" );

            // ������ȡ�����
            final String constantId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "constantId" ), "UTF-8" ) );
            // ��ȡConstantVO����
            final ConstantVO constantVO = constantService.getConstantVOByConstantId( constantId );
            // װ�ؽ��洫ֵ
            constantVO.update( ( ConstantVO ) form );
            // ��ȡ��¼�û�
            constantVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            constantService.updateConstant( constantVO );

            // ��ʼ�������־ö���
            constantsInit( "initConstant", getAccountId( request, response ) );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Action Form
         ( ( ConstantVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
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
         final ConstantService constantService = ( ConstantService ) getService( "constantService" );

         // ���Action Form
         final ConstantVO constantVO = ( ConstantVO ) form;
         // ����ѡ�е�ID
         if ( constantVO.getSelectedIds() != null && !constantVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : constantVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               constantVO.setConstantId( selectedId );
               constantVO.setAccountId( getAccountId( request, response ) );
               constantVO.setModifyBy( getUserId( request, response ) );
               constantService.deleteConstant( constantVO );
            }
         }

         // ��ʼ�������־ö���
         constantsInit( "initConstant", getAccountId( request, response ) );

         // ���Selected IDs����Action
         constantVO.setSelectedIds( "" );
         constantVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
