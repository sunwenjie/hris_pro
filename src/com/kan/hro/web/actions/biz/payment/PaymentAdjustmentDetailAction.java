package com.kan.hro.web.actions.biz.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentDetailService;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentHeaderService;

public class PaymentAdjustmentDetailAction extends BaseAction
{
   /**  
    * List Object
    *	 н�ʵ����б�
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2014-01-01
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��õ�ǰҳ
         final String page = getPage( request );

         // ���Action Form
         final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = ( PaymentAdjustmentDetailVO ) form;

         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // ��ȡ����ID
         String adjustmentHeaderId = request.getParameter( "adjustmentHeaderId" );
         if ( KANUtil.filterEmpty( adjustmentHeaderId ) == null )
         {
            adjustmentHeaderId = paymentAdjustmentDetailVO.getAdjustmentHeaderId();
         }
         else
         {
            adjustmentHeaderId = KANUtil.decodeStringFromAjax( adjustmentHeaderId );
         }

         paymentAdjustmentDetailVO.setAdjustmentHeaderId( adjustmentHeaderId );

         // �����SubAction��ɾ���б����deleteObject
         if ( getSubAction( form ).equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���б��SubAction
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ����������������򡢷�ҳ�򵼳�������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( paymentAdjustmentDetailVO );
         }

         // ���������Ӧ����
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder paymentAdjustmentDetailHolder = new PagedListHolder();

         // ���뵱ǰҳ
         paymentAdjustmentDetailHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ�adjustmentDetailId����
         if ( paymentAdjustmentDetailVO.getSortColumn() == null || paymentAdjustmentDetailVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentDetailVO.setSortColumn( "adjustmentDetailId" );
            paymentAdjustmentDetailVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         paymentAdjustmentDetailHolder.setObject( paymentAdjustmentDetailVO );
         // ����ҳ���¼����
         paymentAdjustmentDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         paymentAdjustmentDetailService.getPaymentAdjustmentDetailVOsByCondition( paymentAdjustmentDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( paymentAdjustmentDetailHolder, request );
         paymentAdjustmentHeaderVO.reset( mapping, request );
         // Holder��д��Request����
         request.setAttribute( "paymentAdjustmentHeaderForm", paymentAdjustmentHeaderVO );
         request.setAttribute( "paymentAdjustmentDetailHolder", paymentAdjustmentDetailHolder );

         // �����Ajax����
         if ( new Boolean( this.getAjax( request ) ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentDetailTable" );
         }

         return mapping.findForward( "listPaymentAdjustmentDetail" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Confirm
    *  н�ʵ����б�
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2014-01-03
   public ActionForward list_object_confirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��õ�ǰҳ
         final String page = getPage( request );

         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // ��ȡ����ID
         final String adjustmentHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) );

         // ���Action Form
         final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = ( PaymentAdjustmentDetailVO ) form;
         paymentAdjustmentDetailVO.setAdjustmentHeaderId( adjustmentHeaderId );

         // ���������Ӧ����
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder paymentAdjustmentDetailHolder = new PagedListHolder();

         // ���뵱ǰҳ
         paymentAdjustmentDetailHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ�adjustmentDetailId����
         if ( paymentAdjustmentDetailVO.getSortColumn() == null || paymentAdjustmentDetailVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentDetailVO.setSortColumn( "adjustmentDetailId" );
            paymentAdjustmentDetailVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         paymentAdjustmentDetailHolder.setObject( paymentAdjustmentDetailVO );
         // ����ҳ���¼����
         paymentAdjustmentDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         paymentAdjustmentDetailService.getPaymentAdjustmentDetailVOsByCondition( paymentAdjustmentDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( paymentAdjustmentDetailHolder, request );
         paymentAdjustmentHeaderVO.reset( mapping, request );
         // Holder��д��Request����
         request.setAttribute( "paymentAdjustmentHeaderForm", paymentAdjustmentHeaderVO );
         request.setAttribute( "paymentAdjustmentDetailHolder", paymentAdjustmentDetailHolder );

         return mapping.findForward( "listPaymentAdjustmentDetailConfrim" );
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

   // Added by Kevin Jin 2014-01-02
   public ActionForward to_objectNew_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );

         // ����SubAction
         ( ( PaymentAdjustmentDetailVO ) form ).setAdjustmentHeaderId( KANUtil.decodeString( ( ( PaymentAdjustmentDetailVO ) form ).getAdjustmentHeaderId() ) );
         ( ( PaymentAdjustmentDetailVO ) form ).setStatus( "1" );
         ( ( PaymentAdjustmentDetailVO ) form ).setSubAction( CREATE_OBJECT );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageAdjustmentDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Add Object
    *	���PaymentAdjustmentDetailVO
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      if ( this.isTokenValid( request, true ) )
      {
         try
         {
            // ��ʼ���ӿ�
            final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );

            // ��ʼ��PaymentAdjustmentDetailVO
            final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = ( PaymentAdjustmentDetailVO ) form;

            // ��ʼ��ItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( paymentAdjustmentDetailVO.getItemId() );
            paymentAdjustmentDetailVO.setAdjustmentHeaderId( KANUtil.decodeString( paymentAdjustmentDetailVO.getAdjustmentHeaderId() ) );
            paymentAdjustmentDetailVO.setItemNo( itemVO.getItemNo() );
            paymentAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            paymentAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );

            if ( KANUtil.filterEmpty( itemVO.getPersonalTax() ) != null && itemVO.getPersonalTax().equals( "1" ) )
            {
               paymentAdjustmentDetailVO.setAddtionalBillAmountPersonal( paymentAdjustmentDetailVO.getBillAmountPersonal() );
            }

            paymentAdjustmentDetailVO.setAccountId( getAccountId( request, response ) );
            paymentAdjustmentDetailVO.setCreateBy( getUserId( request, response ) );
            paymentAdjustmentDetailVO.setModifyBy( getUserId( request, response ) );

            // ��Ӷ���
            paymentAdjustmentDetailService.insertPaymentAdjustmentDetail( paymentAdjustmentDetailVO );

            // ���ء���ӡ��ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, paymentAdjustmentDetailVO, Operate.ADD, paymentAdjustmentDetailVO.getAdjustmentDetailId(), null );

            ( ( PaymentAdjustmentDetailVO ) form ).reset();
         }
         catch ( Exception e )
         {
            throw new KANException( e );
         }
      }

      return list_object( mapping, form, request, response );
   }

   /**  
    * To ObjectModify
    *	 ��ת���޸�
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   /**
    * Ajax ��ת�޸Ŀ�Ŀ
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );

         // �ӱ�ID
         final String adjustmentDetailId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // ���AdjustmentDetailVO����
         final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = paymentAdjustmentDetailService.getPaymentAdjustmentDetailVOByAdjustmentDetailId( adjustmentDetailId );

         // ���ʻ���ֵ
         paymentAdjustmentDetailVO.reset( mapping, request );

         // ����SubAction
         paymentAdjustmentDetailVO.setSubAction( VIEW_OBJECT );

         // ����request����
         request.setAttribute( "paymentAdjustmentDetailForm", paymentAdjustmentDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageAdjustmentDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Modify Object
    *	 �޸Ŀ�Ŀ
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ���ӿ�
            final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );

            // ��õ�ǰ��������������ID
            final String adjustmentDetailId = KANUtil.decodeString( request.getParameter( "adjustmentDetailId" ) );

            // ��� PaymentAdjustmentDetailVO
            final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = paymentAdjustmentDetailService.getPaymentAdjustmentDetailVOByAdjustmentDetailId( adjustmentDetailId );
            paymentAdjustmentDetailVO.update( ( PaymentAdjustmentDetailVO ) form );

            // ��ʼ��ItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( paymentAdjustmentDetailVO.getItemId() );
            paymentAdjustmentDetailVO.setAdjustmentHeaderId( KANUtil.decodeString( paymentAdjustmentDetailVO.getAdjustmentHeaderId() ) );
            paymentAdjustmentDetailVO.setItemNo( itemVO.getItemNo() );
            paymentAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            paymentAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
            paymentAdjustmentDetailVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            paymentAdjustmentDetailService.updatePaymentAdjustmentDetail( paymentAdjustmentDetailVO );

            // ���ء��༭���ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, paymentAdjustmentDetailVO, Operate.MODIFY, paymentAdjustmentDetailVO.getAdjustmentDetailId(), null );

            // ���Form����
            ( ( PaymentAdjustmentDetailVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
   }

   /**
    * ɾ��detail
    */
   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );
         // ���Action Form
         final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = ( PaymentAdjustmentDetailVO ) form;

         // ����ѡ�е�ID
         if ( paymentAdjustmentDetailVO.getSelectedIds() != null && !paymentAdjustmentDetailVO.getSelectedIds().equals( "" ) )
         {
            for ( String selectedId : paymentAdjustmentDetailVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               final PaymentAdjustmentDetailVO paymentAdjustmentDetailVOTemp = paymentAdjustmentDetailService.getPaymentAdjustmentDetailVOByAdjustmentDetailId( KANUtil.decodeStringFromAjax( selectedId ) );
               paymentAdjustmentDetailVOTemp.setModifyBy( getUserId( request, response ) );
               paymentAdjustmentDetailService.deletePaymentAdjustmentDetail( paymentAdjustmentDetailVOTemp );
            }

            insertlog( request, paymentAdjustmentDetailVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( paymentAdjustmentDetailVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         paymentAdjustmentDetailVO.setSelectedIds( "" );
         paymentAdjustmentDetailVO.setSubAction( "" );

         // ���ء�ɾ�����ɹ����
         success( request, MESSAGE_TYPE_DELETE, null, MESSAGE_DETAIL );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
