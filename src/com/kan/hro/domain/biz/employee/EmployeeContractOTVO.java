package com.kan.hro.domain.biz.employee;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.springframework.beans.BeanUtils;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeContractOTVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3986770799631492651L;

   /**
    * For DB
    */

   // ��Ա�Ӱ෽��Id
   private String employeeOTId;

   // �Ͷ���ͬId
   private String contractId;

   //��ĿId�������� - ���� - ��Ŀ��
   private String itemId;

   // ����
   private String base;

   // ������Դ
   private String baseFrom;

   // ��������ʹ�û�����Դʱ���֣�
   private String percentage;

   // �̶��𣨵�ʹ�û�����Դʱ���֣�
   private String fix;

   // ��������ʱ�����㣩
   private String quantity;

   // �ۿۣ���ʱ�����㣩
   private String discount;

   // ���ʣ���ʱ�����㣩
   private String multiple;

   // �������ڣ�������һ���ԣ�
   private String cycle;

   // ��Ч����
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

   //����
   private String description;

   /**
    *  For Application
    */
   // �Ͷ���ͬ������Э�����ƣ����ģ�
   private String contractNameZH;

   // �Ͷ���ͬ������Э�����ƣ�Ӣ�ģ�
   private String contractNameEN;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeOTId );
   }

   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   public String getDecodeCycle()
   {
      return decodeField( this.cycle, KANUtil.getMappings( this.getLocale(), "business.cycles" ) );
   }

   public String getDecodeBaseFrom()
   {
      return decodeField( this.baseFrom, KANConstants.getKANAccountConstants( super.getAccountId() ).getItemGroups( this.getLocale().getLanguage(), super.getCorpId() ) );
   }

   public String getDecodeMultiple()
   {
      return decodeField( this.multiple, KANUtil.getMappings( this.getLocale(), "business.multiples" ), true );
   }

   public String getDecodeItemId()
   {
      return decodeField( this.itemId, KANConstants.getKANAccountConstants( super.getAccountId() ).getItems( this.getLocale().getLanguage(), super.getCorpId() ) );
   }

   @Override
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
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final String ignoreProperties[] = { "employeeOTId", "deleted", "createBy", "createDate" };
      BeanUtils.copyProperties( object, this, ignoreProperties );
      super.setModifyDate( new Date() );
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEmployeeOTId()
   {
      return employeeOTId;
   }

   public void setEmployeeOTId( String employeeOTId )
   {
      this.employeeOTId = employeeOTId;
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
      return formular;
   }

   public void setFormular( String formular )
   {
      this.formular = formular;
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
