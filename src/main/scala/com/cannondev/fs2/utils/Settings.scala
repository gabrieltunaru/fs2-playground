package com.cannondev.fs2.utils

import fs2.kafka.*
import cats.effect.IO

object Settings:
  val intComsumerSettings = ConsumerSettings[IO, String, Int]
    .withAutoOffsetReset(AutoOffsetReset.Earliest)
    .withGroupId("RandomNumbers")
    .withBootstrapServers("localhost:9092")

  val intProducerSettings =
    ProducerSettings[IO, String, Int]
      .withBootstrapServers("localhost:9092")
