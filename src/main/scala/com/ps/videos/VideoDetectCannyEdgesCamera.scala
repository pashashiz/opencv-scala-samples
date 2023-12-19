package com.ps.videos

import org.bytedeco.javacv._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._

import javax.swing.WindowConstants
import scala.collection.immutable.LazyList.continually
import scala.util.Using

object VideoDetectCannyEdgesCamera {

  def main(args: Array[String]): Unit = {

    val grabber = new FFmpegFrameGrabber("0:0")
    grabber.setFormat("avfoundation")
    grabber.setFrameRate(30)
    grabber.setImageWidth(640)
    grabber.setImageHeight(480)
    grabber.setVideoOption("probesize", "10000000")
    grabber.start()

    val canvasFrame = new CanvasFrame("Video", 1)
    canvasFrame.setCanvasSize(grabber.getImageWidth, grabber.getImageHeight)
    canvasFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    val delay = math.round(1000d / grabber.getFrameRate)
    for (frame <- continually(grabber.grab()).takeWhile(_ != null)) {
      if (canvasFrame.isVisible && frame.imageWidth > 0) {
        val outputFrame = Using.resource(new OpenCVFrameConverter.ToMat()) { frameConverter =>
          val input = frameConverter.convert(frame)
          val output = new Mat()
          cvtColor(input, output, COLOR_BGR2GRAY)
          Canny(output, output, 100, 200, 3, true)
          threshold(output, output, 128, 255, THRESH_BINARY_INV)
          frameConverter.convert(output)
        }
        canvasFrame.showImage(outputFrame)
        Thread.sleep(delay)
      }
    }

    grabber.release()
  }
}
