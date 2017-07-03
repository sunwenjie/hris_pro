package com.kan.base.util.poi.bean;

import java.util.List;

import com.kan.base.domain.MappingVO;

public class Property
{
   
   
   private String propertyName;
   private String validateType;
   private String validateRegEx;//正则表达试 
   private String errorMsg;//错误提示
   
   private List<MappingVO> mappingList;
   
   public static String REG_EX_DATE = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
   
   public String getErrorMsg()
   {
      return errorMsg;
   }
   public void setErrorMsg( String errorMsg )
   {
      this.errorMsg = errorMsg;
   }
   public Property( final String propertyName, final String validateType,final String validateRegEx, final String errorMsg )
   {
      super();
      this.propertyName = propertyName;
      this.validateType = validateType;
      this.validateRegEx = validateRegEx;
      this.errorMsg = errorMsg;
   }
   public Property( final String propertyName, final String validateType)
   {
      this.propertyName = propertyName;
      this.validateType = validateType;
   }
   
   public String getPropertyName()
   {
      return propertyName;
   }
   public void setPropertyName( String propertyName )
   {
      this.propertyName = propertyName;
   }
   public String getValidateType()
   {
      return validateType;
   }
   public void setValidateType( String validateType )
   {
      this.validateType = validateType;
   }
   public String getValidateRegEx()
   {
      return validateRegEx;
   }
   public void setValidateRegEx( String validateRegEx )
   {
      this.validateRegEx = validateRegEx;
   }
   public List< MappingVO > getMappingList()
   {
      return mappingList;
   }
   public void setMappingList( List< MappingVO > mappingList )
   {
      this.mappingList = mappingList;
   }
}
