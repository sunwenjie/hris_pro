/*
 * Created on 2013-05-07 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.web.actions.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.EmailConfigurationVO;
import com.kan.base.service.inf.management.EmailConfigurationService;
import com.kan.base.util.KANException;
import com.kan.base.util.Mail;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

/**
 * @author Kevin Jin
 */
public class EmailConfigurationAction extends BaseAction
{

   public static String accessAction = "HRO_MANAGEMENT_MAILCONFIGURATION";

   /**
    * To email configuration modify page
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
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final EmailConfigurationService emailConfigurationService = ( EmailConfigurationService ) getService( "emailConfigurationService" );
         // 获得主键对应对象
         EmailConfigurationVO emailConfigurationVO = emailConfigurationService.getEmailConfigurationVOByAccountId( getAccountId( request, response ) );
         // 刷新对象，初始化对象列表及国际化
         emailConfigurationVO.reset( null, request );

         // 传回ActionForm对象到前端
         request.setAttribute( "emailConfigurationForm", emailConfigurationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "modifyEmailConfiguration" );
   }

   /**
    * Modify email configuration
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 获得当前是否需要发送测试邮件
            final String sendTestMail = request.getParameter( "sendTestMail" );
            // 获得当前需要发送的测试邮件地址
            final String testMailAddress = request.getParameter( "testMailAddress" );

            // 初始化Service接口
            final EmailConfigurationService emailConfigurationService = ( EmailConfigurationService ) getService( "emailConfigurationService" );
            // 获得主键对应对象
            final EmailConfigurationVO emailConfigurationVO = emailConfigurationService.getEmailConfigurationVOByAccountId( getAccountId( request, response ) );
            // 值对象复制
            emailConfigurationVO.update( ( EmailConfigurationVO ) form );
            // 多选项转成Jason对象
            emailConfigurationVO.setModifyBy( getUserId( request, response ) );
            // 修改对象
            emailConfigurationService.updateEmailConfiguration( emailConfigurationVO );
            // 初始化常量持久对象
            constantsInit( "initEmailConfiguration", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            // 发送测试邮件
            if ( sendTestMail != null && sendTestMail.trim().equalsIgnoreCase( "on" ) )
            {
               new Mail( getAccountId( request, response ), testMailAddress, "Hello Mail from HRIS", "Test Mail from HRIS" ).send( true );
            }

            insertlog( request, emailConfigurationVO, Operate.MODIFY, emailConfigurationVO.getConfigurationId(), sendTestMail != null
                  && sendTestMail.trim().equalsIgnoreCase( "on" ) ? "test email" : null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到修改界面
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

}