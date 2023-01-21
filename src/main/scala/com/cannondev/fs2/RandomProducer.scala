package com.cannondev.fs2

import cats.effect.IOApp
import cats.effect.IO
import cats.effect.ExitCode
import fs2.Stream
import cats.implicits.*
import cats.effect.implicits.*
import concurrent.duration.DurationInt
import scala.util.Random
import fs2.kafka.KafkaProducer
import com.cannondev.fs2.utils.Settings
import fs2.kafka.ProducerRecord

object RandomProducer extends IOApp:

  val random = Random

  val randomNumbersStream = Stream
    .awakeEvery[IO](2.seconds)
    .evalMap(_ => IO.pure(random.nextInt(10)))
    .evalTap(r => IO.pure(println(s"Produced $r")))
    .map(r => ProducerRecord("NumbersTopic", "", r))

  private val producer = KafkaProducer.stream[IO, String, Int](Settings.intProducerSettings)

  val program = for
    randomNumber <- randomNumbersStream
    p <- producer
  yield p.produceOne_(randomNumber)

  def run(args: List[String]): IO[ExitCode] =
    println("Starting random numbers producer")
    program.compile.drain.as(ExitCode.Success)
