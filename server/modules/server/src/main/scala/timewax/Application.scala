package timewax

import sttp.tapir.server.ziohttp.{ZioHttpInterpreter, ZioHttpServerOptions}
import timewax.cart.http.CartController
import timewax.cart.repo.CartRepoInMemory
import timewax.cart.services.CartServiceDemo
import zio._
import zio.config.typesafe.FromConfigSourceTypesafe
import zio.http.Server

object Application extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = {
    // use typesafe config at src/main/resources/application.conf
    Runtime.setConfigProvider(ConfigProvider.fromResourcePath())
  }

  private def startServer = for {
    controller <- CartController.makeZIO
    httpApp = ZioHttpInterpreter(
      ZioHttpServerOptions.default
    ).toHttp(controller.routes)
    _ <- Server.serve(httpApp)
  } yield ()

  private def program =
    for {
      _ <- ZIO.log("Starting...")
      _ <- startServer
    } yield ()

  override def run: ZIO[Any, Throwable, Unit] = program.provide(
    // infra
    //   load typesafe config at src/main/resources/application.conf
    Server.configured(),
    // repos
    CartRepoInMemory.layer,
    // services
    CartServiceDemo.layer
  )
}
