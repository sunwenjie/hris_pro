package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.SkillBaseView;
import com.kan.base.domain.management.SkillDTO;
import com.kan.base.domain.management.SkillVO;
import com.kan.base.service.inf.management.SkillService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.management.SkillRender;
import com.kan.hro.domain.biz.employee.EmployeeSkillVO;
import com.kan.hro.service.inf.biz.employee.EmployeeSkillService;

public class SkillAction extends BaseAction
{
   public final static String accessAction = "HRO_EMPLOYEE_SKILLS";

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

         // 初始化Skill Service
         final SkillService skillService = ( SkillService ) getService( "skillService" );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( skillService.getSkillBaseViewsByAccountId( getAccountId( request, response ), getCorpId( request, null ) ) );
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

   /**
    * List skills
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
         // 获得Action Form
         final SkillVO skillVO = ( SkillVO ) form;

         skillVO.setAccountId( getAccountId( request, response ) );

         // 把accountId放入request域中
         request.setAttribute( "accountId", getAccountId( request, response ) );

         // 如果子Action是删除用户列表
         if ( skillVO.getSubAction() != null && skillVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( skillVO );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listSkill" );
   }

   /**
    * To skill modify page
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
         final SkillService skillService = ( SkillService ) getService( "skillService" );
         // 获得当前主键
         String skillId = KANUtil.decodeString( request.getParameter( "skillId" ) );
         if ( KANUtil.filterEmpty( skillId ) == null )
         {
            skillId = ( ( SkillVO ) form ).getSkillId();
         }
         // 获得主键对应对象
         SkillVO skillVO = skillService.getSkillVOBySkillId( skillId );
         // 刷新对象，初始化对象列表及国际化
         skillVO.reset( null, request );
         skillVO.setSubAction( VIEW_OBJECT );

         // 传回ActionForm对象到前端
         request.setAttribute( "skillForm", skillVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageSkill" );
   }

   /**
    * To skill new page
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
      ( ( SkillVO ) form ).setSubAction( CREATE_OBJECT );
      // 设置状态默认值
      ( ( SkillVO ) form ).setStatus( SkillVO.TRUE );
      // 如果存在Parent SkillId
      final String noEncodedParentSkillId = request.getParameter( "parentSkillId" );
      if ( noEncodedParentSkillId != null && !noEncodedParentSkillId.trim().equals( "" ) )
      {
         String parentSkillId;
         try
         {
            // 获得Parent SkillVO的主键
            parentSkillId = Cryptogram.decodeString( URLDecoder.decode( noEncodedParentSkillId, "UTF-8" ) );
            // 初始化Service接口
            final SkillService skillService = ( SkillService ) getService( "skillService" );
            // 根据主键获得Parent SkillVO
            final SkillVO skillVO = skillService.getSkillVOBySkillId( parentSkillId );
            // 获得Parent SkillVO的Skill Name
            final String parentSkillName = skillVO.getSkillNameZH() + " - " + skillVO.getSkillNameEN();
            ( ( SkillVO ) form ).setParentSkillId( parentSkillId );
            ( ( SkillVO ) form ).setParentSkillName( parentSkillName );
         }
         catch ( UnsupportedEncodingException e )
         {
            e.printStackTrace();
         }
      }
      // 跳转到新建界面
      return mapping.findForward( "manageSkill" );
   }

   /**
    * Add skill
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
            final SkillService skillService = ( SkillService ) getService( "skillService" );
            // 获得ActionForm
            final SkillVO skillVO = ( SkillVO ) form;

            skillVO.setAccountId( getAccountId( request, response ) );
            skillVO.setCreateBy( getUserId( request, response ) );
            skillVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            skillService.insertSkill( skillVO );

            // 初始化常量持久对象
            constantsInit( "initSkill", getAccountId( request, response ) );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, skillVO, Operate.ADD, skillVO.getSkillId(), null );
         }
         else
         {
            // 清空FORM
            ( ( SkillVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }

         // 清空Form条件
         ( ( SkillVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify skill
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
            final SkillService skillService = ( SkillService ) getService( "skillService" );
            // 获得当前主键
            final String skillId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "skillId" ), "UTF-8" ) );
            // 获得主键对应未修改前对象
            final SkillVO skillVO = skillService.getSkillVOBySkillId( skillId );
            // 修改对象数据
            skillVO.update( ( SkillVO ) form );
            skillVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            skillService.updateSkill( skillVO );
            // 初始化常量持久对象
            constantsInit( "initSkill", getAccountId( request, response ) );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, skillVO, Operate.MODIFY, skillVO.getSkillId(), null );
         }

         // 清空Form条件
         ( ( SkillVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
   * Delete skill
   *
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws KANException
   */
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SkillService skillService = ( SkillService ) getService( "skillService" );
         // 获得当前主键
         final String skillId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "skillId" ), "UTF-8" ) );
         // 获得主键对应的SkillVO
         final SkillVO skillVO = skillService.getSkillVOBySkillId( skillId );
         // 设置SkillVO的相关值
         skillVO.setModifyBy( getUserId( request, response ) );
         skillVO.setModifyDate( new Date() );
         // 删除选定的skillVO
         skillService.deleteSkill( skillVO );
         // 初始化常量持久对象
         constantsInit( "initSkill", getAccountId( request, response ) );
         insertlog( request, skillVO, Operate.MODIFY, skillId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete skill list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SkillService skillService = ( SkillService ) getService( "skillService" );
         // 获得Action Form
         final SkillVO skillVO = ( SkillVO ) form;
         // 存在选中的ID
         if ( skillVO.getSkillIdArray() != null && skillVO.getSkillIdArray().length > 0 )
         {
            // 分割
            for ( String skillId : skillVO.getSkillIdArray() )
            {
               // 通过id获取id对应的 skillVO
               final SkillVO skillVOForDel = skillService.getSkillVOBySkillId( skillId );
               // 设置SkillVO的相关值
               skillVOForDel.setModifyBy( getUserId( request, response ) );
               skillVOForDel.setModifyDate( new Date() );
               // 删除选定的skillVO
               skillService.deleteSkill( skillVOForDel );
            }

            // 初始化常量持久对象
            constantsInit( "initSkill", getAccountId( request, response ) );

            insertlog( request, skillVO, Operate.DELETE, null, KANUtil.toJasonArray( skillVO.getSkillIdArray() ) );
         }
         // 清除Selected IDs和子Action
         skillVO.setSelectedIds( "" );
         skillVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Skill HTML In Order - 根据条件显示skill list的html
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_skill_order_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final SkillService skillService = ( SkillService ) getService( "skillService" );
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );

         // 获得parentSkillId
         String parentSkillId = request.getParameter( "parentSkillId" );
         // 初始化已有技能集合
         List< Object > employeeSkillVOs = new ArrayList< Object >();
         String[] skillIdArray = {};

         // 如果是根节点
         if ( parentSkillId == null || parentSkillId.isEmpty() )
         {
            parentSkillId = "0";
         }

         // 获得employeeId
         if ( request.getParameter( "employeeId" ) != null && !request.getParameter( "employeeId" ).isEmpty() )
         {
            final String employeeId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeId" ) );

            // 获得EmployeeVO 对应EmployeeSkillVO List
            if ( employeeSkillService.getEmployeeSkillVOsByEmployeeId( employeeId ) != null && employeeSkillService.getEmployeeSkillVOsByEmployeeId( employeeId ).size() > 0 )
            {
               List< String > skillIds = new ArrayList< String >();
               employeeSkillVOs = employeeSkillService.getEmployeeSkillVOsByEmployeeId( employeeId );

               for ( Object employeeSkillVOObject : employeeSkillVOs )
               {
                  EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) employeeSkillVOObject;
                  skillIds.add( employeeSkillVO.getSkillId() );
               }

               skillIdArray = ( String[] ) skillIds.toArray( new String[ skillIds.size() ] );
            }

         }

         // 获得presentSkillId
         final String presentSkillId = request.getParameter( "presentSkillId" );
         // 如果presentSkillId存在且不为根节点则获取当前skillId的presentSkillId
         if ( presentSkillId != null && !"".equals( presentSkillId ) && !"0".equals( presentSkillId ) )
         {
            // 获得presentSkillId对应的SkillVO再找到对应的parentSkillId
            parentSkillId = skillService.getSkillVOBySkillId( presentSkillId ).getParentSkillId();
         }

         // 从常量中获取需要的skillDTOs
         final List< SkillDTO > skillDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSkillDTOsByParentSkillId( parentSkillId );
         // 将skillDTOs放入request中
         request.setAttribute( "skillDTOs", skillDTOs );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         out.println( SkillRender.getSkillListOrderDiv( request, parentSkillId, skillIdArray ) );

         out.flush();
         out.close();

         // Ajax调用无跳转
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    
   * getSkillNameCombo_ajax(根据skillId获得对应的skillName)  
    
   * @param   name  
    
   * @param  @return    设定文件  
    
   * @return String    DOM对象  
    
   * @Exception 异常对象  
    
   * @since  CodingExample　Ver(编码范例查看) 1.1  
    
   */
   public ActionForward getSkillNameCombo_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      try
      {
         // 初始化Service接口
         final SkillService skillService = ( SkillService ) getService( "skillService" );

         // 根据当前account获取所有skillId对应的skillBaseView
         final List< SkillBaseView > skillBaseViews = ( List< SkillBaseView > ) skillService.getSkillBaseViewsByAccountId( getAccountId( request, response ) );
         // 把得到的skillDTOs放置到request中
         request.setAttribute( "skillBaseViews", skillBaseViews );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 获得skillId array
         String skillIdArray = request.getParameter( "skill" );

         if ( skillIdArray != null && !skillIdArray.trim().equals( "" ) )
         {
            // 通过skillArray获得skill list
            final String[] skillIdList = skillIdArray.split( "," );
            out.println( SkillRender.getSkillNameCombo( request, skillIdList ) );
         }

         out.flush();
         out.close();

         // Ajax调用无跳转
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
