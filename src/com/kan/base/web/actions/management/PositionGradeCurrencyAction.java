package com.kan.base.web.actions.management;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.PositionGradeCurrencyVO;
import com.kan.base.domain.management.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.PositionGradeCurrencyService;
import com.kan.base.service.inf.management.PositionGradeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class PositionGradeCurrencyAction extends BaseAction
{
   /**
    * AccessAction
    */
   public static final String accessAction = "HRO_EMPLOYEE_POSITIONGRADES";

   @Override
   // Code reviewed by Siuvan Xia at 2014-07-17
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�AJAX����
         final String ajax = request.getParameter( "ajax" );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ���Action Form
         final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;

         // �����Action��ɾ��
         if ( positionGradeCurrencyVO.getSubAction() != null && positionGradeCurrencyVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( positionGradeCurrencyVO );
         }

         // ��ʼ��PositionGradeService
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "mgtPositionGradeCurrencyService" );

         //���positionGradeId
         String positionGradeId = request.getParameter( "positionGradeId" );
         if ( KANUtil.filterEmpty( positionGradeId ) == null )
         {
            positionGradeId = positionGradeCurrencyVO.getPositionGradeId();
         }
         else
         {
            positionGradeId = KANUtil.decodeStringFromAjax( positionGradeId );
         }

         // ����������� positionGradeVO����
         final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeId );
         positionGradeVO.setSubAction( VIEW_OBJECT );
         positionGradeVO.reset( null, request );
         request.setAttribute( "mgtPositionGradeForm", positionGradeVO );

         // ����������ң��õ�SearchDetailVO����
         positionGradeCurrencyVO.setPositionGradeId( positionGradeId );

         // �˴���ҳ����
         final PagedListHolder positionGradeCurrencyHolder = new PagedListHolder();
         // ���뵱ǰҳ
         positionGradeCurrencyHolder.setPage( page );
         // ���뵱ǰֵ����
         positionGradeCurrencyHolder.setObject( positionGradeCurrencyVO );
         // ����ҳ���¼����
         positionGradeCurrencyHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         positionGradeCurrencyService.getPositionGradeCurrencyVOsByCondition( positionGradeCurrencyHolder, true );
         refreshHolder( positionGradeCurrencyHolder, request );
         // Holder��д��Request����
         request.setAttribute( "positionGradeCurrencyHolder", positionGradeCurrencyHolder );

         // ��ʼ���ֶ�
         positionGradeCurrencyVO.setSubAction( "" );
         positionGradeCurrencyVO.setStatus( PositionGradeCurrencyVO.TRUE );
         request.setAttribute( "positionGradeCurrencyForm", positionGradeCurrencyVO );

         // ����ǵ����򷵻�ajax
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPositionGradeCurrencyTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תҳ��
      return mapping.findForward( "listPositionGradeCurrency" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "mgtPositionGradeCurrencyService" );

            // ��ȡpositionGradeId
            final String positionGradeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionGradeId" ), "UTF-8" ) );
            // ���ActionFrom
            final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;
            // ����positionGradeId
            positionGradeCurrencyVO.setPositionGradeId( positionGradeId );
            positionGradeCurrencyVO.setAccountId( getAccountId( request, response ) );
            positionGradeCurrencyVO.setCreateBy( getUserId( request, response ) );
            positionGradeCurrencyVO.setModifyBy( getUserId( request, response ) );
            // ����insert����
            positionGradeCurrencyService.insertPositionGradeCurrency( positionGradeCurrencyVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
         }

         // ���Action Form
         ( ( PositionGradeCurrencyVO ) form ).reset();
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
      // no use
      return null;
   }

   // Add by siuvan at 2014-07-17
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "mgtPositionGradeCurrencyService" );

         // ��ȡcurrencyId
         final String currencyId = KANUtil.decodeString( request.getParameter( "currencyId" ) );

         // ��ȡPositionGradeCurrencyVO 
         final PositionGradeCurrencyVO positionGradeCurrencyVO = positionGradeCurrencyService.getPositionGradeCurrencyVOByCurrencyId( currencyId );
         // ���ʻ���ֵ
         positionGradeCurrencyVO.reset( null, request );
         // ����SubAction
         positionGradeCurrencyVO.setSubAction( VIEW_OBJECT );

         // ��ȡPositionGradeVO 
         final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeCurrencyVO.getPositionGradeId() );

         // д��Request
         request.setAttribute( "mgtPositionGradeCurrencyForm", positionGradeCurrencyVO );
         request.setAttribute( "mgtPositionGradeForm", positionGradeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "managePositionGradeCurrencyForm" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "mgtPositionGradeCurrencyService" );
            // ��õ�ǰ����
            final String currencyId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "currencyId" ), "UTF-8" ) );
            // ����ID��ȡ��Ӧ��positionGradeCurrencyVO
            final PositionGradeCurrencyVO positionGradeCurrencyVO = positionGradeCurrencyService.getPositionGradeCurrencyVOByCurrencyId( currencyId );
            // ����positionGradeCurrencyVO
            positionGradeCurrencyVO.update( ( PositionGradeCurrencyVO ) form );
            // ��õ�¼�û�
            positionGradeCurrencyVO.setModifyBy( getUserId( request, response ) );
            // �������ݿ�
            positionGradeCurrencyService.updatePositionGradeCurrency( positionGradeCurrencyVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
         }

         // ���Form
         ( ( PositionGradeCurrencyVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   @Override
   public void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡservice�ӿ�
         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "mgtPositionGradeCurrencyService" );
         // ��õ�ǰform
         final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;
         // ����ѡ�е�ID
         if ( positionGradeCurrencyVO.getSelectedIds() != null && !positionGradeCurrencyVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : positionGradeCurrencyVO.getSelectedIds().split( "," ) )
            {
               // ����ID�������ݿ��ж�Ӧ��VO
               final PositionGradeCurrencyVO positionGradeCurrencyVOForDel = positionGradeCurrencyService.getPositionGradeCurrencyVOByCurrencyId( selectedId );
               // ����positionGradeCurrencyVO���������ֵ
               positionGradeCurrencyVOForDel.setModifyBy( getUserId( request, response ) );
               positionGradeCurrencyVOForDel.setModifyDate( new Date() );
               // ����ɾ������
               positionGradeCurrencyService.deletePositionGradeCurrency( positionGradeCurrencyVOForDel );
            }
         }

         // ���Selected IDs����Action
         ( ( PositionGradeCurrencyVO ) form ).setSelectedIds( "" );
         ( ( PositionGradeCurrencyVO ) form ).setSubAction( "" );

         // ���Form����
         ( ( PositionGradeCurrencyVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use

   }

}
