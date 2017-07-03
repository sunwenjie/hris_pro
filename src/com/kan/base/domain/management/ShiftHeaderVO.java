package com.kan.base.domain.management;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ShiftHeaderVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 6894666015579755234L;

   /**
    * For DB
    */

   // �Ű�����Id
   private String headerId;

   // �Ű����ƣ����ģ�
   private String nameZH;

   // �Ű����ƣ�Ӣ�ģ�
   private String nameEN;

   // �Ű����ͣ�1:���ܣ�2:���죬3:�Զ��壩��������Զ���֮�����Ϣ�պͽڼ��ղο�����
   private String shiftType;

   // �Ű�Ƶ�ʣ����磺ÿ�����ܻ�ÿ�����죩 
   private String shiftIndex;

   // �Ű࿪ʼʱ�䣨������õ���
   private String startDate;

   // ����
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // �Ű�����
   private List< MappingVO > shiftTypies;
   @JsonIgnore
   // �Ű�ʱ������MappingVO
   private List< MappingVO > shiftPeriods;
   @JsonIgnore
   // �Ű�ʱ����������
   private String[][] periodArray;

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.shiftTypies = KANUtil.getMappings( request.getLocale(), "sys.shift.header.shift.type" );
      this.shiftPeriods = KANUtil.getMappings( request.getLocale(), "sys.shift.detail.shift.period" );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.shiftType = "0";
      this.shiftIndex = "";
      this.startDate = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ShiftHeaderVO shiftHeaderVO = ( ShiftHeaderVO ) object;
      this.nameZH = shiftHeaderVO.getNameZH();
      this.nameEN = shiftHeaderVO.getNameEN();
      this.shiftType = shiftHeaderVO.getShiftType();
      this.shiftIndex = shiftHeaderVO.getShiftIndex();
      this.startDate = shiftHeaderVO.getStartDate();
      this.description = shiftHeaderVO.getDescription();
      super.setStatus( shiftHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeShiftType() throws KANException
   {
      return decodeField( shiftType, shiftTypies );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getShiftType()
   {
      return shiftType;
   }

   public void setShiftType( String shiftType )
   {
      this.shiftType = shiftType;
   }

   public String getShiftIndex()
   {
      return KANUtil.filterEmpty( shiftIndex ) == null ? "0" : shiftIndex;
   }

   public void setShiftIndex( String shiftIndex )
   {
      this.shiftIndex = shiftIndex;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
   }

   public List< MappingVO > getShiftTypies()
   {
      return shiftTypies;
   }

   public void setShiftTypies( List< MappingVO > shiftTypies )
   {
      this.shiftTypies = shiftTypies;
   }

   public List< MappingVO > getShiftPeriods()
   {
      return shiftPeriods;
   }

   public void setShiftPeriods( List< MappingVO > shiftPeriods )
   {
      this.shiftPeriods = shiftPeriods;
   }

   public String[][] getPeriodArray()
   {
      return periodArray;
   }

   public void setPeriodArray( String[][] periodArray )
   {
      this.periodArray = periodArray;
   }

}
