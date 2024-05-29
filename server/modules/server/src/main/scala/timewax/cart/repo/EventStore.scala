package timewax.cart.repo

import zio.Console.printLine
import zio.IO

import java.io.IOException
import java.time.LocalDateTime

// FIXME proper implementation
// FIXME should be able to inject idea of now for testing
class EventStore {

  // FIXME possibly make event time part of event object
  def publish(event: Event): IO[IOException, Unit] = printLine(s"$event at ${LocalDateTime.now()}")

}
