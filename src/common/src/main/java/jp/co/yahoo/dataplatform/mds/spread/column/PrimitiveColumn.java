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
package jp.co.yahoo.dataplatform.mds.spread.column;

import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import java.util.stream.IntStream;

import jp.co.yahoo.dataplatform.schema.design.IField;
import jp.co.yahoo.dataplatform.schema.objects.PrimitiveObject;

import jp.co.yahoo.dataplatform.mds.inmemory.IMemoryAllocator;
import jp.co.yahoo.dataplatform.mds.spread.column.filter.IFilter;
import jp.co.yahoo.dataplatform.mds.spread.column.index.ICellIndex;
import jp.co.yahoo.dataplatform.mds.spread.expression.IExpressionIndex;

public class PrimitiveColumn implements IColumn{

  private final ColumnType columnType;
  private String columnName;
  private ICellManager cellManager;
  private IColumn parentsColumn = NullColumn.getInstance();
  private ICell defaultCell = NullCell.getInstance();

  public PrimitiveColumn( final ColumnType columnType , final String columnName ){
    this.columnType = columnType;
    this.columnName = columnName;
    cellManager = new CellManager();
  }

  @Override
  public void setColumnName( final String columnName ){
    this.columnName = columnName;
  }

  @Override
  public String getColumnName(){
    return columnName;
  }

  @Override
  public ColumnType getColumnType(){
    return columnType;
  }

  @Override
  public void setParentsColumn( final IColumn parentsColumn ){
    this.parentsColumn = parentsColumn;
  }

  @Override
  public IColumn getParentsColumn(){
    return parentsColumn;
  }

  @Override
  public int add( final ColumnType type , final Object obj , final int index ) throws IOException{
    if( type != getColumnType() ){
      throw new IOException( "Incorrect input data type : " + obj.getClass().getName() );
    }
    cellManager.add( new PrimitiveCell( columnType , (PrimitiveObject)obj ) , index );
    return ColumnTypeFactory.getColumnTypeToJavaPrimitiveByteSize( columnType , (PrimitiveObject)obj );
  }

  @Override
  public void addCell( final ColumnType type , final ICell cell , final int index ) throws IOException{
    cellManager.add( cell , index );
  }

  @Override
  public ICellManager getCellManager(){
    return cellManager;
  }

  @Override
  public void setCellManager( final ICellManager cellManager ){
    this.cellManager = cellManager;
  }

  @Override
  public ICell get( final int index ){
    return cellManager.get( index , defaultCell );
  }

  @Override
  public void setDefaultCell( final ICell defaultCell ){
    this.defaultCell = defaultCell;
  }

  @Override
  public int size(){
    return cellManager.size();
  }

  @Override
  public List<String> getColumnKeys(){
    return new ArrayList<String>();
  }

  @Override
  public int getColumnSize(){
    return 0;
  }

  @Override
  public List<IColumn> getListColumn(){
    return new ArrayList<IColumn>();
  }

  @Override
  public IColumn getColumn( final int index ){
    return NullColumn.getInstance();
  }

  @Override
  public IColumn getColumn( final String columnName ){
    return NullColumn.getInstance();
  }

  @Override
  public IColumn getColumn( final ColumnType type ){
    return NullColumn.getInstance();
  }

  @Override
  public IField getSchema() throws IOException {
    return getSchema( getColumnName() );
  }

  @Override
  public IField getSchema( final String schemaName ) throws IOException{
    return PrimitiveSchemaFactory.getSchema( getColumnType() , schemaName );
  }

  @Override
  public void setIndex( final ICellIndex index ){
    cellManager.setIndex( index );
  }

  @Override
  public boolean[] filter( final IFilter filter , final boolean[] filterArray ) throws IOException{
    return cellManager.filter( filter , filterArray );
  }

  @Override
  public PrimitiveObject[] getPrimitiveObjectArray( final IExpressionIndex indexList , final int start , final int length ){
    return cellManager.getPrimitiveObjectArray( indexList , start , length );
  }

  @Override
  public void setPrimitiveObjectArray( final IExpressionIndex indexList , final int start , final int length , final IMemoryAllocator allocator ){
    cellManager.setPrimitiveObjectArray( indexList , start , length , allocator );
  }

  @Override
  public String toString(){
    StringBuffer result = new StringBuffer();
    result.append( String.format( "Column name : %s\n" , getColumnName() ) );
    result.append( String.format( "Column type : %s\n" , getColumnType() ) );
    result.append( "--------------------------\n" );
    IntStream.range( 0 , size() )
      .forEach( i -> {
        result.append( String.format( "CELL-%d: %s\n" , i , get( i ).toString() ) );
      } );

    return result.toString();
  }

}
