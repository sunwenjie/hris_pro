/*
 * Created on 2013-05-28
 */
package com.kan.base.web.actions.system;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.RightVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.RightService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

/**
 * @author Kevin Jin
 */
public class RightAction extends BaseAction
{

   /**
    * List Rights
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
         final RightService rightService = ( RightService ) getService( "rightService" );
         // ���Action Form
         final RightVO rightVO = ( RightVO ) form;

         // �����Action��ɾ���û��б�
         if ( rightVO != null && rightVO.getSubAction() != null && rightVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( rightVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder rightHolder = new PagedListHolder();

         // ���뵱ǰҳ
         rightHolder.setPage( page );
         // ���뵱ǰֵ����
         rightHolder.setObject( rightVO );
         // ����ҳ���¼����
         rightHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         rightService.getRightVOsByCondition( rightHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( rightHolder, request );

         // Holder��д��Request����
         request.setAttribute( "rightHolder", rightHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listRightTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listRight" );
   }

   /**
    * To right modify page
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
         final RightService rightService = ( RightService ) getService( "rightService" );
         // ��õ�ǰ����
         final String rightId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "rightId" ), "GBK" ) );
         // ���������Ӧ����
         RightVO rightVO = rightService.getRightVOByRightId( rightId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         rightVO.reset( null, request );

         rightVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "rightForm", rightVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageRight" );
   }

   /**
    * To right new page
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
      ( ( RightVO ) form ).setStatus( RightVO.TRUE );
      ( ( RightVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageRight" );
   }

   /**
    * Add right
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
            final RightService rightService = ( RightService ) getService( "rightService" );
            // ���ActionForm
            final RightVO rightVO = ( RightVO ) form;

            // ��ȡ��¼�û�
            rightVO.setCreateBy( getUserId( request, response ) );
            rightVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            rightService.insertRight( rightVO );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }
         // ���form
         ( ( RightVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify right
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
            final RightService rightService = ( RightService ) getService( "rightService" );
            // ��õ�ǰ����
            final String rightId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "rightId" ), "GBK" ) );
            // ���������Ӧ����
            final RightVO rightVO = rightService.getRightVOByRightId( rightId );

            // װ�ؽ��洫ֵ
            rightVO.update( ( RightVO ) form );

            // ��ȡ��¼�û�
            rightVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            rightService.updateRight( rightVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
         //���form
         ( ( RightVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete right
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
         final RightService rightService = ( RightService ) getService( "rightService" );
         final RightVO rightVO = new RightVO();
         // ��õ�ǰ����
         String rightId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "rightId" ), "GBK" ) );

         // ɾ��������Ӧ����
         rightVO.setRightId( rightId );
         rightVO.setModifyBy( getUserId( request, response ) );
         rightService.deleteRight( rightVO );

         // ���Selected IDs����Action
         rightVO.setSelectedIds( "" );
         rightVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete right list
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
         final RightService rightService = ( RightService ) getService( "rightService" );
         // ���Action Form
         RightVO rightVO = ( RightVO ) form;
         // ����ѡ�е�ID
         if ( rightVO.getSelectedIds() != null && !rightVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : rightVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               rightVO.setRightId( selectedId );
               rightVO.setModifyBy( getUserId( request, response ) );
               rightService.deleteRight( rightVO );
            }
         }
         // ���Selected IDs����Action
         rightVO.setSelectedIds( "" );
         rightVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}