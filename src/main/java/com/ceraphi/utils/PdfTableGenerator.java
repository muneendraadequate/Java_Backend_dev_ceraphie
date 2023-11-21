package com.ceraphi.utils;

import com.ceraphi.entities.MasterDataTables.ProDataBaseModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfTableGenerator {

    public static byte[] generatePdf(List<ProDataBaseModel> proDataList) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float tableHeight = 20f; // Adjust as needed
                float marginY = 5;
                float cellMargin = 2f;

                // Define the number of columns and their width ratios
                float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
                float tableWidthFixed = tableWidth - 20;
                float[] cellWidths = new float[columnWidths.length];
                for (int i = 0; i < columnWidths.length; i++) {
                    cellWidths[i] = tableWidthFixed * columnWidths[i] / 22f;
                }

                // Draw the table headers
                drawTableHeader(contentStream, margin, yPosition, tableWidth, tableHeight, cellWidths, cellMargin);

                // Draw the table rows
                yPosition -= tableHeight;
                drawTableRows(contentStream, margin, yPosition, tableWidth, tableHeight, cellWidths, cellMargin, proDataList);

                // Save the document to a byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                document.save(byteArrayOutputStream);

                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();

                // Handle exception as needed
            }

        }catch (IOException e) {
            e.printStackTrace();
    }
        return null;

    }

    public static void drawTableHeader(PDPageContentStream contentStream, float xStart, float yStart, float tableWidth,
                                       float tableHeight, float[] cellWidths, float cellMargin) throws IOException {
        float yPosition = yStart;
        float xPosition = xStart;
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(xPosition, yPosition);
        contentStream.showText("ID");
        contentStream.newLineAtOffset(cellWidths[0], 0);
        contentStream.showText("Geothermal Gradient");
        contentStream.newLineAtOffset(cellWidths[1], 0);
        contentStream.showText("Steady State Temp");
        contentStream.newLineAtOffset(cellWidths[2], 0);
        contentStream.showText("KWt");
        contentStream.newLineAtOffset(cellWidths[3], 0);
        contentStream.showText("Flow Rate");
        contentStream.newLineAtOffset(cellWidths[4], 0);
        contentStream.showText("Pumping Power");
        contentStream.newLineAtOffset(cellWidths[5], 0);
        contentStream.showText("Depth");
        contentStream.newLineAtOffset(cellWidths[6], 0);
        contentStream.showText("Delta");
        contentStream.newLineAtOffset(cellWidths[7], 0);
        contentStream.showText("Pressure Loss");
        contentStream.newLineAtOffset(cellWidths[8], 0);
        contentStream.showText("BHT");
        contentStream.newLineAtOffset(cellWidths[9], 0);
        contentStream.showText("Return Value");
        contentStream.endText();
    }

    public static void drawTableRows(PDPageContentStream contentStream, float xStart, float yStart, float tableWidth,
                                     float tableHeight, float[] cellWidths, float cellMargin, List<ProDataBaseModel> proDataList) throws IOException {
        float yPosition = yStart;
        float xPosition;

        int rows = proDataList.size();
        int cols = 11; // Number of columns in your table

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        for (int i = 0; i < rows; i++) {
            ProDataBaseModel proData = proDataList.get(i);

            xPosition = xStart;

            // Begin the text before using newLineAtOffset
            contentStream.beginText();

            // Add values for all columns
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.showText(String.valueOf(proData.getId()));
            contentStream.newLineAtOffset(cellWidths[0], 0);
            contentStream.showText(String.valueOf(proData.getGeothermalGradient()));
            contentStream.newLineAtOffset(cellWidths[1], 0);
            contentStream.showText(String.valueOf(proData.getSteadyStateTemp()));
            contentStream.newLineAtOffset(cellWidths[2], 0);
            contentStream.showText(String.valueOf( proData.getKWt()));
            contentStream.newLineAtOffset(cellWidths[3], 0);
            contentStream.showText(String.valueOf(proData.getFlowRate()));
            contentStream.newLineAtOffset(cellWidths[4], 0);
            contentStream.showText(String.valueOf( proData.getPumpingPower()));
            contentStream.newLineAtOffset(cellWidths[5], 0);
            contentStream.showText(String.valueOf( proData.getDepth()));
            contentStream.newLineAtOffset(cellWidths[6], 0);
            contentStream.showText(String.valueOf(proData.getDelta()));
            contentStream.newLineAtOffset(cellWidths[7], 0);
            contentStream.showText(String.valueOf(proData.getPressureLoss()));
            contentStream.newLineAtOffset(cellWidths[8], 0);
            contentStream.showText(String.valueOf( proData.getBHT()));
            contentStream.newLineAtOffset(cellWidths[9], 0);
            contentStream.showText(String.valueOf(proData.getReturnValue()));
            // End the text after displaying all columns for the current row
            contentStream.endText();

            yPosition -= tableHeight / rows + cellMargin; // Consider cellMargin when moving to the next line
        }
    }
}
