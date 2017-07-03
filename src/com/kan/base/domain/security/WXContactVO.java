package com.kan.base.domain.security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.kan.base.util.KANConstants;
import com.kan.base.util.KANUtil;
import com.kan.base.util.json.JsonMapper;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;

/**
 * 如果微信字段有变.需要
 * Attrs字段修改
 * fillAttrsInfo方法字段修改
 * jsonObject2VO方法字段修改
 * @author Iori
 *
 */

public class WXContactVO
{
   //姓名, 性别 部门 手机 邮箱 职位 
   //namecn  nameen  dept location title mail phone

   //_userid 对于employeeid
   private String userid;

   private String name;

   // 1表示男性，2表示女性
   private String gender;

   private int[] department;

   private String mobile;

   private String email;

   private String position;

   private String avatar;

   private JSONObject extattr = new JSONObject();

   @JsonIgnore
   private Attrs attrs = new Attrs();

   public void resetWXContactVO( final EmployeeVO employeeVO, final EmployeeContractVO employeeContractVO )
   {
      if ( employeeVO != null && KANUtil.filterEmpty( employeeVO.getEmail1() ) != null )
      {
         // 获取邮箱@前部分
         final String userid = employeeVO.getEmail1().split( "@" )[ 0 ];
         setUserid( userid );
         setName( userid );
         setGender( employeeVO.getSalutation() );
         // bufunction
         final String bufunction = employeeVO.get_tempParentBranchIds();
         if ( KANUtil.filterEmpty( bufunction ) != null )
         {
            final String[] depts = bufunction.split( "," );
            if ( depts != null && depts.length > 0 )
            {
               int deptSize = depts.length;
               int[] deptArr = new int[ deptSize ];
               for ( int i = 0; i < deptSize; i++ )
               {
                  deptArr[ i ] = Integer.parseInt( depts[ i ] );
               }
               // department
               setDepartment( deptArr );
            }
         }

         setMobile( employeeVO.getMobile1() );
         setEmail( employeeVO.getEmail1() );

         //设置自定义字段
         ////namecn  nameen  dept location title mail phone
         final String remark1 = employeeVO.getRemark1();
         final JSONObject jsonRemark1 = KANUtil.toJSONObject( remark1 );
         getAttrs().setNameCN( employeeVO.getNameZH() );
         final String jiancheng = jsonRemark1.getString( "jiancheng" );
         getAttrs().setNameEN( KANUtil.filterEmpty( jiancheng ) == null ? employeeVO.getNameEN() : jiancheng );
         final String branchIds = employeeVO.get_tempBranchIds();
         String branchIdStrs = "";
         if ( KANUtil.filterEmpty( branchIds ) != null )
         {
            final String[] depts = branchIds.split( "," );
            for ( int i = 0; i < depts.length; i++ )
            {
               final BranchVO branchVO = KANConstants.getKANAccountConstants( employeeVO.getAccountId() ).getBranchVOByBranchId( depts[ i ] );
               if ( branchVO != null )
               {
                  if ( i >= 1 )
                  {
                     branchIdStrs += " & ";
                  }
                  branchIdStrs = branchVO.getNameEN();
               }
            }
            getAttrs().setDept( branchIdStrs );
         }

         final String loc = jsonRemark1.getString( "bangongdidian" );
         if ( !"0".equals( loc ) )
         {
            String[] locs = loc.split( "," );
            String locStrs = "";
            for ( int i = 0; i < locs.length; i++ )
            {
               if ( KANUtil.filterEmpty( locs[ i ] ) != null )
               {
                  final LocationVO locationVO = KANConstants.getKANAccountConstants( employeeVO.getAccountId() ).getLocationByLocationId( locs[ i ] );
                  if ( locationVO != null )
                  {
                     if ( locationVO != null )
                     {
                        if ( i >= 1 )
                        {
                           locStrs += " & ";
                        }
                        locStrs += locationVO.getNameEN();
                     }
                  }
               }
            }
            getAttrs().setLocation( locStrs );

         }
         getAttrs().setMail( employeeVO.getEmail1() );
         String phone2 = employeeVO.getPhone2().replaceAll( "\r\n", "" );
         String[] phone2s = phone2.split( "," );
         String tmpPhone = "";
         if ( phone2s != null && phone2s.length > 0 )
         {
            for ( int i = 0; i < phone2s.length; i++ )
            {
               if ( i > 0 )
               {
                  tmpPhone += ",\r\n";
               }
               tmpPhone += phone2s[ i ];
            }
         }
         getAttrs().setPhone( tmpPhone );
         getAttrs().setReportTo( employeeContractVO.getLineManager() );

      }
      if ( employeeContractVO != null )
      {
         // 职位neibuchengwei
         final String contractRemark1 = employeeContractVO.getRemark1();
         final JSONObject jsonRemark1 = KANUtil.toJSONObject( contractRemark1 );
         final String neibuchengwei = jsonRemark1.getString( "neibuchengwei" );
         setPosition( neibuchengwei );
         //title  zhiweimingchengyingwen
         final String zhiweimingchengyingwen = jsonRemark1.getString( "zhiweimingchengyingwen" );
         getAttrs().setTitle( zhiweimingchengyingwen );
      }
      fillAttrsInfo( this.getAttrs() );
   }

   /**
    * 这个方法不用手动调用
    * 设置自定义信息
    * setAttrs( attrs );的同时,会把对象转字符串
    * @param attrs
    */
   public void fillAttrsInfo( Attrs attrs )
   {
      final JSONArray attrsArray = new JSONArray();
      //Name(CN)
      final JSONObject nameCN = new JSONObject();
      nameCN.put( "name", "Name(CN)" );
      nameCN.put( "value", attrs.getNameCN() );
      attrsArray.add( nameCN );
      //Name(EN)
      final JSONObject nameEN = new JSONObject();
      nameEN.put( "name", "Name(EN)" );
      nameEN.put( "value", attrs.getNameEN() );
      attrsArray.add( nameEN );
      //Dept
      final JSONObject dept = new JSONObject();
      dept.put( "name", "Dept" );
      dept.put( "value", attrs.getDept() );
      attrsArray.add( dept );
      //Location
      final JSONObject loc = new JSONObject();
      loc.put( "name", "Location" );
      loc.put( "value", attrs.getLocation() );
      attrsArray.add( loc );
      //Title
      final JSONObject title = new JSONObject();
      title.put( "name", "Title" );
      title.put( "value", attrs.getTitle() );
      attrsArray.add( title );
      //Mail
      final JSONObject mail = new JSONObject();
      mail.put( "name", "Mail" );
      mail.put( "value", attrs.getMail() );
      attrsArray.add( mail );
      //Phone
      final JSONObject phone = new JSONObject();
      phone.put( "name", "Phone" );
      phone.put( "value", attrs.getPhone() );
      attrsArray.add( phone );
      //Report To
      final JSONObject reportTo = new JSONObject();
      reportTo.put( "name", "Report To" );
      reportTo.put( "value", attrs.getReportTo() );
      attrsArray.add( reportTo );
      extattr.put( "attrs", attrsArray );
   }

   public void jsonObject2VO( final String jsonStr )
   {
      jsonObject2VO( JSONObject.fromObject( jsonStr ) );
   }

   public void jsonObject2VO( final JSONObject jsonObject )
   {
      //userid,name,gender,department,mobile,email,position
      //userid
      Object useridObj = jsonObject.get( "userid" );
      if ( useridObj != null )
      {
         setUserid( String.valueOf( useridObj ) );
      }
      //name
      Object nameObj = jsonObject.get( "name" );
      if ( nameObj != null )
      {
         setName( String.valueOf( nameObj ) );
      }
      //gender
      Object genderObj = jsonObject.get( "gender" );
      if ( genderObj != null )
      {
         setGender( String.valueOf( genderObj ) );
      }
      //department
      Object departmentObj = jsonObject.get( "department" );
      if ( departmentObj != null )
      {
         JSONArray jsonArray = ( JSONArray ) departmentObj;
         int[] depts = new int[ jsonArray.size() ];
         for ( int i = 0; i < depts.length; i++ )
         {
            depts[ i ] = jsonArray.getInt( i );
         }
         setDepartment( depts );
      }
      //mobile
      Object mobileObj = jsonObject.get( "mobile" );
      if ( mobileObj != null )
      {
         setMobile( String.valueOf( mobileObj ) );
      }
      //email
      Object emailObj = jsonObject.get( "email" );
      if ( emailObj != null )
      {
         setEmail( String.valueOf( emailObj ) );
      }
      //position
      Object positionObj = jsonObject.get( "position" );
      if ( positionObj != null )
      {
         setPosition( String.valueOf( positionObj ) );
      }
      Object extattrObject = jsonObject.get( "extattr" );
      if ( extattrObject != null )
      {
         final JSONObject extattrJsonObject = ( JSONObject ) extattrObject;
         final Object attrsObject = extattrJsonObject.get( "attrs" );
         final Attrs attrs = new Attrs();
         if ( attrsObject != null )
         {
            final JSONArray attrsArray = ( JSONArray ) attrsObject;
            if ( KANUtil.filterEmpty( attrsArray ) != null && attrsArray.size() > 0 )
            {
               for ( int i = 0; i < attrsArray.size(); i++ )
               {
                  final JSONObject attrObject = attrsArray.getJSONObject( i );
                  if ( attrObject != null && KANUtil.filterEmpty( attrObject.get( "name" ) ) != null )
                  {
                     final String key = attrObject.getString( "name" );
                     if ( "Name(CN)".equals( key ) )
                     {
                        attrs.setNameCN( attrObject.getString( "value" ) );
                     }
                     else if ( "Name(EN)".equals( key ) )
                     {
                        attrs.setNameEN( attrObject.getString( "value" ) );
                     }
                     else if ( "Dept".equals( key ) )
                     {
                        attrs.setDept( attrObject.getString( "value" ) );
                     }
                     else if ( "Location".equals( key ) )
                     {
                        attrs.setLocation( attrObject.getString( "value" ) );
                     }
                     else if ( "Title".equals( key ) )
                     {
                        attrs.setTitle( attrObject.getString( "value" ) );
                     }
                     else if ( "Mail".equals( key ) )
                     {
                        attrs.setMail( attrObject.getString( "value" ) );
                     }
                     else if ( "Phone".equals( key ) )
                     {
                        attrs.setPhone( attrObject.getString( "value" ) );
                     }
                  }
               }
            }
         }
         //设置对象字符串
         setAttrs( attrs );
      }
   }

   /**
    * 对象转成json调用这个方法. 需要过滤字段
    */
   @Override
   public String toString()
   {
      JsonMapper jsonMapper = new JsonMapper( Inclusion.NON_EMPTY );
      return jsonMapper.toJson( this ).toString();
   }

   public String getUserid()
   {
      return userid;
   }

   public void setUserid( String userid )
   {
      this.userid = userid;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

   public String getGender()
   {
      return gender;
   }

   public void setGender( String gender )
   {
      this.gender = gender;
   }

   public int[] getDepartment()
   {
      return department;
   }

   public void setDepartment( int[] department )
   {
      this.department = department;
   }

   public String getMobile()
   {
      //return mobile;
      return "";
   }

   public void setMobile( String mobile )
   {
      this.mobile = mobile;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail( String email )
   {
      this.email = email;
   }

   public String getPosition()
   {
      return position;
   }

   public void setPosition( String position )
   {
      this.position = position;
   }

   public JSONObject getExtattr()
   {
      return extattr;
   }

   public Attrs getAttrs()
   {
      return attrs;
   }

   public void setAttrs( Attrs attrs )
   {
      this.attrs = attrs;
      fillAttrsInfo( attrs );
   }

   public String getAvatar()
   {
      return avatar;
   }

   public void setAvatar( String avatar )
   {
      this.avatar = avatar;
   }

   /**
    * 这个方法基本不调用
    * 用对象设置extattrs用setAttrs
    * @param extattr
    */
   @Deprecated
   public void setExtattr( JSONObject extattr )
   {
      this.extattr = extattr;
   }

   public static void main( String[] args )
   {
      //      final String s = "{\"department\":[10001,1003],\"email\":\"iori.luo@qq.com\",\"extattr\":{\"attrs\":[{\"name\":\"Name(CN)\",\"value\":\"1\"},{\"name\":\"Name(EN)\",\"value\":\"2\"},{\"name\":\"Dept\",\"value\":\"3\"},{\"name\":\"Location\",\"value\":\"4\"},{\"name\":\"Title\",\"value\":\"7\"},{\"name\":\"Mail\",\"value\":\"5\"},{\"name\":\"Phone\",\"value\":\"6\"}]},\"gender\":\"1\",\"mobile\":\"15370129763\",\"name\":\"iori\",\"position\":\"engineer\",\"userid\":\"10001\"}";
      //      WXContactVO wxContactVO = new WXContactVO();
      //      wxContactVO.jsonObject2VO( s );
      WXContactVO wxContactVO = new WXContactVO();
      Attrs attrs = new Attrs();
      attrs.setNameCN( "罗钦" );
      attrs.setNameEN( "iori.luo" );
      attrs.setDept( "dev" );
      attrs.setLocation( "sh" );
      attrs.setTitle( "it" );
      attrs.setMail( "iori.luo@qq.com" );
      attrs.setPhone( "15370129763" );
      wxContactVO.setAttrs( attrs );
      System.out.println( wxContactVO.toString() );
   }
}
