package com.kan.base.domain.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class WorkflowDefineRequirementsVO extends BaseVO
{
   // Serial Version UID
   private static final long serialVersionUID = 1235123425123423234L;

   /**
    * For DB
    */
   // �Զ�����������ID
   private String requirementId;

   // ������ID
   private String defineId;

   // ������������
   private String nameZH;

   // ����Ӣ������
   private String nameEN;

   // ��������
   private String requirementType;

   // �ֶ�ID
   private String columnId;

   // �ֶ����ƣ����ݿ⣩
   private String columnNameDb;

   // �Ƚ�����
   private String compareType;

   // �Ƚ�ֵ
   private String compareValue;

   // ���ʽ(��ʱ����)
   private String expression;

   // ����˳��
   private int columnIndex;

   // �������
   private String combineType;

   /**
    * For Application
    */
   // ��������
   private List< MappingVO > requirementTypes = new ArrayList< MappingVO >();

   // �Ƚ�����
   private List< MappingVO > compareTypes = new ArrayList< MappingVO >();

   // �������
   private List< MappingVO > combineTypes = new ArrayList< MappingVO >();

   // Action�н��г�ʼ��
   private List< MappingVO > columns = new ArrayList< MappingVO >();

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.requirementId );
   }

   @Override
   public void reset() throws KANException
   {
      this.defineId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.requirementType = "";
      this.columnId = "";
      this.columnNameDb = "";
      this.compareType = "";
      this.compareValue = "";
      this.expression = "";
      this.combineType = "";
   }

   @SuppressWarnings("unchecked")
   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.requirementTypes = KANUtil.getMappings( request.getLocale(), "define.requirement.requirementTypes" );
      this.compareTypes = KANUtil.getMappings( request.getLocale(), "define.requirement.compareTypes" );
      this.combineTypes = KANUtil.getMappings( request.getLocale(), "define.requirement.combineTypes" );

      Object obj = request.getAttribute( "REQUEST_CACHE_COLUMNVOS" );
      if ( obj != null )
      {
         this.columns = ( List< MappingVO > ) obj;
      }
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final WorkflowDefineRequirementsVO workflowDefineRequirements = ( WorkflowDefineRequirementsVO ) object;
      this.nameZH = workflowDefineRequirements.getNameZH();
      this.nameEN = workflowDefineRequirements.getNameEN();
      this.requirementType = workflowDefineRequirements.getRequirementType();
      this.columnId = workflowDefineRequirements.getColumnId();
      this.columnNameDb = workflowDefineRequirements.getColumnNameDb();
      this.compareType = workflowDefineRequirements.getCompareType();
      this.compareValue = workflowDefineRequirements.getCompareValue();
      this.expression = workflowDefineRequirements.getExpression();
      this.columnIndex = workflowDefineRequirements.getColumnIndex();
      this.combineType = workflowDefineRequirements.getCombineType();
      super.setStatus( workflowDefineRequirements.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getRequirementId()
   {
      return requirementId;
   }

   public void setRequirementId( String requirementId )
   {
      this.requirementId = requirementId;
   }

   public String getDefineId()
   {
      return defineId;
   }

   public void setDefineId( String defineId )
   {
      this.defineId = defineId;
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

   public String getRequirementType()
   {
      return requirementType;
   }

   public void setRequirementType( String requirementType )
   {
      this.requirementType = requirementType;
   }

   public String getColumnId()
   {
      return columnId;
   }

   public void setColumnId( String columnId )
   {
      this.columnId = columnId;
   }

   public String getColumnNameDb()
   {
      return columnNameDb;
   }

   public void setColumnNameDb( String columnNameDb )
   {
      this.columnNameDb = columnNameDb;
   }

   public String getCompareType()
   {
      return compareType;
   }

   public void setCompareType( String compareType )
   {
      this.compareType = compareType;
   }

   public String getCompareValue()
   {
      return compareValue;
   }

   public void setCompareValue( String compareValue )
   {
      this.compareValue = compareValue;
   }

   public String getExpression()
   {
      return expression;
   }

   public void setExpression( String expression )
   {
      this.expression = expression;
   }

   public String getCombineType()
   {
      return combineType;
   }

   public void setCombineType( String combineType )
   {
      this.combineType = combineType;
   }

   public List< MappingVO > getRequirementTypes()
   {
      return requirementTypes;
   }

   public void setRequirementTypes( List< MappingVO > requirementTypes )
   {
      this.requirementTypes = requirementTypes;
   }

   public List< MappingVO > getCompareTypes()
   {
      return compareTypes;
   }

   public void setCompareTypes( List< MappingVO > compareTypes )
   {
      this.compareTypes = compareTypes;
   }

   public List< MappingVO > getCombineTypes()
   {
      return combineTypes;
   }

   public void setCombineTypes( List< MappingVO > combineTypes )
   {
      this.combineTypes = combineTypes;
   }

   public List< MappingVO > getColumns()
   {
      return columns;
   }

   public void setColumns( List< MappingVO > columns )
   {
      this.columns = columns;
   }

   public String getDecodeColumn()
   {
      return decodeField( this.columnId, this.columns );
   }

   public String getDecodeCompareType()
   {
      return decodeField( this.compareType, this.compareTypes );
   }

   public String getDecodeCombineType()
   {
      return decodeField( this.combineType, this.combineTypes );
   }

   public int getColumnIndex()
   {
      return columnIndex;
   }

   public void setColumnIndex( int columnIndex )
   {
      this.columnIndex = columnIndex;
   }
}
