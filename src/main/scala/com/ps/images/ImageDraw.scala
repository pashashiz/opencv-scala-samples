package com.ps.images

// objects
import com.ps.imshow
import org.bytedeco.javacpp.IntPointer
import org.bytedeco.opencv.opencv_core
import org.bytedeco.opencv.opencv_core._
// functions
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._

object ImageDraw {

  def main(args: Array[String]): Unit = {
    val img = Mat.zeros(512, 512, CV_8UC3).asMat() // unsigned 8bit/pixel and 3 channels
    val green = new Scalar(34.0, 139.0, 34.0, 0)
    val blue = new Scalar(255.0, 0.0, 0.0, 0)
    val yellow = new Scalar(102.0, 255.0, 255.0, 0)
    val white = new Scalar(255.0, 255.0, 255.0, 0)

    // add rectangle
    rectangle(img, new Point(384, 0), new Point(510, 128), green, 5, LINE_8, 0)
    // add circle
    circle(img, new Point(100, 100), 50, blue, 5, LINE_8, 0)
    // add circle filled in
    circle(img, new Point(400, 400), 50, blue, -1, LINE_8, 0)
    // line
    line(img, new Point(0, 0), new Point(511, 511), yellow, 5, LINE_8, 0)
    // text
    putText(img, "Hello!", new Point(10, 500), FONT_HERSHEY_SIMPLEX, 4, white, 2, LINE_AA, false)

    // polygon
    val pts = new Point(4)
    pts.position(0).x(100).y(100)
    pts.position(1).x(100).y(200)
    pts.position(2).x(200).y(200)
    pts.position(3).x(200).y(100)
    val size = new IntPointer(1L).put(4)
    polylines(img, pts.position(0), size, 1, true, white, 2, LINE_AA, 0)

    imshow("Result", img)
  }
}
