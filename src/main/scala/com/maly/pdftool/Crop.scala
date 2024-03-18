package com.maly.pdftool

import org.apache.pdfbox.pdmodel.{PDDocument,PDPage}
import org.apache.pdfbox.pdmodel.common.PDRectangle
import java.io.{File,FileInputStream}

object Crop extends App {
  println("pdf crop tool v1.0")

  val inputFile = new File("/home/loco/scala.pdf")
  val outputFile = new File("/home/loco/scala_cropped.pdf")

  try {
    val document: PDDocument = PDDocument.load(inputFile)
    val cropBox: PDRectangle = new PDRectangle(50, 0, 400, 565)

    val pages = document.getPages
    val iterator = pages.iterator()
    while (iterator.hasNext) {
      val page = iterator.next().asInstanceOf[PDPage]
      page.setCropBox(cropBox)
      //println(page.getCropBox)
    }

    document.save(outputFile)
    document.close()

    println("Cropping completed.")
  }
  catch {
    case e: Exception => e.printStackTrace()
  }
}
