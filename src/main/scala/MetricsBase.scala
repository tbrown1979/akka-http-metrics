package backline.http.metrics
import com.codahale.metrics._
import java.util.Iterator
import akka.actor.Status.Failure
import akka.http.scaladsl.server.{Directive0, RequestContext}
import akka.http.scaladsl.server.directives.BasicDirectives
import scala.util.control.NonFatal

trait MetricsBase extends BasicDirectives {
  def metricRegistry: MetricRegistry

  protected def findAndRegisterTimer(name: String): Timer = {
    val found = metricRegistry.getTimers(new NameBasedMetricFilter(name)).values.iterator
    findAndRegisterMetric(name, new Timer(), found)
  }

  protected def findAndRegisterCounter(name: String): Counter = {
    val found = metricRegistry.getCounters(new NameBasedMetricFilter(name)).values.iterator
    findAndRegisterMetric(name, new Counter(), found)
  }

  protected def findAndRegisterMeter(name: String): Meter = {
    val found = metricRegistry.getMeters(new NameBasedMetricFilter(name)).values.iterator
    findAndRegisterMetric(name, new Meter(), found)
  }

  protected def getMetricName(ctx: RequestContext): String = {
    val methodName = ctx.request.method.name
    val routeName = ctx.request.uri.path.toString.drop(1).replaceAll("/", ".")
    s"${routeName}.${methodName}"
  }

  private[this] def findAndRegisterMetric[T <: Metric](name: String, metric: => T, found: Iterator[T]): T = {
    if (found != null && found.hasNext()) {
      found.next()
    } else {
      val m = metric
      metricRegistry.register(name, m)
      m
    }
  }

  protected class NameBasedMetricFilter(needle: String) extends MetricFilter {
    def matches(name: String, metric: Metric): Boolean = name equalsIgnoreCase needle
  }
}
