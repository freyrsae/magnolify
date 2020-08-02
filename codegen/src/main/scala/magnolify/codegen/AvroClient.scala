/*
 * Copyright 2020 Spotify AB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package magnolify.codegen

import java.io.File

import org.apache.avro.Schema
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.generic.GenericRecord
import org.apache.avro.file.DataFileReader
import org.apache.avro.reflect.ReflectData

object AvroClient {
  def fromAvsc(avsc: String): Schema = new Schema.Parser().parse(new File(avsc))

  def fromAvro(avro: String): Schema = {
    val reader = DataFileReader.openReader(new File(avro), new GenericDatumReader[GenericRecord]())
    val schema = reader.getSchema
    reader.close()
    schema
  }

  def fromClass(cls: String): Schema =
    ReflectData.get().getSchema(getClass.getClassLoader.loadClass(cls))
}
