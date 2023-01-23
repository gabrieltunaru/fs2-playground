package com.cannondev.fs2.consumers

import fs2.kafka.KafkaConsumer
import com.cannondev.fs2.utils.Settings
import cats.effect.kernel.Async
import cats.effect.implicits.*
import fs2.*
import cats.syntax.all.*

class RandomNumbersConsumer[F[_]](using F: Async[F], settings: Settings[F]):

  def stream = KafkaConsumer
    .stream(settings.intComsumerSettings)
    .subscribeTo("NumbersTopic")
    .records
    .evalTap(r => F.pure(println(s"Consumed ${r.record.value}")))
