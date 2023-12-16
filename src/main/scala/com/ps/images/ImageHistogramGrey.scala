package com.ps.images

import com.ps.{Histogram1D, imshow}
import org.bytedeco.opencv.global.opencv_imgcodecs._

// https://docs.opencv.org/4.x/d8/dbc/tutorial_histogram_calculation.html
object ImageHistogramGrey {

  def main(args: Array[String]): Unit = {
    val img = imread("data/bricks.jpg", IMREAD_GRAYSCALE)
    // imshow("Image before", img)
    // Calculate histogram
    val h = new Histogram1D
    val histogram = h.getHistogramAsArray(img)

    // Print histogram values, the Scala way
    histogram.zipWithIndex.foreach {
      case (count, bin) => println("" + bin + ": " + math.round(count))
    }
    imshow("Hist", h.getHistogramImage(img))
  }
}
