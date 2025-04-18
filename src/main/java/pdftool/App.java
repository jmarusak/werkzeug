package pdftool;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.File;

public class App {

    public static void main(String[] args) {
        System.out.println("pdf crop tool v1.0");

        if (args.length != 5) {
            System.out.println("Usage: java pdftool.App <filename> <x> <y> <width> <height>");
            System.exit(1);
        }

        String filename = args[0];
        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int width = Integer.parseInt(args[3]);
        int height = Integer.parseInt(args[4]);

        File inputFile = new File(filename);
        File outputFile = new File(filename.replaceFirst(".pdf", "_cropped.pdf"));

        try (PDDocument document = Loader.loadPDF(inputFile)) {
            PDRectangle cropBox = new PDRectangle(x, y, width, height);

            for (PDPage page : document.getPages()) {
                System.out.println(page.getCropBox());
                page.setCropBox(cropBox);
            }

            document.save(outputFile);

            double ipadRatio = 4.0 / 3.0;
            double pdfRatio = (double) height / width;

            System.out.printf("Cropping completed, iPad: %.4f, PDF: %.4f%n", ipadRatio, pdfRatio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
