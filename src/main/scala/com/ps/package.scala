package com

import org.bytedeco.javacv.{CanvasFrame, OpenCVFrameConverter}
import org.bytedeco.opencv.opencv_core.Mat

import javax.swing.WindowConstants

package object ps {

  // A custom imshow method for problems using the OpenCV original one
  def imshow(name: String, img: Mat) = {
    val canvasFrame = new CanvasFrame(name)
    canvasFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    canvasFrame.setCanvasSize(img.cols, img.rows)
    canvasFrame.showImage(new OpenCVFrameConverter.ToMat().convert(img))
  }
}
