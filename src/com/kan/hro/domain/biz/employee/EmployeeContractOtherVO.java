package com.kan.hro.domain.biz.employee;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeContractOtherVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3986770799631492651L;

   // ��Ա��������
   private String employeeOtherId;

   // �Ͷ���ͬId
   private String contractId;

   // ��ĿId
   private String itemId;

   // ����
   private String base;

   // ������Դ�������Ŀ����ItemGroup��
   private String baseFrom;

   // ����
   private String percentage;

   // �̶���
   private String fix;

   // ����
   private String quantity;

   // �ۿ�
   private String discount;

   // ����
   private String multiple;

   // ��������
   private String cycle;

   // ��Чʱ��
   private String startDate;

   // ����ʱ��
   private String endDate;

   // ����������
   private String resultCap;

   // ����������
   private String resultFloor;

   // ���㹫ʽ����
   private String formularType;

   // ���㹫ʽ
   private String formular;

   // ��ʾ������
   private String showToTS;

   // ����
   private String description;

   /**
    * For Application
    */
   // �Ͷ���ͬ������Э�����ƣ����ģ�
   private String contractNameZH;

   // �Ͷ���ͬ������Э�����ƣ�Ӣ�ģ�
   private String contractNameEN;

   public String getDecodeCycle()
   {
      return decodeField( this.cycle, KANUtil.getMappings( this.getLocale(), "business.cycles" ) );
   }

   public String getDecodeBaseFrom()
   {
      return decodeField( this.baseFrom, KANConstants.getKANAccountConstants( super.getAccountId() ).getItemGroups( this.getLocale().getLanguage(), super.getCorpId() ) );
   }

   public String getDecodeItemId()
   {
      return decodeField( this.itemId, KANConstants.getKANAccountConstants( super.getAccountId() ).getItems( this.getLocale().getLanguage(), super.getCorpId() ) );
   }

   public String getDecodeMultiple()
   {
      return decodeField( this.multiple, KANUtil.getMappings( this.getLocale(), "business.multiples" ), true );
   }

   public void reset() throws KANException
   {
      this.contractId = "";
      this.itemId = "0";
      this.base = "";
      this.baseFrom = "0";
      this.percentage = "";
      this.fix = "";
      this.quantity = "";
      this.discount = "";
      this.multiple = "0";
      this.cycle = "0";
      this.startDate = "";
      this.endDate = "";
      this.resultCap = "";
      this.resultFloor = "";
      this.formularType = "0";
      this.formular = "";
      this.showToTS = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   public void update( Object object ) throws KANException
   {
      final String ignoreProperties[] = { "employeeOtherId", "deleted", "createBy", "createDate" };
      BeanUtils.copyProperties( object, this, ignoreProperties );
      super.setModifyDate( new Date() );
   }

   public String getShowToTS()
   {
      return KANUtil.filterEmpty( showToTS );
   }

   public void setShowToTS( String showToTS )
   {
      this.showToTS = showToTS;
   }

   public String getEmployeeOtherId()
   {
      return employeeOtherId;
   }

   public void setEmployeeOtherId( String employeeOtherId )
   {
      this.employeeOtherId = employeeOtherId;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getBase()
   {
      return KANUtil.filterEmpty( base );
   }

   public void setBase( String base )
   {
      this.base = base;
   }

   public String getBaseFrom()
   {
      return KANUtil.filterEmpty( baseFrom );
   }

   public void setBaseFrom( String baseFrom )
   {
      this.baseFrom = baseFrom;
   }

   public String getPercentage()
   {
      return KANUtil.filterEmpty( percentage );
   }

   public void setPercentage( String percentage )
   {
      this.percentage = percentage;
   }

   public String getFix()
   {
      return KANUtil.filterEmpty( fix );
   }

   public void setFix( String fix )
   {
      this.fix = fix;
   }

   public String getQuantity()
   {
      return KANUtil.filterEmpty( quantity );
   }

   public void setQuantity( String quantity )
   {
      this.quantity = quantity;
   }

   public String getDiscount()
   {
      return KANUtil.filterEmpty( discount );
   }

   public void setDiscount( String discount )
   {
      this.discount = discount;
   }

   public String getMultiple()
   {
      return KANUtil.filterEmpty( multiple );
   }

   public void setMultiple( String multiple )
   {
      this.multiple = multiple;
   }

   public String getCycle()
   {
      return KANUtil.filterEmpty( cycle );
   }

   public void setCycle( String cycle )
   {

      this.cycle = cycle;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public final String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( endDate ) );
   }

   public final void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getResultCap()
   {
      return KANUtil.filterEmpty( resultCap );
   }

   public void setResultCap( String resultCap )
   {
      this.resultCap = resultCap;
   }

   public String getResultFloor()
   {
      return KANUtil.filterEmpty( resultFloor );
   }

   public void setResultFloor( String resultFloor )
   {
      this.resultFloor = resultFloor;
   }

   public String getFormularType()
   {
      return KANUtil.filterEmpty( formularType );
   }

   public void setFormularType( String formularType )
   {
      this.formularType = formularType;
   }

   public String getFormular()
   {
      return KANUtil.filterEmpty( formular );
   }

   public void setFormular( String formular )
   {
      this.formular = formular;
   }

   public String getDescription()
   {
      return KANUtil.filterEmpty( description );
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   public String getEncodedId() throws KANException
   {
      return encodedField( employeeOtherId );
   }

   public final String getContractNameZH()
   {
      return contractNameZH;
   }

   public final void setContractNameZH( String contractNameZH )
   {
      this.contractNameZH = contractNameZH;
   }

   public final String getContractNameEN()
   {
      return contractNameEN;
   }

   public final void setContractNameEN( String contractNameEN )
   {
      this.contractNameEN = contractNameEN;
   }

   public String getContractName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage() != null && this.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            return contractNameZH;
         }
         else
         {
            return contractNameEN;
         }
      }
      else
      {
         return contractNameZH;
      }
   }

}