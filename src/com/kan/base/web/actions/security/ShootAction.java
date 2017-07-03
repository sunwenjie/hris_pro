package com.kan.base.web.actions.security;

import java.io.PrintWriter;
import java.net.URLDecoder;
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
import com.kan.base.domain.security.BranchDTO;
import com.kan.base.domain.security.OrgShootVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.service.inf.security.OrgShootService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class ShootAction extends BaseAction
{
   /**
    * 保存快照
    */
   public ActionForward saveOrgShoot( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 页面类型，1为branch 2 position
         final String pageType = request.getParameter( "pageType" );
         final OrgShootVO orgShootVO = new OrgShootVO();
         orgShootVO.setAccountId( getAccountId( request, null ) );
         orgShootVO.setCorpId( getCorpId( request, null ) );
         final String nameZH = URLDecoder.decode( URLDecoder.decode( ( String ) request.getParameter( "nameZH" ), "UTF-8" ), "UTF-8" );
         final String nameEN = URLDecoder.decode( URLDecoder.decode( ( String ) request.getParameter( "nameEN" ), "UTF-8" ), "UTF-8" );
         final String description = URLDecoder.decode( URLDecoder.decode( ( String ) request.getParameter( "description" ), "UTF-8" ), "UTF-8" );
         orgShootVO.setNameZH( nameZH );
         orgShootVO.setNameEN( nameEN );
         orgShootVO.setDescription( description );
         // 部门类型为1
         if ( "branch".equalsIgnoreCase( pageType ) )
         {
            orgShootVO.setShootType( "1" );
            final List< BranchDTO > branchDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getBranchDTOsByCorpId( getCorpId( request, response ) );
            //设置历史法务实体名字
            for ( int i = 0; i < branchDTOs.size(); i++ )
            {
               branchDTOs.get( i ).resetEntity();
               branchDTOs.get( i ).resetStaffMappingVOs();
            }
            // 把对象存在数据库
            orgShootVO.setShootData( KANUtil.object2bytes( branchDTOs ) );
         }
         else
         {
            orgShootVO.setShootType( "2" );
            final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionDTOsByCorpId( getCorpId( request, response ) );
            for ( int i = 0; i < positionDTOs.size(); i++ )
            {
               positionDTOs.get( i ).resetStaffMappingVOs();
            }
            // 把对象存在数据库
            orgShootVO.setShootData( KANUtil.object2bytes( positionDTOs ) );
         }
         orgShootVO.setCreateBy( getUserId( request, null ) );
         orgShootVO.setCreateDate( new Date() );
         final OrgShootService orgShootService = ( OrgShootService ) getService( "orgShootService" );
         orgShootService.insertOrgShoot( orgShootVO );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         out.print( "success" );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return null;
   }

   /**
    * 列出快照
   */
   public ActionForward list_OrgShoot( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final String pageType = request.getParameter( "pageType" );
         final OrgShootService orgShootService = ( OrgShootService ) getService( "orgShootService" );
         OrgShootVO condVO = new OrgShootVO();
         condVO.setAccountId( getAccountId( request, null ) );
         condVO.setCorpId( getCorpId( request, null ) );
         if ( "branch".equalsIgnoreCase( pageType ) )
         {
            condVO.setShootType( "1" );
         }
         else
         {
            condVO.setShootType( "2" );
         }
         List< Object > orgShootObjects = orgShootService.getOrgShootVOsByCond( condVO );
         OrgShootVO orgShootVO;
         JSONArray jsonArray = new JSONArray();
         for ( Object obj : orgShootObjects )
         {
            orgShootVO = ( OrgShootVO ) obj;
            final JSONObject jsonObj = new JSONObject();
            if ( "zh".equalsIgnoreCase( getLocale( request ).getLanguage() ) )
            {
               jsonObj.put( "name", orgShootVO.getNameZH() );
            }
            else
            {
               jsonObj.put( "name", orgShootVO.getNameEN() );
            }
            jsonObj.put( "description", orgShootVO.getDescription() );
            jsonObj.put( "id", orgShootVO.getShootId() );
            jsonObj.put( "createDate", orgShootVO.getDecodeCreateDate() );
            jsonArray.add( jsonObj );
         }
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         out.print( jsonArray.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return null;
   }

   /**
    * 快照下拉框
    */
   public ActionForward to_orgShoot_detail( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final String pageType = request.getParameter( "pageType" );
         final String id = request.getParameter( "id" );
         final OrgShootService orgShootService = ( OrgShootService ) getService( "orgShootService" );
         final OrgShootVO orgShootVO = orgShootService.getOrgShootVOByShootId( id );
         final byte[] data = orgShootVO.getShootData();
         final Object objs = KANUtil.bytes2Object( data );
         request.setAttribute( "shootId", id );
         request.setAttribute( "orgShootVO", orgShootVO );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final JSONArray jsonArray = new JSONArray();
         if ( "branch".equalsIgnoreCase( pageType ) )
         {
            final List< BranchDTO > branchDTOs = ( List< BranchDTO > ) objs;
            for ( BranchDTO branchDTO : branchDTOs )
            {
               if ( "0".equals( branchDTO.getBranchVO().getParentBranchId() ) )
               {
                  final JSONObject jsonObject = new JSONObject();
                  jsonObject.put( "branchId", branchDTO.getBranchVO().getBranchId() );
                  jsonObject.put( "decodeEntityId", branchDTO.getEntityVO().getNameZH() );
                  jsonObject.put( "decodeBranchName", branchDTO.getBranchVO().getNameZH() );
                  jsonArray.add( jsonObject );
               }
            }
         }
         else
         {
            final List< PositionDTO > positionDTOs = ( List< PositionDTO > ) objs;
            for ( PositionDTO positionDTO : positionDTOs )
            {
               if ( "0".equals( positionDTO.getPositionVO().getParentPositionId() ) || "1".equals( positionDTO.getPositionVO().getIsIndependentDisplay() ) )
               {
                  final JSONObject jsonObject = new JSONObject();
                  jsonObject.put( "positionId", positionDTO.getPositionVO().getPositionId() );
                  jsonObject.put( "title", positionDTO.getPositionVO().getTitleZH() );
                  jsonArray.add( jsonObject );
               }
            }
         }
         out.print( jsonArray.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return null;
   }

   /**
    * 根据快照ID 获取快照
    */
   public ActionForward getOrgShootChart( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      final String pageType = request.getParameter( "pageType" );
      final String branchId = request.getParameter( "branchId" );
      final String positionId = request.getParameter( "positionId" );
      String shootId = request.getParameter( "shootId" );
      final OrgShootService orgShootService = ( OrgShootService ) getService( "orgShootService" );
      final OrgShootVO orgShootVO = orgShootService.getOrgShootVOByShootId( shootId );
      final byte[] data = orgShootVO.getShootData();
      request.setAttribute( "isShoot", true );
      request.setAttribute( "shootId", shootId );
      if ( "branch".equalsIgnoreCase( pageType ) )
      {
         final List< BranchDTO > branchDTOs = ( List< BranchDTO > ) KANUtil.bytes2Object( data );
         List< BranchDTO > targetBranchDTOs = new ArrayList< BranchDTO >();
         for ( BranchDTO tempDTO : branchDTOs )
         {
            if ( branchId.equals( tempDTO.getBranchVO().getBranchId() ) )
            {
               targetBranchDTOs.add( tempDTO );
               break;
            }
         }
         request.setAttribute( "branchDTOs", targetBranchDTOs );

         return mapping.findForward( "treeBranchTable" );
      }
      else
      {
         final List< PositionDTO > positionDTOs = ( List< PositionDTO > ) KANUtil.bytes2Object( data );
         List< PositionDTO > targetPositionDTOs = new ArrayList< PositionDTO >();
         for ( PositionDTO tempDTO : positionDTOs )
         {
            if ( positionId.equals( tempDTO.getPositionVO().getPositionId() ) )
            {
               targetPositionDTOs.add( tempDTO );
               break;
            }
         }
         request.setAttribute( "positionDTOs", targetPositionDTOs );
         return mapping.findForward( "treePositionTable" );
      }

   }

   public ActionForward getHistoryEmployeeNames( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws Exception
   {
      response.setContentType( "text/html" );
      response.setCharacterEncoding( "GBK" );
      final PrintWriter out = response.getWriter();
      final String type = request.getParameter( "type" );
      final String id = request.getParameter( "id" );
      final String shootId = request.getParameter( "shootId" );
      final OrgShootService orgShootService = ( OrgShootService ) getService( "orgShootService" );
      final OrgShootVO orgShootVO = orgShootService.getOrgShootVOByShootId( shootId );
      final byte[] data = orgShootVO.getShootData();
      final String isSumSubBranchHC = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).OPTIONS_ISSUMSUBBRANCHHC;
      if ( "branch".equalsIgnoreCase( type ) )
      {
         final List< BranchDTO > branchDTOs = ( List< BranchDTO > ) KANUtil.bytes2Object( data );
         final List< MappingVO > mappingVOs = getDistinctMappingVOs( getBranchMappingVOs( id, branchDTOs, isSumSubBranchHC ) );
         final JSONArray jsonArray = JSONArray.fromObject( mappingVOs );
         out.print( jsonArray.toString() );
      }
      else
      {
         final List< PositionDTO > positionDTOs = ( List< PositionDTO > ) KANUtil.bytes2Object( data );
         final List< MappingVO > mappingVOs = getDistinctMappingVOs( getPositionMappingVOs( id, positionDTOs, "0" ) );
         final JSONArray jsonArray = JSONArray.fromObject( mappingVOs );
         out.print( jsonArray.toString() );
      }
      out.flush();
      out.close();
      return null;
   }
   private List< MappingVO > getBranchMappingVOs( final String branchId, final List< BranchDTO > branchDTOs, final String isSumSubBranchHC )
   {
      final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      final BranchDTO branchDTO = getBranchDTO( branchId, branchDTOs );
      if ( "1".equals( isSumSubBranchHC ) )
      {
         mappingVOs.addAll( fetchBranchDTO( branchDTO ) );
      }
      else
      {
         mappingVOs.addAll( branchDTO.getStaffMappingVOs() );
      }
      return mappingVOs;
   }
   private BranchDTO getBranchDTO( final String branchId, final List< BranchDTO > branchDTOs )
   {
      for ( BranchDTO branchDTO : branchDTOs )
      {
         if ( branchId.equals( branchDTO.getBranchVO().getBranchId() ) )
         {
            return branchDTO;
         }
         else
         {
            branchDTO = getBranchDTO( branchId, branchDTO.getBranchDTOs() );
            if ( branchDTO != null )
            {
               return branchDTO;
            }
         }
      }
      return null;
   }
   private List< MappingVO > fetchBranchDTO( final BranchDTO branchDTO )
   {
      final List< MappingVO > mappingVO = new ArrayList< MappingVO >();
      if ( branchDTO.getStaffMappingVOs() != null && branchDTO.getStaffMappingVOs().size() > 0 )
      {
         mappingVO.addAll( branchDTO.getStaffMappingVOs() );
      }
      if ( branchDTO.getBranchDTOs() != null && branchDTO.getBranchDTOs().size() > 0 )
      {
         for ( BranchDTO tempDTO : branchDTO.getBranchDTOs() )
         {
            mappingVO.addAll( fetchBranchDTO( tempDTO ) );
         }
      }
      return mappingVO;
   }
   private List< MappingVO > getPositionMappingVOs( final String positionId, final List< PositionDTO > positionDTOs, final String isSumSubBranchHC )
   {
      final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      final PositionDTO positionDTO = getPositionDTO( positionId, positionDTOs );
      if ( "1".equals( isSumSubBranchHC ) )
      {
         mappingVOs.addAll( fetchPositionDTO( positionDTO ) );
      }
      else
      {
         mappingVOs.addAll( positionDTO.getStaffMappingVOs() );
      }
      return mappingVOs;
   }
   private PositionDTO getPositionDTO( final String positionId, final List< PositionDTO > positionDTOs )
   {
      for ( PositionDTO positionDTO : positionDTOs )
      {
         if ( positionId.equals( positionDTO.getPositionVO().getPositionId() ) )
         {
            return positionDTO;
         }
         else
         {
            positionDTO = getPositionDTO( positionId, positionDTO.getPositionDTOs() );
            if ( positionDTO != null )
            {
               return positionDTO;
            }
         }
      }
      return null;
   }
   private List< MappingVO > fetchPositionDTO( final PositionDTO positionDTO )
   {
      final List< MappingVO > mappingVO = new ArrayList< MappingVO >();
      if ( positionDTO.getStaffMappingVOs() != null && positionDTO.getStaffMappingVOs().size() > 0 )
      {
         mappingVO.addAll( positionDTO.getStaffMappingVOs() );
      }
      if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
      {
         for ( PositionDTO tempDTO : positionDTO.getPositionDTOs() )
         {
            mappingVO.addAll( fetchPositionDTO( tempDTO ) );
         }
      }
      return mappingVO;
   }
   private List< MappingVO > getDistinctMappingVOs( final List< MappingVO > mappingVOs )
   {
      final List< MappingVO > distinctMappingVOs = new ArrayList< MappingVO >();
      for ( MappingVO mappingVO : mappingVOs )
      {
         boolean flag = false;
         for ( int i = distinctMappingVOs.size() - 1; i >= 0; i-- )
         {
            if ( mappingVO.getMappingId().equals( distinctMappingVOs.get( i ).getMappingId() ) )
            {
               flag = true;
               break;
            }
         }
         if ( !flag )
         {
            distinctMappingVOs.add( mappingVO );
         }
      }
      return distinctMappingVOs;
   }
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
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
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
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
}
