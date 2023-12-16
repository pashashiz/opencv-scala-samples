package com.ps.videos

import org.bytedeco.javacv.{CanvasFrame, OpenCVFrameConverter}
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_videoio.VideoCapture

import javax.swing.WindowConstants

object VideoBasics {

  def main(args: Array[String]): Unit = {
    // get stream from web camera
    val cap = new VideoCapture(0)
    while (!cap.isOpened) {
      // wait for camera
    }
    val canvasFrame = new CanvasFrame("video")
    canvasFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    while (true) {
      val frame = new Mat()
      cap.read(frame)
      canvasFrame.setCanvasSize(frame.cols / 2, frame.rows / 2)
      canvasFrame.showImage(new OpenCVFrameConverter.ToMat().convert(frame))
    }
  }
}
