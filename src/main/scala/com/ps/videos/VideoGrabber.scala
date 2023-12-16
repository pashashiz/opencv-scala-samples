package com.ps.videos

import org.bytedeco.javacv.{CanvasFrame, FFmpegFrameGrabber}

import javax.swing.WindowConstants
import scala.collection.immutable.LazyList.continually

object VideoGrabber {

  def main(args: Array[String]): Unit = {

    val screenSize = 0.5

    // get stream from web camera
    val grabber = new FFmpegFrameGrabber("0")
    grabber.setFormat("avfoundation")
    grabber.setFrameRate(30)
    grabber.setVideoOption("probesize", "10000000")
    grabber.start()

    // Prepare window to display frames
    val canvasFrame = new CanvasFrame("Video", 1)
    canvasFrame.setCanvasSize(
      (grabber.getImageWidth * screenSize).toInt,
      (grabber.getImageHeight * screenSize).toInt)
    // Exit the example when the canvas frame is closed
    canvasFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    val delay = math.round(1000d / grabber.getFrameRate)
    // Read frame by frame, stop early if the display window is closed
    for (frame <- continually(grabber.grab()).takeWhile(_ != null)
      if canvasFrame.isVisible) {
      // Capture and show the frame
      canvasFrame.showImage(frame)
      // Delay
      Thread.sleep(delay)
    }

    // Close the video file
    grabber.release()
  }
}
