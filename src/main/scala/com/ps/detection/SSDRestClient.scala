package com.ps.detection

import org.json4s.DefaultFormats
import org.json4s.native.Serialization
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend
import sttp.client3.{SttpBackend, _}
import sttp.client3.json4s.asJson
import sttp.client3.logging.LogLevel
import sttp.client3.logging.slf4j.Slf4jLoggingBackend

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.{DurationInt, FiniteDuration}

case class DetectedObject(label: String, score: Float, bbox: Seq[Float])

class SSDRestClient(config: SSDRestClient.Config)(implicit backend: SttpBackend[Future, Any]) {

  implicit private val formats = DefaultFormats
  implicit private val serialization = Serialization

  def detectImage(img: Array[Byte]): Future[Seq[DetectedObject]] = {
    basicRequest
      .post(uri"${config.url}/image/detect")
      .body(img)
      .header("Content-Type", "application/octet-stream")
      .readTimeout(config.timeout)
      .response(asJson[Seq[DetectedObject]])
      .send(backend)
      .flatMap { resp =>
        resp.body match {
          case Left(e)       => Future.failed(new RuntimeException(e))
          case Right(result) => Future.successful(result)
        }
      }
  }
}

object SSDRestClient {

  case class Config(url: String, timeout: FiniteDuration = 15.seconds)

  def async(config: Config): SSDRestClient =
    new SSDRestClient(config)(
      Slf4jLoggingBackend(
        AsyncHttpClientFutureBackend(),
        logRequestBody = true,
        logResponseBody = true,
        responseLogLevel = code => if (code.isServerError) LogLevel.Warn else LogLevel.Debug))
}
