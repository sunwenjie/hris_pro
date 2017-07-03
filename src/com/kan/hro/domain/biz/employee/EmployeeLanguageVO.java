package com.kan.hro.domain.biz.employee;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.LanguageVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class EmployeeLanguageVO extends BaseVO
{

   
    // Serial Version UID
    
   private static final long serialVersionUID = -3986770799631492651L;

   
    /**
     *  For DB
     */
    

   
    // 雇员技能Id
    
   private String employeeLanguageId;

   
    // 雇员Id
    
   private String employeeId;

   
    // 技能Id（绑定设置 - 资质 - 技能）
    
   private String languageId;

   
    //描述
    
   private String description;

   /**
    * For Application
    */
     
    
   // 技能名称
   private String languageName;

   private String languageNameZH;
   private String languageNameEN;
   
   private String employeeName;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeLanguageId );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.languageId = "";
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
      EmployeeLanguageVO employeeLanguageVO = ( EmployeeLanguageVO ) object;
      this.employeeLanguageId = employeeLanguageVO.getLanguageId();
      this.description = employeeLanguageVO.getDescription();
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

   public String getEmployeeLanguageId()
   {
      return employeeLanguageId;
   }

   public String getLanguageId()
   {
      return languageId;
   }

   public void setLanguageId( String languageId )
   {
      this.languageId = languageId;
   }

   public String getLanguageName()
   {
      return languageName;
   }

   public void setLanguageName( String languageName )
   {
      this.languageName = languageName;
   }

   public void setEmployeeLanguageId( String employeeLanguageId )
   {
      this.employeeLanguageId = employeeLanguageId;
   }

   public String getLanguageNameZH()
   {
      return languageNameZH;
   }

   public void setLanguageNameZH( String languageNameZH )
   {
      this.languageNameZH = languageNameZH;
   }

   public String getLanguageNameEN()
   {
      return languageNameEN;
   }

   public void setLanguageNameEN( String languageNameEN )
   {
      this.languageNameEN = languageNameEN;
   }

   public String getLanguageNameByLocale()
   {
      if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
      {
         return languageNameZH;
      }
      else
      {
         return languageNameEN;
      }
   }
   
   public String getDecodeLanguageName(){
      List< LanguageVO > languageVOs = KANConstants.getKANAccountConstants( getAccountId() ).LANGUAGE_VO;
      if ( languageVOs != null )
      {
         for ( LanguageVO languageVO : languageVOs )
         {
            if ( languageVO.getLanguageId().equals( this.languageId ) )
            {
               if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  return languageVO.getNameZH();
               }
               else
               {
                  return languageVO.getNameEN();
               }
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
