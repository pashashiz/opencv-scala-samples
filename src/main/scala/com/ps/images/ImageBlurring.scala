package com.ps.images

import com.ps.imshow
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._

// image kernels explained https://setosa.io/ev/image-kernels/
object ImageBlurring {

  def main(args: Array[String]): Unit = {
    val image = imread("data/bricks.jpg")
    val dest = new Mat()
    GaussianBlur(image, dest, new Size(5, 5), 3.0)
    imshow("Image before", image)
    imshow("Image after", dest)
  }
}
