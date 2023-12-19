package com.ps.detection

import com.ps.imshow
import org.bytedeco.javacpp.DoublePointer
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._

// see https://docs.opencv.org/4.x/de/da9/tutorial_template_matching.html
object TemplateDetection {

  def main(args: Array[String]): Unit = {
    val dog = imread("data/sammy.jpg")
    val face = imread("data/sammy_face.jpg")

    val heatmap = new Mat()
    // methods: TM_CCOEFF, TM_CCOEFF_NORMED, TM_CCORR, TM_CCORR_NORMED, TM_SQDIFF, TM_SQDIFF_NORMED
    matchTemplate(dog, face, heatmap, TM_CCOEFF)

    // to display heatmap we need to have 8bit output (have to fix imshow to support float)
    val heatmap8U = new Mat()
    normalize(heatmap, heatmap8U, 255.0, 0.0, NORM_MINMAX, CV_8UC1, null)
    imshow("Detection heatmap", heatmap8U)

    // find most similar location
    val minVal = new DoublePointer(1)
    val maxVal = new DoublePointer(1)
    val minPt = new Point()
    val maxPt = new Point()
    minMaxLoc(heatmap, minVal, maxVal, minPt, maxPt, null)

    println(s"minPt = ${minPt.x}, ${minPt.y}")

    // draw rectangle at most similar location
    // at minPt in this case
    rectangle(dog, new Rect(maxPt.x, maxPt.y, face.cols, face.rows), new Scalar(255, 255, 255, 0))
    imshow("Best match", dog)
  }
}
