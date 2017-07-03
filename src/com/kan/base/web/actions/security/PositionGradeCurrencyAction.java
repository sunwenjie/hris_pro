package com.kan.base.web.actions.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.PositionGradeCurrencyVO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.PositionGradeCurrencyService;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

/**
 * @author Jack
 */
public class PositionGradeCurrencyAction extends BaseAction
{

   /**
    * List PositionsGradeCurrency
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

         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "positionGradeCurrencyService" );
         final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;

         // �����Action��ɾ���û��б�
         if ( positionGradeCurrencyVO.getSubAction() != null && positionGradeCurrencyVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }

         // ��ȡpositionGradeId
         String positionGradeId = request.getParameter( "positionGradeId" );
         positionGradeCurrencyVO.setPositionGradeId( positionGradeId );

         final PositionGradeVO positionGradeVO = new PositionGradeVO();
         positionGradeVO.setPositionGradeId( positionGradeId );
         request.setAttribute( "positionGradeForm", positionGradeVO );

         final PagedListHolder pagedListGradeCurrencyHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListGradeCurrencyHolder.setPage( page );
         // ����ҳ���¼����
         pagedListGradeCurrencyHolder.setPageSize( listPageSize );
         // ���positionGradeCurrencyVO
         positionGradeCurrencyVO.reset();
         // ���뵱ǰֵ����
         pagedListGradeCurrencyHolder.setObject( positionGradeCurrencyVO );
         // ����service �����Ƿ��ҳ
         positionGradeCurrencyService.getPositionGradeCurrencyVOByCondition( pagedListGradeCurrencyHolder, true );

         // ˢ��holder�����ʻ���ֵ
         refreshHolder( pagedListGradeCurrencyHolder, request );
         request.setAttribute( "pagedListGradeCurrencyHolder", pagedListGradeCurrencyHolder );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();

         // ����ǰְ����Ӧ��Currency����Json
         array.addAll( pagedListGradeCurrencyHolder.getSource() );

         request.setAttribute( "currencyListJson", array );

         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax��������ת
            return mapping.findForward( "listPositionGradeCurrency" );
         }
         else
         {
            // ��Ajax���ã�Holder��д��Request����
            request.setAttribute( "pagedListGradeCurrencyHolder", pagedListGradeCurrencyHolder );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listPositionGradeCurrency" );
   }


   
   /**
    * Manage Object
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward manage_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      try
      {
         // ��ʼ��Service�ӿ�
         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "positionGradeCurrencyService" );
         // ���ActionForm
         final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;
         // �����˻�ID
         positionGradeCurrencyVO.setAccountId( getAccountId( request, response ) );
         // ���ò�����ԱID
         positionGradeCurrencyVO.setModifyBy( getUserId( request, response ) );

         // �޸�positionGradeCurrencyVO
         positionGradeCurrencyService.updatePositionGradeCurrency( positionGradeCurrencyVO );

         // ���ر༭�ɹ����
         success( request, MESSAGE_TYPE_UPDATE );
         
         // ���Form����
         ( ( PositionGradeCurrencyVO ) form ).reset();
         //         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   
   /**
    * Delete PositionGradeCurrencyList
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "positionGradeCurrencyService" );

         final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;

         if ( positionGradeCurrencyVO.getSelectedIds() != null && !positionGradeCurrencyVO.getSelectedIds().equals( "" ) )
         {

            for ( String selectedId : positionGradeCurrencyVO.getSelectedIds().split( "," ) )
            {
               //ת��
               String newId = new String( selectedId.getBytes( "ISO8859_1" ), "GB2312" );
               positionGradeCurrencyVO.setCurrencyId( newId );
               positionGradeCurrencyVO.setModifyBy( getUserId( request, response ) );
               positionGradeCurrencyService.deletePositionGradeCurrencyByCondition( positionGradeCurrencyVO );
            }

         }
         // ���Selected IDs����Action
         positionGradeCurrencyVO.setSelectedIds( "" );
         positionGradeCurrencyVO.setSubAction( "" );
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
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

}
