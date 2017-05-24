/*
 * This is a simplified version of the tutorial made available at
 * thrift.apache.org.
 * Changes made by <ronald.moore@h-da.de>.
 *
 * Original Thrift Tutorial
 * by  Mark Slee (mcslee@facebook.com)
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


/**
 * The available types in Thrift are:
 *
 *  bool        Boolean, one byte
 *  i8 (byte)   Signed 8-bit integer
 *  i16         Signed 16-bit integer
 *  i32         Signed 32-bit integer
 *  i64         Signed 64-bit integer
 *  double      64-bit floating point value
 *  string      String
 *  binary      Blob (byte array)
 *  map<t1,t2>  Map from one type to another
 *  list<t1>    Ordered list of one type
 *  set<t1>     Set of unique elements of one type
 *
 */

/**
 * Thrift files can namespace, package, or prefix their output in various
 * target languages.
 */

namespace java de.sbuettner.vs.praktikum







/**
 * Ahh, now onto the cool part, defining a service. Services just need a name.
 */
service ShopService{

   double getPrice(1:string article),

   bool order(1:string article, 2:i32 amount),

}

