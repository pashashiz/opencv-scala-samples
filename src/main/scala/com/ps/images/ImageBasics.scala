package com.ps.images

// objects
import org.bytedeco.opencv.opencv_core._
// functions
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._
import com.ps.imshow

object ImageBasics {
  // Mat - new C++ API
  // CvArr - old C API, used in functions cvFunc
  def main(args: Array[String]): Unit = {
    val image = imread("data/00-puppy.jpg")
     imshow("Image 1", image)
    // convert color
    val image2 = new Mat()
    cvtColor(image, image2, COLOR_RGB2GRAY)
    // imshow("Image 2", image2)
    // resize image by ratio
    val image3 = new Mat()
    resize(image, image3, new Size(0, 0), 0.5, 0.5, INTER_LINEAR)
    // imshow("Image 3", image3)
    val image4 = new Mat()
    // flip
    flip(image3, image4, 0)
    // imshow("Image 4", image4)
    // save
    imwrite("data/00-puppy-resized-flipped.jpg", image4)
  }
}
