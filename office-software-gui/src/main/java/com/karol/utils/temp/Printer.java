package com.karol.utils.temp;

import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.transform.Scale;

public class Printer {

    public void test(Node contractorsTable) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        javafx.print.Printer printer = javafx.print.Printer.getDefaultPrinter();
        PageLayout layout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, javafx.print.Printer.MarginType.HARDWARE_MINIMUM);
        double scaleX = (layout.getPrintableWidth() - 50) / contractorsTable.getBoundsInParent().getWidth();
        double scaleY = (layout.getPrintableHeight() - 50) / contractorsTable.getBoundsInLocal().getHeight() / 2;
        Scale scale = new Scale(scaleX, scaleY);
        contractorsTable.getTransforms().add(scale);
        printerJob.printPage(layout, contractorsTable);
        printerJob.endJob();
        printerJob.showPrintDialog(null);
        contractorsTable.getTransforms().remove(scale);
    }
}
