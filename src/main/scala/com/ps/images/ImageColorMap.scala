package com.ps.images

// objects
import com.ps.imshow
import org.bytedeco.javacpp.IntPointer
import org.bytedeco.opencv.global.opencv_imgcodecs.imread
import org.bytedeco.opencv.opencv_core._
// functions
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgproc._

object ImageColorMap {

  def main(args: Array[String]): Unit = {
    val img = imread("data/00-puppy.jpg")

    // imshow("Image 1", image)
    // convert color
    val imgGray = new Mat()
    cvtColor(img, imgGray, COLOR_RGB2GRAY)
    imshow("GRAY", imgGray)

    val imgHsl = new Mat()
    cvtColor(img, imgHsl, COLOR_RGB2HLS)
    imshow("HLS", imgHsl)

    val imgHsv = new Mat()
    cvtColor(img, imgHsv, COLOR_RGB2HSV)
    imshow("HLV", imgHsv)
  }
}
