package com.kan.base.domain.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;

public class LogVO extends BaseVO
{

   private static final long serialVersionUID = 1L;
   private Long id;
   private String type;
   private String module;
   private String content;
   private String ip;
   private String operateTime;
   private String operateBy;
   private String pKey;
   private String remark;
   private String employeeId;
   private String changeType;
   private String changeReason;
   private String employeeNameZH;
   private String employeeNameEN;

   //for app

   private List< MappingVO > types = new ArrayList< MappingVO >();
   private List< MappingVO > modules = new ArrayList< MappingVO >();
   private List< MappingVO > changeReasons = new ArrayList< MappingVO >();

   /**
    * App
    */

   private String typeArray;
   private String operateTimeBegin;
   private String operateTimeEnd;

   public String getOperateTimeBegin()
   {
      return operateTimeBegin;
   }

   public void setOperateTimeBegin( String operateTimeBegin )
   {
      this.operateTimeBegin = operateTimeBegin;
   }

   public String getOperateTimeEnd()
   {
      return operateTimeEnd;
   }

   public void setOperateTimeEnd( String operateTimeEnd )
   {
      this.operateTimeEnd = operateTimeEnd;
   }

   public String getTypeArray()
   {
      return typeArray;
   }

   public void setTypeArray( String typeArray )
   {
      this.typeArray = typeArray;
   }

   public Long getId()
   {
      return id;
   }

   public void setId( Long id )
   {
      this.id = id;
   }

   public String getType()
   {
      return KANUtil.filterEmpty( type ) == null ? "0" : type;
   }

   public void setType( String type )
   {
      this.type = type;
   }

   public String getModule()
   {
      return module;
   }

   public void setModule( String module )
   {
      this.module = module;
   }

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
   }

   public String getIp()
   {
      return ip;
   }

   public void setIp( String ip )
   {
      this.ip = ip;
   }

   public String getOperateTime()
   {
      return KANUtil.formatDate( operateTime, "yyyy-MM-dd HH:mm:ss" );
   }

   public void setOperateTime( String operateTime )
   {
      this.operateTime = operateTime;
   }

   public String getOperateBy()
   {
      return operateBy;
   }

   public void setOperateBy( String operateBy )
   {
      this.operateBy = operateBy;
   }

   public String getRemark()
   {
      return remark;
   }

   public void setRemark( String remark )
   {
      this.remark = remark;
   }

   public String getpKey()
   {
      return pKey;
   }

   public void setpKey( String pKey )
   {
      this.pKey = pKey;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getChangeType()
   {
      return changeType;
   }

   public void setChangeType( String changeType )
   {
      this.changeType = changeType;
   }

   public String getChangeReason()
   {
      return changeReason;
   }

   public void setChangeReason( String changeReason )
   {
      this.changeReason = changeReason;
   }

   public String getEmployeeNameZH()
   {
      return employeeNameZH;
   }

   public void setEmployeeNameZH( String employeeNameZH )
   {
      this.employeeNameZH = employeeNameZH;
   }

   public String getEmployeeNameEN()
   {
      return employeeNameEN;
   }

   public void setEmployeeNameEN( String employeeNameEN )
   {
      this.employeeNameEN = employeeNameEN;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      this.type = "0";
      this.module = "";
      this.content = "";
      this.ip = "";
      this.operateTime = null;
      this.operateBy = "";
      this.pKey = "";
      this.remark = "";
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.modules = KANConstants.getKANAccountConstants( KANConstants.DEFAULT_ACCOUNTID ).getSystemLogModule( null );
      this.modules.add( 0, super.getEmptyMappingVO() );
      this.types = KANConstants.getKANAccountConstants( KANConstants.DEFAULT_ACCOUNTID ).getSystemLogOperType( getLocale().getLanguage() );
      this.types.add( 0, super.getEmptyMappingVO() );
      this.changeReasons = KANUtil.getMappings( request.getLocale(), "business.employee.change.report.changeReason" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   public List< MappingVO > getModules()
   {
      return modules;
   }

   public void setModules( List< MappingVO > modules )
   {
      this.modules = modules;
   }

   public List< MappingVO > getTypes()
   {
      return types;
   }

   public void setTypes( List< MappingVO > types )
   {
      this.types = types;
   }

   public List< MappingVO > getChangeReasons()
   {
      return changeReasons;
   }

   public void setChangeReasons( List< MappingVO > changeReasons )
   {
      this.changeReasons = changeReasons;
   }

   public String getDecodeOperateBy()
   {
      return super.decodeUserId( operateBy );
   }

   public String getDecodeOperateTime()
   {
      return super.decodeDatetime( operateTime );
   }

   // “Ï∂Ø‘≠“Ú
   public String getDecodeChangeReason()
   {
      return decodeField( changeReason, changeReasons );
   }

   public String getDecodeOperateType()
   {
      Operate operate = null;
      int tmpType = KANUtil.filterEmpty( type ) == null ? 0 : Integer.parseInt( type );
      switch ( tmpType )
      {
         case 1:
            operate = Operate.ADD;
            break;
         case 2:
            operate = Operate.MODIFY;
            break;
         case 3:
            operate = Operate.DELETE;
            break;
         case 4:
            operate = Operate.SUBMIT;
            break;
         case 5:
            operate = Operate.ROllBACK;
            break;

         case 7:
            operate = Operate.APPROVE;
            break;
         case 8:
            operate = Operate.REJECT;
            break;
         case 9:
            operate = Operate.BATCH_SUBMIT;
            break;
         default:
            return "";

      }

      return "zh".equals( super.getLocale().getLanguage() ) ? operate.getName() : operate.getNameEN();
   }

}
