package com.ps.images

import com.ps.imshow
import org.bytedeco.opencv.opencv_core._
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._

// read https://docs.opencv.org/4.8.0/d7/d4d/tutorial_py_thresholding.html
// https://docs.opencv.org/4.x/db/d8e/tutorial_threshold.html
object ImageThresholding {
  def main(args: Array[String]): Unit = {
    val image = imread("data/rainbow.jpg", IMREAD_GRAYSCALE) // read 1 channel
    println(image)
    imshow("Image", image)
    val image2 = new Mat()
    // < 127 will be 0, >= 127 will be 255
    threshold(image, image2, 127, 255, THRESH_BINARY)
    val image3 = new Mat()
    // < 127 will be same, >= 127 will be 127 (basically max function)
    threshold(image, image3, 127, 255, THRESH_TRUNC)
    imshow("Image", image3)
  }
}
