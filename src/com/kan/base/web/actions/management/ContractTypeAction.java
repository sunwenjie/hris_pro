package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ContractTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ContractTypeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

public class ContractTypeAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final ContractTypeService contractTypeService = ( ContractTypeService ) getService( "contractTypeService" );
         // ��ʼ��Service�ӿ�
         final ContractTypeVO contractTypeVO = ( ContractTypeVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         contractTypeVO.setAccountId( getAccountId( request, response ) );

         // ����ɾ������
         if ( contractTypeVO.getSubAction() != null && contractTypeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder contractTypeHolder = new PagedListHolder();
         // ���뵱ǰҳ
         contractTypeHolder.setPage( page );
         // ���뵱ǰֵ����
         contractTypeHolder.setObject( contractTypeVO );
         // ����ҳ���¼����
         contractTypeHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ   
         contractTypeService.getContractTypeVOsByCondition( contractTypeHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( contractTypeHolder, request );

         // Holder��д��Request����
         request.setAttribute( "contractTypeHolder", contractTypeHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���ContractType JSP
            return mapping.findForward( "listContractTypeTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listContractType" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( ContractTypeVO ) form ).setStatus( ContractTypeVO.TRUE );
      ( ( ContractTypeVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageContractType" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ContractTypeService contractTypeService = ( ContractTypeService ) getService( "contractTypeService" );

            // ��õ�ǰFORM
            final ContractTypeVO contractTypeVO = ( ContractTypeVO ) form;
            contractTypeVO.setCreateBy( getUserId( request, response ) );
            contractTypeVO.setModifyBy( getUserId( request, response ) );
            contractTypeVO.setAccountId( getAccountId( request, response ) );
            contractTypeService.insertContractType( contractTypeVO );

            // ��ʼ�������־ö���
            constantsInit( "initContractType", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request );
         }

         // ���Action Form
         ( ( ContractTypeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final ContractTypeService contractTypeService = ( ContractTypeService ) getService( "contractTypeService" );
         // ������ȡ�����
         final String typeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "typeId" ), "UTF-8" ) );
         // ���ContractTypeVO����
         final ContractTypeVO contractTypeVO = contractTypeService.getContractTypeVOByTypeId( typeId );
         contractTypeVO.reset( null, request );
         // ����Add��Update
         contractTypeVO.setSubAction( VIEW_OBJECT );
         // ��ContractTypeVO����Request����
         request.setAttribute( "contractTypeForm", contractTypeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageContractType" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ContractTypeService contractTypeService = ( ContractTypeService ) getService( "contractTypeService" );

            // ��õ�ǰ����
            final String typeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "typeId" ), "UTF-8" ) );
            // ��ȡҪ�޸ĵĶ���
            final ContractTypeVO contractTypeVO = contractTypeService.getContractTypeVOByTypeId( typeId );
            // װ�ؽ��洫ֵ
            contractTypeVO.update( ( ContractTypeVO ) form );
            // ��ȡ��¼�û�
            contractTypeVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            contractTypeService.updateContractType( contractTypeVO );
            // ��ʼ�������־ö���
            constantsInit( "initContractType", getAccountId( request, response ) );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Action Form
         ( ( ContractTypeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
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
         final ContractTypeService contractTypeService = ( ContractTypeService ) getService( "contractTypeService" );

         // ���Action Form
         ContractTypeVO contractTypeVO = ( ContractTypeVO ) form;
         // ����ѡ�е�ID
         if ( contractTypeVO.getSelectedIds() != null && !contractTypeVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : contractTypeVO.getSelectedIds().split( "," ) )
            {
               contractTypeVO.setTypeId( selectedId );
               contractTypeVO.setModifyBy( getUserId( request, response ) );
               // ����ɾ���ӿ�
               contractTypeService.deleteContractType( contractTypeVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initContractType", getAccountId( request, response ) );
         }

         // ���Selected IDs����Action
         contractTypeVO.setSelectedIds( "" );
         contractTypeVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
