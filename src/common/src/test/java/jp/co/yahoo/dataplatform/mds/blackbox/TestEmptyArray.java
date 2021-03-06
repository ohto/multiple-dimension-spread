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
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import jp.co.yahoo.dataplatform.mds.spread.Spread;
import jp.co.yahoo.dataplatform.mds.spread.column.ArrayCell;
import jp.co.yahoo.dataplatform.mds.spread.column.ColumnType;
import jp.co.yahoo.dataplatform.mds.spread.column.IColumn;
import org.testng.annotations.Test;

import jp.co.yahoo.dataplatform.schema.objects.PrimitiveObject;
import jp.co.yahoo.dataplatform.schema.parser.IParser;
import jp.co.yahoo.dataplatform.schema.parser.JacksonMessageReader;
import jp.co.yahoo.dataplatform.config.Configuration;
import jp.co.yahoo.dataplatform.mds.MDSWriter;
import jp.co.yahoo.dataplatform.mds.MDSReader;

public class TestEmptyArray{


  @Test
  public void T_1() throws IOException{
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Configuration config = new Configuration();
    JacksonMessageReader messageReader = new JacksonMessageReader();
    BufferedReader in = new BufferedReader( new InputStreamReader( this.getClass().getClassLoader().getResource( "blackbox/TestEmptyArray.json" ).openStream() ) );
    String line = in.readLine();
    Spread writeSpread = new Spread();
    try(MDSWriter writer = new MDSWriter( out , config )) {
      while (line != null) {
        IParser parser = messageReader.create(line);
        writeSpread.addParserRow(parser);
        line = in.readLine();
      }
      writer.append(writeSpread);
    }
    IColumn writeArrayColumn = writeSpread.getColumn( "array1" );
    System.err.println( writeSpread.toString() );
    assertEquals( writeArrayColumn.getColumnType() , ColumnType.ARRAY );
    assertEquals( writeArrayColumn.size() , 1 );
    ArrayCell arrayCell = (ArrayCell)( writeArrayColumn.get(0) );
    System.err.println( writeArrayColumn.toString() );
    assertEquals( arrayCell.getStart() , 0 );
    assertEquals( arrayCell.getEnd() , 4 );
    IColumn writeSpreadColumn = writeArrayColumn.getColumn( 0 );
    System.err.println( writeSpreadColumn.toString() );
    assertEquals( writeSpreadColumn.getColumnType() , ColumnType.SPREAD );
    assertEquals( writeSpreadColumn.size() , 2 );

    try(MDSReader reader = new MDSReader()) {
      Configuration readerConfig = new Configuration();
      byte[] data = out.toByteArray();
      InputStream fileIn = new ByteArrayInputStream(data);
      reader.setNewStream(fileIn, data.length, readerConfig);
      while (reader.hasNext()) {
        Spread spread = reader.next();
        IColumn unionColumn = spread.getColumn("array1");
        assertEquals(unionColumn.getColumnType(), ColumnType.ARRAY);
        assertEquals(unionColumn.size(), 1);
        IColumn spreadColumn = unionColumn.getColumn(0);
        assertEquals(spreadColumn.getColumnType(), ColumnType.SPREAD);
        assertEquals(spreadColumn.size(), 2);
      }
    }
  }

  @Test
  public void T_2() throws IOException{
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Configuration config = new Configuration();
    try(MDSWriter writer = new MDSWriter( out , config )) {
      JacksonMessageReader messageReader = new JacksonMessageReader();
      BufferedReader in = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResource("blackbox/TestEmptyArray.json").openStream()));
      String line = in.readLine();
      Spread writeSpread = new Spread();
      while (line != null) {
        IParser parser = messageReader.create(line);
        writeSpread.addParserRow(parser);
        line = in.readLine();
      }
      writer.append(writeSpread);
    }

    try(MDSReader reader = new MDSReader()) {
      Configuration readerConfig = new Configuration();
      readerConfig.set("spread.reader.expand.column", "{ \"base\" : { \"node\" : \"array1\" , \"link_name\" : \"expand_array1\" } }");
      byte[] data = out.toByteArray();
      InputStream fileIn = new ByteArrayInputStream(data);
      reader.setNewStream(fileIn, data.length, readerConfig);
      while (reader.hasNext()) {
        Spread spread = reader.next();
        IColumn spreadColumn = spread.getColumn("expand_array1");
        assertEquals(spreadColumn.getColumnType(), ColumnType.SPREAD);
        IColumn stringColumn = spreadColumn.getColumn("aa");
        assertEquals(2, spreadColumn.size());
        assertEquals(2, stringColumn.size());

        assertEquals(1, ((PrimitiveObject) (stringColumn.get(0).getRow())).getInt());
      }
    }
  }

}
