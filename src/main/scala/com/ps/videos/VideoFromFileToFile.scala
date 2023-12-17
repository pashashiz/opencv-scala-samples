package com.ps.videos

import org.bytedeco.ffmpeg.global.avcodec._
import org.bytedeco.javacv._

import scala.collection.immutable.LazyList.continually

object VideoFromFileToFile {

  def main(args: Array[String]): Unit = {

    val grabber = new FFmpegFrameGrabber("data/hand_move.mp4")
    grabber.start()

    val recorder =
      new FFmpegFrameRecorder(
        "hand_move.mp4",
        grabber.getImageWidth,
        grabber.getImageHeight,
        grabber.getAudioChannels)
    recorder.setVideoCodec(AV_CODEC_ID_H264)
    recorder.start()

    // Read frame by frame, stop early if the display window is closed
    for (frame <- continually(grabber.grab()).takeWhile(_ != null)) {
      recorder.record(frame)
    }

    grabber.release()
    recorder.stop()
  }
}
