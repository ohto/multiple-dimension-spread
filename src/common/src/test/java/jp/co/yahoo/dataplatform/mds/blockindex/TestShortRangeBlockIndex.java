/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.co.yahoo.dataplatform.mds.blockindex;

import java.io.IOException;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import jp.co.yahoo.dataplatform.schema.objects.*;

import jp.co.yahoo.dataplatform.mds.spread.column.filter.NumberFilter;
import jp.co.yahoo.dataplatform.mds.spread.column.filter.NumberRangeFilter;
import jp.co.yahoo.dataplatform.mds.spread.column.filter.NumberFilterType;
import jp.co.yahoo.dataplatform.mds.spread.column.filter.IFilter;

public class TestShortRangeBlockIndex{

  @DataProvider(name = "T_canBlockSkip_1")
  public Object[][] data1() {
    return new Object[][] {
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.EQUAL , new ShortObj( (short)21 ) ) , true },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.EQUAL , new ShortObj( (short)9 ) ) , true },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.EQUAL , new ShortObj( (short)15 ) ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.EQUAL , new ShortObj( (short)10 ) ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.EQUAL , new ShortObj( (short)20 ) ) , false },

      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.LT , new ShortObj( (short)9 ) ) , true },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.LT , new ShortObj( (short)10 ) ) , true },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.LT , new ShortObj( (short)11 ) ) , false },

      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.LE , new ShortObj( (short)9 ) ) , true },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.LE , new ShortObj( (short)10 ) ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.LE , new ShortObj( (short)11 ) ) , false },

      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.GT , new ShortObj( (short)21 ) ) , true },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.GT , new ShortObj( (short)20 ) ) , true },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.GT , new ShortObj( (short)19 ) ) , false },

      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.GE , new ShortObj( (short)21 ) ) , true },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.GE , new ShortObj( (short)20 ) ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberFilter( NumberFilterType.GE , new ShortObj( (short)19 ) ) , false },

      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( false , new ShortObj( (short)10 ) , true , new ShortObj( (short)10 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( false , new ShortObj( (short)5 ) , true , new ShortObj( (short)15 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( false , new ShortObj( (short)20 ) , true , new ShortObj( (short)21 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( false , new ShortObj( (short)15 ) , true , new ShortObj( (short)25 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( false , new ShortObj( (short)10 ) , true , new ShortObj( (short)20 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( false , new ShortObj( (short)10 ) , true , new ShortObj( (short)10 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( false , new ShortObj( (short)20 ) , true , new ShortObj( (short)20 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( false , new ShortObj( (short)15 ) , true , new ShortObj( (short)16 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( false , new ShortObj( (short)9 ) , true , new ShortObj( (short)9 ) , true ) , true },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( false , new ShortObj( (short)21 ) , true , new ShortObj( (short)21 ) , true ) , true },

      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( true , new ShortObj( (short)10 ) , true , new ShortObj( (short)10 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( true , new ShortObj( (short)5 ) , true , new ShortObj( (short)15 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( true , new ShortObj( (short)20 ) , true , new ShortObj( (short)21 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( true , new ShortObj( (short)15 ) , true , new ShortObj( (short)25 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( true , new ShortObj( (short)10 ) , true , new ShortObj( (short)20 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( true , new ShortObj( (short)10 ) , true , new ShortObj( (short)10 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( true , new ShortObj( (short)20 ) , true , new ShortObj( (short)20 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( true , new ShortObj( (short)15 ) , true , new ShortObj( (short)16 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( true , new ShortObj( (short)9 ) , true , new ShortObj( (short)9 ) , true ) , false },
      { new ShortRangeBlockIndex( (short)10 , (short)20 ) , new NumberRangeFilter( true , new ShortObj( (short)21 ) , true , new ShortObj( (short)21 ) , true ) , false },

    };
  }

  @Test
  public void T_newInstance_1(){
    ShortRangeBlockIndex bIndex = new ShortRangeBlockIndex( (short)10 , (short)20 );
    assertEquals( (short)10 , bIndex.getMin() );
    assertEquals( (short)20 , bIndex.getMax() );
  }

  @Test
  public void T_newInstance_2(){
    ShortRangeBlockIndex bIndex = new ShortRangeBlockIndex();
    assertEquals( Short.MAX_VALUE , bIndex.getMin() );
    assertEquals( Short.MIN_VALUE , bIndex.getMax() );
  }

  @Test
  public void T_getBlockIndexType_1(){
    IBlockIndex bIndex = new ShortRangeBlockIndex();
    assertEquals( BlockIndexType.RANGE_SHORT , bIndex.getBlockIndexType() );
  }

  @Test
  public void T_merge_1(){
    ShortRangeBlockIndex bIndex = new ShortRangeBlockIndex( (short)10 , (short)20 );
    assertEquals( (short)10 , bIndex.getMin() );
    assertEquals( (short)20 , bIndex.getMax() );
    assertTrue( bIndex.merge( new ShortRangeBlockIndex( (short)110 , (short)150 ) ) );
    assertEquals( (short)10 , bIndex.getMin() );
    assertEquals( (short)150 , bIndex.getMax() );
  }

  @Test
  public void T_merge_2(){
    ShortRangeBlockIndex bIndex = new ShortRangeBlockIndex( (short)10 , (short)20 );
    assertEquals( (short)10 , bIndex.getMin() );
    assertEquals( (short)20 , bIndex.getMax() );
    assertTrue( bIndex.merge( new ShortRangeBlockIndex( (short)9 , (short)20 ) ) );
    assertEquals( (short)9 , bIndex.getMin() );
    assertEquals( (short)20 , bIndex.getMax() );
  }

  @Test
  public void T_merge_3(){
    ShortRangeBlockIndex bIndex = new ShortRangeBlockIndex( (short)10 , (short)20 );
    assertEquals( (short)10 , bIndex.getMin() );
    assertEquals( (short)20 , bIndex.getMax() );
    assertTrue( bIndex.merge( new ShortRangeBlockIndex( (short)10 , (short)21 ) ) );
    assertEquals( (short)10 , bIndex.getMin() );
    assertEquals( (short)21 , bIndex.getMax() );
  }

  @Test
  public void T_merge_4(){
    ShortRangeBlockIndex bIndex = new ShortRangeBlockIndex( (short)10 , (short)20 );
    assertEquals( (short)10 , bIndex.getMin() );
    assertEquals( (short)20 , bIndex.getMax() );
    assertTrue( bIndex.merge( new ShortRangeBlockIndex( (short)9 , (short)21 ) ) );
    assertEquals( (short)9 , bIndex.getMin() );
    assertEquals( (short)21 , bIndex.getMax() );
  }

  @Test
  public void T_merge_5(){
    ShortRangeBlockIndex bIndex = new ShortRangeBlockIndex( (short)10 , (short)20 );
    assertEquals( (short)10 , bIndex.getMin() );
    assertEquals( (short)20 , bIndex.getMax() );
    assertFalse( bIndex.merge( UnsupportedBlockIndex.INSTANCE ) );
  }

  @Test
  public void T_getBinarySize_1(){
    ShortRangeBlockIndex bIndex = new ShortRangeBlockIndex( (short)10 , (short)20 );
    assertEquals( 4 , bIndex.getBinarySize() );
  }

  @Test
  public void T_binary_1(){
    ShortRangeBlockIndex bIndex = new ShortRangeBlockIndex( (short)10 , (short)20 );
    byte[] binary = bIndex.toBinary();
    assertEquals( binary.length , bIndex.getBinarySize() );
    ShortRangeBlockIndex bIndex2 = new ShortRangeBlockIndex();
    assertEquals( Short.MAX_VALUE , bIndex2.getMin() );
    assertEquals( Short.MIN_VALUE , bIndex2.getMax() );
    bIndex2.setFromBinary( binary , 0 , binary.length );
    assertEquals( bIndex2.getMin() , bIndex.getMin() );
    assertEquals( bIndex2.getMax() , bIndex.getMax() );
  }

  @Test( dataProvider = "T_canBlockSkip_1" )
  public void T_canBlockSkip_1( final IBlockIndex bIndex , final IFilter filter , final boolean result ){
    if( result ){
      assertEquals( result , bIndex.getBlockSpreadIndex( filter ).isEmpty() );
    }
    else{
      assertTrue( bIndex.getBlockSpreadIndex( filter ) == null );
    }
  }

}
