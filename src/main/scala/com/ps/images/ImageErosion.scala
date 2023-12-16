package com.ps.images

import com.ps.imshow
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._

// https://docs.opencv.org/4.x/db/df6/tutorial_erosion_dilatation.html
object ImageErosion {

  def main(args: Array[String]): Unit = {
    val img = Mat.zeros(600, 600, CV_8UC3).asMat() // unsigned 8bit/pixel and 3 channels
    val black = new Scalar(255.0, 255.0, 255.0, 0)
    putText(img, "ABCDE", new Point(50, 300), FONT_HERSHEY_SIMPLEX, 5, black, 25, LINE_AA, false)
    val kernel = new Mat(3, 3, CV_8U, new Scalar(1))
    val dest = new Mat()
    erode(img, dest, kernel, new Point(-1, -1), 5, BORDER_CONSTANT, morphologyDefaultBorderValue())
    imshow("Result", dest)
  }
}
