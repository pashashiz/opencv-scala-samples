package com.ps.images

import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._

object ImageSmoother {

  // Mat.ones(1, 1, CV_8U).mul(new Mat(new Scalar(5.0)))

  def main(args: Array[String]): Unit = {
    val image = imread("data/00-puppy.jpg")
    if (image != null) {
      GaussianBlur(image, image, new Size(3, 3), 0)
      imwrite("data/00-puppy_smooth.jpg", image)
    }
  }
}
