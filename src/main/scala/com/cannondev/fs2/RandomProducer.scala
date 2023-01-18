package com.cannondev.fs2

import cats.effect.IOApp
import cats.effect.IO
import cats.effect.ExitCode
import fs2.Stream
import cats.implicits.*
import cats.effect.implicits.*
import concurrent.duration.DurationInt
import scala.util.Random

object RandomProducer extends IOApp:

  val random = Random

  val randomNumbersStream = Stream
    .awakeEvery[IO](2.seconds)
    .evalMap(_ => IO.pure(random.nextInt(10)))
    .evalTap(r => IO.pure(println(r)))

  def run(args: List[String]): IO[ExitCode] =
    println("Starting random numbers producer")
    randomNumbersStream.compile.drain.as(ExitCode.Success)
