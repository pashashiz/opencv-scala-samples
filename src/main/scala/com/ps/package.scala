package com

import org.bytedeco.javacpp.{BytePointer, IntPointer}
import org.bytedeco.javacv.{CanvasFrame, Java2DFrameConverter, OpenCVFrameConverter}
import org.bytedeco.opencv.global.opencv_imgcodecs.imencode
import org.bytedeco.opencv.opencv_core.{Mat, MatVector}

import java.awt.image.BufferedImage
import javax.swing.WindowConstants

package object ps {

  // A custom imshow method for problems using the OpenCV original one
  def imshow(title: String, img: Mat) = {
    val canvasFrame = new CanvasFrame(title)
    canvasFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    canvasFrame.setCanvasSize(img.cols, img.rows)
    canvasFrame.showImage(new OpenCVFrameConverter.ToMat().convert(img))
  }

  def imshow(title: String, image: BufferedImage) = {
    val canvas = new CanvasFrame(title, 1)
    canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    canvas.showImage(image)
  }

  def wrapInIntPointer(v: Int): IntPointer = {
    new IntPointer(1L).put(v)
  }

  def wrapInMatVector(mat: Mat): MatVector = {
    new MatVector(Array(mat): _*)
  }

  def toBufferedImage(mat: Mat): BufferedImage = {
    val converter = new OpenCVFrameConverter.ToMat()
    try {
      val frame = converter.convert(mat)
      val java2DConverter = new Java2DFrameConverter()
      try {
        java2DConverter.convert(frame)
      } finally {
        if (frame != null) frame.close()
        if (java2DConverter != null) java2DConverter.close()
      }
    } finally {
      if (converter != null) converter.close()
    }
  }

  def fromBuffereImage(image: BufferedImage): Mat = {
    val java2DConverter = new Java2DFrameConverter()
    try {
      val frame = java2DConverter.convert(image)
      val converter = new OpenCVFrameConverter.ToMat()
      try {
        converter.convert(frame)
      } finally {
        if (frame != null) frame.close()
        if (converter != null) java2DConverter.close()
      }
    } finally {
      if (java2DConverter != null) java2DConverter.close()
    }
  }

  def encodeImage(image: Mat, ext: String = ".jpg", maxBufferSizeMb: Int = 4): Array[Byte] = {
    val out = new BytePointer(maxBufferSizeMb * 1024 * 1024)
    imencode(ext, image, out)
    val buffer = out.asBuffer()
    val bytes = new Array[Byte](buffer.remaining)
    buffer.get(bytes)
    bytes
  }
}
