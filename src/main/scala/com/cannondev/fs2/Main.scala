package com.cannondev.fs2

import cats.effect.IOApp
import com.cannondev.fs2.consumers.RandomNumbersConsumer
import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.syntax.*
import fs2.*
import scala.collection.immutable.Stream
import cats.effect.kernel.Async
import cats.effect.implicits.*
import cats.effect.Concurrent

import cats.syntax.all.toFunctorOps
import cats.implicits.toFlatMapOps
import com.cannondev.fs2.utils.Settings
object Main extends IOApp:
  def run(args: List[String]): cats.effect.IO[cats.effect.ExitCode] =
    start[IO].as(ExitCode.Success)

  def start[F[_]](using F: Async[F]) =
    given Settings[F] = Settings[F]
    val randomProducer = new RandomProducer[F]()
    val randomConsumer = new RandomNumbersConsumer[F]
    val app = for
      _ <- F.start(randomProducer.program.compile.drain)
      _ <- F.start(randomConsumer.stream.compile.drain)
      _ <- F.never
    yield ()
    app

    // app.parMapN((a, b) => ()).as(ExitCode.Success)
    // RandomProducer.program..merge[IO, IO].compile.drain.as(ExitCode.Success)
    // RandomProducer.program.con(RandomNumbersConsumer.stream)
    // val streams = List(RandomProducer.program, RandomNumbersConsumer.stream)
    // RandomProducer.program(RandomNumbersConsumer.stream).compile.drain.as(ExitCode.Success)
