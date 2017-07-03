package com.kan.base.domain.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 全局ModuleDTO
 * @author Kevin
 */
public class ModuleDTO implements Serializable
{
   /**  
   * Serial Version UID
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = -5737362422742171158L;

   private ModuleVO moduleVO = new ModuleVO();

   // 用于生成Module树
   private List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

   /**
    * For Application 
    */
   public ModuleVO getModuleVO()
   {
      return moduleVO;
   }

   public void setModuleVO( ModuleVO moduleVO )
   {
      this.moduleVO = moduleVO;
   }

   public List< ModuleDTO > getModuleDTOs()
   {
      return moduleDTOs;
   }

   public void setModuleDTOs( List< ModuleDTO > moduleDTOs )
   {
      this.moduleDTOs = moduleDTOs;
   }

   public static List< Map< String, Object >> iterator( List< ModuleDTO > moduleDTOs, IModuleDTOIterator interfaceDO )
   {
      List< Map< String, Object >> retList = new ArrayList< Map< String, Object > >();
      List< Integer > indexs = new ArrayList< Integer >();
      List< ModuleDTO > stackModules = new ArrayList< ModuleDTO >();
      for ( int i = 0; i < moduleDTOs.size(); i++ )
      {
         if ( stackModules.size() > 0 )
         {
            indexs.set( 0, i );
            ModuleDTO moduleDTO = moduleDTOs.get( i );
            stackModules.set( 0, moduleDTO );
         }
         else
         {
            indexs.add( 0 );
            ModuleDTO moduleDTO = moduleDTOs.get( i );
            stackModules.add( moduleDTO );
         }
         //先根遍历
         Map< String, Object > returnMap = interfaceDO.doReturn( stackModules );
         if ( returnMap != null )
         {
            retList.add( returnMap );
         }
         while ( true )
         {
            //先判断是否是先找兄弟节点
            if ( stackModules.size() == indexs.size() )
            //不是找兄弟节点
            {
            // stackModules 最后一个DTO的DTOs的size 是否大于零
               boolean hasChild = stackModules.get( stackModules.size() - 1 ).getModuleDTOs().size() > 0;
               if ( hasChild == false )
               {
                  int stackSize = stackModules.size();
                  if ( stackSize > 1 )//不是栈顶元素
                  {
                     //当前节点相对于归属父节点的下标
                     int childIndex = indexs.get( stackSize - 1 );
                     //当前节点的归属父节点
                     ModuleDTO parentDTO = stackModules.get( stackSize - 2 );
                     //父节点的子节点数量-1 大于 当前节点相对于归属父节点的下标  ，说明有兄弟节点
                     if ( parentDTO.getModuleDTOs().size() - 1 > childIndex )
                     {
                        //栈顶替换
                        indexs.set( stackSize - 1, childIndex + 1 );
                        ModuleDTO nextChildDTO = parentDTO.getModuleDTOs().get( childIndex + 1 );
                        stackModules.set( stackSize - 1, nextChildDTO );
                        //先根遍历
                        Map< String, Object > returnMap2 = interfaceDO.doReturn( stackModules );
                        if ( returnMap2 != null )
                        {
                           retList.add( returnMap2 );
                        }
                     }
                     else
                     //没有兄弟节点
                     {
                        //出栈
                        stackModules.remove( stackSize - 1 );
                        // 栈顶元素不出栈，而是标记为-1 ， 下次循环先找兄弟节点
                        indexs.set( indexs.size() - 1, -1 );
                     }

                  }
                  else
                  //是栈顶元素
                  {
                     if(stackModules.size() != indexs.size()){
                        //System.out.println("-----0101-------");
                     }else{
                        //System.out.println("-----0102-------");
                     }
                     break;
                  }
               }
               else
               {
                  ModuleDTO mDTO = stackModules.get( stackModules.size() - 1 );
                  //入栈
                  indexs.add( 0 );
                  stackModules.add( mDTO.getModuleDTOs().get( 0 ) );
                  //先根遍历
                  Map< String, Object > returnMap2 = interfaceDO.doReturn( stackModules );
                  if ( returnMap2 != null )
                  {
                     retList.add( returnMap2 );
                  }
               }
            }else{
               
               if(indexs.get( indexs.size()-1 )==-1){
                  //System.out.println("-------直接找兄弟节点-------");
               }else{
                  //System.out.println("-------不是直接找兄弟节点-------");
               }
               
               
               //找兄弟节点
               int stackSize = stackModules.size();
               if ( stackSize > 1 )//不是栈顶元素
               {
                  //当前节点相对于归属父节点的下标
                  int childIndex = indexs.get( stackSize - 1 );
                  //当前节点的归属父节点
                  ModuleDTO parentDTO = stackModules.get( stackSize - 2 );
                  //父节点的子节点数量-1 大于 当前节点相对于归属父节点的下标  ，说明有兄弟节点
                  if ( parentDTO.getModuleDTOs().size() - 1 > childIndex )
                  {
                     //栈顶替换
                     indexs.remove( stackSize );
                     indexs.set( stackSize - 1, childIndex + 1 );
                     ModuleDTO nextChildDTO = parentDTO.getModuleDTOs().get( childIndex + 1 );
                     stackModules.set( stackSize - 1, nextChildDTO );
                     //先根遍历
                     Map< String, Object > returnMap2 = interfaceDO.doReturn( stackModules );
                     if ( returnMap2 != null )
                     {
                        retList.add( returnMap2 );
                     }
                  }
                  else
                  //没有兄弟节点
                  {
                     //出栈
                     stackModules.remove( stackSize - 1 );
                     // 栈顶元素不出栈，而是标记为-1 ， 下次循环先找兄弟节点
                     indexs.remove( stackSize-1 );
                     indexs.set( indexs.size() - 1, -1 );
                  }

               }
               else
               //是栈顶元素
               {
                  if(stackModules.size() != indexs.size()){
                     indexs.remove( stackSize-1 );
                     //System.out.println("-----0201-------");
                  }else{
                     //System.out.println("-----0202-------");
                  }
                  break;
               }
            }
            

         }
      }
      return retList;
   }

}
