package com.ps.images

import com.ps.imshow
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.opencv_core._

object ImageBlendDiffSize {

  // Illustrates operation on a small portion of the input image: a region of interest (ROI)
  def main(args: Array[String]): Unit = {

    val dog = imread("data/dog_backpack.png")
    println(s"dog (${dog.cols()}, ${dog.rows()})") // (934, 1401)

    val watermark = imread("data/watermark_no_copy.png")
    println(s"watermark (${watermark.cols()}, ${watermark.rows()})") // (1277, 1280)

    val watermarkSmall = new Mat()
    resize(watermark, watermarkSmall, new Size(0, 0), 0.3, 0.3, INTER_LINEAR)

    val watermarkMask = new Mat()
    cvtColor(watermarkSmall, watermarkMask, COLOR_RGB2GRAY)
    // imshow("watermarkMask", watermarkMask)
    val watermarkMask2 = new Mat()
    bitwise_not(watermarkMask, watermarkMask2)
    // there are some artifacts around the watermark with non-intense color, we should drop them
    // 255 if the condition is satisfied and 0 if it is not
    val watermarkMask3 = greaterThan(watermarkMask2, 200.0).asMat()
    // imshow("watermarkMask3", watermarkMask3)

    // Define region of interest that matches the size of the watermark
    val dogROI = dog(new Rect(
      (dog.cols - watermarkSmall.cols) / 2,
      (dog.rows - watermarkSmall.rows) / 2,
      watermarkSmall.cols,
      watermarkSmall.rows))

    watermarkSmall.copyTo(dogROI, watermarkMask3)
    // note: we can also combine them with addWeighted
    imshow("dog", dog)
  }
}
