package com.maly.pdftool

import scala.sys.exit

import org.apache.pdfbox.pdmodel.{PDDocument,PDPage}
import org.apache.pdfbox.pdmodel.common.PDRectangle
import java.io.{File,FileInputStream}

object Crop extends App {
  println("pdf crop tool v1.0")

  if (args.length != 5) {
    println("Usage: scala Crop <filename> <x> <y> <width> <height>")
    exit(1)
  }

  val filename = args(0)
  val x = args(1).toInt
  val y = args(2).toInt
  val width = args(3).toInt
  val height = args(4).toInt

  val inputFile = new File(filename)
  val outputFile = new File(filename.replaceFirst(".pdf", "_cropped.pdf"))

  try {
    val document: PDDocument = PDDocument.load(inputFile)
    val cropBox: PDRectangle = new PDRectangle(x, y, width, height)

    val pages = document.getPages
    val iterator = pages.iterator()
    while (iterator.hasNext) {
      val page = iterator.next().asInstanceOf[PDPage]
      println(page.getCropBox)
      page.setCropBox(cropBox)
    }

    document.save(outputFile)
    document.close()

    val ipadRatio = 2048.0 / 1536.0
    val pdfRatio = (height - y).toDouble / (width - x).toDouble

    println(s"Cropping completed, iPad: $ipadRatio, PDF: $pdfRatio")
  }
  catch {
    case e: Exception => e.printStackTrace()
  }
}
