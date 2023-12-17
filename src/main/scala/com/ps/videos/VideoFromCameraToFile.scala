package com.ps.videos

import org.bytedeco.javacv.{CanvasFrame, FFmpegFrameGrabber, FFmpegFrameRecorder}

import javax.swing.WindowConstants
import scala.collection.immutable.LazyList.continually

object VideoFromCameraToFile {

  def main(args: Array[String]): Unit = {

    // get stream from web camera
    val grabber = new FFmpegFrameGrabber("0:0")
    grabber.setFormat("avfoundation")
    grabber.setFrameRate(30)
    grabber.setImageWidth(640)
    grabber.setImageHeight(480)
    grabber.setVideoOption("probesize", "10000000")
    grabber.start()

    println(s"Video Codec: ${grabber.getVideoCodec}")
    println(s"Audio Channels: ${grabber.getAudioChannels}")
    println(s"Audio Codec: ${grabber.getAudioCodec}")

    // Prepare window to display frames
    val canvasFrame = new CanvasFrame("Video", 1)
    canvasFrame.setCanvasSize(grabber.getImageWidth, grabber.getImageHeight)
    // Exit the example when the canvas frame is closed
    canvasFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    // File to write
    // mp4 doesn't support streaming use avi or mkv
    val recorder =
      new FFmpegFrameRecorder("target/camera.avi", grabber.getImageWidth, grabber.getImageHeight, 2)
    recorder.setFrameRate(grabber.getFrameRate)
    recorder.start()

    // todo: for some reason audio is not written, even though that is present in grabber and samples are saved
    val delay = math.round(1000d / grabber.getFrameRate)
    // Read frame by frame, stop early if the display window is closed
    for (out <- continually(grabber.grab())
        .zipWithIndex
        .takeWhile { case (frame, index) => frame != null & index < 30 * 5 }) {
      val (frame, _) = out
      if (canvasFrame.isVisible) {
        // Capture and show the frame
        canvasFrame.showImage(frame)
        // write frame
        recorder.record(frame)
        // Delay
        Thread.sleep(delay)
      } else {
        println("invisible!")
      }
    }

    println("release!")
    // Close the video file
    grabber.release()
    recorder.flush()
    recorder.release()
  }
}
