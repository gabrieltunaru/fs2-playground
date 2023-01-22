package com.cannondev.fs2.utils

import fs2.kafka.*
import cats.effect.IO
import cats.effect.kernel.Async

class Settings[F[_]: Async]:
  val intComsumerSettings = ConsumerSettings[F, String, Int]
    .withAutoOffsetReset(AutoOffsetReset.Earliest)
    .withGroupId("RandomNumbers")
    .withBootstrapServers("localhost:9092")

  val intProducerSettings =
    ProducerSettings[F, String, Int]
      .withBootstrapServers("localhost:9092")

object Settings:
  def apply[F[_]: Async] = new Settings[F]
