package com.ps.images

import com.ps.imshow
import org.bytedeco.opencv.opencv_core._
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._

object ImageBlendSameSize {

  def main(args: Array[String]): Unit = {
    val dog = imread("data/dog_backpack.png")
    println(s"dog (${dog.cols()}, ${dog.rows()})") // (934, 1401)
    val dogSmall = new Mat()
    resize(dog, dogSmall, new Size(500, 500), 0, 0, INTER_LINEAR)
    println(s"dogSmall (${dogSmall.cols()}, ${dogSmall.rows()})") // (934, 1401)
    val watermark = imread("data/watermark_no_copy.png")
    println(s"watermark (${watermark.cols()}, ${watermark.rows()})") // (1277, 1280)
    val watermarkSmall = new Mat()
    resize(watermark, watermarkSmall, new Size(500, 500), 0, 0, INTER_LINEAR)
    println(s"watermarkSmall (${watermarkSmall.cols()}, ${watermarkSmall.rows()})") // (1277, 1280)
    // imshow("dog", dogSmall)
    // imshow("watermark", watermark)
    val dogWithWatermarkSmall = new Mat()
    addWeighted(dogSmall, 0.7, watermarkSmall, 0.3, 0, dogWithWatermarkSmall)
    imshow("dogWithWatermarkSmall", dogWithWatermarkSmall)
  }
}
