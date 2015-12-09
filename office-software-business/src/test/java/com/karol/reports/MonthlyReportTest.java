package com.karol.reports;

import com.karol.model.Transfer;
import com.karol.utils.DateFormatter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MonthlyReportTest {

    private static List<Transfer> transferList;

    @BeforeClass
    public static void initTransferList() {
        transferList = new ArrayList<>();
        LocalDate date = LocalDate.of(2000, 1, 1);
        //transfers in January
        transferList.add(new Transfer(DateFormatter.fromLocalDate(date), 1, 1));
        transferList.add(new Transfer(DateFormatter.fromLocalDate(date), 1, 1));
        transferList.add(new Transfer(DateFormatter.fromLocalDate(date), 1, 1));
        //transfers in March
        transferList.add(new Transfer(DateFormatter.fromLocalDate(date.plusMonths(2)), 2, 2));
        transferList.add(new Transfer(DateFormatter.fromLocalDate(date.plusMonths(2)), 2, 2));
        transferList.add(new Transfer(DateFormatter.fromLocalDate(date.plusMonths(2)), 2, 2));
        //transfer in December+
        transferList.add(new Transfer(DateFormatter.fromLocalDate(date.plusMonths(11)), 3, 3));
        transferList.add(new Transfer(DateFormatter.fromLocalDate(date.plusMonths(11)), 3, 3));
        transferList.add(new Transfer(DateFormatter.fromLocalDate(date.plusMonths(11)), 3, 3));
    }

    @Test
    public void monthlyReportTestShouldCreateCorrectReportFromMockTransfers() {
        //given
        List<Transfer> transfers = transferList;
        //when
        List<MonthlyReport.ReportRow> report = MonthlyReport.createReport(transfers);
        //then
        double netAmountInJanuary = report.get(0).netValue;
        double grossAmountInJanuary = report.get(0).netValue;
        assertEquals(3, netAmountInJanuary, 2);
        assertEquals(3, grossAmountInJanuary, 2);

        double netAmountInMarch = report.get(2).netValue;
        double grossAmountInMarch = report.get(2).netValue;
        assertEquals(6, netAmountInMarch, 2);
        assertEquals(6, grossAmountInMarch, 2);

        double netAmountInDecember = report.get(11).netValue;
        double grossAmountInDecember = report.get(11).netValue;
        assertEquals(9, netAmountInDecember, 2);
        assertEquals(9, grossAmountInDecember, 2);
    }

}