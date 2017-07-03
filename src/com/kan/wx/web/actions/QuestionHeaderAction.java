package com.kan.wx.web.actions;

import java.net.URLDecoder;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.TokenThread;
import com.kan.base.util.WXUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.wx.domain.QuestionDetailVO;
import com.kan.wx.domain.QuestionHeaderVO;
import com.kan.wx.service.inf.QuestionHeaderService;

public class QuestionHeaderAction extends BaseAction
{
   public final static String accessAction = "HRO_QUESITION_HEADER";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final QuestionHeaderVO questionHeaderVO = ( QuestionHeaderVO ) form;
         // ����SubAction
         dealSubAction( questionHeaderVO, mapping, form, request, response );
         // ��ʼ��Service�ӿ�
         final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( questionHeaderVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         questionHeaderService.getQuestionHeaderVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "questionHeaderHolder", pagedListHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listQuestionHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listQuestionHeader" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action��Ĭ��״̬Ϊ����
      ( ( QuestionHeaderVO ) form ).setStatus( QuestionHeaderVO.TRUE );
      ( ( QuestionHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "managerQuestionHeader" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��QuestionDetailVO
         final QuestionDetailVO questionDetailVO = new QuestionDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );

            // ��õ�ǰFORM
            final QuestionHeaderVO questionHeaderVO = ( QuestionHeaderVO ) form;
            questionHeaderVO.setAccountId( getAccountId( request, response ) );
            questionHeaderVO.setCreateBy( getUserId( request, response ) );
            questionHeaderVO.setModifyBy( getUserId( request, response ) );
            questionHeaderService.insertQuestionHeader( questionHeaderVO );
            questionDetailVO.setHeaderId( questionHeaderVO.getHeaderId() );
            // ���ر���ɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            insertlog( request, questionHeaderVO, Operate.ADD, questionHeaderVO.getHeaderId(), null );
         }
         else
         {
            // ���Form����
            ( ( QuestionHeaderVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            // ��ת���б����
            return list_object( mapping, form, request, response );
         }

         return new QuestionDetailAction().list_object( mapping, questionDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );
            // �������
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            final QuestionHeaderVO questionHeaderVO = questionHeaderService.getQuestionHeaderVOByHeaderId( headerId );
            // װ�ؽ��洫ֵ
            questionHeaderVO.update( ( QuestionHeaderVO ) form );
            // ��ȡ��¼�û�
            questionHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            questionHeaderService.updateQuestionHeader( questionHeaderVO );
            // ���ر༭�ɹ��ı�� 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, questionHeaderVO, Operate.MODIFY, questionHeaderVO.getHeaderId(), null );
         }

         // ���ActionForm
         ( ( QuestionHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new QuestionDetailAction().list_object( mapping, new QuestionDetailVO(), request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );

         // ���Action Form
         QuestionHeaderVO questionHeaderVO = ( QuestionHeaderVO ) form;

         // ����ѡ�е�ID
         if ( questionHeaderVO.getSelectedIds() != null && !questionHeaderVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : questionHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               final QuestionHeaderVO tempQuestionHeaderVO = questionHeaderService.getQuestionHeaderVOByHeaderId( selectedId );
               tempQuestionHeaderVO.setModifyBy( getUserId( request, response ) );
               tempQuestionHeaderVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               questionHeaderService.deleteQuestionHeader( tempQuestionHeaderVO );
            }

            insertlog( request, questionHeaderVO, Operate.DELETE, null, questionHeaderVO.getSelectedIds() );
         }

         // ���Selected IDs����Action
         questionHeaderVO.setSelectedIds( "" );
         questionHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward wxtest( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
     long time = System.currentTimeMillis()/1000;    
     String randomStr = UUID.randomUUID().toString();  
     //�ر�ע����ǵ���΢��js��url�����ǵ�ǰҳ��(ת���Ĳ���)  
     String jsApiTicket =WXUtil.JSApiTIcket();
     String str = "jsapi_ticket="+jsApiTicket+"&noncestr="+randomStr+"&timestamp="+time+"&url=https://hris.luoxxy.com/questionHeaderAction.do?proc=wxtest";    
     System.out.println(str);
     String signature = WXUtil.sha1Encrypt(str);    
     String accerssToken =TokenThread.accessToken.getToken();  
     request.setAttribute("time", time);  
     request.setAttribute("randomStr", randomStr);  
     request.setAttribute("signature", signature);  
     request.setAttribute("accessToken", accerssToken);  
     request.setAttribute("jsapi_ticket", jsApiTicket);  
     return mapping.findForward( "wxtest" );
   }
}
