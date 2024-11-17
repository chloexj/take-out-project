package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO getTurnoverStatistics(LocalDate begin,LocalDate end);

    UserReportVO getuserStatistics(LocalDate begin, LocalDate end);

   OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getTop10Statistics(LocalDate begin, LocalDate end);

    void exportBusinessData(HttpServletResponse response) throws IOException;
}