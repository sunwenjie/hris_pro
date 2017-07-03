package com.kan.hro.web.actions.biz.sb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.system.SocialBenefitDTO;
import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBDetailService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentDetailService;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentHeaderService;

public class SBAdjustmentDetailAction extends BaseAction
{

   /**
    * �걨��������
    */
   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��õ�ǰҳ
         final String page = getPage( request );
         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );

         // ��ʼ��Service�ӿ�
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
         final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );
         final EmployeeContractSBDetailService employeeContractSBDetailService = ( EmployeeContractSBDetailService ) getService( "employeeContractSBDetailService" );

         // �����������
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = ( ( SBAdjustmentDetailVO ) form ).getAdjustmentHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ����������
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( headerId );

         // �籣������Ŀ����
         sb_adjustment_item( request, employeeContractSBDetailService, sbAdjustmentHeaderVO.getEmployeeSBId() );

         // ˢ�¹��ʻ�
         sbAdjustmentHeaderVO.reset( null, request );

         // ����SubAction
         sbAdjustmentHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "sbAdjustmentHeaderForm", sbAdjustmentHeaderVO );

         // ���Action Form
         final SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) form;

         sbAdjustmentDetailVO.setAdjustmentHeaderId( headerId );

         // ����SubAction
         dealSubAction( sbAdjustmentDetailVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbAdjustmentDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbAdjustmentDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         sbAdjustmentDetailHolder.setObject( sbAdjustmentDetailVO );
         // ����ҳ���¼����
         sbAdjustmentDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbAdjustmentDetailService.getSBAdjustmentDetailVOsByCondition( sbAdjustmentDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( sbAdjustmentDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "sbAdjustmentDetailHolder", sbAdjustmentDetailHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table����
            return mapping.findForward( "listSBAdjustmentDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSBAdjustmentDetail" );
   }

   /**
    * �걨����ȷ������
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward list_object_confirm( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��õ�ǰҳ
         final String page = getPage( request );
         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );

         // ��ʼ��Service�ӿ�
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
         final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );
         final EmployeeContractSBDetailService employeeContractSBDetailService = ( EmployeeContractSBDetailService ) getService( "employeeContractSBDetailService" );

         // �����������
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );
         // ����������
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( headerId );
         // �籣������Ŀ����
         sb_adjustment_item( request, employeeContractSBDetailService, sbAdjustmentHeaderVO.getEmployeeSBId() );

         // ˢ�¹��ʻ�
         sbAdjustmentHeaderVO.reset( null, request );

         // ����SubAction
         sbAdjustmentHeaderVO.setSubAction( VIEW_OBJECT );

         // д��request����
         request.setAttribute( "sbAdjustmentHeaderForm", sbAdjustmentHeaderVO );

         // ���Action Form
         final SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) form;

         sbAdjustmentDetailVO.setAdjustmentHeaderId( headerId );
         // ����SubAction
         dealSubAction( sbAdjustmentDetailVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbAdjustmentDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbAdjustmentDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         sbAdjustmentDetailHolder.setObject( sbAdjustmentDetailVO );
         // ����ҳ���¼����
         sbAdjustmentDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbAdjustmentDetailService.getSBAdjustmentDetailVOsByCondition( sbAdjustmentDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( sbAdjustmentDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "sbAdjustmentDetailHolder", sbAdjustmentDetailHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���CalendarDetail JSP
            return mapping.findForward( "listSBAdjustmentDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSBAdjustmentDetailConfrim" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
            final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );
            final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

            // �������ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ���SBAdjustmentHeaderVO
            final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( headerId );

            // ��õ�ǰFORM
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) form;

            String attribute = null;

            if ( sbAdjustmentHeaderVO != null )
            {
               // ���EmployeeContractSBVO
               final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( sbAdjustmentHeaderVO.getEmployeeSBId() );

               if ( employeeContractSBVO != null )
               {
                  // ���SocialBenefitSolutionDTO
                  final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );

                  if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() != null )
                  {
                     // ���socialBenefitDTO
                     final SocialBenefitDTO socialBenefitDTO = KANConstants.getSocialBenefitDTOBySBHeaderId( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSysHeaderId() );

                     if ( socialBenefitDTO != null )
                     {
                        // ��ȡSocialBenefitDetailVO
                        final SocialBenefitDetailVO socialBenefitDetailVO = socialBenefitDTO.getSocialBenefitDetailVOByItemId( sbAdjustmentDetailVO.getItemId() );

                        if ( socialBenefitDetailVO != null )
                        {
                           attribute = socialBenefitDetailVO.getAttribute();
                        }
                     }
                  }
               }
            }

            // ��ʼ��ItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( sbAdjustmentDetailVO.getItemId() );

            int gapMonths = 0;
            if ( attribute != null && KANUtil.filterEmpty( attribute ) != null )
            {
               if ( attribute.trim().equals( "2" ) )
               {
                  gapMonths = 1;
               }
               else if ( attribute.trim().equals( "3" ) )
               {
                  gapMonths = -1;
               }
            }

            sbAdjustmentDetailVO.setAdjustmentHeaderId( headerId );
            sbAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            sbAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
            sbAdjustmentDetailVO.setMonthly( sbAdjustmentHeaderVO.getMonthly() );
            sbAdjustmentDetailVO.setCreateBy( getUserId( request, response ) );
            sbAdjustmentDetailVO.setModifyBy( getUserId( request, response ) );
            sbAdjustmentDetailService.insertSBAdjustmentDetail( sbAdjustmentDetailVO );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, sbAdjustmentDetailVO, Operate.ADD, sbAdjustmentDetailVO.getAdjustmentDetailId(), null );
         }
         else
         {
            // ���س���ı��
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }

         // ���Form
         ( ( SBAdjustmentDetailVO ) form ).reset();
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
      // No Use
      return null;
   }

   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
         final EmployeeContractSBDetailService employeeContractSBDetailService = ( EmployeeContractSBDetailService ) getService( "employeeContractSBDetailService" );
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // �����ӱ�ID
         final String detailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ���SBAdjustmentDetailVO����
         final SBAdjustmentDetailVO sbAdjustmentDetailVO = sbAdjustmentDetailService.getSBAdjustmentDetailVOByAdjustmentDetailId( detailId );

         // ���SBAdjustmentHeaderVO����
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( sbAdjustmentDetailVO.getAdjustmentHeaderId() );

         // �籣������Ŀ����
         sb_adjustment_item( request, employeeContractSBDetailService, sbAdjustmentHeaderVO.getEmployeeSBId() );

         String attribute = null;

         if ( sbAdjustmentHeaderVO != null )
         {
            // ���EmployeeContractSBVO
            final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( sbAdjustmentHeaderVO.getEmployeeSBId() );

            if ( employeeContractSBVO != null )
            {
               // ���SocialBenefitSolutionDTO
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );

               if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() != null )
               {
                  // ���socialBenefitDTO
                  final SocialBenefitDTO socialBenefitDTO = KANConstants.getSocialBenefitDTOBySBHeaderId( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSysHeaderId() );

                  if ( socialBenefitDTO != null )
                  {
                     // ��ȡSocialBenefitDetailVO
                     final SocialBenefitDetailVO socialBenefitDetailVO = socialBenefitDTO.getSocialBenefitDetailVOByItemId( sbAdjustmentDetailVO.getItemId() );

                     if ( socialBenefitDetailVO != null )
                     {
                        attribute = socialBenefitDetailVO.getAttribute();
                     }
                  }
               }
            }
         }

         // ��ʼ��ItemVO
         final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( sbAdjustmentDetailVO.getItemId() );

         int gapMonths = 0;
         if ( attribute != null && KANUtil.filterEmpty( attribute ) != null )
         {
            if ( attribute.trim().equals( "2" ) )
            {
               gapMonths = 1;
            }
            else if ( attribute.trim().equals( "3" ) )
            {
               gapMonths = -1;
            }
         }

         if ( KANUtil.filterEmpty( sbAdjustmentDetailVO.getNameZH() ) == null )
         {
            sbAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
         }
         if ( KANUtil.filterEmpty( sbAdjustmentDetailVO.getNameEN() ) == null )
         {
            sbAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
         }
         if ( KANUtil.filterEmpty( sbAdjustmentDetailVO.getAccountMonthly() ) == null )
         {
            sbAdjustmentDetailVO.setAccountMonthly( KANUtil.getMonthly( sbAdjustmentHeaderVO.getMonthly(), gapMonths ) );
         }

         // ���ʻ���ֵ
         sbAdjustmentDetailVO.reset( null, request );
         // ����SubAction
         sbAdjustmentDetailVO.setSubAction( VIEW_OBJECT );

         // ����request����
         request.setAttribute( "sbAdjustmentHeaderForm", sbAdjustmentHeaderVO );
         request.setAttribute( "sbAdjustmentDetailForm", sbAdjustmentDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageSBAdjustmentDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );

            // ������ȡ - �����
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );

            // ��ȡ��������
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = sbAdjustmentDetailService.getSBAdjustmentDetailVOByAdjustmentDetailId( detailId );

            // װ�ؽ��洫ֵ
            sbAdjustmentDetailVO.update( ( SBAdjustmentDetailVO ) form );

            // ��ʼ��ItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( sbAdjustmentDetailVO.getItemId() );

            if ( KANUtil.filterEmpty( sbAdjustmentDetailVO.getNameZH() ) == null )
            {
               sbAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            }
            if ( KANUtil.filterEmpty( sbAdjustmentDetailVO.getNameEN() ) == null )
            {
               sbAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
            }

            sbAdjustmentDetailVO.setModifyBy( getUserId( request, response ) );
            sbAdjustmentDetailService.updateSBAdjustmentDetail( sbAdjustmentDetailVO );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, sbAdjustmentDetailVO, Operate.MODIFY, sbAdjustmentDetailVO.getAdjustmentDetailId(), null );
         }
         else
         {
            // ���س���ı��
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }

         // ���Form
         ( ( SBAdjustmentDetailVO ) form ).reset();

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
         final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );
         // ��õ�ǰform
         SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) form;
         // ����ѡ�е�ID
         if ( sbAdjustmentDetailVO.getSelectedIds() != null && !sbAdjustmentDetailVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : sbAdjustmentDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               sbAdjustmentDetailVO = sbAdjustmentDetailService.getSBAdjustmentDetailVOByAdjustmentDetailId( KANUtil.decodeStringFromAjax( selectedId ) );
               sbAdjustmentDetailVO.setModifyBy( getUserId( request, response ) );
               sbAdjustmentDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               sbAdjustmentDetailService.deleteSBAdjustmentDetail( sbAdjustmentDetailVO );
            }

            insertlog( request, sbAdjustmentDetailVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( sbAdjustmentDetailVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         ( ( SBAdjustmentDetailVO ) form ).setSelectedIds( "" );
         ( ( SBAdjustmentDetailVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   //  �籣������Ŀ����
   private void sb_adjustment_item( final HttpServletRequest request, final EmployeeContractSBDetailService employeeContractSBDetailService, final String employeeSBId )
         throws KANException
   {
      //  ��ȡEmployeeContractSBDetailVO�б�
      final List< Object > employeeContractSBDetailVOs = employeeContractSBDetailService.getEmployeeContractSBDetailVOsByEmployeeSBId( employeeSBId );

      // ��ʼ��item�б�
      final List< MappingVO > items = new ArrayList< MappingVO >();

      if ( employeeContractSBDetailVOs != null && employeeContractSBDetailVOs.size() > 0 )
      {
         for ( Object employeeContractSBDetailVOObject : employeeContractSBDetailVOs )
         {
            // ��ȡItemVO
            final ItemVO itemVO = KANConstants.getItemVOByItemId( ( ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject ).getItemId() );

            // ��ʼ��MappingVO
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( itemVO.getItemId() );

            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( itemVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( itemVO.getNameEN() );
            }

            items.add( mappingVO );
         }
      }

      items.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      request.setAttribute( "items", items );
   }

}
