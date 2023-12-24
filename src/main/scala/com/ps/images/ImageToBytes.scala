package com.ps.images

import com.ps.encodeImage
import org.bytedeco.opencv.global.opencv_imgcodecs._

import java.nio.file.{Files, Paths}

object ImageToBytes {
  def main(args: Array[String]): Unit = {
    val image = imread("data/00-puppy.jpg")
    val bytes = encodeImage(image, ".jpg")
    Files.write(Paths.get("target/00-puppy-copy.jpg"), bytes)
  }
}
