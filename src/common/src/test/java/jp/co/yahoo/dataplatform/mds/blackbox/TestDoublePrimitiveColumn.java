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
package jp.co.yahoo.dataplatform.mds.blackbox;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import jp.co.yahoo.dataplatform.config.Configuration;

import jp.co.yahoo.dataplatform.schema.objects.*;

import jp.co.yahoo.dataplatform.mds.spread.expression.*;
import jp.co.yahoo.dataplatform.mds.spread.column.filter.*;
import jp.co.yahoo.dataplatform.mds.spread.column.*;
import jp.co.yahoo.dataplatform.mds.binary.*;
import jp.co.yahoo.dataplatform.mds.binary.maker.*;

public class TestDoublePrimitiveColumn {

  @DataProvider(name = "target_class")
  public Object[][] data1() throws IOException{
    return new Object[][] {
      { "jp.co.yahoo.dataplatform.mds.binary.maker.DumpDoubleColumnBinaryMaker" },
      { "jp.co.yahoo.dataplatform.mds.binary.maker.RangeDumpDoubleColumnBinaryMaker" },
      { "jp.co.yahoo.dataplatform.mds.binary.maker.OptimizeDoubleColumnBinaryMaker" },
    };
  }

  public IColumn createNotNullColumn( final String targetClassName ) throws IOException{
    IColumn column = new PrimitiveColumn( ColumnType.DOUBLE , "column" );
    column.add( ColumnType.DOUBLE , new DoubleObj( Double.MAX_VALUE ) , 0 );
    column.add( ColumnType.DOUBLE , new DoubleObj( Double.MIN_VALUE ) , 1 );
    column.add( ColumnType.DOUBLE , new DoubleObj( -200.0d ) , 2 );
    column.add( ColumnType.DOUBLE , new DoubleObj( -300.1d ) , 3 );
    column.add( ColumnType.DOUBLE , new DoubleObj( -400.2d ) , 4 );
    column.add( ColumnType.DOUBLE , new DoubleObj( -500.3d ) , 5 );
    column.add( ColumnType.DOUBLE , new DoubleObj( -600.4d ) , 6 );
    column.add( ColumnType.DOUBLE , new DoubleObj( 700.5d ) , 7 );
    column.add( ColumnType.DOUBLE , new DoubleObj( 800.6d ) , 8 );
    column.add( ColumnType.DOUBLE , new DoubleObj( 900.7d ) , 9 );
    column.add( ColumnType.DOUBLE , new DoubleObj( 0.0d ) , 10 );

    IColumnBinaryMaker maker = FindColumnBinaryMaker.get( targetClassName );
    ColumnBinaryMakerConfig defaultConfig = new ColumnBinaryMakerConfig();
    ColumnBinaryMakerCustomConfigNode configNode = new ColumnBinaryMakerCustomConfigNode( "root" , defaultConfig );
    ColumnBinary columnBinary = maker.toBinary( defaultConfig , null , column );
    return FindColumnBinaryMaker.get( columnBinary.makerClassName ).toColumn( columnBinary );
  }

  public IColumn createNullColumn( final String targetClassName ) throws IOException{
    IColumn column = new PrimitiveColumn( ColumnType.DOUBLE , "column" );

    IColumnBinaryMaker maker = FindColumnBinaryMaker.get( targetClassName );
    ColumnBinaryMakerConfig defaultConfig = new ColumnBinaryMakerConfig();
    ColumnBinaryMakerCustomConfigNode configNode = new ColumnBinaryMakerCustomConfigNode( "root" , defaultConfig );
    ColumnBinary columnBinary = maker.toBinary( defaultConfig , null , column );
    return  FindColumnBinaryMaker.get( columnBinary.makerClassName ).toColumn( columnBinary );
  }

  public IColumn createHasNullColumn( final String targetClassName ) throws IOException{
    IColumn column = new PrimitiveColumn( ColumnType.DOUBLE , "column" );
    column.add( ColumnType.DOUBLE , new DoubleObj( (double)0 ) , 0 );
    column.add( ColumnType.DOUBLE , new DoubleObj( (double)4 ) , 4 );
    column.add( ColumnType.DOUBLE , new DoubleObj( (double)8 ) , 8 );

    IColumnBinaryMaker maker = FindColumnBinaryMaker.get( targetClassName );
    ColumnBinaryMakerConfig defaultConfig = new ColumnBinaryMakerConfig();
    ColumnBinaryMakerCustomConfigNode configNode = new ColumnBinaryMakerCustomConfigNode( "root" , defaultConfig );
    ColumnBinary columnBinary = maker.toBinary( defaultConfig , null , column );
    return FindColumnBinaryMaker.get( columnBinary.makerClassName ).toColumn( columnBinary );
  }

  public IColumn createLastCellColumn( final String targetClassName ) throws IOException{
    IColumn column = new PrimitiveColumn( ColumnType.DOUBLE , "column" );
    column.add( ColumnType.DOUBLE , new DoubleObj( Double.MAX_VALUE ) , 10000 );

    IColumnBinaryMaker maker = FindColumnBinaryMaker.get( targetClassName );
    ColumnBinaryMakerConfig defaultConfig = new ColumnBinaryMakerConfig();
    ColumnBinaryMakerCustomConfigNode configNode = new ColumnBinaryMakerCustomConfigNode( "root" , defaultConfig );
    ColumnBinary columnBinary = maker.toBinary( defaultConfig , null , column );
    return FindColumnBinaryMaker.get( columnBinary.makerClassName ).toColumn( columnBinary );
  }

  @Test( dataProvider = "target_class" )
  public void T_notNull_1( final String targetClassName ) throws IOException{
    IColumn column = createNotNullColumn( targetClassName );
    assertEquals( ( (PrimitiveObject)( column.get(0).getRow() ) ).getDouble() , Double.MAX_VALUE );
    assertEquals( ( (PrimitiveObject)( column.get(1).getRow() ) ).getDouble() , Double.MIN_VALUE );
    assertEquals( ( (PrimitiveObject)( column.get(2).getRow() ) ).getDouble() , -200.0d );
    assertEquals( ( (PrimitiveObject)( column.get(3).getRow() ) ).getDouble() , -300.1d );
    assertEquals( ( (PrimitiveObject)( column.get(4).getRow() ) ).getDouble() , -400.2d );
    assertEquals( ( (PrimitiveObject)( column.get(5).getRow() ) ).getDouble() , -500.3d );
    assertEquals( ( (PrimitiveObject)( column.get(6).getRow() ) ).getDouble() , -600.4d );
    assertEquals( ( (PrimitiveObject)( column.get(7).getRow() ) ).getDouble() , 700.5d );
    assertEquals( ( (PrimitiveObject)( column.get(8).getRow() ) ).getDouble() , 800.6d );
    assertEquals( ( (PrimitiveObject)( column.get(9).getRow() ) ).getDouble() , 900.7d );
    assertEquals( ( (PrimitiveObject)( column.get(10).getRow() ) ).getDouble() , 0.0d );
  }

  @Test( dataProvider = "target_class" )
  public void T_null_1( final String targetClassName ) throws IOException{
    IColumn column = createNullColumn( targetClassName );
    assertNull( column.get(0).getRow() );
    assertNull( column.get(1).getRow() );
  }

  @Test( dataProvider = "target_class" )
  public void T_hasNull_1( final String targetClassName ) throws IOException{
    IColumn column = createHasNullColumn( targetClassName );
    assertEquals( ( (PrimitiveObject)( column.get(0).getRow() ) ).getDouble() , (double)0 );
    assertNull( column.get(1).getRow() );
    assertNull( column.get(2).getRow() );
    assertNull( column.get(3).getRow() );
    assertEquals( ( (PrimitiveObject)( column.get(4).getRow() ) ).getDouble() , (double)4 );
    assertNull( column.get(5).getRow() );
    assertNull( column.get(6).getRow() );
    assertNull( column.get(7).getRow() );
    assertEquals( ( (PrimitiveObject)( column.get(8).getRow() ) ).getDouble() , (double)8 );
  }

  @Test( dataProvider = "target_class" )
  public void T_lastCell_1( final String targetClassName ) throws IOException{
    IColumn column = createLastCellColumn( targetClassName );
    for( int i = 0 ; i < 10000 ; i++ ){
      assertNull( column.get(i).getRow() );
    }
    assertEquals( ( (PrimitiveObject)( column.get(10000).getRow() ) ).getDouble() , Double.MAX_VALUE );
  }

}
