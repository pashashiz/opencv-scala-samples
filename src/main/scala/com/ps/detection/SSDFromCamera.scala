package com.ps.detection

import com.ps.encodeImage
import org.bytedeco.javacv._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._

import java.util.concurrent.atomic.AtomicReference
import javax.swing.WindowConstants
import scala.collection.immutable.LazyList.continually
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Using}

case class DetectionResult(objects: Seq[DetectedObject], fps: Float)

object SSDFromCamera {

  def main(args: Array[String]): Unit = {

    val client = SSDRestClient.async(SSDRestClient.Config(url = "http://127.0.0.1:8000"))

    val grabber = new FFmpegFrameGrabber("0:0")
    grabber.setFormat("avfoundation")
    grabber.setFrameRate(30)
    grabber.setImageWidth(1280)
    grabber.setImageHeight(720)
    grabber.setVideoOption("probesize", "10000000")
    grabber.start()

    val canvasFrame = new CanvasFrame("Video", 1)
    canvasFrame.setCanvasSize(grabber.getImageWidth, grabber.getImageHeight)
    canvasFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    val white = new Scalar(255.0, 255.0, 255.0, 0)

    val lastDetections = new AtomicReference[DetectionResult]
    val isDetecting = new AtomicReference[Boolean](false)

    val delay = math.round(1000d / grabber.getFrameRate)
    for (frame <- continually(grabber.grab()).takeWhile(_ != null)) {
      if (canvasFrame.isVisible && frame.imageWidth > 0) {
        val outputFrame = Using.resource(new OpenCVFrameConverter.ToMat()) { frameConverter =>
          val input = frameConverter.convert(frame)

          if (!isDetecting.get()) {
            isDetecting.set(true)
            val jpg = encodeImage(input)
            val start = System.currentTimeMillis()
            client.detectImage(jpg).onComplete {
              case Success(objects) =>
                val end = System.currentTimeMillis()
                val duration = (end - start).toFloat / 1000
                val fps = 1.0f / duration
                println(s"[FPS: $fps] Detected: $objects")
                lastDetections.set(DetectionResult(objects, fps))
                isDetecting.set(false)
              case Failure(e) =>
                println("Failed: " + e)
                isDetecting.set(false)
            }
          }
          Option(lastDetections.get()) match {
            case Some(detections) =>
              val p0 = new Point(0, 30)
              val text = s"FPS:${detections.fps}"
              putText(input, text, p0, FONT_HERSHEY_SIMPLEX, 1, white, 2, LINE_AA, false)
              detections.objects.foreach { (detection: DetectedObject) =>
                val bbox = detection.bbox
                val colls = input.cols()
                val rows = input.rows()
                val p1 = new Point((bbox(1) * colls).toInt, (bbox(0) * rows).toInt)
                val p2 = new Point((bbox(3) * colls).toInt, (bbox(2) * rows).toInt)
                rectangle(input, p1, p2, white, 2, LINE_8, 0)
                val label = s"${detection.label}:${detection.score}"
                putText(input, label, p1, FONT_HERSHEY_SIMPLEX, 1, white, 2, LINE_AA, false)
              }
            case None => ()
          }
          frameConverter.convert(input)
        }
        canvasFrame.showImage(outputFrame)
        Thread.sleep(delay)
      }
    }

    grabber.release()
  }
}
