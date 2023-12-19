package com.ps.videos

import org.bytedeco.ffmpeg.global.avcodec._
import org.bytedeco.javacv._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._

import scala.collection.immutable.LazyList.continually
import scala.util.Using

object VideoDetectCannyEdges {

  def main(args: Array[String]): Unit = {

    val grabber = new FFmpegFrameGrabber("data/hand_move.mp4")
    grabber.start()

    val recorder =
      new FFmpegFrameRecorder(
        "target/hand_move_greyscale_canny_edges.mp4",
        grabber.getImageWidth,
        grabber.getImageHeight,
        grabber.getAudioChannels)
    recorder.setVideoCodec(AV_CODEC_ID_H264)
    recorder.start()

    // Read frame by frame, stop early if the display window is closed
    for (frame <- continually(grabber.grab()).takeWhile(_ != null)) {
      val outputFrame = Using.resource(new OpenCVFrameConverter.ToMat()) { frameConverter =>
        // creates a pointer to Frame byte buffer and creates Mat from this pointer
        // important: both share same byte bugger now
        val input = frameConverter.convert(frame)
        val output = new Mat()
        cvtColor(input, output, COLOR_BGR2GRAY)
        // Compute Canny edges
        Canny(output, output, 50, 200, 3, true)
        // Invert the image
        threshold(output, output, 128, 255, THRESH_BINARY_INV)
        frameConverter.convert(output)
      }
      recorder.record(outputFrame)
    }

    grabber.release()
    recorder.stop()
  }
}
