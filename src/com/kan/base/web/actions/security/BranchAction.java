package com.kan.base.web.actions.security;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.BranchDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffBaseView;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.BranchService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.WXUtil;
import com.kan.base.util.pdf.Block;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.employee.EmployeeEducationVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeEducationService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

public class BranchAction extends BaseAction
{
   public static String accessAction = "HRO_SYS_BRANCH";

   /**  
    * List position HTML
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException 
    */
   public ActionForward list_positions_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取部门Id
         final String branchId = request.getParameter( "branchId" );
         // 获得职位控件Id_name
         final String id_name = request.getParameter( "id_name" );
         // 获得职位控件value
         final String value = request.getParameter( "value" );

         // 初始化下拉选项
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         // 获得当前Branch所包含的PositionVOs
         List< PositionVO > positionVOs = new ArrayList< PositionVO >();
         if ( branchId != null && !branchId.trim().equals( "" ) && !branchId.trim().equals( "0" ) )
         {
            positionVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOsByBranchId( branchId );
         }

         // 如果PositionVOs不为空
         if ( positionVOs != null & positionVOs.size() > 0 )
         {
            final String corpId = getCorpId( request, response );

            // 遍历PositionVOs以获得每个PositionId对应的positionDTO
            for ( PositionVO positionVO : positionVOs )
            {

               if ( KANUtil.filterEmpty( corpId ) == null || ( KANUtil.filterEmpty( corpId ) != null && positionVO.getCorpId() != null && corpId.equals( positionVO.getCorpId() ) ) )
               {
                  // 根据PositionId获得已关联的Staff
                  final List< PositionStaffRelationVO > positionStaffRelationVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionDTOByPositionId( positionVO.getPositionId() ).getPositionStaffRelationVOs();

                  // 初始化StaffBaseView
                  final List< StaffBaseView > staffBaseViews = new ArrayList< StaffBaseView >();
                  // 从PositionStaffRelation中创建StaffBaseView对象
                  if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
                  {
                     for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelationVOs )
                     {
                        StaffBaseView staffBaseView = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffBaseViewByStaffId( positionStaffRelationVO.getStaffId() );
                        if ( staffBaseView != null )
                        {
                           staffBaseViews.add( staffBaseView );
                        }
                     }
                  }

                  // 把当前position对应的信息添加到mappingVO中      
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( positionVO.getPositionId() );
                  mappingVO.setMappingValue( KANUtil.getPositionNameWithStaffs( positionVO, staffBaseViews, request.getLocale() ) );
                  mappingVOs.add( mappingVO );
               }
            }
         }

         // MappingVOs添加空选项
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, id_name, value ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   /**
    * List branch
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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final BranchService branchService = ( BranchService ) getService( "branchService" );
         // 获得Action Form
         final BranchVO branchVO = ( BranchVO ) form;

         // 如果子Action是删除用户列表
         if ( branchVO.getSubAction() != null && branchVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         else if ( branchVO.getSubAction() != null && branchVO.getSubAction().equalsIgnoreCase( DELETE_OBJECT ) )
         {
            delete_object( mapping, form, request, response );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder branchHolder = new PagedListHolder();

         // 传入当前页
         branchHolder.setPage( page );
         // 传入当前值对象
         branchHolder.setObject( branchVO );
         // 设置页面记录条数
         branchHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         branchService.getBranchVOsByCondition( branchHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( branchHolder, request );

         // Holder需写入Request对象
         final String rootBranchId = request.getParameter( "rootBranchId" );
         final String branchPrefer = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_BRANCH_PREFER;
         request.setAttribute( "branchHolder", branchHolder );
         request.setAttribute( "branchPrefer", branchPrefer );
         request.setAttribute( "rootBranchId", rootBranchId );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax调用无跳转
            if ( "2".equals( branchPrefer ) )
            {
               return mapping.findForward( "listBranchTable" );
            }
            else
            {
               return getOrgChart( mapping, form, request, response );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listBranch" );
   }

   /**
    * to_objectNew
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
      // 添加页面Token
      try
      {
         this.saveToken( request );

         // 获取ParentPositionId
         String parentBranchId = request.getParameter( "parentBranchId" );
         // 如果ParentPositionId不为空，解密并设置初始化值
         if ( parentBranchId != null && !parentBranchId.trim().equals( "" ) )
         {
            parentBranchId = Cryptogram.decodeString( URLDecoder.decode( parentBranchId, "UTF-8" ) );
            ( ( BranchVO ) form ).setParentBranchId( parentBranchId );
            final BranchVO tempBranchVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getBranchVOByBranchId( parentBranchId );
            ( ( BranchVO ) form ).setParentBranchName( tempBranchVO.getNameZH() );
            // 设置默认法务实体也业务类型为父节点的法务实体和业务类型
            ( ( BranchVO ) form ).setEntityId( tempBranchVO.getEntityId() );
            ( ( BranchVO ) form ).setBusinessTypeId( tempBranchVO.getBusinessTypeId() );

         }

         // 设置Sub Action
         ( ( BranchVO ) form ).setStatus( BranchVO.TRUE );
         ( ( BranchVO ) form ).setSubAction( CREATE_OBJECT );
         final String rootBranchId = request.getParameter( "rootBranchId" );
         request.setAttribute( "rootBranchId", rootBranchId );
         // 用户返回时显示为上次浏览状态
         request.setAttribute( "branchPrefer", KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_BRANCH_PREFER );
      }
      catch ( final Exception e )
      {
         // TODO Auto-generated catch block
         throw new KANException( e );
      }

      // 跳转到新建界面
      return mapping.findForward( "manageBranch" );
   }

   /**
    * Add branch
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
         final String rootBranchId = request.getParameter( "rootBranchId" );
         request.setAttribute( "rootBranchId", rootBranchId );
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final BranchService branchService = ( BranchService ) getService( "branchService" );

            // 获得ActionForm
            final BranchVO branchVO = ( BranchVO ) form;

            // 获取登录用户
            branchVO.setAccountId( getAccountId( request, response ) );
            branchVO.setCreateBy( getUserId( request, response ) );
            branchVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            branchService.insertBranch( branchVO );

            // 初始化常量持久对象
            constantsInit( "initBranch", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
            
            syncWebChartBUFunction();

            insertlog( request, branchVO, Operate.ADD, branchVO.getBranchId(), null );
         }
         else
         {
            // 清空Form条件
            ( ( BranchVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            // 跳转到列表界面
            return list_object( mapping, form, request, response );
         }

         return to_objectModify( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * to_objectModify
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
         final BranchService branchService = ( BranchService ) getService( "branchService" );
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // 获得branchId
         String branchId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( branchId ) == null )
         {
            branchId = ( ( BranchVO ) form ).getBranchId();
         }
         // 获得BranchVO
         final BranchVO branchVO = branchService.getBranchVOByBranchId( branchId );
         // 刷新对象，初始化对象列表及国际化
         branchVO.reset( null, request );
         branchVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "branchForm", branchVO );

         final PagedListHolder staffListHolder = new PagedListHolder();
         staffListHolder.setObject( branchId );
         staffListHolder.setPageSize( listPageSize );
         final String isSumSubBranchHC = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).OPTIONS_ISSUMSUBBRANCHHC;
         staffService.getStaffVOsByBranchId( branchId, staffListHolder, true, isSumSubBranchHC, getAccountId( request, null ) );
         refreshHolder( staffListHolder, request );
         resetStaffEmployeeHolder( mapping, form, request, response, staffListHolder.getSource() );

         request.setAttribute( "staffListHolder", staffListHolder );
         request.setAttribute( "branchPrefer", KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_BRANCH_PREFER );
         final String rootBranchId = request.getParameter( "rootBranchId" );
         request.setAttribute( "rootBranchId", rootBranchId );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageBranch" );
   }

   /**
    * Modify branch
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
            // 初始化Service接口
            final BranchService branchService = ( BranchService ) getService( "branchService" );

            // 主键获取需解码
            final String branchId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得BranchVO
            final BranchVO branchVO = branchService.getBranchVOByBranchId( branchId );
            // 装载界面传值
            branchVO.update( ( BranchVO ) form );
            branchVO.setModifyBy( getUserId( request, response ) );
            branchService.updateBranch( branchVO );

            // 初始化常量持久对象
            constantsInit( "initBranch", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
            
            syncWebChartBUFunction();

            insertlog( request, branchVO, Operate.MODIFY, branchVO.getBranchId(), null );
         }

         // 清空Form条件
         ( ( BranchVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      final String rootBranchId = request.getParameter( "rootBranchId" );
      request.setAttribute( "rootBranchId", rootBranchId );
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete branch
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */

   private List< BranchDTO > returnList = new ArrayList< BranchDTO >();

   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final BranchService branchService = ( BranchService ) getService( "branchService" );

         BranchVO branchVO = ( BranchVO ) form;
         if ( branchVO.getSelectedIds() != null && !branchVO.getSelectedIds().equals( "" ) )
         {
            String branchId = branchVO.getSelectedIds();
            // 从Constants中得到当前Account的PositionDTO的列表
            final List< BranchDTO > branchDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).BRANCH_DTO;
            if ( branchDTOs == null && branchId == null )
               return;
            getNode( branchDTOs, branchId );
            for ( BranchDTO branchDTO : returnList )
            {
               if ( branchDTO.getBranchVO().getEncodedId().equals( "1" ) )
               {
                  returnList.clear();
               }
            }
            if ( returnList != null && returnList.size() > 0 )
            {
               for ( BranchDTO branchDTO : returnList )
               {
                  // 删除主键对应对象
                  branchVO.setBranchId( branchDTO.getBranchVO().getBranchId() );
                  branchVO.setModifyBy( getUserId( request, response ) );
                  branchService.deleteBranch( branchVO );
               }
            }

            insertlog( request, branchVO, Operate.DELETE, branchVO.getSelectedIds(), null );

            // 初始化常量持久对象
            constantsInit( "initBranch", getAccountId( request, response ) );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getNode( List< BranchDTO > list, String id )
   {
      for ( BranchDTO branch : list )
      {
         if ( branch.getBranchVO().getBranchId().equals( id ) )
         {
            returnList.add( branch );
            if ( branch.getBranchDTOs() != null && branch.getBranchDTOs().size() > 0 )
            {
               for ( BranchDTO branchDTO : branch.getBranchDTOs() )
               {
                  getNode( branchDTO.getBranchDTOs(), branchDTO.getBranchVO().getBranchId() );
               }
            }
         }
         if ( branch.getBranchDTOs() != null && branch.getBranchDTOs().size() > 0 )
         {
            getNode( branch.getBranchDTOs(), id );
         }
      }
   }

   /**
    * Delete branch list
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
         // 初始化Service接口
         final BranchService branchService = ( BranchService ) getService( "branchService" );

         // 获得Action Form
         BranchVO branchVO = ( BranchVO ) form;
         // 存在选中的ID
         if ( branchVO.getSelectedIds() != null && !branchVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : branchVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               branchVO.setBranchId( selectedId );
               branchVO.setModifyBy( getUserId( request, response ) );
               branchService.deleteBranch( branchVO );
            }

            insertlog( request, branchVO, Operate.DELETE, null, branchVO.getSelectedIds() );
         }

         // 初始化常量持久对象
         constantsInit( "initBranch", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         branchVO.setSelectedIds( "" );
         branchVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         final String corpId = KANUtil.filterEmpty( getCorpId( request, response ) );
         for ( BranchVO branchVO : KANConstants.getKANAccountConstants( getAccountId( request, response ) ).BRANCH_VO )
         {
            if ( branchVO != null
                  && ( ( corpId == null && KANUtil.filterEmpty( branchVO.getCorpId() ) == null ) || ( corpId != null && branchVO.getCorpId() != null && branchVO.getCorpId().equals( corpId ) ) ) )
            {
               JSONObject jsonObject = new JSONObject();
               jsonObject.put( "id", branchVO.getBranchId() );
               jsonObject.put( "name", branchVO.getBranchCode() + "-" + branchVO.getNameZH() + "-" + branchVO.getNameEN() );
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

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

   public ActionForward exportPdf( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化 JSONArray
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
            block.setObjectId( jsonObject.getString( "branchId" ) );
            block.setParentObjectId( jsonObject.getString( "parentId" ) );
            block.setObjectNameZH( jsonObject.getString( "branchNameZH" ) );
            block.setObjectNameEN( jsonObject.getString( "branchNameEN" ) );
            // 英文名和部门编号一起
            block.setObjectNO( "" );
            blocks.add( block );
         }

         // 获取最左边的边距，减小纸张
         int minLeft = Block.minLeft( blocks );
         Block.reSize( blocks, minLeft );
         final int maxWidth = Integer.parseInt( request.getParameter( "maxWidth" ) );
         final int maxHeight = Integer.parseInt( request.getParameter( "maxHeight" ) );

         BufferedImage image = new BufferedImage( maxWidth + 40, maxHeight + 100, BufferedImage.TYPE_INT_RGB );
         Graphics g = image.getGraphics();
         g.setFont( new Font( "宋体", Font.PLAIN, 12 ) );// 宋体  粗体（Font.PLAIN） 大小12
         g.setColor( Color.WHITE );
         g.fillRect( 0, 0, image.getWidth(), image.getHeight() );
         // 画出所有的格子
         g.setColor( Color.BLACK );
         for ( Block block : blocks )
         {
            block.paintThis( g, block );
         }
         // 连接格子
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
         // 标题
         Block.paintTitle( g, titleObject.getString( "titleName" ), titleObject.getInt( "titleLeft" ) - minLeft - Block.PAGEBLANKX, titleObject.getInt( "titleTop" )
               - Block.PAGEBLANKY );

         // 下载
         new DownloadFileAction().download( response, KANUtil.getByteArray( image ), "branchChart.png" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

   /**
    * 部门员工
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_special_info_list( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 获得Action Form
      final BranchVO branchVO = ( BranchVO ) form;

      // 初始化employeeContractService接口
      final StaffService staffService = ( StaffService ) getService( "staffService" );

      PagedListHolder staffListHolder = new PagedListHolder();

      staffListHolder.setObject( branchVO.getBranchId() );
      staffListHolder.setPageSize( listPageSize );
      staffListHolder.setPage( request.getParameter( "page" ) );
      // 调用Service方法，引用对象返回，第二个参数说明是否分页
      staffService.getStaffVOsByBranchId( staffListHolder, true );

      // 刷新Holder，国际化传值
      refreshHolder( staffListHolder, request );
      resetStaffEmployeeHolder( mapping, form, request, response, staffListHolder.getSource() );

      // Holder需写入Request对象
      request.setAttribute( "staffListHolder", staffListHolder );

      return mapping.findForward( "listBranchStaffTable" );
   }

   /**
    * 根据branchId 获取树
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward getOrgChart( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      String branchId = request.getParameter( "branchId" );
      if ( KANUtil.filterEmpty( branchId ) == null )
      {
         branchId = ( String ) request.getAttribute( "rootBranchId" );
      }
      final List< BranchDTO > branchDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).BRANCH_DTO;
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

   // 获取成本部门的Options
   public ActionForward getSettlementBranchOptions( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 初始化 Service
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final String employeeId = request.getParameter( "employeeId" );
         final String settlementBranch = request.getParameter( "settlementBranch" );
         String selectedId = "";
         // 如果是浏览
         if ( KANUtil.filterEmpty( employeeId ) != null )
         {
            selectedId = settlementBranch;
         }
         // 如果 是新建
         else
         {
            final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeId );
            if ( staffVO != null )
            {
               List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionDTOsByStaffId( staffVO.getStaffId() );
               if ( positionDTOs != null && positionDTOs.size() == 1 )
               {
                  selectedId = positionDTOs.get( 0 ).getPositionVO().getBranchId();
               }

            }
         }

         final List< BranchDTO > branchDTOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).BRANCH_DTO;
         final String branchOptionsHTML = fetchBranchDTO( branchDTOs, new StringBuffer(), "zh".equalsIgnoreCase( request.getLocale().getLanguage() ), request, 0, selectedId );
         out.print( branchOptionsHTML );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return null;
   }

   private String fetchBranchDTO( final List< BranchDTO > branchDTOs, final StringBuffer sb, boolean isZH, final HttpServletRequest request, final int level,
         final String selectedId ) throws KANException
   {
      final String corpId = getCorpId( request, null );
      int index = 0;
      for ( BranchDTO branchDTO : branchDTOs )
      {
         if ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) == null )
               || ( KANUtil.filterEmpty( corpId ) != null && KANUtil.filterEmpty( branchDTO.getBranchVO().getCorpId() ) != null && branchDTO.getBranchVO().getCorpId().equals( corpId ) ) )
         {
            String singleOption = "";
            for ( int i = 0; i <= level; i++ )
            {
               if ( level == 0 )
               {
                  singleOption += "├─";
               }
               else
               {
                  if ( i == 0 )
                  {
                     singleOption += "&nbsp;│";
                  }
                  else if ( i == 1 )
                  {
                     singleOption += "├─";
                  }
                  else if ( i < level )
                  {
                     singleOption += "──";
                  }
                  else if ( i == level )
                  {
                     singleOption += ( index + 1 == branchDTOs.size() ? "└─" : "├─" );
                  }
               }
            }
            // 是否被选中
            final String selected = branchDTO.getBranchVO().getBranchId().equals( selectedId ) ? "selected" : "";
            singleOption += isZH ? branchDTO.getBranchVO().getNameZH() : branchDTO.getBranchVO().getNameEN();
            singleOption = "<option " + selected + " id='option_settlementBranch_" + branchDTO.getBranchVO().getBranchId() + "' value='" + branchDTO.getBranchVO().getBranchId()
                  + "'>" + singleOption + "</option>";
            sb.append( singleOption );
            if ( branchDTO.getBranchDTOs() != null && branchDTO.getBranchDTOs().size() > 0 )
            {
               fetchBranchDTO( branchDTO.getBranchDTOs(), sb, isZH, request, level + 1, selectedId );
            }
         }
         index++;
      }
      return sb.toString();
   }

   public ActionForward exportExcel( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final String rootBranchId = request.getParameter( "rootBranchId" );
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // 获取当前的部门的
         final PagedListHolder staffListHolder = new PagedListHolder();
         // "1" 表示包括当前子部门的人
         staffService.getStaffVOsByBranchId( rootBranchId, staffListHolder, false, "1", getAccountId( request, null ) );
         refreshHolder( staffListHolder, request );
         final List< StaffDTO > staffDTOs = new ArrayList< StaffDTO >();
         if ( staffListHolder != null && staffListHolder.getSource().size() > 0 )
         {
            for ( Object obj : staffListHolder.getSource() )
            {
               final StaffDTO staffDTO = new StaffDTO();
               final StaffVO staffVO = ( StaffVO ) obj;
               staffDTO.setStaffVO( staffVO );
               final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionDTOsByStaffId( staffVO.getStaffId() );
               for ( PositionDTO positionDTO : positionDTOs )
               {
                  final List< BranchVO > branchVOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getParentBranchVOsByBranchId( positionDTO.getPositionVO().getBranchId() );

                  positionDTO.setParentBranchVOs( branchVOs );
               }
               staffDTO.setPositionDTOs( positionDTOs );
               staffDTOs.add( staffDTO );
            }
         }
         request.setAttribute( "staffDTOs", staffDTOs );
         return new DownloadFileAction().exportExcel4Branch( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   private void resetStaffEmployeeHolder( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response,
         final List< Object > objects ) throws KANException
   {
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
      for ( Object obj : objects )
      {
         EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( ( ( StaffVO ) obj ).getEmployeeId() );
         if ( employeeVO != null )
         {
            employeeVO.reset( mapping, request );
            final List< Object > employeeEducationVOs = employeeEducationService.getEmployeeEducationVOsByEmployeeId( employeeVO.getEmployeeId() );
            String graduateSchool = "";
            for ( Object employeeEducationObject : employeeEducationVOs )
            {
               graduateSchool += ( ( EmployeeEducationVO ) employeeEducationObject ).getSchoolName();
               graduateSchool += ";";
            }
            if ( KANUtil.filterEmpty( graduateSchool ) != null )
            {
               graduateSchool = graduateSchool.substring( 0, graduateSchool.length() - 1 );
               employeeVO.setGraduateSchool( graduateSchool.length() > 20 ? graduateSchool.substring( 0, 20 ) + "..." : graduateSchool );
            }
         }
         else
         {
            employeeVO = new EmployeeVO();
         }
         employeeVO.reset( null, request );
         ( ( StaffVO ) obj ).setEmployeeVO( employeeVO );
      }
   }

   /**
    * 同步微信部门，按照bu/fuction
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward syncWebChartBranch( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         syncWebChartBUFunction();

         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();
         out.println( "success" );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * show_copyBranchO_ChartPage_ajax 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward show_copyBranchO_ChartPage_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         this.saveToken( request );
         // 获取rootId
         final String rootId = request.getParameter( "rootId" );
         final BranchVO bu = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getBranchVOByBranchId( rootId );
         final List< MappingVO > copyBranchVOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getBranchsByParentBranchId( rootId );
         final Map< String, List< PositionVO > > branchId_positionVOs = new HashMap< String, List< PositionVO > >();
         if ( copyBranchVOs != null && copyBranchVOs.size() > 0 )
         {
            for ( MappingVO cropBranchVO : copyBranchVOs )
            {
               List< PositionVO > positionVOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionVOsByBranchId( cropBranchVO.getMappingId() );
               branchId_positionVOs.put( cropBranchVO.getMappingId(), positionVOs );
            }
         }
         ( ( BranchVO ) form ).setCopyBranchVOs( copyBranchVOs );
         request.setAttribute( "BUFunction", bu );
         request.setAttribute( "branchForm", form );
         request.setAttribute( "branch_position_map", branchId_positionVOs );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "toCopyBranchOChartPage" );
   }

   /**
    * copyO_Chart 复制部门架构
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward copyBranchO_Chart( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final Set< String > employeeIds = new HashSet< String >();

         if ( this.isTokenValid( request, true ) )
         {
            // 装入参数
            final Map< String, String > parameterMap = new HashMap< String, String >();

            final String[] copyBranchIdArray = ( ( BranchVO ) form ).getCopyBranchIdArray();
            final String[] copyBranchNameZHArray = ( ( BranchVO ) form ).getCopyBranchNameZHArray();
            final String[] copyBranchNameENArray = ( ( BranchVO ) form ).getCopyBranchNameENArray();

            if ( copyBranchIdArray != null && copyBranchIdArray.length > 0 )
            {
               for ( int i = 0; i < copyBranchIdArray.length; i++ )
               {
                  parameterMap.put( copyBranchIdArray[ i ], URLDecoder.decode( URLDecoder.decode( copyBranchNameZHArray[ i ], "UTF-8" ), "UTF-8" ) + "_"
                        + URLDecoder.decode( URLDecoder.decode( copyBranchNameENArray[ i ], "UTF-8" ), "UTF-8" ) );
               }

               parameterMap.put( "accountId", getAccountId( request, null ) );
               parameterMap.put( "userId", getUserId( request, response ) );
               parameterMap.put( "ip", getIPAddress( request ) );
               parameterMap.put( "createBy", ( ( BaseVO ) form ).decodeUserId( getUserId( request, null ) ) );
               parameterMap.put( "parentBranchId", request.getParameter( "rootId" ) );

               // 初始化Service
               final BranchService branchService = ( BranchService ) getService( "branchService" );
               employeeIds.addAll( branchService.copyO_Chart( request.getLocale(), parameterMap ) );
            }
         }

         // 初始化常量持久对象
         constantsInit( "initBranch", getAccountId( request, response ) );
         constantsInit( "initPosition", getAccountId( request, response ) );

         success( request, null, "Copy success!" );

         // 同步微信
         if ( employeeIds != null && employeeIds.size() > 0 )
         {
            syncWebChartBUFunction();

            for ( String employeeId : employeeIds )
            {
               syncWXContacts( employeeId );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   private void syncWebChartBUFunction() throws KANException
   {
      // 初始化Service接口
      final BranchService branchService = ( BranchService ) getService( "branchService" );

      //BUFuction貌似就是一级部门
      List< Object > branchs = branchService.getBUFuction();

      String insertUrl = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + WXUtil.getAccessToken().getToken();
      String updateUrl = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=" + WXUtil.getAccessToken().getToken();
      String listUrl = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=" + WXUtil.getAccessToken().getToken() + "&id=";

      //{"errcode":0,"errmsg":"ok","department":[]}

      int order = 10;
      for ( Object object : branchs )
      {
         BranchVO branch = ( BranchVO ) object;
         String para = "{\"name\": \"" + branch.getNameEN() + "\",\"parentid\":\"1\",\"order\":\"" + order + "\",\"id\":\"" + branch.getBranchId() + "\"}";
         JSONObject jsonObject = WXUtil.httpRequest( listUrl + branch.getBranchId(), "GET", null );
         if ( jsonObject != null && jsonObject.getInt( "errcode" ) == 0 )
         {
            JSONArray array = jsonObject.getJSONArray( "department" );

            if ( array.isEmpty() )
            {
               WXUtil.httpRequest( insertUrl, "POST", para );
            }
            else
            {
               WXUtil.httpRequest( updateUrl, "POST", para );
            }
         }else{
           WXUtil.httpRequest( insertUrl, "POST", para );
         }
         order++;
      }
   }

}
