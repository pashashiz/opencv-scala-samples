package com.ps.images

import com.ps.imshow
import org.bytedeco.javacpp.indexer.UByteIndexer
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.opencv_core.Mat

// https://docs.opencv.org/4.x/d3/dc1/tutorial_basic_linear_transform.html
object ImageGammaCorrection {

  def main(args: Array[String]): Unit = {
    val image = imread("data/bricks.jpg")
    // contrast control
    val alpha = 1.1
    // brightness control
    val beta = 10
    val nbElements = image.rows * image.cols * image.channels
    // Indexer is used to access value in the image
    // Reshape to create flat view so we can iterate using a single loop
    // Annotate type correct type of the indexer: UByteIndexer
    val indexer: UByteIndexer = image.reshape(1, nbElements).createIndexer()
    for (i <- 0 until nbElements) {
      val v = indexer.get(i)
      val newV = math.min((v * alpha).toInt + beta, 255)
      indexer.put(i, newV)
    }
    imshow("Image", image)
  }
}
