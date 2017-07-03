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
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Skill Service
         final SkillService skillService = ( SkillService ) getService( "skillService" );

         // ��ʼ�� JSONArray
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

      // ��ת���б����
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
         // ���Action Form
         final SkillVO skillVO = ( SkillVO ) form;

         skillVO.setAccountId( getAccountId( request, response ) );

         // ��accountId����request����
         request.setAttribute( "accountId", getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( skillVO.getSubAction() != null && skillVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( skillVO );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
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
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final SkillService skillService = ( SkillService ) getService( "skillService" );
         // ��õ�ǰ����
         String skillId = KANUtil.decodeString( request.getParameter( "skillId" ) );
         if ( KANUtil.filterEmpty( skillId ) == null )
         {
            skillId = ( ( SkillVO ) form ).getSkillId();
         }
         // ���������Ӧ����
         SkillVO skillVO = skillService.getSkillVOBySkillId( skillId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         skillVO.reset( null, request );
         skillVO.setSubAction( VIEW_OBJECT );

         // ����ActionForm����ǰ��
         request.setAttribute( "skillForm", skillVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
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
      // ���ҳ��Token
      this.saveToken( request );
      ( ( SkillVO ) form ).setSubAction( CREATE_OBJECT );
      // ����״̬Ĭ��ֵ
      ( ( SkillVO ) form ).setStatus( SkillVO.TRUE );
      // �������Parent SkillId
      final String noEncodedParentSkillId = request.getParameter( "parentSkillId" );
      if ( noEncodedParentSkillId != null && !noEncodedParentSkillId.trim().equals( "" ) )
      {
         String parentSkillId;
         try
         {
            // ���Parent SkillVO������
            parentSkillId = Cryptogram.decodeString( URLDecoder.decode( noEncodedParentSkillId, "UTF-8" ) );
            // ��ʼ��Service�ӿ�
            final SkillService skillService = ( SkillService ) getService( "skillService" );
            // �����������Parent SkillVO
            final SkillVO skillVO = skillService.getSkillVOBySkillId( parentSkillId );
            // ���Parent SkillVO��Skill Name
            final String parentSkillName = skillVO.getSkillNameZH() + " - " + skillVO.getSkillNameEN();
            ( ( SkillVO ) form ).setParentSkillId( parentSkillId );
            ( ( SkillVO ) form ).setParentSkillName( parentSkillName );
         }
         catch ( UnsupportedEncodingException e )
         {
            e.printStackTrace();
         }
      }
      // ��ת���½�����
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
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final SkillService skillService = ( SkillService ) getService( "skillService" );
            // ���ActionForm
            final SkillVO skillVO = ( SkillVO ) form;

            skillVO.setAccountId( getAccountId( request, response ) );
            skillVO.setCreateBy( getUserId( request, response ) );
            skillVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            skillService.insertSkill( skillVO );

            // ��ʼ�������־ö���
            constantsInit( "initSkill", getAccountId( request, response ) );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, skillVO, Operate.ADD, skillVO.getSkillId(), null );
         }
         else
         {
            // ���FORM
            ( ( SkillVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }

         // ���Form����
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
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final SkillService skillService = ( SkillService ) getService( "skillService" );
            // ��õ�ǰ����
            final String skillId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "skillId" ), "UTF-8" ) );
            // ���������Ӧδ�޸�ǰ����
            final SkillVO skillVO = skillService.getSkillVOBySkillId( skillId );
            // �޸Ķ�������
            skillVO.update( ( SkillVO ) form );
            skillVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            skillService.updateSkill( skillVO );
            // ��ʼ�������־ö���
            constantsInit( "initSkill", getAccountId( request, response ) );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, skillVO, Operate.MODIFY, skillVO.getSkillId(), null );
         }

         // ���Form����
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
         // ��ʼ��Service�ӿ�
         final SkillService skillService = ( SkillService ) getService( "skillService" );
         // ��õ�ǰ����
         final String skillId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "skillId" ), "UTF-8" ) );
         // ���������Ӧ��SkillVO
         final SkillVO skillVO = skillService.getSkillVOBySkillId( skillId );
         // ����SkillVO�����ֵ
         skillVO.setModifyBy( getUserId( request, response ) );
         skillVO.setModifyDate( new Date() );
         // ɾ��ѡ����skillVO
         skillService.deleteSkill( skillVO );
         // ��ʼ�������־ö���
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
         // ��ʼ��Service�ӿ�
         final SkillService skillService = ( SkillService ) getService( "skillService" );
         // ���Action Form
         final SkillVO skillVO = ( SkillVO ) form;
         // ����ѡ�е�ID
         if ( skillVO.getSkillIdArray() != null && skillVO.getSkillIdArray().length > 0 )
         {
            // �ָ�
            for ( String skillId : skillVO.getSkillIdArray() )
            {
               // ͨ��id��ȡid��Ӧ�� skillVO
               final SkillVO skillVOForDel = skillService.getSkillVOBySkillId( skillId );
               // ����SkillVO�����ֵ
               skillVOForDel.setModifyBy( getUserId( request, response ) );
               skillVOForDel.setModifyDate( new Date() );
               // ɾ��ѡ����skillVO
               skillService.deleteSkill( skillVOForDel );
            }

            // ��ʼ�������־ö���
            constantsInit( "initSkill", getAccountId( request, response ) );

            insertlog( request, skillVO, Operate.DELETE, null, KANUtil.toJasonArray( skillVO.getSkillIdArray() ) );
         }
         // ���Selected IDs����Action
         skillVO.setSelectedIds( "" );
         skillVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Skill HTML In Order - ����������ʾskill list��html
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
         // ��ʼ��Service�ӿ�
         final SkillService skillService = ( SkillService ) getService( "skillService" );
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );

         // ���parentSkillId
         String parentSkillId = request.getParameter( "parentSkillId" );
         // ��ʼ�����м��ܼ���
         List< Object > employeeSkillVOs = new ArrayList< Object >();
         String[] skillIdArray = {};

         // ����Ǹ��ڵ�
         if ( parentSkillId == null || parentSkillId.isEmpty() )
         {
            parentSkillId = "0";
         }

         // ���employeeId
         if ( request.getParameter( "employeeId" ) != null && !request.getParameter( "employeeId" ).isEmpty() )
         {
            final String employeeId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeId" ) );

            // ���EmployeeVO ��ӦEmployeeSkillVO List
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

         // ���presentSkillId
         final String presentSkillId = request.getParameter( "presentSkillId" );
         // ���presentSkillId�����Ҳ�Ϊ���ڵ����ȡ��ǰskillId��presentSkillId
         if ( presentSkillId != null && !"".equals( presentSkillId ) && !"0".equals( presentSkillId ) )
         {
            // ���presentSkillId��Ӧ��SkillVO���ҵ���Ӧ��parentSkillId
            parentSkillId = skillService.getSkillVOBySkillId( presentSkillId ).getParentSkillId();
         }

         // �ӳ����л�ȡ��Ҫ��skillDTOs
         final List< SkillDTO > skillDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSkillDTOsByParentSkillId( parentSkillId );
         // ��skillDTOs����request��
         request.setAttribute( "skillDTOs", skillDTOs );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         out.println( SkillRender.getSkillListOrderDiv( request, parentSkillId, skillIdArray ) );

         out.flush();
         out.close();

         // Ajax��������ת
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    
   * getSkillNameCombo_ajax(����skillId��ö�Ӧ��skillName)  
    
   * @param   name  
    
   * @param  @return    �趨�ļ�  
    
   * @return String    DOM����  
    
   * @Exception �쳣����  
    
   * @since  CodingExample��Ver(���뷶���鿴) 1.1  
    
   */
   public ActionForward getSkillNameCombo_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      try
      {
         // ��ʼ��Service�ӿ�
         final SkillService skillService = ( SkillService ) getService( "skillService" );

         // ���ݵ�ǰaccount��ȡ����skillId��Ӧ��skillBaseView
         final List< SkillBaseView > skillBaseViews = ( List< SkillBaseView > ) skillService.getSkillBaseViewsByAccountId( getAccountId( request, response ) );
         // �ѵõ���skillDTOs���õ�request��
         request.setAttribute( "skillBaseViews", skillBaseViews );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ���skillId array
         String skillIdArray = request.getParameter( "skill" );

         if ( skillIdArray != null && !skillIdArray.trim().equals( "" ) )
         {
            // ͨ��skillArray���skill list
            final String[] skillIdList = skillIdArray.split( "," );
            out.println( SkillRender.getSkillNameCombo( request, skillIdList ) );
         }

         out.flush();
         out.close();

         // Ajax��������ת
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
