package com.karol.reports;

import com.karol.model.Transfer;
import com.karol.utils.DateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MonthlyReport {

    public static List<ReportRow> createReport(List<Transfer> transfers) {
        List<ReportRow> report = new ArrayList<>();
        List<Integer> years = getReportYears(transfers);
        years.stream()
                .forEach(year -> {
                    List<Transfer> reportsInYear = getTransfersInYear(year, transfers);
                    IntStream.range(1, 13).forEach(monthNumber -> {
                        ReportRow monthInYearRow = new ReportRow();
                        monthInYearRow.date = DateFormatter.fromLocalDate(LocalDate.of(year, monthNumber, 1));
                        List<Transfer> transfersInMonth = getTransfersInMonth(monthNumber, reportsInYear);
                        monthInYearRow.grossValue = transfersInMonth.stream().mapToDouble(t -> t.getGrossValue()).sum();
                        monthInYearRow.netValue = transfersInMonth.stream().mapToDouble(t -> t.getNetValue()).sum();
                        report.add(monthInYearRow);
                    });
                });
        return report;
    }

    private static List<Transfer> getTransfersInMonth(int month, List<Transfer> transfers) {
        return transfers.stream()
                .filter(t -> DateFormatter.toLocalDate(t.getTransferDate()).getMonthValue() == month)
                .collect(Collectors.toList());
    }

    private static List<Transfer> getTransfersInYear(int year, List<Transfer> transfers) {
        return transfers.stream()
                .filter(t -> DateFormatter.toLocalDate(t.getTransferDate()).getYear() == year)
                .collect(Collectors.toList());
    }

    private static List<Integer> getReportYears(List<Transfer> transfers) {
        return transfers.stream()
                .map(t -> DateFormatter.toLocalDate(t.getTransferDate()).getYear())
                .distinct()
                .collect(Collectors.toList());
    }

    public static class ReportRow {
        public Date date;
        public double netValue = 0;
        public double grossValue = 0;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ReportRow reportRow = (ReportRow) o;

            if (Double.compare(reportRow.netValue, netValue) != 0) return false;
            if (Double.compare(reportRow.grossValue, grossValue) != 0) return false;
            if (date != null ? !date.equals(reportRow.date) : reportRow.date != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = date != null ? date.hashCode() : 0;
            temp = Double.doubleToLongBits(netValue);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(grossValue);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }
}


