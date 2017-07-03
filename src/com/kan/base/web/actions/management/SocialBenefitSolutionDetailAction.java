package com.kan.base.web.actions.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.domain.system.SocialBenefitDTO;
import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.domain.system.SocialBenefitHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SocialBenefitSolutionDetailService;
import com.kan.base.service.inf.management.SocialBenefitSolutionHeaderService;
import com.kan.base.service.inf.system.SocialBenefitHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class SocialBenefitSolutionDetailAction extends BaseAction
{
   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_SB_SOLUTION";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ���Action Form
         final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = ( SocialBenefitSolutionDetailVO ) form;

         // ��ʼ��Service�ӿ�
         final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );
         final SocialBenefitSolutionDetailService socialBenefitSolutionDetailService = ( SocialBenefitSolutionDetailService ) getService( "socialBenefitSolutionDetailService" );
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );

         // �����������
         String headerId = request.getParameter( "headerId" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = socialBenefitSolutionDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ����������
         final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOByHeaderId( headerId );

         // ����������ϵͳ����Ϊ�˻�ȡ����ID��
         final SocialBenefitHeaderVO socialBenefitHeaderVO = socialBenefitHeaderService.getSocialBenefitHeaderVOByHeaderId( socialBenefitSolutionHeaderVO.getSysHeaderId() );
         socialBenefitSolutionHeaderVO.setCityId( socialBenefitHeaderVO.getCityId() );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         socialBenefitSolutionHeaderVO.reset( null, request );

         // ���City Id�������Province Id
         if ( socialBenefitSolutionHeaderVO.getCityId() != null && !socialBenefitSolutionHeaderVO.getCityId().trim().equals( "" )
               && !socialBenefitSolutionHeaderVO.getCityId().trim().equals( "0" ) )
         {
            socialBenefitSolutionHeaderVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( socialBenefitSolutionHeaderVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
         }

         // �����޸ġ��鿴
         socialBenefitSolutionHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "socialBenefitSolutionHeaderForm", socialBenefitSolutionHeaderVO );

         socialBenefitSolutionDetailVO.setSubAction( VIEW_OBJECT );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder socialBenefitSolutionDetailHolder = new PagedListHolder();
         // ��������ID
         socialBenefitSolutionDetailVO.setHeaderId( headerId );
         // ���뵱ǰֵ����
         socialBenefitSolutionDetailHolder.setObject( socialBenefitSolutionDetailVO );
         // ����ҳ���¼����
         socialBenefitSolutionDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         socialBenefitSolutionDetailService.getSocialBenefitSolutionDetailVOsByCondition( socialBenefitSolutionDetailHolder, false );

         // ���ϵͳ����SOCIAL_BENEFIT_DTO
         final SocialBenefitDTO socialBenefitDTO = KANConstants.getSocialBenefitDTOBySBHeaderId( socialBenefitSolutionHeaderVO.getSysHeaderId() );

         // ��ȡSocialBenefitDTO�б�����List< MappingVO > ��ʽ��
         if ( socialBenefitDTO != null && socialBenefitDTO.getSocialBenefitDetailVOs() != null && socialBenefitDTO.getSocialBenefitDetailVOs().size() > 0 )
         {
            for ( SocialBenefitDetailVO tempSocialBenefitDetailVO : socialBenefitDTO.getSocialBenefitDetailVOs() )
            {
               // ������
               int count = 0;
               if ( socialBenefitSolutionDetailHolder.getSource() != null && socialBenefitSolutionDetailHolder.getSource().size() > 0 )
               {
                  for ( Object tempSocialBenefitSolutionDetailVO : socialBenefitSolutionDetailHolder.getSource() )
                  {
                     if ( ( ( SocialBenefitSolutionDetailVO ) tempSocialBenefitSolutionDetailVO ).getSysDetailId().equals( tempSocialBenefitDetailVO.getDetailId() ) )
                     {
                        ( ( SocialBenefitSolutionDetailVO ) tempSocialBenefitSolutionDetailVO ).setCompanyPercentSection( KANUtil.generatePercents( tempSocialBenefitDetailVO.getCompanyPercentLow() ) );
                        ( ( SocialBenefitSolutionDetailVO ) tempSocialBenefitSolutionDetailVO ).setPersonalPercentSection( KANUtil.generatePercents( tempSocialBenefitDetailVO.getPersonalPercentLow() ) );
                        count++;
                        break;
                     }
                  }
               }

               // ������������籣������ϵͳ��
               if ( count == 0 )
               {
                  socialBenefitSolutionDetailHolder.getSource().add( generateSocialBenefitSolutionDetailVO( tempSocialBenefitDetailVO ) );
                  socialBenefitSolutionDetailHolder.setHolderSize( socialBenefitSolutionDetailHolder.getHolderSize() + 1 );
               }
            }
         }
         
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( socialBenefitSolutionDetailHolder, request );

         // Holder��д��Request����
         request.setAttribute( "socialBenefitSolutionDetailHolder", socialBenefitSolutionDetailHolder );

         // ���subAction
         ( ( SocialBenefitSolutionDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // listSocialBenefitSolutionDetail
      return mapping.findForward( "listSocialBenefitSolutionDetail" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   // Modify by siuvan@2014-08-20
   public ActionForward to_objectNew_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡ�籣��ϵͳ��ID
         final String sysHeaderId = request.getParameter( "sysHeaderId" );

         // ��ʼ��PagedListHolder
         final PagedListHolder socialBenefitSolutionDetailHolder = new PagedListHolder();

         SocialBenefitHeaderVO socialBenefitHeaderVO = null;

         // ��ȡSocialBenefitDTO
         final SocialBenefitDTO socialBenefitDTO = KANConstants.getSocialBenefitDTOBySBHeaderId( sysHeaderId );

         if ( socialBenefitDTO != null && socialBenefitDTO.getSocialBenefitDetailVOs() != null && socialBenefitDTO.getSocialBenefitDetailVOs().size() > 0 )
         {
            socialBenefitSolutionDetailHolder.setHolderSize( socialBenefitDTO.getSocialBenefitDetailVOs().size() );
            for ( SocialBenefitDetailVO tempSocialBenefitDetailVO : socialBenefitDTO.getSocialBenefitDetailVOs() )
            {
               socialBenefitSolutionDetailHolder.getSource().add( generateSocialBenefitSolutionDetailVO( tempSocialBenefitDetailVO ) );
            }

            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( socialBenefitSolutionDetailHolder, request );

            if ( socialBenefitDTO.getSocialBenefitHeaderVO() != null )
            {
               socialBenefitHeaderVO = socialBenefitDTO.getSocialBenefitHeaderVO();
            }
         }

         request.setAttribute( "socialBenefitHeaderForm", socialBenefitHeaderVO );

         // Holder��д��Request����
         request.setAttribute( "socialBenefitSolutionDetailHolder", socialBenefitSolutionDetailHolder );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax Table���ã�ֱ�Ӵ���SocialBenefitSolutionHeader JSP
      return mapping.findForward( "listSocialBenefitSolutionDetailTable" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
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
      // No Use
      return null;
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   private SocialBenefitSolutionDetailVO generateSocialBenefitSolutionDetailVO( final SocialBenefitDetailVO socialBenefitDetailVO )
   {
      // ��ʼ��SocialBenefitSolutionDetailVO
      final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = new SocialBenefitSolutionDetailVO();

      // �̳�SocialBenefitDetailVO������
      socialBenefitSolutionDetailVO.setSysDetailId( socialBenefitDetailVO.getDetailId() );
      socialBenefitSolutionDetailVO.setCompanyPercentSection( KANUtil.generatePercents( socialBenefitDetailVO.getCompanyPercentLow() ) );
      socialBenefitSolutionDetailVO.setPersonalPercentSection( KANUtil.generatePercents( socialBenefitDetailVO.getPersonalPercentLow() ) );
      socialBenefitSolutionDetailVO.setItemId( socialBenefitDetailVO.getItemId() );
      socialBenefitSolutionDetailVO.setCompanyFloor( socialBenefitDetailVO.getCompanyFloor() );
      socialBenefitSolutionDetailVO.setCompanyCap( socialBenefitDetailVO.getCompanyCap() );
      socialBenefitSolutionDetailVO.setPersonalFloor( socialBenefitDetailVO.getPersonalFloor() );
      socialBenefitSolutionDetailVO.setPersonalCap( socialBenefitDetailVO.getPersonalCap() );
      socialBenefitSolutionDetailVO.setCompanyFixAmount( socialBenefitDetailVO.getCompanyFixAmount() );
      socialBenefitSolutionDetailVO.setPersonalFixAmount( socialBenefitDetailVO.getPersonalFixAmount() );
      socialBenefitSolutionDetailVO.setAttribute( socialBenefitDetailVO.getAttribute() );
      socialBenefitSolutionDetailVO.setEffective( socialBenefitDetailVO.getEffective() );
      socialBenefitSolutionDetailVO.setStartDateLimit( socialBenefitDetailVO.getStartDateLimit() );
      socialBenefitSolutionDetailVO.setEndDateLimit( socialBenefitDetailVO.getEndDateLimit() );
      socialBenefitSolutionDetailVO.setStartRule( socialBenefitDetailVO.getStartRule() );
      socialBenefitSolutionDetailVO.setStartRuleRemark( socialBenefitDetailVO.getStartRuleRemark() );
      socialBenefitSolutionDetailVO.setEndRule( socialBenefitDetailVO.getEndRule() );
      socialBenefitSolutionDetailVO.setEndRuleRemark( socialBenefitDetailVO.getEndRuleRemark() );
      socialBenefitSolutionDetailVO.setItemNo( socialBenefitDetailVO.getItemNo() );

      return socialBenefitSolutionDetailVO;
   }

}
