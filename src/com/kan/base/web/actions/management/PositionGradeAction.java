package com.kan.base.web.actions.management;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.PositionGradeCurrencyVO;
import com.kan.base.domain.management.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.PositionGradeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class PositionGradeAction extends BaseAction
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
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
         // ���Action Form
         final PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;
         // �����Action��ɾ���û��б�
         if ( positionGradeVO.getSubAction() != null && positionGradeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( positionGradeVO );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListGradeHolder = new PagedListHolder();
         // ���õ�ǰҳ
         pagedListGradeHolder.setPage( page );
         // ��Ӳ�����positionGradeVO
         pagedListGradeHolder.setObject( positionGradeVO );
         // ����ÿҳ��ʾ����
         pagedListGradeHolder.setPageSize( listPageSize );
         // ��ѯ
         positionGradeService.getPositionGradeVOByCondition( pagedListGradeHolder, true );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         refreshHolder( pagedListGradeHolder, request );
         // ���pageHolder
         request.setAttribute( "positionGradeHolder", pagedListGradeHolder );

         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPositionGradeTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listPositionGrade" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      this.saveToken( request );
      ( ( PositionGradeVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( PositionGradeVO ) form ).setStatus( BaseVO.TRUE );

      // ��ת���½�����
      return mapping.findForward( "managePositionGrade" );
   }

   @Override
   // Code Reviewed by Siuvan Xia at 2014-7-17
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // ��ʼ��PositionGradeCurrencyVO
      final PositionGradeCurrencyVO positionGradeCurrencyVO = new PositionGradeCurrencyVO();

      try
      {
         // ��ֹҳ���ظ��ύ
         if ( isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
            // ���ActionForm
            final PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;
            positionGradeVO.setCreateBy( getUserId( request, response ) );
            positionGradeVO.setAccountId( getAccountId( request, response ) );
            positionGradeVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            positionGradeService.insertPositionGrade( positionGradeVO );
            positionGradeCurrencyVO.setPositionGradeId( positionGradeVO.getPositionGradeId() );
            // ���¼��س����е�EmployeePositionGrade
            constantsInit( "initEmployeePositionGrade", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            // ���Form����
            ( ( PositionGradeVO ) form ).reset();
         }
         else
         {
            // ���form
            ( ( PositionGradeVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תҳ��
      return new PositionGradeCurrencyAction().list_object( mapping, positionGradeCurrencyVO, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   /**
    * Modify positionGrade
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );

            // ��ȡ��positionGrade������ID
            final String positionGradeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionGradeId" ), "UTF-8" ) );
            // ��������ID��ȡ��Ӧ��PositionGradeVO
            final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeId );
            // ����VO����
            positionGradeVO.update( ( PositionGradeVO ) form );
            positionGradeVO.setModifyBy( getUserId( request, response ) );
            // �޸Ķ���
            positionGradeService.updatePositionGrade( positionGradeVO );

            // ���ر༭�ɹ��ı�� 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            // ���¼��س����е�EmployeePositionGrade
            constantsInit( "initEmployeePositionGrade", getAccountId( request, response ) );
         }

         // ���Form����
         ( ( PositionGradeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת��list currency Action
      return new PositionGradeCurrencyAction().list_object( mapping, new PositionGradeCurrencyVO(), request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
         // ��õ�ǰ����
         final String positionGradeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionGradeId" ), "UTF-8" ) );
         // ����������ȡ��Ӧ��positionGradeVO
         final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeId );
         // ɾ��������Ӧ����
         positionGradeVO.setModifyBy( getUserId( request, response ) );
         positionGradeVO.setModifyDate( new Date() );
         // ���ɾ��ָ����positionGradeVO
         positionGradeService.deletePositionGrade( positionGradeVO );
         // ���¼��س����е�EmployeePositionGrade
         constantsInit( "initEmployeePositionGrade", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
         // ���Action Form
         final PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;
         // ����ѡ�е�ID
         if ( positionGradeVO.getSelectedIds() != null && !positionGradeVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : positionGradeVO.getSelectedIds().split( "," ) )
            {
               // ����������ȡ��Ӧ��positionGradeVO
               final PositionGradeVO positionGradeVOForDel = positionGradeService.getPositionGradeVOByPositionGradeId( selectedId );
               // ɾ��������Ӧ����
               positionGradeVOForDel.setModifyBy( getUserId( request, response ) );
               positionGradeVOForDel.setModifyDate( new Date() );
               // ���ɾ��ָ����positionGradeVOForDel
               positionGradeService.deletePositionGrade( positionGradeVOForDel );
            }
         }
         // ���¼��س����е�EmployeePositionGrade
         constantsInit( "initEmployeePositionGrade", getAccountId( request, response ) );
         // ���Form����
         ( ( PositionGradeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}