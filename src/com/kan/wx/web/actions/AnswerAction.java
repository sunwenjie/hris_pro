package com.kan.wx.web.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.AccessToken;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.TokenThread;
import com.kan.base.util.WXUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.wx.domain.AnswerVO;
import com.kan.wx.domain.QuestionHeaderVO;
import com.kan.wx.service.inf.AnswerService;
import com.kan.wx.service.inf.QuestionDetailService;
import com.kan.wx.service.inf.QuestionHeaderService;

public class AnswerAction extends BaseAction
{

   public final static String[] ANSWER_INDEX = new String[] { "A", "B", "C", "D", "E", "F", "G", "H" };

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   public ActionForward list_object_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final AnswerService answerService = ( AnswerService ) getService( "answerService" );
         // ���Action Form 
         final AnswerVO answerVO = ( AnswerVO ) form;
         // ���û��ָ������
         if ( answerVO.getSortColumn() == null || answerVO.getSortColumn().isEmpty() )
         {
            answerVO.setSortColumn( "submitDate" );
            answerVO.setSortOrder( "asc" );
         }
         answerVO.setHeaderId( KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) ) );
         // ����
         decodedObject( answerVO );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( answerVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize_popup );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         answerService.getAnswerVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "answerHolder", pagedListHolder );

         if ( "1".equals( KANUtil.filterEmpty( request.getParameter( "export" ) ) ) && request.getParameter( "titleNameList" ) != null
               && request.getParameter( "titleIdList" ) != null )
         {
            request.setAttribute( "nameZHArray", request.getParameter( "titleNameList" ).split( "," ) );
            request.setAttribute( "nameSysArray", request.getParameter( "titleIdList" ).split( "," ) );
            request.setAttribute( "holderName", "answerHolder" );
            request.setAttribute( "fileName", "anwers" );
            // �����ļ�
            return new DownloadFileAction().commonExportList( mapping, form, request, response, true );
         }

         // Ajax Table���ã�ֱ�Ӵ��� JSP
         return mapping.findForward( "listAnswerTableAjax" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final AnswerService answerService = ( AnswerService ) getService( "answerService" );
            final String[] answers = request.getParameterValues( "answer" );

            String answerStr = "";
            if ( answers != null && answers.length > 0 )
            {
               for ( int i = 0; i < answers.length; i++ )
               {
                  answerStr = answerStr + ANSWER_INDEX[ Integer.valueOf( answers[ i ] ) ];
               }

            }
            final AnswerVO answerVO = ( AnswerVO ) form;
            answerVO.setSubmitDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd hh:mm:ss" ) );
            answerVO.setAnswer( answerStr );
            answerVO.setDeleted( "1" );
            answerVO.setStatus( "1" );

            answerService.insertAnswer( answerVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "submitSuccess" );
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

   public String getWXUsername( HttpServletRequest request )
   {
      final String code = request.getParameter( "code" );
      String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
      AccessToken accessToken = TokenThread.accessToken;
      if ( accessToken == null )
      {
         accessToken = WXUtil.getAccessToken();
      }
      requestUrl = requestUrl.replaceAll( "ACCESS_TOKEN", accessToken.getToken() ).replaceAll( "CODE", KANUtil.filterEmpty( code ) == null ? "" : code );
      JSONObject jsonObject = WXUtil.httpRequest( requestUrl, "GET", null );
      if ( jsonObject != null && jsonObject.get( "UserId" ) != null && jsonObject.get( "UserId" ).toString() != null )
      {
         return jsonObject.get( "UserId" ).toString();
      }

      return null;
   }

   public ActionForward to_answer( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         this.saveToken( request );

         final String wxUserId = getWXUsername( request );
         final String questionId = request.getParameter( "q_id" );
         final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );
         final QuestionDetailService questionDetailService = ( QuestionDetailService ) getService( "questionDetailService" );
         final AnswerService answerService = ( AnswerService ) getService( "answerService" );
         final AnswerVO tempAnswerVO = new AnswerVO();
         tempAnswerVO.setHeaderId( questionId );
         tempAnswerVO.setWeChatId( wxUserId );

         final QuestionHeaderVO questionHeaderVO = questionHeaderService.getQuestionHeaderVOByHeaderId( questionId );

         // ҳ���ʶ:1�����2�Ѳ���3δ����
         String pageFlag = "0";
         if ( questionHeaderVO != null )
         {
            //ʱ����Ч
            if ( new Date().getTime() <= KANUtil.createDate( questionHeaderVO.getExpirationDate() ).getTime() )
            {
               final AnswerVO answerVO = answerService.getAnswerVOByWXUserIdAndQuestionId( tempAnswerVO );
               // ����|δ����
               pageFlag = answerVO == null ? "3" : "2";
            }
            else
            {
               // �����
               pageFlag = "1";
            }
         }

         final List< Object > questionDetailVOs = questionDetailService.getQuestionDetailVOsByHeaderId( questionId );
         request.setAttribute( "questionHeaderVO", questionHeaderVO );
         request.setAttribute( "questionDetailVOs", questionDetailVOs );

         request.setAttribute( "answerForm", tempAnswerVO );
         request.setAttribute( "pageFlag", pageFlag );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "toAnswer" );
   }

}
