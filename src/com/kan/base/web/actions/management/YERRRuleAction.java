package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.YERRRuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.YERRRuleService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class YERRRuleAction extends BaseAction
{
   public final static String accessAction = "HRO_PM_YERR_RULE";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final YERRRuleService yerrRuleService = ( YERRRuleService ) getService( "yerrRuleService" );
         // ���Action Form
         final YERRRuleVO yerrRuleVO = ( YERRRuleVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         yerrRuleVO.setAccountId( getAccountId( request, response ) );
         // ����ɾ������
         if ( yerrRuleVO.getSubAction() != null && yerrRuleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder yerrRuleHolder = new PagedListHolder();
         // ���뵱ǰҳ
         yerrRuleHolder.setPage( page );
         // ���뵱ǰֵ����
         yerrRuleHolder.setObject( yerrRuleVO );
         // ����ҳ���¼����
         yerrRuleHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         yerrRuleService.getYERRRuleVOsByCondition( yerrRuleHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( yerrRuleHolder, request );
         // Holder��д��Request����
         request.setAttribute( "yerrRuleHolder", yerrRuleHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listYERRRuleTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listYERRRule" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );
      // ����Sub Action
      ( ( YERRRuleVO ) form ).setDistribution( 2 );
      ( ( YERRRuleVO ) form ).setStatus( YERRRuleVO.TRUE );
      ( ( YERRRuleVO ) form ).setSubAction( CREATE_OBJECT );
      // ��ת���½�����  
      return mapping.findForward( "manageYERRRule" );
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
            final YERRRuleService yerrRuleService = ( YERRRuleService ) getService( "yerrRuleService" );

            // ��õ�ǰFORM
            final YERRRuleVO yerrRuleVO = ( YERRRuleVO ) form;
            yerrRuleVO.setDistribution( yerrRuleVO.getDistribution() );
            yerrRuleVO.setCreateBy( getUserId( request, response ) );
            yerrRuleVO.setModifyBy( getUserId( request, response ) );
            yerrRuleVO.setAccountId( getAccountId( request, response ) );
            yerrRuleService.insertYERRRule( yerrRuleVO );

            // ��ʼ�������־ö���
            constantsInit( "initYERRRule", getAccountId( request, response ) );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, yerrRuleVO, Operate.ADD, yerrRuleVO.getRuleId(), null );
         }
         else
         {
            // ���FORM
            ( ( YERRRuleVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // ���Action Form
         ( ( YERRRuleVO ) form ).reset();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final YERRRuleService yerrRuleService = ( YERRRuleService ) getService( "yerrRuleService" );
         // ������ȡ�����
         String ruleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( ruleId ) == null )
         {
            ruleId = ( ( YERRRuleVO ) form ).getRuleId();
         }
         // ���YERRRuleVO����
         YERRRuleVO yerrRuleVO = yerrRuleService.getYERRRuleVOByRuleId( ruleId );
         yerrRuleVO.reset( null, request );
         // ����Add��Update
         yerrRuleVO.setSubAction( VIEW_OBJECT );
         // ��YERRRuleVO����request����
         request.setAttribute( "yerrRuleForm", yerrRuleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageYERRRule" );
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
            final YERRRuleService yerrRuleService = ( YERRRuleService ) getService( "yerrRuleService" );

            // ������ȡ�����
            final String ruleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // ��ȡYERRRuleVO����
            final YERRRuleVO yerrRuleVO = yerrRuleService.getYERRRuleVOByRuleId( ruleId );
            // װ�ؽ��洫ֵ
            yerrRuleVO.update( ( YERRRuleVO ) form );
            // ��ȡ��¼�û�
            yerrRuleVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            yerrRuleService.updateYERRRule( yerrRuleVO );

            // ��ʼ�������־ö���
            constantsInit( "initYERRRule", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, yerrRuleVO, Operate.MODIFY, yerrRuleVO.getRuleId(), null );
         }

         // ���Action Form
         ( ( YERRRuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );

      }
      return to_objectModify( mapping, form, request, response );
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
         // ��ʼ��Service�ӿ�
         final YERRRuleService yerrRuleService = ( YERRRuleService ) getService( "yerrRuleService" );

         // ���Action Form
         final YERRRuleVO yerrRuleVO = ( YERRRuleVO ) form;
         // ����ѡ�е�ID
         if ( yerrRuleVO.getSelectedIds() != null && !yerrRuleVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : yerrRuleVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               yerrRuleVO.setRuleId( selectedId );
               yerrRuleVO.setModifyBy( getUserId( request, response ) );
               yerrRuleVO.setAccountId( getAccountId( request, response ) );
               yerrRuleService.deleteYERRRule( yerrRuleVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initYERRRule", getAccountId( request, response ) );
            insertlog( request, yerrRuleVO, Operate.DELETE, null, yerrRuleVO.getSelectedIds() );
         }

         // ���Selected IDs����Action
         yerrRuleVO.setSelectedIds( "" );
         yerrRuleVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
