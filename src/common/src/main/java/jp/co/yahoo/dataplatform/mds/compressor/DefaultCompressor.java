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
package jp.co.yahoo.dataplatform.mds.compressor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;

public class DefaultCompressor implements ICompressor{

  @Override
  public byte[] compress( final byte[] data , final int start , final int length ) throws IOException{
    byte[] result = new byte[length];
    System.arraycopy( data , start , result , 0 , length );
    return result;
  }

  @Override
  public void compress( final byte[] data , final int start , final int length , final OutputStream out ) throws IOException{
    out.write( data , start , length );
  }

  @Override
  public int getDecompressSize( final byte[] data , final int start , final int length ) throws IOException {
    return length;
  }

  @Override
  public byte[] decompress( final byte[] data , final int start , final int length ) throws IOException{
    byte[] result = new byte[length];
    System.arraycopy( data , start , result , 0 , length );
    return data;
  }

  @Override
  public int decompressAndSet( final byte[] data , final int start , final int length , final byte[] buffer ) throws IOException{
    System.arraycopy( data , start , buffer , 0 , length );
    return length;
  }

  @Override
  public InputStream getDecompressInputStream( final byte[] data , final int start , final int length ) throws IOException{
    return new ByteArrayInputStream( data , start , length );
  }

}
