package io.everis.akka.runners
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestProbe}
import io.everis.akka.actors.Greeter

import scala.concurrent.duration._
import scala.language.postfixOps
import io.everis.akka.actors.Greeter._
import io.everis.akka.actors.Printer._

class AkkaQuickstartSpec(_system: ActorSystem)
  extends TestKit(_system)
    with WordSpecLike
    with Matchers
    with BeforeAndAfterAll {

  def this() = this(ActorSystem("AkkaQuickstartSpec"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "A Greeter Actor" should {
    "pass on a greeting message when instructed to" in {
      val testProbe = TestProbe()

      val helloGreetingMessage = "hello"
      val helloGreeter = system.actorOf(Greeter.props(helloGreetingMessage, testProbe.ref))
      val greetPerson = "Akka"
      helloGreeter ! WhoToGreet(greetPerson)
      helloGreeter ! Greet
      testProbe.expectMsg(500 millis, Greeting(helloGreetingMessage + ", " + greetPerson))
    }
  }
}