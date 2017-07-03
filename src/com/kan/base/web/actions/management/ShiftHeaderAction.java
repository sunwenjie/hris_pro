package com.kan.base.web.actions.management;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.domain.management.ShiftHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ShiftDetailService;
import com.kan.base.service.inf.management.ShiftHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ShiftHeaderAction extends BaseAction
{
   public static final String accessAction = "HRO_ATTENDANCE_SHIFT";

   public static final String WEEK_ZH[] = { "����", "��һ", "�ܶ�", "����", "����", "����", "����" };

   public static final String WEEK_EN[] = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

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
         final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );
         // ���Action Form
         final ShiftHeaderVO shiftHeaderVO = ( ShiftHeaderVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         shiftHeaderVO.setAccountId( getAccountId( request, response ) );

         // ����ɾ������
         if ( shiftHeaderVO.getSubAction() != null && shiftHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( shiftHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder shiftHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         shiftHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         shiftHeaderHolder.setObject( shiftHeaderVO );
         // ����ҳ���¼����
         shiftHeaderHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         shiftHeaderVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         shiftHeaderService.getShiftHeaderVOsByCondition( shiftHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( shiftHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "shiftHeaderHolder", shiftHeaderHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "accountId", getAccountId( request, null ) );
            // Ajax Table���ã�ֱ�Ӵ���ShiftHeader JSP
            return mapping.findForward( "listShiftHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listShiftHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      ( ( ShiftHeaderVO ) form ).reset( mapping, request );

      // ����Sub Action
      ( ( ShiftHeaderVO ) form ).setStatus( BaseVO.TRUE );
      ( ( ShiftHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����  
      return mapping.findForward( "listShiftDetail" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );
            // ��õ�ǰFORM
            final ShiftHeaderVO shiftHeaderVO = ( ShiftHeaderVO ) form;

            if ( !shiftHeaderVO.getShiftType().equals( "3" ) && KANUtil.filterEmpty( shiftHeaderVO.getShiftIndex() ) != null )
            {
               // ��ʼ���Ű�Ƶ��
               int index = Integer.parseInt( shiftHeaderVO.getShiftIndex() );

               // ����Űఴ��
               if ( shiftHeaderVO.getShiftType().equals( "1" ) )
               {
                  index = index * 7;
               }

               // ��ʼ����ά���飬����Ű�ʱ��
               final String[][] tempPeriodArray = new String[ index ][];

               // ѭ����ȡShiftPeriod
               for ( int i = 0; i < index; i++ )
               {
                  String parameterName = "periodArray" + String.valueOf( i + 1 );
                  final String[] periodArray = request.getParameterValues( parameterName );
                  tempPeriodArray[ i ] = periodArray;
               }

               shiftHeaderVO.setPeriodArray( tempPeriodArray );
            }

            shiftHeaderVO.setCreateBy( getUserId( request, response ) );
            shiftHeaderVO.setModifyBy( getUserId( request, response ) );
            shiftHeaderService.insertShiftHeader( shiftHeaderVO );

            // ���¼��ص�������
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            shiftDetailVO.setHeaderId( shiftHeaderVO.getHeaderId() );

            insertlog( request, shiftHeaderVO, Operate.ADD, shiftHeaderVO.getHeaderId(), null );
         }
         else
         {
            // ���Action Form
            ( ( ShiftHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new ShiftDetailAction().list_object( mapping, shiftDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
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
            final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );
            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡShiftHeaderVO����
            final ShiftHeaderVO shiftHeaderVO = shiftHeaderService.getShiftHeaderVOByHeaderId( headerId );
            // װ�ؽ��洫ֵ
            shiftHeaderVO.update( ( ShiftHeaderVO ) form );
            // ��ȡ��¼�û�
            shiftHeaderVO.setModifyBy( getUserId( request, response ) );

            if ( !shiftHeaderVO.getShiftType().equals( "3" ) && KANUtil.filterEmpty( shiftHeaderVO.getShiftIndex() ) != null )
            {
               // ��ʼ���Ű�Ƶ��
               int index = Integer.parseInt( shiftHeaderVO.getShiftIndex() );

               // ����Űఴ��
               if ( shiftHeaderVO.getShiftType().equals( "1" ) )
               {
                  index = index * 7;
               }

               // ��ʼ����ά���飬����Ű�ʱ��
               final String[][] tempPeriodArray = new String[ index ][];

               // ѭ����ȡShiftPeriod
               for ( int i = 0; i < index; i++ )
               {
                  String parameterName = "periodArray" + String.valueOf( i + 1 );
                  final String[] periodArray = request.getParameterValues( parameterName );
                  tempPeriodArray[ i ] = periodArray;
               }

               shiftHeaderVO.setPeriodArray( tempPeriodArray );
            }

            // �����޸ķ���
            shiftHeaderService.updateShiftHeader( shiftHeaderVO );

            // ���¼��ص�������
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, shiftHeaderVO, Operate.MODIFY, shiftHeaderVO.getHeaderId(), null );
         }

         // ���Action Form
         ( ( ShiftHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new ShiftDetailAction().list_object( mapping, new ShiftDetailVO(), request, response );
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
         final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );
         // ���Action Form
         ShiftHeaderVO shiftHeaderVO = ( ShiftHeaderVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( shiftHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, shiftHeaderVO, Operate.DELETE, null, shiftHeaderVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : shiftHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               shiftHeaderVO = shiftHeaderService.getShiftHeaderVOByHeaderId( selectedId );
               shiftHeaderVO.setHeaderId( selectedId );
               shiftHeaderVO.setAccountId( getAccountId( request, response ) );
               shiftHeaderVO.setModifyBy( getUserId( request, response ) );
               shiftHeaderService.deleteShiftHeader( shiftHeaderVO );
            }
         }
         // ���Selected IDs����Action
         shiftHeaderVO.setSelectedIds( "" );
         shiftHeaderVO.setSubAction( "" );

         constantsInit( "initShiftHeader", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �Ű�����
         final String shiftType = request.getParameter( "shiftType" );

         // ����Ű�Ƶ��
         final String shiftIndex = request.getParameter( "shiftIndex" );

         // ��ÿ�ʼʱ�䣨�����õ���
         final String startDate = request.getParameter( "startDate" );

         // ��ʼ��
         final ShiftHeaderVO shiftHeaderVO = new ShiftHeaderVO();
         shiftHeaderVO.setShiftType( shiftType );
         shiftHeaderVO.setShiftIndex( shiftIndex );
         shiftHeaderVO.setStartDate( startDate );

         // ��ȡ��ʱShiftDtailVO�б�
         final List< Object > shiftDtailVOs = getTempShiftDetailVOsByCondition( shiftHeaderVO );

         final PagedListHolder shiftDetailHolder = new PagedListHolder();
         shiftDetailHolder.setSource( shiftDtailVOs );
         shiftDetailHolder.setHolderSize( shiftDtailVOs.size() );
         request.setAttribute( "listShiftDetailCount", shiftDtailVOs.size() );
         request.setAttribute( "shiftDetailHolder", shiftDetailHolder );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSpecialInfo" );
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ������ȡ
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // ��ʼ��Service�ӿ�
         final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );

         // ��ö�ӦShiftDetailVO�б�д��request
         final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();
         shiftDetailVO.setHeaderId( headerId );
         shiftDetailVO.setStatus( "1" );

         final PagedListHolder shiftDetailHolder = new PagedListHolder();
         shiftDetailHolder.setObject( shiftDetailVO );

         shiftDetailService.getShiftDetailVOsByCondition( shiftDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( shiftDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "shiftDetailHolder", shiftDetailHolder );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSpecialInfo" );
   }

   /**
    * ajax��ȡShiftDetailVO�б�
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   private List< Object > getTempShiftDetailVOsByCondition( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      try
      {
         // ��ʼ������ֵ
         final List< Object > shiftDetailVOs = new ArrayList< Object >();

         if ( shiftHeaderVO != null && KANUtil.filterEmpty( shiftHeaderVO.getShiftType(), "0" ) != null )
         {
            // �������
            if ( shiftHeaderVO.getShiftType().equals( "1" ) )
            {
               for ( int i = 0; i < Integer.parseInt( shiftHeaderVO.getShiftIndex() ) * 7; i++ )
               {
                  // ��ʼ��ShiftDetailVO
                  final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();
                  shiftDetailVO.setNameZH( WEEK_ZH[ i > 6 ? i % 7 : i ] );
                  shiftDetailVO.setNameEN( WEEK_EN[ i > 6 ? i % 7 : i ] );
                  shiftDetailVO.setDayIndex( String.valueOf( ( i + 1 ) ) );

                  shiftDetailVOs.add( shiftDetailVO );
               }
            }
            // �������
            else if ( shiftHeaderVO.getShiftType().equals( "2" ) )
            {
               for ( int i = 0; i < Integer.parseInt( shiftHeaderVO.getShiftIndex() ); i++ )
               {
                  // ��ʼ��ShiftDetailVO
                  final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();
                  shiftDetailVO.setNameZH( KANUtil.getStrDate( shiftHeaderVO.getStartDate(), i ) );
                  shiftDetailVO.setNameEN( KANUtil.getStrDate( shiftHeaderVO.getStartDate(), i ) );
                  shiftDetailVO.setDayIndex( String.valueOf( ( i + 1 ) ) );

                  shiftDetailVOs.add( shiftDetailVO );
               }
            }
         }

         return shiftDetailVOs;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}
