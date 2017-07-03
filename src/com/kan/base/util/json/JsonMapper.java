package com.kan.base.util.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.util.JSONPObject;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * �򵥷�װJackson��ʵ��JSON String<->Java Object��Mapper.
 * 
 * ��װ��ͬ��������, ʹ�ò�ͬ��builder��������ʵ��.
 * 
 */
public class JsonMapper
{

   private static Logger logger = LoggerFactory.getLogger( JsonMapper.class );

   private ObjectMapper mapper;

   public JsonMapper( Inclusion inclusion )
   {
      mapper = new ObjectMapper();
      //�������ʱ�������Եķ��
      mapper.setSerializationInclusion( inclusion );
      
      mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
      
      //��������ʱ������JSON�ַ����д��ڵ�Java����ʵ��û�е�����
      mapper.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );
      //��ֹʹ��int����Enum��order()�����л�Enum,�ǳ�Σ�U
      mapper.configure( DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true );
   }

   /**
    * �������ȫ�����Ե�Json�ַ�����Mapper.
    */
   public static JsonMapper buildNormalMapper()
   {
      return new JsonMapper( Inclusion.ALWAYS );
   }

   /**
    * ����ֻ����ǿ����Ե�Json�ַ�����Mapper.
    */
   public static JsonMapper buildNonNullMapper()
   {
      return new JsonMapper( Inclusion.NON_NULL );
   }

   /**
    * ����ֻ�����ʼֵ���ı�����Ե�Json�ַ�����Mapper.
    */
   public static JsonMapper buildNonDefaultMapper()
   {
      return new JsonMapper( Inclusion.NON_DEFAULT );
   }

   /**
    * ����ֻ�����Null�ҷ�Empty(��List.isEmpty)�����Ե�Json�ַ�����Mapper.
    */
   public static JsonMapper buildNonEmptyMapper()
   {
      return new JsonMapper( Inclusion.NON_EMPTY );
   }

   /**
    * �������ΪNull, ����"null".
    * �������Ϊ�ռ���, ����"[]".
    */
   public String toJson( Object object )
   {

      try
      {
         return mapper.writeValueAsString( object );
      }
      catch ( IOException e )
      {
         throw NestedException.wrap( e );
      }
   }

   /**
    * ���JSON�ַ���ΪNull��"null"�ַ���, ����Null.
    * ���JSON�ַ���Ϊ"[]", ���ؿռ���.
    * 
    * �����ȡ������List/Map, �Ҳ���List<String>���ּ�����ʱ,��ʹ�ú���constructParametricType��������.
    * @see #constructParametricType(Class, Class...)
    */
   public < T > T fromJson( String jsonString, Class< T > clazz )
   {
      if ( StringUtils.isEmpty( jsonString ) )
      {
         return null;
      }

      try
      {
         return mapper.readValue( jsonString, clazz );
      }
      catch ( IOException e )
      {
         throw NestedException.wrap( e );
      }
   }

   /**
    * ���JSON�ַ���ΪNull��"null"�ַ���, ����Null.
    * ���JSON�ַ���Ϊ"[]", ���ؿռ���.
    * 
    * �����ȡ������List/Map, �Ҳ���List<String>���ּ�����ʱ,��ʹ�ú���constructParametricType��������.
    * @see #constructParametricType(Class, Class...)
    */
   @SuppressWarnings("unchecked")
   public < T > T fromJson( String jsonString, JavaType javaType )
   {
      if ( StringUtils.isEmpty( jsonString ) )
      {
         return null;
      }

      try
      {
         return ( T ) mapper.readValue( jsonString, javaType );
      }
      catch ( IOException e )
      {
         throw NestedException.wrap( e );
      }
   }

   @SuppressWarnings("unchecked")
   public < T > T fromJson( String jsonString, Class< ? > parametrized, Class< ? >... parameterClasses )
   {
      return ( T ) this.fromJson( jsonString, constructParametricType( parametrized, parameterClasses ) );
   }

   @SuppressWarnings("unchecked")
   public < T > List< T > fromJsonToList( String jsonString, Class< T > classMeta )
   {
      return ( List< T > ) this.fromJson( jsonString, constructParametricType( List.class, classMeta ) );
   }

   @SuppressWarnings("unchecked")
   public < T > T fromJson( JsonNode node, Class< ? > parametrized, Class< ? >... parameterClasses )
   {
      JavaType javaType = constructParametricType( parametrized, parameterClasses );
      try
      {
         return ( T ) mapper.readValue( node, javaType );
      }
      catch ( IOException e )
      {
         throw NestedException.wrap( e );
      }
   }

   @SuppressWarnings("unchecked")
   public < T > T pathAtRoot( String json, String path, Class< ? > parametrized, Class< ? >... parameterClasses )
   {
      JsonNode rootNode = parseNode( json );
      JsonNode node = rootNode.path( path );
      return ( T ) fromJson( node, parametrized, parameterClasses );
   }

   @SuppressWarnings("unchecked")
   public < T > T pathAtRoot( String json, String path, Class< T > clazz )
   {
      JsonNode rootNode = parseNode( json );
      JsonNode node = rootNode.path( path );
      return ( T ) fromJson( node, clazz );
   }

   /**
    * ���췺�͵�Type��List<MyBean>, �����constructParametricType(ArrayList.class,MyBean.class)
    *             Map<String,MyBean>�����(HashMap.class,String.class, MyBean.class)
    */
   public JavaType constructParametricType( Class< ? > parametrized, Class< ? >... parameterClasses )
   {
      return mapper.getTypeFactory().constructParametricType( parametrized, parameterClasses );
   }

   /**
    * ��JSON�eֻ����Bean�Ĳ��֌��ԕr������һ���Ѵ���Bean��ֻ���wԓ���ֵČ���.
    */
   @SuppressWarnings("unchecked")
   public < T > T update( T object, String jsonString )
   {
      try
      {
         return ( T ) mapper.readerForUpdating( object ).readValue( jsonString );
      }
      catch ( JsonProcessingException e )
      {
         logger.warn( "update json string:" + jsonString + " to object:" + object + " error.", e );
      }
      catch ( IOException e )
      {
         logger.warn( "update json string:" + jsonString + " to object:" + object + " error.", e );
      }
      return null;
   }

   /**
    * ݔ��JSONP��ʽ����.
    */
   public String toJsonP( String functionName, Object object )
   {
      return toJson( new JSONPObject( functionName, object ) );
   }

   /**
    * �O���Ƿ�ʹ��Enum��toString�������x��Enum,
    * ��False�r�rʹ��Enum��name()�������x��Enum, Ĭ�J��False.
    * ע�Ȿ����һ��Ҫ��Mapper������, ���е��x������֮ǰ�{��.
    */
   public void setEnumUseToString( boolean value )
   {
      mapper.configure( SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING, value );
      mapper.configure( DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING, value );
   }

   /**
    * ȡ��Mapper����һ�������û�ʹ���������л�API.
    */
   public ObjectMapper getMapper()
   {
      return mapper;
   }

   public JsonNode parseNode( String json )
   {
      try
      {
         return mapper.readValue( json, JsonNode.class );
      }
      catch ( IOException e )
      {
         throw NestedException.wrap( e );
      }
   }

   /**
    * ���ȫ������
    * @param object
    * @return
    */
   public static String toNormalJson( Object object )
   {
      return new JsonMapper( Inclusion.ALWAYS ).toJson( object );
   }

   /**
    * ����ǿ�����
    * @param object
    * @return
    */
   public static String toNonNullJson( Object object )
   {
      return new JsonMapper( Inclusion.NON_NULL ).toJson( object );
   }

   /**
    * �����ʼֵ���ı䲿�ֵ�����
    * @param object
    * @return
    */
   public static String toNonDefaultJson( Object object )
   {
      return new JsonMapper( Inclusion.NON_DEFAULT ).toJson( object );
   }

   /**
    * �����Null�ҷ�Empty(��List.isEmpty)������
    * @param object
    * @return
    */
   public static String toNonEmptyJson( Object object )
   {
      return new JsonMapper( Inclusion.NON_EMPTY ).toJson( object );
   }

   public void setDateFormat( String dateFormat )
   {
      mapper.setDateFormat( new SimpleDateFormat( dateFormat ) );
   }

   public static String toLogJson( Object object )
   {
      JsonMapper jsonMapper = new JsonMapper( Inclusion.NON_EMPTY );
      jsonMapper.setDateFormat( "yyyy-MM-dd HH:mm:ss" );
      return jsonMapper.toJson( object );
   }

}
