package com.example.Library.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import com.example.Library.Service.IssueService;

import tools.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/issue")
public class IssueController {

    @Autowired
    private IssueService issueService;


    // Issue a new Book
    @PostMapping("/newBook")
    public JsonNode issueNewBook(@RequestBody Map<String,String> data){
        Long bookId = Long.parseLong(data.get("bookId"));
        Long memberId =Long.parseLong(data.get("memberId"));
        LocalDate issueDate = LocalDate.parse(data.get("issueDate"));
        LocalDate dueDate = LocalDate.parse(data.get("dueDate"));
        return issueService.issueBook(bookId,memberId,issueDate,dueDate);
    }

    @GetMapping("/getAllIssue")
    public JsonNode getAllIssueBooks(){
        return issueService.getAllIssueBooks();
    }

    // Get issue details by issueId;

    @GetMapping("/{issueId}")
    public JsonNode getIssueById(@PathVariable Long issueId){
        return issueService.getIssueById(issueId);
    }

    // Get All issue for a member
    @GetMapping("/member/{memberId}")
    public JsonNode getByMember(@PathVariable Long memberId){
        return issueService.getByMember(memberId);
    }

    // Get All issue for a Book
    @GetMapping("/books/{bookId}")
    public JsonNode getByBooks(@PathVariable Long bookId){
        return issueService.getByBooks(bookId);
    }


    // return a book
    @PostMapping("/return/{issueId}")
    public JsonNode returnBook(@PathVariable Long issueId,@RequestBody Map<String,String> data){
        LocalDate returnDate = LocalDate.parse(data.get("returnDate"));
        return issueService.returnBook(issueId,returnDate);
    }
}
