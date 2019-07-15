package io.everis.akka.runners

import akka.actor.{ActorSystem, Props}
import akka.management.cluster.bootstrap.ClusterBootstrap
import akka.management.scaladsl.AkkaManagement
import com.typesafe.config.ConfigFactory
import io.everis.akka.cluster.SimpleClusterListener
import kamon.Kamon
import kamon.influxdb.InfluxDBReporter
import kamon.jaeger.JaegerReporter
import kamon.prometheus.PrometheusReporter
import kamon.system.SystemMetrics
import kamon.zipkin.ZipkinReporter

object SimpleClusterApp {
  def main(args: Array[String]): Unit = {
    SystemMetrics.startCollecting()
    Kamon.addReporter(new PrometheusReporter())
//    Kamon.addReporter(new ZipkinReporter())
//    Kamon.addReporter(new JaegerReporter())
//    Kamon.addReporter(new InfluxDBReporter())
    if (args.isEmpty)
      startup(Seq("2551", "2552", "0"))
    else
      startup(Seq("2551", "2552", "0")) // startup(args)
    SystemMetrics.stopCollecting()
  }

  def startup(ports: Seq[String]): Unit = {
     ports foreach { port =>
      // Override the configuration of the port
      // To use artery instead of netty, change to "akka.remote.artery.canonical.port"
      // See https://doc.akka.io/docs/akka/current/remoting-artery.html for details
      val config = ConfigFactory.parseString(s"""
        akka.remote.netty.tcp.port=$port
        """).withFallback(ConfigFactory.load())

      // Create an Akka system
      val system = ActorSystem("ClusterSystem", config)
      // Create an actor that handles cluster domain events
//       AkkaManagement(system).start()
//       ClusterBootstrap(system).start() //Ensure that seed-nodes is not present in configuration and that start() is called on every node.
      system.actorOf(Props[SimpleClusterListener], name = "clusterListener")
    }
  }

}
