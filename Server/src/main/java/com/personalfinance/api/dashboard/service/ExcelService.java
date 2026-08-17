package com.personalfinance.api.dashboard.service;

import java.io.IOException;

public interface ExcelService {

    byte[] exportDashboard(Integer month, Integer year) throws IOException;
}
