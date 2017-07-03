/*
 * Created on 2007-1-11
 */
package com.kan.cp.web.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.RandomUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientUserVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.cp.biz.client.CPClientService;
import com.kan.hro.service.inf.cp.biz.client.CPClientUserService;
import com.kan.hro.web.actions.biz.client.ClientUserAction;

/**  
*   
* 项目名称：HRO_CP  
* 类名称：SecurityAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-9-25 上午11:14:29  
* 修改人：Jixiang  
* 修改时间：2013-9-25 上午11:14:29  
* 修改备注：  
* @version   
*   
*/
public class SecurityAction extends BaseAction
{

   public ActionForward logon( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         final CPClientUserService clientUserService = ( CPClientUserService ) getService( "clientUserService" );
         final ClientUserVO clientUserVO = ( ClientUserVO ) form;
         final ActionErrors errors = new ActionErrors();
         
         if ( clientUserVO.getClientId() == null || clientUserVO.getClientId().trim().isEmpty() )
         {
            // 账号错误
            errors.add( "LoginError", new ActionMessage( "error.security.contactId" ) );
            addErrors( request, errors );
            return mapping.findForward( "logon" );
         }

         final ClientUserVO userVO_s = new ClientUserVO();
         userVO_s.setUsername( clientUserVO.getUsername() );
         userVO_s.setClientId( clientUserVO.getClientId() );
         userVO_s.setCorpId( clientUserVO.getCorpId() );
         userVO_s.setDeleted( BaseVO.TRUE );
         userVO_s.setStatus( BaseVO.TRUE );
         final List<Object> listClientUsers = clientUserService.getClientUserVOByCondition(userVO_s);
         
         if(listClientUsers==null || listClientUsers.size()==0){
            //  用户名不存在
            errors.add( "LoginError", new ActionMessage( "error.security.username" ) );
            addErrors( request, errors );
            return mapping.findForward( "logon" );
         }
         
         if(listClientUsers==null || listClientUsers.size()>1){
            //  用户名重复
            errors.add( "LoginError", new ActionMessage( "error.security.username" ) );
            addErrors( request, errors );
            return mapping.findForward( "logon" );
         }
         
         final ClientUserVO loginClientUserVO = ( ClientUserVO ) listClientUsers.get( 0 );
         loginClientUserVO.setSystemId( KANUtil.getApplicationPropertiesValue( "application.systemId" ) );
         System.out.println("loginClientUserVO.systemId------------"+loginClientUserVO.getSystemId());
         
         if ( !Cryptogram.decodeString( loginClientUserVO.getPassword() ).equals( clientUserVO.getPassword() ) )
         {
            errors.add( "LoginError", new ActionMessage( "error.security.password" ) );
            addErrors( request, errors );
            return mapping.findForward( "logon" );
         }

         if ( loginClientUserVO.getBindIP() != null && !loginClientUserVO.getBindIP().trim().equalsIgnoreCase( "" ) )
         {
            if ( !loginClientUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
            {
               errors.add( "LoginError", new ActionMessage( "error.security.bindip" ) );
               addErrors( request, errors );
               return mapping.findForward( "logon" );
            }
         }

         final CPClientService clientService = ( CPClientService ) getService( "clientService" );
         
         // 获得用户对应的Client信息
         final ClientVO clientVO = clientService.getClientVOByClientId( loginClientUserVO.getClientId());
         
         //??? 国际化没做
         // 设置客户公司名称
         loginClientUserVO.setClientName( clientVO != null ? clientVO.getNameZH() : "" );
         
         // 设置用户登录序列号
         loginClientUserVO.setUserToken( new RandomUtil().getRandomString( 16 ) );

         // 设置Request Attribute
         request.setAttribute( "clientId", loginClientUserVO.getClientId() );
         request.setAttribute( "corpId", loginClientUserVO.getCorpId() );
         request.setAttribute( "clientUserId", loginClientUserVO.getClientUserId() );
         request.setAttribute( "username", loginClientUserVO.getUsername() );
         request.setAttribute( "clientName", loginClientUserVO.getClientName() );
         request.setAttribute( "userToken", loginClientUserVO.getUserToken() );
         this.saveUserToClient( response,request, ClientUserVO.changeToUserVO( loginClientUserVO ));

         // 更新用户登录信息 ??? 晚点再做
//         loginClientUserVO.setLastLogin( new Date() );
//         loginClientUserVO.setLastLoginIP( getIPAddress( request ) );
//         loginClientUserVO.setModifyDate( new Date() );
//         userService.updateUser( loginClientUserVO );

         ( ( ClientUserVO ) form ).reset();
         
         return new ClientUserAction().list_object( mapping, form, request, response );
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.SecurityAction.logon" ), e, request, response );
      }

   }

   public ActionForward to_index( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return mapping.findForward( "index" );
   }

   public ActionForward logout( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      removeObjectFromClient( request, response, COOKIE_USER );
      removeObjectFromClient( request, response, COOKIE_SECURITY );

      return mapping.findForward( "logon" );
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
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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