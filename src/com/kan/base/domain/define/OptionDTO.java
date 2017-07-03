package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.MappingVO;

/**
 * 封装Account对应的Column Option对象
 * 
 * @author Kevin
 */
public class OptionDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -842672533889138340L;

   // 当前Option Header对象
   private OptionHeaderVO optionHeaderVO = new OptionHeaderVO();

   // 当前Option Header包含的Option Detail
   private List< OptionDetailVO > optionDetailVOs = new ArrayList< OptionDetailVO >();

   public OptionHeaderVO getOptionHeaderVO()
   {
      return optionHeaderVO;
   }

   public void setOptionHeaderVO( OptionHeaderVO optionHeaderVO )
   {
      this.optionHeaderVO = optionHeaderVO;
   }

   public List< OptionDetailVO > getOptionDetailVOs()
   {
      return optionDetailVOs;
   }

   public void setOptionDetailVOs( List< OptionDetailVO > optionDetailVOs )
   {
      this.optionDetailVOs = optionDetailVOs;
   }

   // 按照Option Detail Id得到OptionDetailVO
   public OptionDetailVO getOptionDetailVOByOptionDetailId( final String optionDetailId )
   {
      if ( optionDetailVOs != null && optionDetailVOs.size() > 0 )
      {
         for ( OptionDetailVO optionDetailVO : optionDetailVOs )
         {
            if ( optionDetailVO.getOptionDetailId() != null && optionDetailVO.getOptionDetailId().equals( optionDetailId ) )
            {
               return optionDetailVO;
            }
         }
      }

      return null;
   }

   // 得到当前选项的所有选项值
   public List< MappingVO > getOptions( final String localeLanguage )
   {
      final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      // 如果当前选项存在选项值
      if ( optionDetailVOs != null && optionDetailVOs.size() > 0 )
      {
         for ( OptionDetailVO optionDetailVO : optionDetailVOs )
         {
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( optionDetailVO.getOptionId() );
            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( optionDetailVO.getOptionNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( optionDetailVO.getOptionNameEN() );
            }
            mappingVO.setMappingTemp( optionDetailVO.getOptionValue() );
            mappingVOs.add( mappingVO );
         }
      }

      return mappingVOs;
   }

}
