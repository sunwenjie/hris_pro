package com.kan.hro.domain.biz.employee;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.SkillDTO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class EmployeeSkillVO extends BaseVO
{

   // Serial Version UID
   private static final long serialVersionUID = -3986770799631492651L;

   /**
    *  For DB
    */
   // ��Ա����Id
   private String employeeSkillId;
   // ��ԱId
   private String employeeId;
   // ����Id�������� - ���� - ���ܣ�
   private String skillId;
   //����
   private String description;

   /**
    * For Application
    */
   // ��������
   private String skillName;
   // �������ƣ����ģ�
   private String skillNameZH;
   // �������ƣ�Ӣ�ģ�
   private String skillNameEN;
   // ��Ա����
   private String employeeName;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeSkillId );
   }

   public String getEncodedSkillId() throws KANException
   {
      return encodedField( skillId );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.skillId = "";
      this.description = "";
      super.setStatus( "" );

   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) object;
      this.employeeSkillId = employeeSkillVO.getSkillId();
      this.description = employeeSkillVO.getDescription();
      super.setModifyDate( new Date() );
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEmployeeSkillId()
   {
      return employeeSkillId;
   }

   public String getSkillId()
   {
      return skillId;
   }

   public void setSkillId( String skillId )
   {
      this.skillId = skillId;
   }

   public String getSkillName()
   {
      return skillName;
   }

   public void setSkillName( String skillName )
   {
      this.skillName = skillName;
   }

   public void setEmployeeSkillId( String employeeSkillId )
   {
      this.employeeSkillId = employeeSkillId;
   }

   public String getSkillNameZH()
   {
      return skillNameZH;
   }

   public void setSkillNameZH( String skillNameZH )
   {
      this.skillNameZH = skillNameZH;
   }

   public String getSkillNameEN()
   {
      return skillNameEN;
   }

   public void setSkillNameEN( String skillNameEN )
   {
      this.skillNameEN = skillNameEN;
   }

   public String getSkillNameByLocale()
   {
      if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
      {
         return skillNameZH;
      }
      else
      {
         return skillNameEN;
      }
   }

   public String getDecodeSkillName()
   {
      SkillDTO skillDTO = KANConstants.getKANAccountConstants( getAccountId() ).getSkillDTOBySkillId( skillId, super.getCorpId() );
      if ( skillDTO != null )
      {
         if ( skillDTO.getSkillVO().getSkillId().equals( this.skillId ) )
         {
            if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               return skillDTO.getSkillVO().getSkillNameZH();
            }
            else
            {
               return skillDTO.getSkillVO().getSkillNameEN();
            }
         }
      }
      return "";
   }

   public String getEmployeeName()
   {
      return employeeName;
   }

   public void setEmployeeName( String employeeName )
   {
      this.employeeName = employeeName;
   }

   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( this.employeeId );
   }

}
