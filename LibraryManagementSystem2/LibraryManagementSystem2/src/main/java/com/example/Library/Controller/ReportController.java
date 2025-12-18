package com.example.Library.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Library.Service.ReportService;

import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/available-books")
    public JsonNode getAvailableBooks(){
        return reportService.getAvailableBooks();
    }

    @GetMapping("/issued-books")
    public JsonNode getAllIssuedBooks(){
        return reportService.getAllIssuedBooks();
    }

    // Get overdue Books

    @GetMapping("/overDue-books")
    public JsonNode getAllOverDueIssuedBooks(){
        return reportService.getAllOverDueIssuedBooks();
    }
}
