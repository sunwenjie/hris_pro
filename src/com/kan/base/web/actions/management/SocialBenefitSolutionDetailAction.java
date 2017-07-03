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
         // 添加页面Token
         this.saveToken( request );

         // 获得Action Form
         final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = ( SocialBenefitSolutionDetailVO ) form;

         // 初始化Service接口
         final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );
         final SocialBenefitSolutionDetailService socialBenefitSolutionDetailService = ( SocialBenefitSolutionDetailService ) getService( "socialBenefitSolutionDetailService" );
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );

         // 获得主表主键
         String headerId = request.getParameter( "headerId" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = socialBenefitSolutionDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得主表对象
         final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOByHeaderId( headerId );

         // 获得主表对象（系统），为了获取城市ID。
         final SocialBenefitHeaderVO socialBenefitHeaderVO = socialBenefitHeaderService.getSocialBenefitHeaderVOByHeaderId( socialBenefitSolutionHeaderVO.getSysHeaderId() );
         socialBenefitSolutionHeaderVO.setCityId( socialBenefitHeaderVO.getCityId() );
         // 刷新对象，初始化对象列表及国际化
         socialBenefitSolutionHeaderVO.reset( null, request );

         // 如果City Id，则填充Province Id
         if ( socialBenefitSolutionHeaderVO.getCityId() != null && !socialBenefitSolutionHeaderVO.getCityId().trim().equals( "" )
               && !socialBenefitSolutionHeaderVO.getCityId().trim().equals( "0" ) )
         {
            socialBenefitSolutionHeaderVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( socialBenefitSolutionHeaderVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
         }

         // 区分修改、查看
         socialBenefitSolutionHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "socialBenefitSolutionHeaderForm", socialBenefitSolutionHeaderVO );

         socialBenefitSolutionDetailVO.setSubAction( VIEW_OBJECT );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder socialBenefitSolutionDetailHolder = new PagedListHolder();
         // 传入主表ID
         socialBenefitSolutionDetailVO.setHeaderId( headerId );
         // 传入当前值对象
         socialBenefitSolutionDetailHolder.setObject( socialBenefitSolutionDetailVO );
         // 设置页面记录条数
         socialBenefitSolutionDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         socialBenefitSolutionDetailService.getSocialBenefitSolutionDetailVOsByCondition( socialBenefitSolutionDetailHolder, false );

         // 获得系统常量SOCIAL_BENEFIT_DTO
         final SocialBenefitDTO socialBenefitDTO = KANConstants.getSocialBenefitDTOBySBHeaderId( socialBenefitSolutionHeaderVO.getSysHeaderId() );

         // 获取SocialBenefitDTO中比例（List< MappingVO > 形式）
         if ( socialBenefitDTO != null && socialBenefitDTO.getSocialBenefitDetailVOs() != null && socialBenefitDTO.getSocialBenefitDetailVOs().size() > 0 )
         {
            for ( SocialBenefitDetailVO tempSocialBenefitDetailVO : socialBenefitDTO.getSocialBenefitDetailVOs() )
            {
               // 计数器
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

               // 如果不存在于社保方案（系统）
               if ( count == 0 )
               {
                  socialBenefitSolutionDetailHolder.getSource().add( generateSocialBenefitSolutionDetailVO( tempSocialBenefitDetailVO ) );
                  socialBenefitSolutionDetailHolder.setHolderSize( socialBenefitSolutionDetailHolder.getHolderSize() + 1 );
               }
            }
         }
         
         // 刷新Holder，国际化传值
         refreshHolder( socialBenefitSolutionDetailHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "socialBenefitSolutionDetailHolder", socialBenefitSolutionDetailHolder );

         // 清空subAction
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
         // 获取社保（系统）ID
         final String sysHeaderId = request.getParameter( "sysHeaderId" );

         // 初始化PagedListHolder
         final PagedListHolder socialBenefitSolutionDetailHolder = new PagedListHolder();

         SocialBenefitHeaderVO socialBenefitHeaderVO = null;

         // 获取SocialBenefitDTO
         final SocialBenefitDTO socialBenefitDTO = KANConstants.getSocialBenefitDTOBySBHeaderId( sysHeaderId );

         if ( socialBenefitDTO != null && socialBenefitDTO.getSocialBenefitDetailVOs() != null && socialBenefitDTO.getSocialBenefitDetailVOs().size() > 0 )
         {
            socialBenefitSolutionDetailHolder.setHolderSize( socialBenefitDTO.getSocialBenefitDetailVOs().size() );
            for ( SocialBenefitDetailVO tempSocialBenefitDetailVO : socialBenefitDTO.getSocialBenefitDetailVOs() )
            {
               socialBenefitSolutionDetailHolder.getSource().add( generateSocialBenefitSolutionDetailVO( tempSocialBenefitDetailVO ) );
            }

            // 刷新Holder，国际化传值
            refreshHolder( socialBenefitSolutionDetailHolder, request );

            if ( socialBenefitDTO.getSocialBenefitHeaderVO() != null )
            {
               socialBenefitHeaderVO = socialBenefitDTO.getSocialBenefitHeaderVO();
            }
         }

         request.setAttribute( "socialBenefitHeaderForm", socialBenefitHeaderVO );

         // Holder需写入Request对象
         request.setAttribute( "socialBenefitSolutionDetailHolder", socialBenefitSolutionDetailHolder );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax Table调用，直接传回SocialBenefitSolutionHeader JSP
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
      // 初始化SocialBenefitSolutionDetailVO
      final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = new SocialBenefitSolutionDetailVO();

      // 继承SocialBenefitDetailVO的属性
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
