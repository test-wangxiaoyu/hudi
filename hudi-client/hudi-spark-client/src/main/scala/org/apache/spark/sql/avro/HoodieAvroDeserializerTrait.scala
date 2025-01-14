/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.sql.avro

/**
 * Deserializes Avro payload into Catalyst object
 *
 * NOTE: This is low-level component operating on Spark internal data-types (comprising [[InternalRow]]).
 *       If you're looking to convert Avro into "deserialized" [[Row]] (comprised of Java native types),
 *       please check [[AvroConversionUtils]]
 */
trait HoodieAvroDeserializerTrait {
  final def deserialize(data: Any): Option[Any] =
    doDeserialize(data) match {
      case opt: Option[_] => opt    // As of Spark 3.1, this will return data wrapped with Option, so we fetch the data
      case row => Some(row)         // For other Spark versions, return the data as is
    }

  protected def doDeserialize(data: Any): Any
}
