/*
 * Copyright 2012 Pellucid and Zenexity
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package datomisca

import scala.util.{Try, Success, Failure}


class DEntity(val entity: datomic.Entity) extends DatomicData {
  def toNative = entity

  def id: Long = as[Long](Namespace.DB / "id")

  def touch() = {
    entity.touch()
    this
  }

  def apply(keyword: Keyword): DatomicData =
    get(keyword) getOrElse { throw new EntityKeyNotFoundException(keyword) }

  def get(keyword: Keyword): Option[DatomicData] =
    Option {
      entity.get(keyword.toNative)
    } map (Datomic.toDatomicData(_))

  def as[T](keyword: Keyword)(implicit fdat: FromDatomicCast[T]): T =
    fdat.from(apply(keyword))

  
  def getAs[T](keyword: Keyword)(implicit fdat: FromDatomicCast[T]): Option[T] =
    get(keyword) map (fdat.from(_))

  def keySet: Set[Keyword] = {
    import scala.collection.JavaConverters._

    entity.keySet.asScala.view.map{ key: Any =>
      Keyword(key.asInstanceOf[clojure.lang.Keyword])
    }.toSet
  }

  def toMap: Map[Keyword, DatomicData] =
    keySet.view.map{ key: Keyword =>
      (key -> apply(key))
    }.toMap

}

object DEntity {
  def apply(ent: datomic.Entity) = new DEntity(ent)
}
