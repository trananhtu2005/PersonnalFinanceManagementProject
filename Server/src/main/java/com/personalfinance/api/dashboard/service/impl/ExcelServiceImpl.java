package com.personalfinance.api.dashboard.service.impl;

import com.personalfinance.api.budget.dto.response.BudgetResponse;
import com.personalfinance.api.category.dto.response.DashboardCategoryResponse;
import com.personalfinance.api.dashboard.dto.response.DashboardResponse;
import com.personalfinance.api.dashboard.service.DashboardService;
import com.personalfinance.api.dashboard.service.ExcelService;
import com.personalfinance.api.payment_reminder.dto.response.PaymentReminderResponse;
import com.personalfinance.api.saving_goal.dto.response.SavingGoalResponse;
import com.personalfinance.api.wallet.dto.response.WalletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final DashboardService dashboardService;

    private void createSummarySheet(Workbook workbook, DashboardResponse dashboard) {
        Sheet sheet = workbook.createSheet("Summary");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Dashboard");
        header.createCell(1).setCellValue(dashboard.getMonth() + "/" + dashboard.getYear());
        Row balance = sheet.createRow(2);
        balance.createCell(0).setCellValue("Total Balance");
        balance.createCell(1).setCellValue(dashboard.getSumBalance().doubleValue());
        Row expense = sheet.createRow(3);
        expense.createCell(0).setCellValue("Expense");
        expense.createCell(1).setCellValue(dashboard.getExpense().getAmount().doubleValue());
        expense.createCell(2).setCellValue(dashboard.getExpense().getDifference().doubleValue());
        Row income = sheet.createRow(4);
        income.createCell(0).setCellValue("Income");
        income.createCell(1).setCellValue(dashboard.getIncome().getAmount().doubleValue());
        income.createCell(2).setCellValue(dashboard.getIncome().getDifference().doubleValue());
        Row saving = sheet.createRow(5);
        saving.createCell(0).setCellValue("Saving");
        saving.createCell(1).setCellValue(dashboard.getSaving().getAmount().doubleValue());
        saving.createCell(2).setCellValue(dashboard.getSaving().getDifference().doubleValue());
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
    }

    private void createCategorySheet(Workbook workbook, DashboardResponse dashboard) {
        Sheet sheet = workbook.createSheet("Categories");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Color");
        header.createCell(3).setCellValue("Type");
        header.createCell(4).setCellValue("Amount");
        int rowIndex = 1;

        for (DashboardCategoryResponse category
                : dashboard.getCategories()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(category.getId());
            row.createCell(1).setCellValue(category.getName());
            row.createCell(2).setCellValue(category.getColorCode());
            row.createCell(3).setCellValue(category.getType().name());
            row.createCell(4).setCellValue(category.getAmount().doubleValue());
        }
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createBudgetSheet(Workbook workbook, DashboardResponse dashboard) {
        Sheet sheet = workbook.createSheet("Budgets");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Category");
        header.createCell(2).setCellValue("Amount");
        header.createCell(3).setCellValue("Spent");
        header.createCell(4).setCellValue("Exceeded");
        header.createCell(5).setCellValue("Month");
        header.createCell(6).setCellValue("Year");
        int rowIndex = 1;

        for (BudgetResponse budget : dashboard.getBudgets()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(budget.getId());
            row.createCell(1).setCellValue(budget.getCategoryName());
            row.createCell(2).setCellValue(budget.getAmount().doubleValue());
            row.createCell(3).setCellValue(budget.getSpent().doubleValue());
            row.createCell(4).setCellValue(budget.isExceeded());
            row.createCell(5).setCellValue(budget.getMonth());
            row.createCell(6).setCellValue(budget.getYear());
        }
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createWalletSheet(Workbook workbook, DashboardResponse dashboard) {
        Sheet sheet = workbook.createSheet("Wallets");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Balance");
        int rowIndex = 1;

        for (WalletResponse wallet : dashboard.getWallets()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(wallet.getId());
            row.createCell(1).setCellValue(wallet.getName());
            row.createCell(2).setCellValue(wallet.getBalance().doubleValue());
        }
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createPaymentReminderSheet(Workbook workbook, DashboardResponse dashboard) {
        Sheet sheet = workbook.createSheet("Payment Reminders");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Title");
        header.createCell(2).setCellValue("Amount");
        header.createCell(3).setCellValue("Due At");
        header.createCell(4).setCellValue("Category");
        header.createCell(5).setCellValue("Note");
        int rowIndex = 1;

        for (PaymentReminderResponse reminder : dashboard.getPaymentReminders()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(reminder.getId());
            row.createCell(1).setCellValue(reminder.getTitle());
            row.createCell(2).setCellValue(reminder.getAmount().doubleValue());
            row.createCell(3).setCellValue(reminder.getDueAt().toString());
            row.createCell(4).setCellValue(reminder.getCategoryName());

            if (reminder.getNote() != null) {
                row.createCell(5).setCellValue(reminder.getNote());
            }
        }
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createSavingGoalSheet(Workbook workbook, DashboardResponse dashboard) {
        Sheet sheet = workbook.createSheet("Saving Goals");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Title");
        header.createCell(2).setCellValue("Target");
        header.createCell(3).setCellValue("Current Amount");
        header.createCell(4).setCellValue("Start At");
        header.createCell(5).setCellValue("End At");
        header.createCell(6).setCellValue("Status");
        header.createCell(7).setCellValue("Category");
        int rowIndex = 1;

        for (SavingGoalResponse goal : dashboard.getSavingGoals()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(goal.getId());
            row.createCell(1).setCellValue(goal.getTitle());
            row.createCell(2).setCellValue(goal.getTarget().doubleValue());
            row.createCell(3).setCellValue(goal.getCurrentAmount().doubleValue());
            row.createCell(4).setCellValue(goal.getStartAt().toString());
            row.createCell(5).setCellValue(goal.getEndAt().toString());
            row.createCell(6).setCellValue(goal.getStatus().name());
            row.createCell(7).setCellValue(goal.getCategoryName());
        }
        for (int i = 0; i < 8; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    @Override
    public byte[] exportDashboard(Integer month, Integer year) throws IOException {
        DashboardResponse response = dashboardService.getDashBoard(month, year);
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            createSummarySheet(workbook, response);
            createCategorySheet(workbook, response);
            createBudgetSheet(workbook, response);
            createWalletSheet(workbook, response);
            createPaymentReminderSheet(workbook, response);
            createSavingGoalSheet(workbook, response);
            workbook.write(outputStream);

            return outputStream.toByteArray();
        }
    }
}
