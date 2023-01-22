package com.cannondev.fs2

import cats.effect.ExitCode
import fs2._
import cats.implicits.*
import cats.effect.implicits.*
import scala.util.Random
import fs2.kafka.KafkaProducer
import com.cannondev.fs2.utils.Settings
import fs2.kafka.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import cats.effect.Async

import scala.concurrent.duration.DurationInt
import org.apache.kafka.clients.producer.Producer

class RandomProducer[F[_]](using F: Async[F], settings: Settings[F]):

  val random = Random

  val randomNumbersStream = Stream
    .awakeEvery(2.seconds)
    .evalMap(_ => F.pure(random.nextInt(10)))
    .evalTap(r => F.pure(println(s"Produced $r")))
    .evalMap(r => F.pure(ProducerRecord("NumbersTopic", "", r)))

  private val producer = KafkaProducer.stream[F, String, Int](settings.intProducerSettings)

  val program = producer.flatMap(pr => randomNumbersStream.evalMap(r => pr.produceOne(r, "test")))
