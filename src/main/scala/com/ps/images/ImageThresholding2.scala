package com.ps.images

import com.ps.imshow
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._

// image kernels explained https://setosa.io/ev/image-kernels/
// read https://docs.opencv.org/4.8.0/d7/d4d/tutorial_py_thresholding.html
// https://docs.opencv.org/4.x/db/d8e/tutorial_threshold.html
object ImageThresholding2 {
  def main(args: Array[String]): Unit = {
    val image = imread("data/crossword.jpg", IMREAD_GRAYSCALE) // read 1 channel
    println(image)
    val image2 = new Mat()
    // < 127 will be 0, >= 127 will be 255
    threshold(image, image2, 160, 255, THRESH_BINARY)
    val image3 = new Mat()
    adaptiveThreshold(image, image3, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 15, 20)
    imshow("Image", image3)
  }
}
