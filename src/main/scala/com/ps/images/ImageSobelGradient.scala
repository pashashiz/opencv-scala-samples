package com.ps.images

import com.ps.imshow
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._

// https://docs.opencv.org/4.x/db/df6/tutorial_erosion_dilatation.html
object ImageSobelGradient {

  def main(args: Array[String]): Unit = {
    val img = imread("data/sudoku.jpg", IMREAD_GRAYSCALE)
    val gradX = new Mat()
    Sobel(img, gradX, CV_8U, 1, 0, 7, 1, 0, BORDER_DEFAULT)
    val gradY = new Mat()
    Sobel(img, gradY, CV_8U, 0, 1, 7, 1, 0, BORDER_DEFAULT)
    val grad = new Mat()
    Laplacian(img, grad, CV_8U)
    imshow("Grad", grad)
  }
}
