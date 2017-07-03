package com.kan.base.web.actions.security;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.PositionGradeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class PositionGradeAction extends BaseAction
{
   public static String accessAction = "HRO_SEC_POSITIONGRADE";

   /**
    * List Position Grade
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "positionGradeService" );
         // ���Action Form
         final PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;

         // �����Action��ɾ���û��б�
         if ( positionGradeVO.getSubAction() != null && positionGradeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( positionGradeVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder positionGradeHolder = new PagedListHolder();

         // ���뵱ǰҳ
         positionGradeHolder.setPage( page );
         // ���뵱ǰֵ����
         positionGradeHolder.setObject( positionGradeVO );
         // ����ҳ���¼����
         positionGradeHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         positionGradeService.getPositionGradeVOsByCondition( positionGradeHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( positionGradeHolder, request );
         // Holder��д��Request����
         request.setAttribute( "positionGradeHolder", positionGradeHolder );

         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax��������ת
            return mapping.findForward( "listPositionGradeTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listPositionGrade" );
   }

   /**
    * To Object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( PositionGradeVO ) form ).setSubAction( CREATE_OBJECT );
      //Ĭ������״̬
      ( ( PositionGradeVO ) form ).setStatus( "1" );

      // ��ת���½�����
      return mapping.findForward( "managePositionGrade" );
   }

   /**
    * To Object Modifyy
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
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "positionGradeService" );
         // ��õ�ǰ����
         String positionGradeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( positionGradeId ) == null )
         {
            positionGradeId = ( ( PositionGradeVO ) form ).getPositionGradeId();
         }
         // ���������Ӧ����
         final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         positionGradeVO.reset( null, request );
         positionGradeVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "positionGradeForm", positionGradeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "managePositionGrade" );
   }

   /**
    * Add Position Grade
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "positionGradeService" );
            // ���ActionForm
            final PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;

            // ��ȡ��¼�û�
            positionGradeVO.setAccountId( getAccountId( request, response ) );
            positionGradeVO.setCreateBy( getUserId( request, response ) );
            positionGradeVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            positionGradeService.insertPositionGrade( positionGradeVO );

            // ˢ�³���
            constantsInit( "initPositionGrade", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, positionGradeVO, Operate.ADD, positionGradeVO.getPositionGradeId(), null );
         }
         else
         {
            // ���Form����
            ( ( PositionGradeVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            // ��ת���б����
            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���޸�
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Position Grade
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
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "positionGradeService" );
            // ��õ�ǰ����
            final String positionGradeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeId );

            // ��ȡ��¼�û�
            positionGradeVO.update( ( PositionGradeVO ) form );
            positionGradeVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            positionGradeService.updatePositionGrade( positionGradeVO );

            // ˢ�³���
            constantsInit( "initPositionGrade", getAccountId( request, response ) );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, positionGradeVO, Operate.MODIFY, positionGradeVO.getPositionGradeId(), null );

            // ���Form����
            ( ( PositionGradeVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete Position Grade
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * Delete Position Grade list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "positionGradeService" );
         // ���Action Form
         PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;

         // ����ѡ�е�ID
         if ( positionGradeVO.getSelectedIds() != null && !positionGradeVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : positionGradeVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               final PositionGradeVO tempPositionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( selectedId );
               tempPositionGradeVO.setModifyBy( getUserId( request, response ) );
               tempPositionGradeVO.setModifyDate( new Date() );
               positionGradeService.deletePositionGrade( tempPositionGradeVO );
            }

            insertlog( request, positionGradeVO, Operate.DELETE, null, positionGradeVO.getSelectedIds() );
         }

         // ˢ�³���
         constantsInit( "initPositionGrade", getAccountId( request, response ) );
         // ���Selected IDs����Action
         positionGradeVO.setSelectedIds( "" );
         positionGradeVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getPositionGradeList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      response.setContentType( "application/json;charset=UTF-8" );
      response.setCharacterEncoding( "UTF-8" );

      List< PositionGradeVO > positionGradeVOList = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_GRADE_VO;
      List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
      for ( PositionGradeVO positionGradeVO : positionGradeVOList )
      {
         if ( StringUtils.equals( positionGradeVO.getCorpId(), BaseAction.getCorpId( request, response ) ) )
         {
            Map< String, String > map = new HashMap< String, String >();
            map.put( "id", positionGradeVO.getPositionGradeId() );
            map.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? positionGradeVO.getGradeNameZH() : positionGradeVO.getGradeNameEN() );
            listReturn.add( map );
         }
      }

      JSONArray json = JSONArray.fromObject( listReturn );
      try
      {
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
   }
}