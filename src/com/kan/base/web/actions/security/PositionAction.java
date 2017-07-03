package com.kan.base.web.actions.security;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.GroupVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.service.inf.security.PositionService;
import com.kan.base.service.inf.system.LogService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.pdf.Block;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

public class PositionAction extends BaseAction
{
   public static String accessAction = "HRO_SEC_POSITION_INTERNAL";

   /**
    * List Positions Json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ʼ��Service�ӿ�

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         final String corpId = KANUtil.filterEmpty( getCorpId( request, response ) );
         for ( PositionVO positionVO : KANConstants.getKANAccountConstants( getAccountId( request, response ) ).POSITION_VO )
         {
            if ( positionVO != null
                  && ( ( corpId == null && KANUtil.filterEmpty( positionVO.getCorpId() ) == null ) || ( corpId != null && positionVO.getCorpId() != null && positionVO.getCorpId().equals( corpId ) ) ) )
            {
               final String positionOwnerNames = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).get3StaffNamesByPositionId( request.getLocale().getLanguage(), positionVO.getPositionId() );
               final StringBuffer strb = new StringBuffer();
               JSONObject jsonObject = new JSONObject();
               jsonObject.put( "id", positionVO.getPositionId() );
               strb.append( positionVO.getTitleZH() + " / " + positionVO.getTitleEN() + ( KANUtil.filterEmpty( positionOwnerNames ) == null ? "" : " - " + positionOwnerNames ) );
               jsonObject.put( "name", strb.toString() );

               array.add( jsonObject );
            }
         }

         // Send to client
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }

   @Override
   /**
    * List Positions
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException 
    */
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ����ʵ��
         List< MappingVO > entities = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getEntities( request.getLocale().getLanguage(), getCorpId( request, null ) );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ���Action Form
         final PositionVO positionVO = ( PositionVO ) form;
         MappingVO emptyMappingVO = new MappingVO( "", KANUtil.getProperty( request.getLocale(), "public.empty.mapping.value" ) );
         entities.add( 0, emptyMappingVO );

         // �����Action��ɾ��ְλ�б�
         if ( positionVO != null && positionVO.getSubAction() != null && positionVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ��ְλ�б��Action
            delete_objectList( mapping, form, request, response );
         }
         else if ( positionVO != null && positionVO.getSubAction() != null && positionVO.getSubAction().equalsIgnoreCase( DELETE_OBJECT ) )
         {
            // ����ɾ��ְλ�б��Action
            delete_object( mapping, form, request, response );
         }

         final String rootPositionId = request.getParameter( "rootPositionId" );
         String positionPrefer = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_POSITION_PREFER;
         final String tempPositionPrefer = request.getParameter( "tempPositionPrefer" );
         if ( KANUtil.filterEmpty( tempPositionPrefer ) != null )
         {
            positionPrefer = tempPositionPrefer;
         }
         request.setAttribute( "tempPositionPrefer", tempPositionPrefer );
         request.setAttribute( "positionPrefer", positionPrefer );
         request.setAttribute( "rootPositionId", rootPositionId );
         request.setAttribute( "entities", entities );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // ����EXCEL
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().exportPositionExcel( mapping, form, request, response );
            }

            // Ajax��������ת
            if ( "2".equals( positionPrefer ) )
            {
               return mapping.findForward( "listPositionBody" );
            }
            else
            {
               return getOrgChart( mapping, form, request, response );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listPosition" );
   }

   public void load_html_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         StaffDTO staffDTO = null;
         // ��ȡbranchId
         final String branchId = request.getParameter( "branchId" );
         // ��ȡstaffId(����ȥ���Ѿ����ڵ�ְλ)
         final String staffId = request.getParameter( "staffId" );
         // ��ʼ��Positions
         final List< MappingVO > positionMappingVOs = new ArrayList< MappingVO >();
         // ��ȡ����PositionVO�б�
         final List< PositionVO > positionVOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionVOsByBranchId( branchId );

         if ( KANUtil.filterEmpty( staffId ) != null )
         {
            staffDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getStaffDTOByStaffId( staffId );
         }

         // ����PositionVO�б�
         if ( positionVOs != null && positionVOs.size() > 0 )
         {
            for ( PositionVO positionVO : positionVOs )
            {
               // ����ȥ���Ѿ����ڵ�ְλ
               if ( staffDTO != null && staffDTO.getPositionStaffRelationVOs() != null && staffDTO.getPositionStaffRelationVOs().size() > 0 )
               {
                  boolean exist = false;
                  for ( PositionStaffRelationVO o : staffDTO.getPositionStaffRelationVOs() )
                  {
                     if ( o.getPositionId().equals( positionVO.getPositionId() ) )
                     {
                        exist = true;
                        break;
                     }
                  }
                  if ( exist )
                     continue;
               }

               if ( KANUtil.filterEmpty( getCorpId( request, null ) ) != null && KANUtil.filterEmpty( positionVO.getCorpId() ) == null )
                  continue;

               final PositionVO tempPositionVO = new PositionVO();
               tempPositionVO.setPositionGradeId( positionVO.getPositionGradeId() );
               tempPositionVO.reset( null, request );
               // ְ������
               final String positionGradeName = ( KANUtil.filterEmpty( positionVO.getPositionGradeId(), "0" ) == null ? ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "[����ְ��]"
                     : "[No Position Grade]" )
                     : "[" + tempPositionVO.getDecodePositionGradeId() + "]" );
               // ְλ����
               final String positionName = request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? positionVO.getTitleZH() : positionVO.getTitleEN();
               // ������
               final String isVacant = positionVO.getIsVacant();
               // ְλ����Ա��
               final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffDTOsByPositionId( positionVO.getPositionId() );
               // ����������ʾ
               final String isVacantTips = ( Integer.valueOf( isVacant ) != 0 && ( staffDTOs.size() >= Integer.valueOf( isVacant ) ) ? ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "[��������]"
                     : "[All positions are filled.]" )
                     : "" );
               // ��ȡ�ϼ�ְλstaff
               final List< StaffDTO > parentStaffDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffDTOsByPositionId( positionVO.getPositionId() );
               // �ϼ�ְλ������
               String parenrPositionOwner = "";
               if ( parentStaffDTOs != null && parentStaffDTOs.size() > 0 )
               {
                  for ( StaffDTO parentStaffDTO : parentStaffDTOs )
                  {
                     if ( KANUtil.filterEmpty( parenrPositionOwner ) == null )
                     {
                        parenrPositionOwner = request.getLocale().getLanguage().equals( "zh" ) ? parentStaffDTO.getStaffVO().getNameZH() : parentStaffDTO.getStaffVO().getNameEN();
                     }
                     else
                     {
                        parenrPositionOwner = parenrPositionOwner + "��"
                              + ( request.getLocale().getLanguage().equals( "zh" ) ? parentStaffDTO.getStaffVO().getNameZH() : parentStaffDTO.getStaffVO().getNameEN() );
                     }
                  }
               }
               // ��ʼ��MappingVO
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( positionVO.getPositionId() );
               mappingVO.setMappingValue( positionGradeName + "&nbsp;" + positionName + "&nbsp;" + isVacantTips + "&nbsp;" + parenrPositionOwner );
               if ( KANUtil.filterEmpty( isVacantTips ) != null )
               {
                  mappingVO.setOptionStyle( "style=\"color: red;\" disabled=\"disabled\"" );
               }

               positionMappingVOs.add( mappingVO );
            }
         }

         positionMappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         // Send to client
         out.println( KANUtil.getOptionHTML( positionMappingVOs, "positionId", null ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_object_options_ajax_inHouse( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ʼ������ѡ��
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ��ò��š�ְ��
         final String branchId = KANUtil.filterEmpty( request.getParameter( "branchId" ) );
         final String positionGradeId = KANUtil.filterEmpty( request.getParameter( "positionGradeId" ) );

         // ְλ�ѹ�������Ա��������ʾ

         if ( branchId != null && positionGradeId != null )
         {
            // ����˻���Чְλ
            final List< PositionVO > accountPositionVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).POSITION_VO;

            for ( PositionVO positionVO : accountPositionVOs )
            {

               if ( !"0".equals( branchId ) && !"0".equals( positionGradeId ) && branchId.equals( positionVO.getBranchId() )
                     && positionGradeId.equals( positionVO.getPositionGradeId() ) )
               {
                  // ���������Ƿ�Ϊ�����
                  final String isVacant = positionVO.getIsVacant();

                  final int staffNum = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffNumByPositionId( request.getLocale().getLanguage(), positionVO.getPositionId() );

                  // ְλ����������������ʾ
                  if ( Integer.parseInt( isVacant ) == staffNum )
                  {
                     // ��������Ļ���
                     if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                     {
                        MappingVO mappingVO = new MappingVO();
                        mappingVO.setMappingId( null );
                        mappingVO.setMappingValue( positionVO.getTitleZH() + "[��������]" );
                        mappingVOs.add( mappingVO );
                     }
                     else
                     {
                        MappingVO mappingVO = new MappingVO();
                        mappingVO.setMappingId( null );
                        mappingVO.setMappingValue( positionVO.getTitleEN() + "[All positions are filled.]" );
                        mappingVOs.add( mappingVO );
                     }
                     continue;
                  }

                  // ��������Ļ���
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( positionVO.getPositionId() );
                     mappingVO.setMappingValue( positionVO.getTitleZH() );
                     mappingVOs.add( mappingVO );
                  }
                  else
                  {
                     MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( positionVO.getPositionId() );
                     mappingVO.setMappingValue( positionVO.getTitleEN() );
                     mappingVOs.add( mappingVO );
                  }
               }

            }

         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "positionId", null ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "" );
   }

   /**
    * To Group Create
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
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ȡParentPositionId
         String parentPositionId = request.getParameter( "parentPositionId" );
         // ���ParentPositionId��Ϊ�գ����ܲ����ó�ʼ��ֵ
         if ( parentPositionId != null && !parentPositionId.trim().equals( "" ) )
         {
            parentPositionId = Cryptogram.decodeString( URLDecoder.decode( parentPositionId, "UTF-8" ) );
            ( ( PositionVO ) form ).setParentPositionId( parentPositionId );
            ( ( PositionVO ) form ).setParentPositionName( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionNameByPositionId( parentPositionId ) );
         }

         // ����Sub Action
         ( ( PositionVO ) form ).setStatus( PositionVO.TRUE );
         ( ( PositionVO ) form ).setNeedPublish( PositionVO.FALSE );
         ( ( PositionVO ) form ).setSubAction( CREATE_OBJECT );
         final String rootPositionId = request.getParameter( "rootPositionId" );
         String positionPrefer = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_POSITION_PREFER;
         final String tempPositionPrefer = request.getParameter( "tempPositionPrefer" );
         if ( KANUtil.filterEmpty( tempPositionPrefer ) != null )
         {
            positionPrefer = tempPositionPrefer;
         }
         request.setAttribute( "tempPositionPrefer", tempPositionPrefer );

         request.setAttribute( "rootPositionId", rootPositionId );
         request.setAttribute( "positionPrefer", positionPrefer );

         // ��ʼ������Tab Number
         request.setAttribute( "staffCount", 0 );
         request.setAttribute( "positionGroupCount", 0 );
         request.setAttribute( "attachmentCount", 0 );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���½�����
      return mapping.findForward( "managePosition" );
   }

   /**
    * To Group Modify
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
         final PositionService positionService = ( PositionService ) getService( "positionService" );

         // ��õ�ǰ����
         String positionId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionId" ), "UTF-8" ) );

         if ( KANUtil.filterEmpty( positionId ) == null )
         {
            positionId = ( ( PositionVO ) form ).getPositionId();
         }

         // ���������Ӧ����
         PositionVO positionVO = positionService.getPositionVOByPositionId( positionId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         positionVO.reset( null, request );
         positionVO.setParentPositionName( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionNameByPositionId( positionVO.getParentPositionId() ) );
         positionVO.setSubAction( VIEW_OBJECT );

         // ���Staff Count����Tab Number��ʾ
         final int staffCount = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffDTOsByPositionId( positionId ).size();
         request.setAttribute( "staffCount", staffCount );

         // ���Position Group Count����Tab Number��ʾ
         final PositionDTO positionDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionDTOByPositionId( positionId );
         final List< GroupVO > groupVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionGroupVOs( getCorpId( request, response ) );

         final int positionGroupCount = getPositionGroupCount( groupVOs, positionDTO );
         request.setAttribute( "positionGroupCount", positionGroupCount );

         // ���Attachment Count����Tab Number��ʾ
         final int attachmentCount = positionVO.getAttachmentArray().length;
         request.setAttribute( "attachmentCount", attachmentCount );
         request.setAttribute( "positionForm", positionVO );

         String positionPrefer = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_POSITION_PREFER;
         final String tempPositionPrefer = request.getParameter( "tempPositionPrefer" );
         if ( KANUtil.filterEmpty( tempPositionPrefer ) != null )
         {
            positionPrefer = tempPositionPrefer;
         }
         request.setAttribute( "positionPrefer", positionPrefer );
         request.setAttribute( "tempPositionPrefer", tempPositionPrefer );
         final String rootPositionId = request.getParameter( "rootPositionId" );
         request.setAttribute( "rootPositionId", rootPositionId );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "managePosition" );
   }

   /**  
    * GetPositionGroupCount
    *	�������ְλ������
    *	@param groupVOs
    *	@param positionDTO
    *	@return
    *	@throws KANException
    */
   private int getPositionGroupCount( final List< GroupVO > groupVOs, final PositionDTO positionDTO ) throws KANException
   {

      // ��ʼ��ְ�� - ְλ���ϵ�б�
      List< PositionGroupRelationVO > relationVOsByPositionId = null;

      if ( positionDTO != null )
      {
         relationVOsByPositionId = positionDTO.getPositionGroupRelationVOs();
      }

      try
      {
         int size = 0;

         if ( groupVOs != null && groupVOs.size() > 0 )
         {
            for ( GroupVO positionGroupVO : groupVOs )
            {
               String tempGroupId = positionGroupVO.getGroupId();

               // ����positionId�ж��Ƿ���Ҫѡ��
               if ( relationVOsByPositionId != null && relationVOsByPositionId.size() > 0 )
               {
                  for ( PositionGroupRelationVO relationVO : relationVOsByPositionId )
                  {
                     if ( relationVO.getGroupId() != null && relationVO.getGroupId().trim().equals( tempGroupId ) )
                     {
                        size += 1;
                     }
                  }
               }

            }

         }

         return size;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Add Position
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
         final String rootPositionId = request.getParameter( "rootPositionId" );
         request.setAttribute( "rootPositionId", rootPositionId );
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final PositionService positionService = ( PositionService ) getService( "positionService" );

            // ���ActionForm
            final PositionVO positionVO = ( PositionVO ) form;

            // ��ȡ��¼�û�
            positionVO.setAccountId( getAccountId( request, response ) );
            positionVO.setCreateBy( getUserId( request, response ) );
            positionVO.setModifyBy( getUserId( request, response ) );

            // �½�����
            positionService.insertPosition( positionVO );

            // ��ʼ��StaffId�ַ����б�
            final List< String > staffIds = new ArrayList< String >();

            // װ�ظ���ǰ��StaffIds
            if ( positionVO.getStaffIdArray() != null && positionVO.getStaffIdArray().length > 0 )
            {
               for ( String staffId : positionVO.getStaffIdArray() )
               {
                  String tempStaffId = "";

                  if ( staffId.contains( "_" ) )
                  {
                     tempStaffId = staffId.split( "_" )[ 0 ];
                  }

                  if ( !staffIds.contains( tempStaffId ) )
                  {
                     staffIds.add( tempStaffId );
                  }
               }
            }

            insertlog( request, positionVO, Operate.ADD, positionVO.getPositionId(), "ְλ�����Ա����" + KANUtil.stringListToJasonArray( staffIds ) );

            // ���¼��س����е�PositionGroup��Position
            constantsInit( "initPositionGroup", getAccountId( request, response ) );
            constantsInit( "initPosition", getAccountId( request, response ) );

            // ���¼���Ӱ���Staff
            if ( staffIds != null && staffIds.size() > 0 )
            {
               for ( String staffId : staffIds )
               {
                  constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffId } );
                  constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffId } );
               }
            }

            constantsInit( "initBranch", getAccountId( request, response ) );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
            if ( KANUtil.filterEmpty( request.getParameter( "forwardURL" ) ) != null )
            {
               // ����ת���ַ
               request.getRequestDispatcher( request.getParameter( "forwardURL" ) ).forward( request, response );
               return null;
            }
         }

         // ���Form����
         ( ( PositionVO ) form ).reset();

         request.removeAttribute( "positionForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Position
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
            final PositionService positionService = ( PositionService ) getService( "positionService" );

            // ��õ�ǰ����
            final String positionId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionId" ), "UTF-8" ) );

            // ���������Ӧ����
            final PositionVO positionVO = positionService.getPositionVOByPositionId( positionId );

            positionVO.update( ( PositionVO ) form );
            // ��ȡ��¼�û�
            positionVO.setModifyBy( getUserId( request, response ) );
            // �޸Ķ���
            positionService.updatePosition( positionVO );

            final List< String > staffIds = new ArrayList< String >();

            // ��ȡ��Ҫ���µ�
            final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getStaffDTOsByPositionId( positionId );
            for ( StaffDTO staffDTO : staffDTOs )
            {
               staffIds.add( staffDTO.getStaffVO().getStaffId() );
            }

            // ��ȡ���µ�Ա��
            if ( positionVO.getStaffIdArray() != null && positionVO.getStaffIdArray().length > 0 )
            {
               // ɾ����ǰ��Ա��ְλ��ϵ
               positionService.deletePositionStaffRelationByPositionId( positionId );

               for ( String staffIdArray : positionVO.getStaffIdArray() )
               {
                  String tempStaffId = "";
                  String tempStaffType = "";

                  if ( staffIdArray.contains( "_" ) && staffIdArray.split( "_" ).length >= 2 )
                  {
                     tempStaffId = staffIdArray.split( "_" )[ 0 ];
                     tempStaffType = staffIdArray.split( "_" )[ 1 ];
                  }

                  // ���staff postion ��ϵ
                  final PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
                  positionStaffRelationVO.setPositionId( positionId );
                  positionStaffRelationVO.setStaffId( tempStaffId );
                  positionStaffRelationVO.setStaffType( KANUtil.filterEmpty( tempStaffType ) == null ? "1" : tempStaffType );

                  // ����Ǵ���ְλ
                  if ( "2".equals( tempStaffType.trim() ) && staffIdArray.split( "_" ).length >= 4 && !staffIdArray.split( "_" )[ 2 ].equals( "null" )
                        && !staffIdArray.split( "_" )[ 3 ].equals( "null" ) )
                  {

                     if ( KANUtil.filterEmpty( staffIdArray.split( "_" )[ 2 ] ) != null )
                     {
                        positionStaffRelationVO.setAgentStart( staffIdArray.split( "_" )[ 2 ] );
                     }

                     if ( KANUtil.filterEmpty( staffIdArray.split( "_" )[ 3 ] ) != null )
                     {
                        positionStaffRelationVO.setAgentEnd( staffIdArray.split( "_" )[ 3 ] );
                     }

                  }

                  positionStaffRelationVO.setStatus( "1" );
                  positionStaffRelationVO.setCreateBy( getUserId( request, null ) );
                  positionStaffRelationVO.setCreateDate( new Date() );
                  positionService.insertPositionStaffRelation( positionStaffRelationVO );
                  staffIds.add( tempStaffId );
               }
            }

            insertlog( request, positionVO, Operate.MODIFY, positionVO.getPositionId(), "ְλ��Ա����" + KANUtil.stringListToJasonArray( staffIds ) );

            // ���¼��س����е�PositionGroup��Position
            constantsInit( "initPositionGroup", getAccountId( request, response ) );
            constantsInit( "initPosition", getAccountId( request, response ) );

            // ��Ӱ���staff
            if ( staffIds != null && staffIds.size() > 0 )
            {
               for ( String staffId : staffIds )
               {
                  constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffId } );
                  constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffId } );
               }
            }
            constantsInit( "initBranch", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
            if ( KANUtil.filterEmpty( request.getParameter( "forwardURL" ) ) != null )
            {
               // ����ת���ַ
               request.getRequestDispatcher( request.getParameter( "forwardURL" ) ).forward( request, response );
               return null;
            }
         }

         // ���Form����
         ( ( PositionVO ) form ).reset();
         request.removeAttribute( "positionForm" );
         final String rootPositionId = request.getParameter( "rootPositionId" );
         request.setAttribute( "rootPositionId", rootPositionId );
         final String tempPositionPrefer = request.getParameter( "tempPositionPrefer" );
         String positionPrefer = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_POSITION_PREFER;
         if ( KANUtil.filterEmpty( tempPositionPrefer ) != null )
         {
            positionPrefer = tempPositionPrefer;
         }
         request.setAttribute( "positionPrefer", positionPrefer );
         request.setAttribute( "tempPositionPrefer", tempPositionPrefer );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return to_objectModify( mapping, form, request, response );
   }

   public ActionForward check_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();
         JSONObject jsonObject = new JSONObject();
         final PositionService positionService = ( PositionService ) getService( "positionService" );
         String staffId = request.getParameter( "staffId" );
         boolean flag = false;
         if ( staffId != null && staffId != "" )
         {
            List< Object > list = positionService.getPositionStaffRelationVOsByStaffId( staffId );
            if ( list != null && list.size() > 0 )
            {
               for ( Object object : list )
               {
                  PositionStaffRelationVO positionStaffRelationVO = ( com.kan.base.domain.security.PositionStaffRelationVO ) object;
                  if ( positionStaffRelationVO.getStaffType().equals( "1" ) )
                  {
                     flag = true;
                  }
               }
            }
         }
         jsonObject.put( "success", flag );
         out.println( jsonObject != null ? jsonObject.toString() : "" );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "" );
   }

   /**
    * Modify Position Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PositionService positionService = ( PositionService ) getService( "positionService" );

         // ��õ�ǰ����
         final String encodePositionId = request.getParameter( "positionId" );
         String positionId = "";
         PositionVO positionVO = new PositionVO();

         // ���PositionId��Ϊ�յ�����������ݿ����PositionVO����
         if ( encodePositionId != null && !encodePositionId.equals( "" ) )
         {
            positionId = KANUtil.decodeStringFromAjax( encodePositionId );
            positionVO = positionService.getPositionVOByPositionId( positionId );
         }

         // �����ύ����������
         positionVO.update( ( PositionVO ) form );
         // ����
         positionVO.setTitleZH( URLDecoder.decode( URLDecoder.decode( positionVO.getTitleZH(), "UTF-8" ), "UTF-8" ) );
         positionVO.setTitleEN( URLDecoder.decode( URLDecoder.decode( positionVO.getTitleEN(), "UTF-8" ), "UTF-8" ) );
         positionVO.setDescription( URLDecoder.decode( URLDecoder.decode( positionVO.getDescription(), "UTF-8" ), "UTF-8" ) );

         positionVO.setAccountId( getAccountId( request, response ) );
         positionVO.setModifyBy( getUserId( request, response ) );

         // ���PositionId��Ϊ�յ�������޸�PositionModule���󣬷�֮����PositionVOһ���½�
         if ( encodePositionId != null && !encodePositionId.equals( "" ) )
         {
            // �޸�PositionModule����
            positionService.updatePositionModule( positionVO );
         }
         else
         {
            // �½�Position����
            positionService.insertPosition( positionVO );
         }

         // ���ر༭�ɹ����
         success( request, MESSAGE_TYPE_UPDATE );

         insertlog( request, positionVO, Operate.MODIFY, positionVO.getPositionId(), "modify_object_ajax" );

         // ���Form����
         ( ( PositionVO ) form ).reset();

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // Send to client
         out.println( URLEncoder.encode( Cryptogram.encodeString( positionVO.getPositionId() ), "UTF-8" ) );
         out.flush();
         out.close();

         // ���¼��س����е�Position
         constantsInit( "initPosition", getAccountId( request, response ) );
         constantsInit( "initBranch", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax���ã���ת���հ�ҳ��
      return mapping.findForward( "" );
   }

   /**  
    * Modify Object Ajax Popup
    *	ģ̬���޸�ְλ����Ȩ�� - Ajax
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward modify_object_ajax_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PositionService positionService = ( PositionService ) getService( "positionService" );

         // ���PositionId�� moduleId
         final String positionId = KANUtil.decodeStringFromAjax( request.getParameter( "positionId" ) );
         final String moduleId = request.getParameter( "moduleId" );

         final PositionVO positionVO = positionService.getPositionVOByPositionId( positionId );

         // ����PositionVO �� RightIdArray��RuleIdArray
         positionVO.setRightIdArray( ( ( PositionVO ) form ).getRightIdArray() );
         positionVO.setRuleIdArray( ( ( PositionVO ) form ).getRuleIdArray() );

         positionVO.setAccountId( getAccountId( request, response ) );
         positionVO.setModifyBy( getUserId( request, response ) );

         // �������ݿ�
         positionService.updatePositionModuleRelationPopup( positionVO, moduleId );

         // ���ر༭�ɹ����
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

         insertlog( request, positionVO, Operate.MODIFY, positionVO.getPositionId(), "modify_object_ajax_popup" );

         // ���Form����
         ( ( PositionVO ) form ).reset();

         // ���¼��س����е�Position
         constantsInit( "initPosition", getAccountId( request, response ) );
         constantsInit( "initBranch", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax�޸ģ�����ת
      return mapping.findForward( "" );
   }

   /**
    * Delete Position
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
      try
      {
         // ��ʼ��Service�ӿ�
         final PositionService positionService = ( PositionService ) getService( "positionService" );

         // ���Action Form
         final PositionVO positionVO = ( PositionVO ) form;
         // ����ѡ�е�ID
         if ( positionVO.getSelectedIds() != null && !positionVO.getSelectedIds().equals( "" ) )
         {
            // ����ɾ���ӿ�
            positionVO.setAccountId( getAccountId( request, response ) );
            positionVO.setPositionId( positionVO.getSelectedIds() );
            positionVO.setModifyBy( getUserId( request, response ) );
            positionService.deletePosition( positionVO );
         }

         // ���¼��س����е�Position
         constantsInit( "initPosition", getAccountId( request, response ) );
         constantsInit( "initBranch", getAccountId( request, response ) );

         // ���SubAction
         positionVO.setSubAction( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Position List
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
         final PositionService positionService = ( PositionService ) getService( "positionService" );

         // ���Action Form
         final PositionVO positionVO = ( PositionVO ) form;
         // ����ѡ�е�ID
         if ( positionVO.getPositionIdArray() != null && positionVO.getPositionIdArray().length > 0 )
         {
            for ( String positionId : positionVO.getPositionIdArray() )
            {
               // ����ɾ���ӿ�
               positionVO.setAccountId( getAccountId( request, response ) );
               positionVO.setPositionId( positionId );
               positionVO.setModifyBy( getUserId( request, response ) );
               positionService.deletePosition( positionVO );
            }

            insertlog( request, positionVO, Operate.DELETE, null, KANUtil.toJasonArray( positionVO.getPositionIdArray() ) );
         }

         // ���¼��س����е�Position
         constantsInit( "initPosition", getAccountId( request, response ) );
         constantsInit( "initBranch", getAccountId( request, response ) );

         // ���SubAction
         positionVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward getOrgChart( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      String positionId = request.getParameter( "positionId" );
      if ( KANUtil.filterEmpty( positionId ) == null )
      {
         positionId = ( String ) request.getAttribute( "rootPositionId" );
      }
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getIndependentDisplayPositionDTOs( getCorpId( request, response ) );
      List< PositionDTO > targetpositionDTOs = new ArrayList< PositionDTO >();
      for ( PositionDTO tempDTO : positionDTOs )
      {
         if ( positionId.equals( tempDTO.getPositionVO().getPositionId() ) )
         {
            targetpositionDTOs.add( tempDTO );
            break;
         }
      }
      request.setAttribute( "positionDTOs", targetpositionDTOs );

      return mapping.findForward( "treePositionTable" );
   }

   public ActionForward exportPdf( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ�� JSONArray
         final String data = URLDecoder.decode( URLDecoder.decode( ( String ) request.getParameter( "data" ), "UTF-8" ), "UTF-8" );
         JSONArray jsonArray = JSONArray.fromObject( data );
         final String title = URLDecoder.decode( URLDecoder.decode( ( String ) request.getParameter( "title" ), "UTF-8" ), "UTF-8" );
         JSONObject titleObject = JSONObject.fromObject( title );
         List< Block > blocks = new ArrayList< Block >();
         for ( int i = 0; i < jsonArray.size(); i++ )
         {
            JSONObject jsonObject = ( JSONObject ) jsonArray.get( i );
            final Block block = new Block();
            block.setPosX( jsonObject.getInt( "left" ) - Block.PAGEBLANKX );
            block.setPosY( jsonObject.getInt( "top" ) - Block.PAGEBLANKY );
            block.setWidth( jsonObject.getInt( "width" ) );
            block.setHeight( jsonObject.getInt( "height" ) );
            block.setObjectId( jsonObject.getString( "positionId" ) );
            block.setParentObjectId( jsonObject.getString( "parentId" ) );
            block.setObjectNameZH( jsonObject.getString( "positionNameZH" ) );
            block.setObjectNameEN( jsonObject.getString( "positionNameEN" ) );
            // Ӣ�����Ͳ��ű��һ��
            block.setObjectNO( "" );
            blocks.add( block );
         }

         // ��ȡ����ߵı߾࣬��Сֽ��
         int minLeft = Block.minLeft( blocks );
         Block.reSize( blocks, minLeft );
         final int maxWidth = Integer.parseInt( request.getParameter( "maxWidth" ) );
         final int maxHeight = Integer.parseInt( request.getParameter( "maxHeight" ) );

         BufferedImage image = new BufferedImage( maxWidth + 40, maxHeight + 100, BufferedImage.TYPE_INT_RGB );
         Graphics g = image.getGraphics();
         g.setFont( new Font( "����", Font.PLAIN, 12 ) );// ����  ���壨Font.PLAIN�� ��С12
         g.setColor( Color.WHITE );
         g.fillRect( 0, 0, image.getWidth(), image.getHeight() );
         // �������еĸ���
         g.setColor( Color.BLACK );
         for ( Block block : blocks )
         {
            block.paintThis( g, block );
         }
         // ���Ӹ���
         for ( int i = 0; i < blocks.size(); i++ )
         {
            for ( int j = i + 1; j < blocks.size(); j++ )
            {
               if ( blocks.get( j ).getParentObjectId().equals( blocks.get( i ).getObjectId() ) && !"0".equals( blocks.get( i ) ) && !"0".equals( blocks.get( j ) ) )
               {
                  Block.blockToBlock( g, blocks.get( i ), blocks.get( j ) );
               }
            }
         }
         // ����
         Block.paintTitle( g, titleObject.getString( "titleName" ), titleObject.getInt( "titleLeft" ) - minLeft - Block.PAGEBLANKX, titleObject.getInt( "titleTop" )
               - Block.PAGEBLANKY );

         // ����pdf
         //KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         //HTMLParseUtil.imageParsePDF( image, getAccountId( request, response ), true, KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) ? accountConstants.getClientLogoFileByCorpId( BaseAction.getCorpId( request, response ) ): accountConstants.OPTIONS_LOGO_FILE )

         new DownloadFileAction().download( response, KANUtil.getByteArray( image ), "positionOrganizationChart.png" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }

   /***
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_transferHROwner( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      return mapping.findForward( "/popup/transferHROwner.jsp" );
   }

   /***
    * ת��HR�Խ���
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward transferHROwner( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      final String newOwner = request.getParameter( "newHROwner" );
      final String oldOwner = request.getParameter( "oldHROwner" );
      final String entityId = request.getParameter( "entityId" );
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
      String rowStr = employeeContractService.transferHROwner( oldOwner, newOwner, entityId );
      if ( !"0_0".equals( rowStr ) )
      {
         success( request, null, "�����ɹ�����ת�� " + rowStr.split( "_" )[ 0 ] + " ��Ա��, " + rowStr.split( "_" )[ 1 ] + " ���Ͷ���ͬ" );
         JSONObject jsonObject = new JSONObject();
         jsonObject.put( "newOwner", newOwner );
         jsonObject.put( "oldOwner", oldOwner );
         jsonObject.put( "entityId", entityId );
         LogVO logVO = new LogVO();
         logVO.setType( String.valueOf( Operate.TRANSFER_HROWNER.getIndex() ) );
         logVO.setModule( "transfer.hr.owner" );
         logVO.setIp( getIPAddress( request ) );
         logVO.setOperateTime( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
         logVO.setOperateBy( getUserId( request, response ) );
         logVO.setRemark( "ת��HR�Խ���" );
         logVO.setContent( jsonObject.toString() );
         
         LogService logService = ( LogService ) getService( "logService" );
         logService.insertLog( logVO );
      }
      else
      {
         warning( request, null, "û���κ�Ա����HR�Խ���ת��" );
      }
      return list_object( mapping, form, request, response );
   }

}
