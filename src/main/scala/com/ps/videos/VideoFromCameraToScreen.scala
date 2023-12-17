package com.ps.videos

import org.bytedeco.javacv.{CanvasFrame, FFmpegFrameGrabber}

import javax.swing.WindowConstants
import scala.collection.immutable.LazyList.continually

import org.bytedeco.opencv.global.opencv_videoio._
import org.bytedeco.opencv.global.opencv_video._

object VideoFromCameraToScreen {

  def main(args: Array[String]): Unit = {

    val screenSize = 1

    // get stream from web camera
    val grabber = new FFmpegFrameGrabber("0")
    grabber.setFormat("avfoundation")
    grabber.setFrameRate(30)
    grabber.setImageWidth(640)
    grabber.setImageHeight(480)
    grabber.setVideoOption("probesize", "10000000")
    grabber.start()

    // org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_RAWVIDEO = 13
    println(s"Video Codec: ${grabber.getVideoCodec}")
    println(s"Audio Codec: ${grabber.getAudioCodec}")

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
