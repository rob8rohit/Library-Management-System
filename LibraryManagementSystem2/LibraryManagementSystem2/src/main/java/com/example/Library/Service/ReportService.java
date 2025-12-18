package com.example.Library.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Library.Entity.Books;
import com.example.Library.Entity.Issue;
import com.example.Library.Repository.BooksRepository;
import com.example.Library.Repository.IssueRepository;
import com.example.Library.Repository.MemberRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private MemberRepository memberRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode getAvailableBooks() {

        ObjectNode response = objectMapper.createObjectNode();

        List<Books> booksList = booksRepository.findAll()
                .stream()
                .filter(b->b.getAvailableCopies()>0)
                .toList();

        return objectMapper.valueToTree(booksList);
    }

    public JsonNode getAllIssuedBooks() {
        ObjectNode response = objectMapper.createObjectNode();
        List<Issue> issueList = issueRepository.findAll()
                .stream()
                .filter(b->b.getReturnDate()==null)
                .toList();


        ArrayNode arrayNode = objectMapper.createArrayNode();

        for(Issue issue:issueList){
            ObjectNode obj =objectMapper.createObjectNode();

            obj.put("issueId",issue.getIssueId());
            obj.put("memberId",issue.getMember().getMemberId());
            obj.put("bookId",issue.getBooks().getBookId());
            obj.put("issueDate",String.valueOf(issue.getIssueDate()));
            obj.put("dueDate",String.valueOf(issue.getDueDate()));
            obj.put("returnDate",String.valueOf(issue.getReturnDate()));
            obj.put("fineAmount",issue.getFineAmount());

            arrayNode.add(obj);
        }
        response.set("Issue",arrayNode);
        return response;

    }

    public JsonNode getAllOverDueIssuedBooks() {
        ObjectNode response = objectMapper.createObjectNode();
        List<Issue>issueList = issueRepository.findAll()
                .stream()
                .filter(i->i.getReturnDate()==null && i.getDueDate().isBefore(LocalDate.now()))
                .toList();


        ArrayNode arrayNode = objectMapper.createArrayNode();

        for(Issue issue:issueList){
            ObjectNode obj =objectMapper.createObjectNode();

            obj.put("issueId",issue.getIssueId());
            obj.put("memberId",issue.getMember().getMemberId());
            obj.put("bookId",issue.getBooks().getBookId());
            obj.put("issueDate",String.valueOf(issue.getIssueDate()));
            obj.put("dueDate",String.valueOf(issue.getDueDate()));
            obj.put("returnDate",String.valueOf(issue.getReturnDate()));
            obj.put("fineAmount",issue.getFineAmount());

            arrayNode.add(obj);
        }
        response.set("Issue",arrayNode);
        return response;
    }
}
