package com.kan.hro.web.actions.biz.performance;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.service.inf.message.MessageMailService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.performance.BudgetSettingHeaderVO;
import com.kan.hro.domain.biz.performance.InviteAssessmentVO;
import com.kan.hro.domain.biz.performance.SelfAssessmentVO;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.performance.BudgetSettingHeaderService;
import com.kan.hro.service.inf.biz.performance.InviteAssessmentService;
import com.kan.hro.service.inf.biz.performance.SelfAssessmentService;

public class InviteAssessmentAction extends BaseAction
{

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return mapping.findForward( "manageInviteAssessmentForMail" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         String randomKey = request.getParameter( "randomKey" );
         if ( randomKey == null )
         {
            randomKey = ( ( InviteAssessmentVO ) form ).getRandomKey();
         }
         final InviteAssessmentService inviteAssessmentService = ( InviteAssessmentService ) getService( "inviteAssessmentService" );
         final SelfAssessmentService selfAssessmentService = ( SelfAssessmentService ) getService( "selfAssessmentService" );
         final InviteAssessmentVO inviteAssessmentVO = inviteAssessmentService.getInviteAssessmentVOByRandomKey( randomKey );

         if ( inviteAssessmentVO != null )
         {
            SelfAssessmentVO selfAssessmentVO = selfAssessmentService.getSelfAssessmentVOByAssessmentId( inviteAssessmentVO.getAssessmentId() );
            // 刷新对象，初始化对象列表及国际化
            inviteAssessmentVO.reset( null, request );
            inviteAssessmentVO.setSubAction( VIEW_OBJECT );
            request.setAttribute( "year", selfAssessmentVO.getYear() );
            request.setAttribute( "employeeName", "zh".equals( request.getLocale().getLanguage() ) ? selfAssessmentVO.getEmployeeNameZH() : selfAssessmentVO.getEmployeeNameEN() );
            request.setAttribute( "inviteAssessmentForm", inviteAssessmentVO );
            selfAssessmentVO.reset( null, request );
            request.setAttribute( "selfAssessmentForm", selfAssessmentVO );

            // 不在填写日期范围内
            final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
            final Map< String, Object > mapParameter = new HashMap< String, Object >();
            mapParameter.put( "accountId", getAccountId( request, response ) );
            mapParameter.put( "corpId", getCorpId( request, response ) );
            mapParameter.put( "year", selfAssessmentVO.getYear() );
            final List< Object > list = budgetSettingHeaderService.getBudgetSettingHeaderVOsByMapParameter( mapParameter );
            if ( list != null && list.size() > 0 )
            {
               final BudgetSettingHeaderVO object = ( BudgetSettingHeaderVO ) list.get( 0 );
               Date nowDate = new Date();
               Date beginDate = KANUtil.createDate( object.getStartDate_bl() );
               Date endDate = KANUtil.createDate( object.getEndDate_bl() + " 23:59:59" );
               if ( !( nowDate.getTime() >= beginDate.getTime() && nowDate.getTime() <= endDate.getTime() ) )
               {
                  request.setAttribute( "editBtnHide", true );
               }
               request.setAttribute( "closeDate", KANUtil.formatDate( object.getEndDate_bl(), "yyyy-MM-dd" ) );
            }

            return mapping.findForward( "manageInviteAssessmentForMail" );
         }
         else
         {
            return mapping.findForward( "notFoundInviteLink" );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         InviteAssessmentVO inviteAssessmentForm = ( InviteAssessmentVO ) form;
         final String inviteId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         final InviteAssessmentService inviteAssessmentService = ( InviteAssessmentService ) getService( "inviteAssessmentService" );
         final InviteAssessmentVO inviteAssessmentVO = inviteAssessmentService.getInviteAssessmentVOByInviteId( inviteId );
         inviteAssessmentVO.setAnswer1( inviteAssessmentForm.getAnswer1() );
         inviteAssessmentVO.setAnswer2( inviteAssessmentForm.getAnswer2() );
         inviteAssessmentVO.setAnswer3( inviteAssessmentForm.getAnswer3() );

         if ( SUBMIT_OBJECT.equals( getSubAction( inviteAssessmentForm ) ) )
         {
            inviteAssessmentVO.setStatus( "2" );
            request.setAttribute( "randomKey", request.getParameter( "randomKey" ) );
            inviteAssessmentService.updateInviteAssessment( inviteAssessmentVO );
            return mapping.findForward( "submitAssessmentSuccess" );
         }
         else
         {
            success( request, MESSAGE_TYPE_SAVE );
            inviteAssessmentService.updateInviteAssessment( inviteAssessmentVO );
            return to_objectModify( mapping, inviteAssessmentVO, request, response );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   /***
    * ajax检查是否超过最大邀请数
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void checkMaxInvitaion_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      String result = "";
      try
      {
         final String assessmentId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "assessmentId" ), "UTF-8" ) );
         final InviteAssessmentService inviteAssessmentService = ( InviteAssessmentService ) getService( "inviteAssessmentService" );
         final SelfAssessmentService selfAssessmentService = ( SelfAssessmentService ) getService( "selfAssessmentService" );
         final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
         final SelfAssessmentVO selfAssessmentVO = selfAssessmentService.getSelfAssessmentVOByAssessmentId( assessmentId );

         if ( selfAssessmentVO != null )
         {
            List< Object > inviteAssessmentVOs = null;
            final Map< String, Object > mapParameter = new HashMap< String, Object >();
            mapParameter.put( "assessmentId", assessmentId );
            inviteAssessmentVOs = inviteAssessmentService.getInviteAssessmentVOsByMapParameter( mapParameter );
            mapParameter.clear();
            mapParameter.put( "accountId", getAccountId( request, response ) );
            mapParameter.put( "corpId", getCorpId( request, response ) );
            mapParameter.put( "year", selfAssessmentVO.getYear() );
            List< Object > budgetSettingHeaderVOs = budgetSettingHeaderService.getBudgetSettingHeaderVOsByMapParameter( mapParameter );

            int maxInvitation = 3;
            if ( budgetSettingHeaderVOs != null && budgetSettingHeaderVOs.size() > 0 )
            {
               maxInvitation = ( ( BudgetSettingHeaderVO ) budgetSettingHeaderVOs.get( 0 ) ).getMaxInvitation();
            }

            if ( inviteAssessmentVOs.size() >= maxInvitation )
            {
               result = KANUtil.getProperty( request.getLocale(), "invite.fail.gt.max.invitation", maxInvitation );
            }
         }

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // Send to front
         out.print( result );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
      }
   }

   /***
    * ajax邀请评估
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void add_object_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 定义返回值
         boolean inviteFlag = true;
         String message = KANUtil.getProperty( request.getLocale(), "invite.success" );
         final String assessmentId = request.getParameter( "assessmentId" );
         final String inviteEmployeeId = request.getParameter( "inviteEmployeeId" );
         final String selfEmployeeId = request.getParameter( "selfEmployeeId" );

         final SelfAssessmentService selfAssessmentService = ( SelfAssessmentService ) getService( "selfAssessmentService" );
         final SelfAssessmentVO selfAssessmentVO = selfAssessmentService.getSelfAssessmentVOByAssessmentId( assessmentId );

         if ( selfAssessmentVO == null )
         {
            inviteFlag = false;
            message = KANUtil.getProperty( request.getLocale(), "invite.fail.not.found.SelfAssessmentVO" );
         }

         if ( inviteFlag && inviteEmployeeId != null && inviteEmployeeId.equals( selfEmployeeId ) )
         {
            inviteFlag = false;
            message = KANUtil.getProperty( request.getLocale(), "invite.fail.cannot.invitation.employees", request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? selfAssessmentVO.getEmployeeNameZH()
                  : selfAssessmentVO.getEmployeeNameEN() );
         }
         else
         {
            // 初始化Service 
            final InviteAssessmentService inviteAssessmentService = ( InviteAssessmentService ) getService( "inviteAssessmentService" );
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            final StaffService staffService = ( StaffService ) getService( "staffService" );
            final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );

            // 判断被邀请人是否已经参与 BL(业务汇报线)PM(主管)
            if ( inviteFlag )
            {
               final StaffVO inviteStaffVO = staffService.getStaffVOByEmployeeId( inviteEmployeeId );
               if ( inviteStaffVO != null )
               {
                  final PositionVO invitePositionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getMainPositionVOByStaffId( inviteStaffVO.getStaffId() );
                  if ( invitePositionVO != null )
                  {
                     if ( invitePositionVO.getPositionId().equals( selfAssessmentVO.getBizLeaderPositionId() ) )
                     {
                        inviteFlag = false;
                        message = KANUtil.getProperty( request.getLocale(), "invite.fail.invitees.is.BL" );
                     }
                     else if ( invitePositionVO.getPositionId().equals( selfAssessmentVO.getParentPositionId() ) )
                     {
                        inviteFlag = false;
                        message = KANUtil.getProperty( request.getLocale(), "invite.fail.invitees.is.PM" );
                     }
                  }
               }
            }

            if ( inviteFlag )
            {
               List< Object > inviteAssessmentVOs = null;
               final Map< String, Object > mapParameter = new HashMap< String, Object >();
               mapParameter.put( "assessmentId", assessmentId );
               mapParameter.put( "inviteEmployeeId", inviteEmployeeId );
               inviteAssessmentVOs = inviteAssessmentService.getInviteAssessmentVOsByMapParameter( mapParameter );

               mapParameter.clear();
               mapParameter.put( "accountId", getAccountId( request, response ) );
               mapParameter.put( "corpId", getCorpId( request, response ) );
               mapParameter.put( "year", selfAssessmentVO.getYear() );
               List< Object > budgetSettingHeaderVOs = budgetSettingHeaderService.getBudgetSettingHeaderVOsByMapParameter( mapParameter );

               int maxInvitation = 3;
               if ( budgetSettingHeaderVOs != null && budgetSettingHeaderVOs.size() > 0 )
               {
                  maxInvitation = ( ( BudgetSettingHeaderVO ) budgetSettingHeaderVOs.get( 0 ) ).getMaxInvitation();
               }

               if ( inviteAssessmentVOs.size() >= maxInvitation )
               {
                  inviteFlag = false;
                  message = KANUtil.getProperty( request.getLocale(), "invite.fail.gt.max.invitation", maxInvitation );
               }
               else
               {
                  if ( inviteAssessmentVOs != null && inviteAssessmentVOs.size() > 0 )
                  {
                     inviteFlag = false;
                     message = KANUtil.getProperty( request.getLocale(), "invite.fail.invitees.exist.list" );
                  }
                  else
                  {

                     final InviteAssessmentVO inviteAssessmentVO = new InviteAssessmentVO( assessmentId, inviteEmployeeId );
                     inviteAssessmentVO.setStatus( InviteAssessmentVO.TRUE );
                     inviteAssessmentVO.setCreateBy( getUserId( request, response ) );
                     inviteAssessmentVO.setModifyBy( getUserId( request, response ) );
                     inviteAssessmentVO.setRandomKey( UUID.randomUUID().toString().trim().replaceAll( "-", "" ) );
                     inviteAssessmentService.insertInviteAssessment( inviteAssessmentVO );
                     EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( inviteEmployeeId );
                     employeeVO.reset( null, request );
                     sendInviteMail( employeeVO, inviteAssessmentVO, selfAssessmentVO, ( BudgetSettingHeaderVO ) budgetSettingHeaderVOs.get( 0 ) );
                  }
               }
            }
         }

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // Send to front
         out.println( message );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   // 发送邀请评估邮件
   private void sendInviteMail( final EmployeeVO inviteEmployeeVO, final InviteAssessmentVO inviteAssessmentVO, final SelfAssessmentVO selfAssessmentVO,
         final BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException
   {
      String year = KANUtil.formatDate( selfAssessmentVO.getYear(), "yyyy" );
      String endDateStr = KANUtil.formatDate( budgetSettingHeaderVO.getEndDate_bl(), "yyyy年MM月dd日" );
      String endDateStrEN = KANUtil.formatDate( budgetSettingHeaderVO.getEndDate_bl(), "yyyy/MM/dd" );

      String domain = "";
      if ( KANConstants.isDebug() )
      {
         domain = "http://" + KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME;
      }
      else
      {
         domain = KANConstants.HTTP + KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME;
      }
      final String writeAddress = domain + "/inviteAssessmentAction.do?proc=to_objectModify&randomKey=" + inviteAssessmentVO.getRandomKey();

      final StringBuilder contents = new StringBuilder();
      contents.append( "<!DOCTYPE html>" );
      contents.append( "<html lang=\"en\">" );
      contents.append( "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head>" );
      contents.append( "<body>" );
      contents.append( "<div style=\"margin:2% 0%\">" );
      contents.append( "<p><H2>" + year + "年终考核– 同事反馈表</H2></p>" );
      contents.append( "<p>致 " + inviteEmployeeVO.getNameZH() + " ，该表是帮助主管在评估员工时有更多客观的意见。</p>" );
      contents.append( "<p>您的反馈不会被受评分员工读取，和其它人的反馈合并在主管的评估中。</p>" );
      contents.append( "<p>感谢您的参与， 让年终考核更有建设及价值。</p>" );
      contents.append( "<p>请举例说明您对 <span style=\"background: #acd015\">" + selfAssessmentVO.getEmployeeNameZH()
            + "</span> 的反馈， 包含他/她如何完成工作成果，并于 <span style=\"background: #acd015\">" + endDateStr + "</span> 前填写完成。</p><p><a href=\"" + writeAddress + "\">请点击这里开始填写</a></p>" );

      contents.append( "<hr/>" );

      contents.append( "<p><H2>" + year + " Year End Review – Peer Feedback Form</H2></p>" );
      contents.append( "<p>Dear " + inviteEmployeeVO.getShortName() + ", peer feedback is to help managers evaluating staff with more objective inputs.</p>" );
      contents.append( "<p>Your inputs will not be viewed by appraisee and being summarized into manager's evaluation.</p>" );
      contents.append( "<p>Thank you for your participation and make YERR process more constructive and valuable.</p>" );
      contents.append( "<p>Please provide your feedback on <span style=\"background: #acd015\">" + selfAssessmentVO.getEmployeeNameEN()
            + "</span>, supported by examples to show how he/she delivering results by taking certain actions. " );
      contents.append( "Appreciate you may complete this form by <span style=\"background: #acd015\">" + endDateStrEN + "</span>. </p><p><a href=\"" + writeAddress
            + "\">Click here to start input your feedback</a></p>" );
      contents.append( "</div></body></html>" );

      final MessageMailVO messageMailVO = new MessageMailVO();
      messageMailVO.setSystemId( KANConstants.SYSTEM_ID );
      messageMailVO.setAccountId( inviteEmployeeVO.getAccountId() );
      messageMailVO.setCorpId( inviteEmployeeVO.getCorpId() );
      messageMailVO.setTitle( year + " Year End Review – Peer Feedback Form" );
      messageMailVO.setContent( contents.toString() );
      messageMailVO.setContentType( "2" );
      messageMailVO.setStatus( "1" );
      messageMailVO.setCreateBy( "system" );
      messageMailVO.setCreateDate( new Date() );
      messageMailVO.setModifyBy( "system" );
      messageMailVO.setModifyDate( new Date() );
      messageMailVO.setReception( inviteEmployeeVO.getEmail1() );

      final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
      messageMailService.insertMessageMail( messageMailVO, inviteEmployeeVO.getAccountId() );
   }

   /***
    * ajax加载邀请列表，
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void load_objects_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final String assessmentId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "assessmentId" ), "UTF-8" ) );
         final InviteAssessmentService inviteAssessmentService = ( InviteAssessmentService ) getService( "inviteAssessmentService" );
         final Map< String, Object > mapParameter = new HashMap< String, Object >();
         mapParameter.put( "assessmentId", assessmentId );
         final List< Object > list = inviteAssessmentService.getInviteAssessmentVOsByMapParameter( mapParameter );

         JSONArray jsonArray = new JSONArray();

         if ( list != null && list.size() > 0 )
         {
            for ( Object obj : list )
            {
               ( ( InviteAssessmentVO ) obj ).reset( null, request );
               ( ( InviteAssessmentVO ) obj ).tempReset();
               JSONObject jsonObject = JSONObject.fromObject( obj );
               jsonObject.put( "employeeName", ( "zh".equalsIgnoreCase( request.getLocale().getLanguage() ) ? ( ( InviteAssessmentVO ) obj ).getInviteEmployeeNameZH()
                     : ( ( InviteAssessmentVO ) obj ).getInviteEmployeeNameEN() ) );
               jsonArray.add( jsonObject );
            }
         }
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // Send to front
         out.println( jsonArray.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }
}
