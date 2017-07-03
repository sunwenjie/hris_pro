package com.kan.base.web.actions.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.GroupModuleRuleRelationVO;
import com.kan.base.domain.security.GroupVO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.GroupService;
import com.kan.base.service.inf.security.PositionService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class GroupAction extends BaseAction
{

   public static String accessAction = "HRO_SEC_POSITIONGROUP";

   /**
    * List Group
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
         final GroupService groupService = ( GroupService ) getService( "groupService" );
         // 获得Action Form
         final GroupVO groupVO = ( GroupVO ) form;

         // 处理subAction
         dealSubAction( groupVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder groupHolder = new PagedListHolder();

         // 传入当前页
         groupHolder.setPage( page );
         // 传入当前值对象
         groupHolder.setObject( groupVO );
         // 设置页面记录条数
         groupHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         groupService.getGroupVOsByCondition( groupHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( groupHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "groupHolder", groupHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax调用无跳转
            return mapping.findForward( "listGroupTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listGroup" );
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
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( GroupVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( GroupVO ) form ).setStatus( "1" );

      // 初始化设置Tab Number
      request.setAttribute( "positionCount", 0 );

      // 跳转到新建界面
      return mapping.findForward( "manageGroup" );
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
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final GroupService groupService = ( GroupService ) getService( "groupService" );
         // 获得当前主键
         String groupId = KANUtil.decodeString( request.getParameter( "groupId" ) );
         if ( KANUtil.filterEmpty( groupId ) == null )
         {
            groupId = ( ( GroupVO ) form ).getGroupId();
         }
         // 获得主键对应对象
         final GroupVO groupVO = groupService.getGroupVOByGroupId( groupId );
         // 刷新对象，初始化对象列表及国际化
         groupVO.reset( null, request );
         groupVO.setSubAction( VIEW_OBJECT );

         // Checkbox处理
         groupVO.setHrFunction( ( groupVO.getHrFunction() != null && groupVO.getHrFunction().equalsIgnoreCase( GroupVO.TRUE ) ) ? "on" : "" );

         // 添加Position Group Count用于Tab Number显示

         int positionCount = 0;
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
         if ( accountConstants != null )
         {
            final GroupDTO groupDTO = accountConstants.getGroupDTOByGroupId( groupId );
            if ( groupDTO != null && groupDTO.getPositionGroupRelationVOs() != null )
            {
               positionCount = groupDTO.getPositionGroupRelationVOs().size();
            }
         }
         request.setAttribute( "positionCount", positionCount );

         request.setAttribute( "groupForm", groupVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageGroup" );
   }

   /**
    * Add Group
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final GroupService groupService = ( GroupService ) getService( "groupService" );
            final StaffService staffService = ( StaffService ) getService( "staffService" );

            // 获得ActionForm
            final GroupVO groupVO = ( GroupVO ) form;

            // 获取登录用户
            groupVO.setAccountId( getAccountId( request, response ) );
            groupVO.setCreateBy( getUserId( request, response ) );
            groupVO.setModifyBy( getUserId( request, response ) );

            // HrService默认为HrFunction
            if ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) )
            {
               groupVO.setHrFunction( GroupVO.TRUE );
            }
            else
            {
               // Checkbox处理
               groupVO.setHrFunction( ( groupVO.getHrFunction() != null && groupVO.getHrFunction().equalsIgnoreCase( "on" ) ) ? GroupVO.TRUE : GroupVO.FALSE );
            }

            // 新建对象
            groupService.insertGroup( groupVO );

            // 初始化StaffId字符串列表
            final List< String > staffIds = new ArrayList< String >();

            if ( groupVO.getPositionIdArray() != null && groupVO.getPositionIdArray().length > 0 )
            {
               for ( String positionId : groupVO.getPositionIdArray() )
               {
                  String tempPositionId = "";

                  if ( positionId.contains( "_" ) )
                  {
                     tempPositionId = positionId.split( "_" )[ 0 ];
                  }

                  final List< Object > positionStaffRelationVOs = staffService.getPositionStaffRelationVOsByPositionId( tempPositionId );

                  // 装载更改前的StaffIds
                  if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
                  {
                     for ( Object object : positionStaffRelationVOs )
                     {
                        if ( !staffIds.contains( ( ( PositionStaffRelationVO ) object ).getStaffId() ) )
                        {
                           staffIds.add( ( ( PositionStaffRelationVO ) object ).getStaffId() );
                        }
                     }
                  }
               }
            }

            insertlog( request, groupVO, Operate.ADD, groupVO.getGroupId(), "职位组staffIds: " + KANUtil.stringListToJasonArray( staffIds ) );
            // 重新加载常量中的PositionGroup，Position和Staff
            constantsInit( "initPositionGroup", getAccountId( request, response ) );
            constantsInit( "initPosition", getAccountId( request, response ) );

            // 重新加载影响的Staff
            if ( staffIds != null && staffIds.size() > 0 )
            {
               for ( String staffId : staffIds )
               {
                  constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffId } );
               }
            }

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }
         else
         {
            // 清空Form
            ( ( GroupVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify group
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
            final GroupService groupService = ( GroupService ) getService( "groupService" );
            final PositionService positionService = ( PositionService ) getService( "positionService" );
            final StaffService staffService = ( StaffService ) getService( "staffService" );

            // 获得当前主键
            final String groupId = KANUtil.decodeString( request.getParameter( "groupId" ) );

            // 获得主键对应对象
            final GroupVO groupVO = groupService.getGroupVOByGroupId( groupId );

            // 初始化PositionGroupRelationVO列表（更新前）
            final List< Object > updateBeforePositionGroupRelationVOs = positionService.getRelationVOsByGroupId( groupId );

            // 初始化StaffId字符串列表
            final List< String > staffIds = new ArrayList< String >();

            if ( updateBeforePositionGroupRelationVOs != null && updateBeforePositionGroupRelationVOs.size() > 0 )
            {
               for ( Object positionGroupRelationVOObject : updateBeforePositionGroupRelationVOs )
               {
                  // 初始化PositionVO
                  final List< Object > positionStaffRelationVOs = staffService.getPositionStaffRelationVOsByPositionId( ( ( PositionGroupRelationVO ) positionGroupRelationVOObject ).getPositionId() );

                  // 装载更改前的StaffIds
                  if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
                  {
                     for ( Object positionStaffRelationVOObject : positionStaffRelationVOs )
                     {
                        if ( !staffIds.contains( ( ( PositionStaffRelationVO ) positionStaffRelationVOObject ).getStaffId() ) )
                        {
                           staffIds.add( ( ( PositionStaffRelationVO ) positionStaffRelationVOObject ).getStaffId() );
                        }
                     }
                  }
               }
            }

            // 获取登录用户
            groupVO.update( ( GroupVO ) form );
            groupVO.setModifyBy( getUserId( request, response ) );

            // HrService默认为HrFunction
            if ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) )
            {
               groupVO.setHrFunction( GroupVO.TRUE );
            }
            else
            {
               // Checkbox处理
               groupVO.setHrFunction( ( groupVO.getHrFunction() != null && groupVO.getHrFunction().equalsIgnoreCase( "on" ) ) ? GroupVO.TRUE : GroupVO.FALSE );
            }

            // 修改对象
            groupService.updateGroup( groupVO );

            // 初始化PositionGroupRelationVO列表（更新后）
            final List< Object > updateAfterPositionGroupRelationVOs = positionService.getRelationVOsByGroupId( groupId );

            if ( updateAfterPositionGroupRelationVOs != null && updateAfterPositionGroupRelationVOs.size() > 0 )
            {
               for ( Object positionGroupRelationVOObject : updateAfterPositionGroupRelationVOs )
               {
                  // 初始化PositionVO
                  final List< Object > positionStaffRelationVOs = staffService.getPositionStaffRelationVOsByPositionId( ( ( PositionGroupRelationVO ) positionGroupRelationVOObject ).getPositionId() );

                  // 装载更改前的StaffIds
                  if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
                  {
                     for ( Object positionStaffRelationVOObject : positionStaffRelationVOs )
                     {
                        if ( !staffIds.contains( ( ( PositionStaffRelationVO ) positionStaffRelationVOObject ).getStaffId() ) )
                        {
                           staffIds.add( ( ( PositionStaffRelationVO ) positionStaffRelationVOObject ).getStaffId() );
                        }
                     }
                  }
               }
            }

            insertlog( request, groupVO, Operate.MODIFY, groupVO.getGroupId(), "职位组staffIds: " + KANUtil.stringListToJasonArray( staffIds ) );
            // 重新加载常量中的PositionGroup，Position
            constantsInit( "initPositionGroup", getAccountId( request, response ) );
            constantsInit( "initPosition", getAccountId( request, response ) );

            // 重新加载影响的Staff
            if ( staffIds != null && staffIds.size() > 0 )
            {
               for ( String staffId : staffIds )
               {
                  constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffId } );
               }
            }

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Form条件
         ( ( GroupVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查询
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Group Ajax
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
         // 初始化Service接口
         final GroupService groupService = ( GroupService ) getService( "groupService" );

         // 获得当前主键
         final String encodeGroupId = request.getParameter( "groupId" );
         String groupId = "";
         GroupVO groupVO = new GroupVO();

         // 如果GroupId不为空的情况，重数据库加载GroupVO对象
         if ( encodeGroupId != null && !encodeGroupId.equals( "" ) )
         {
            groupId = KANUtil.decodeStringFromAjax( encodeGroupId );
            groupVO = groupService.getGroupVOByGroupId( groupId );
         }

         // 更新提交过来的数据
         groupVO.update( ( GroupVO ) form );
         // 解码
         groupVO.setNameEN( URLDecoder.decode( groupVO.getNameEN(), "UTF-8" ) );

         groupVO.setAccountId( getAccountId( request, response ) );
         groupVO.setModifyBy( getUserId( request, response ) );

         // 如果GroupId不为空的情况，修改GroupModule对象，反之整个GroupVO一起新建
         if ( encodeGroupId != null && !encodeGroupId.equals( "" ) )
         {
            // 修改GroupModule对象
            groupService.updateGroupModule( groupVO );
         }
         else
         {
            // 新建Position对象
            groupService.insertGroup( groupVO );
         }

         // 返回编辑成功标记
         success( request, MESSAGE_TYPE_UPDATE );

         insertlog( request, groupVO, Operate.MODIFY, groupVO.getGroupId(), "modify_object_ajax" );

         // 清空Form条件
         ( ( GroupVO ) form ).reset();

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // Send to client
         out.println( URLEncoder.encode( Cryptogram.encodeString( groupVO.getGroupId() ), "UTF-8" ) );
         out.flush();
         out.close();

         // 重新加载常量中的Group
         constantsInit( "initPositionGroup", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用，跳转到空白页面
      return mapping.findForward( "" );
   }

   /**  
    * Modify Object Ajax Popup
    *	
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
         // 初始化Service接口
         final GroupService groupService = ( GroupService ) getService( "groupService" );

         // 获得GroupId及 moduleId
         final String groupId = KANUtil.decodeStringFromAjax( request.getParameter( "groupId" ) );
         final String moduleId = request.getParameter( "moduleId" );

         final GroupVO groupVO = groupService.getGroupVOByGroupId( groupId );

         // 更新GroupVO 的 RightIdArray和RuleIdArray
         groupVO.setRightIdArray( ( ( GroupVO ) form ).getRightIdArray() );
         groupVO.setRuleIdArray( ( ( GroupVO ) form ).getRuleIdArray() );

         groupVO.setAccountId( getAccountId( request, response ) );
         groupVO.setModifyBy( getUserId( request, response ) );

         // 更新数据库
         groupService.updateGroupModuleRelationPopup( groupVO, moduleId );

         // 返回编辑成功标记
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

         insertlog( request, groupVO, Operate.MODIFY, groupVO.getGroupId(), "modify_object_ajax_popup" );

         // 清空Form条件
         ( ( GroupVO ) form ).reset();

         // 重新加载常量中的Group
         constantsInit( "initPositionGroup", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用，跳转到空白页面
      return mapping.findForward( "" );
   }

   /**
    * Delete Group
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
         // 初始化Service接口
         final GroupService groupService = ( GroupService ) getService( "groupService" );

         final GroupVO groupVO = new GroupVO();
         // 获得当前主键
         final String groupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "groupId" ), "UTF-8" ) );

         // 删除主键对应对象
         groupVO.setGroupId( groupId );
         groupVO.setModifyBy( getUserId( request, response ) );

         groupService.deleteGroup( groupVO );

         insertlog( request, groupVO, Operate.DELETE, groupVO.getGroupId(), null );

         // 重新加载常量中的group
         constantsInit( "initPositionGroup", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Group List
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
         final GroupService groupService = ( GroupService ) getService( "groupService" );

         // 获得Action Form
         GroupVO groupVO = ( GroupVO ) form;
         // 存在选中的ID
         if ( groupVO.getSelectedIds() != null && !groupVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : groupVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               groupVO.setGroupId( selectedId );
               groupVO.setModifyBy( getUserId( request, response ) );
               groupService.deleteGroup( groupVO );
            }

            insertlog( request, groupVO, Operate.DELETE, null, groupVO.getSelectedIds() );
         }

         // 重新加载常量中的group
         constantsInit( "initPositionGroup", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         groupVO.setSelectedIds( "" );
         groupVO.setSubAction( "" );
         groupVO.setGroupId( "" );
         groupVO.reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getRulesByGroupId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      response.setContentType( "application/json;charset=UTF-8" );
      response.setCharacterEncoding( "UTF-8" );
      String groupId = request.getParameter( "groupId" );
      if ( groupId != null && !groupId.equals( "" ) )
      {
         groupId = KANUtil.decodeStringFromAjax( groupId );
      }
      GroupDTO groupDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getGroupDTOByGroupId( groupId );
      List< GroupModuleRuleRelationVO > groupModuleRuleRelationVOs = groupDTO.getGroupModuleRuleRelationVOs();

      List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
      for ( GroupModuleRuleRelationVO groupModuleRuleRelationVO : groupModuleRuleRelationVOs )
      {
         Map< String, String > map = new HashMap< String, String >();
         map.put( "ruleId", groupModuleRuleRelationVO.getRuleId() );
         map.put( "remark1", StringUtils.isEmpty( groupModuleRuleRelationVO.getRemark1() ) ? "" : groupModuleRuleRelationVO.getRemark1() );
         listReturn.add( map );
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
