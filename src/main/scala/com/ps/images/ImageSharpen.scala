package com.ps.images

import com.ps.imshow
import org.bytedeco.javacpp.indexer.FloatIndexer
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._

// image kernels explained https://setosa.io/ev/image-kernels/
object ImageSharpen {

  def main(args: Array[String]): Unit = {
    val image = imread("data/bricks.jpg")
    // Construct sharpening kernel, oll unassigned values are 0
    val kernel = new Mat(3, 3, CV_32F, new Scalar(0))
    // Indexer is used to access value in the matrix
    val ki: FloatIndexer = kernel.createIndexer()
    ki.put(1, 1, 5)
    ki.put(0, 1, -1)
    ki.put(2, 1, -1)
    ki.put(1, 0, -1)
    ki.put(1, 2, -1)
    val dest = new Mat()
    // Filter the image
    filter2D(image, dest, image.depth(), kernel)
    imshow("Image before", image)
    imshow("Image after", dest)
  }
}
