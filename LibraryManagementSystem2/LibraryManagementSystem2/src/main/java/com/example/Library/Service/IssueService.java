package com.example.Library.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Library.Entity.Books;
import com.example.Library.Entity.Fine;
import com.example.Library.Entity.Issue;
import com.example.Library.Entity.Member;
import com.example.Library.Repository.BooksRepository;
import com.example.Library.Repository.FineRepository;
import com.example.Library.Repository.IssueRepository;
import com.example.Library.Repository.MemberRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FineRepository fineRepository;

    final private ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode issueBook(Long bookId, Long memberId, LocalDate issueDate, LocalDate dueDate) {

        ObjectNode response = objectMapper.createObjectNode();
        Optional<Books> booksOptional = booksRepository.findById(bookId);
        if(booksOptional.isEmpty()){
            response.put("status","error");
            response.put("message","Invalid book");
            return response;

        }

        Optional<Member>optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isEmpty()){
            response.put("status","error");
            response.put("message","Invalid member");
            return response;
        }

        Books books = booksOptional.get();
        Member member = optionalMember.get();

        if(books.getAvailableCopies()<0){
            response.put("status","error");
            response.put("message","Book is not available");
            return response;
        }

        Issue issue = new Issue();
        issue.setBooks(books);
        issue.setMember(member);
        issue.setIssueDate(issueDate);
        issue.setDueDate(dueDate);
        issue.setFineAmount(0.00);

        issueRepository.save(issue);

        // decrease the count of books availabe copies in books db

        books.setAvailableCopies(books.getAvailableCopies()-1);
        booksRepository.save(books);

        response.put("status","success");
        response.put("message","book issued successfully");
        response.put("issueId",issue.getIssueId());
        response.put("memberId",memberId);
        response.put("bookId",bookId);

        return response;
    }

    public JsonNode getAllIssueBooks() {

        ObjectNode response = objectMapper.createObjectNode();

        List<Issue>issueList=issueRepository.findAll();

        if(issueList.isEmpty()){
            response.put("status","error");
            response.put("message","No issue found");
            return response;
        }

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

    public JsonNode getIssueById(Long issueId) {

        ObjectNode response = objectMapper.createObjectNode();

        Optional<Issue>optionalIssue = issueRepository.findById(issueId);
        if(optionalIssue.isEmpty()){
            response.put("status","error");
            response.put("message","Invalid issue Id");

            return response;
        }

        Issue issue = optionalIssue.get();
        response.put("status","success");
        response.put("issueId",issue.getIssueId());
        response.put("memberId",issue.getMember().getMemberId());
        response.put("bookId",issue.getBooks().getBookId());
        response.put("issueDate",String.valueOf(issue.getIssueDate()));
        response.put("dueDate",String.valueOf(issue.getDueDate()));
        response.put("returnDate",String.valueOf(issue.getReturnDate()));
        response.put("fineAmount",issue.getFineAmount());

       return response;
    }

    public JsonNode getByMember(Long memberId) {

        ObjectNode response = objectMapper.createObjectNode();
        Optional<Member>memberOptional = memberRepository.findById(memberId);
        if(memberOptional.isEmpty()){
            response.put("status","error");
            response.put("message","invalid member");
            return response;
        }

        List<Issue>issueList= issueRepository.findByMember_MemberId(memberId);
        if(issueList.isEmpty()){
            response.put("status","error");
            response.put("message","no issue found for given member");
            return response;
        }

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

    public JsonNode getByBooks(Long bookId) {

        ObjectNode response = objectMapper.createObjectNode();


        List<Issue>issueList= issueRepository.findByBooks_BookId(bookId);
        if(issueList.isEmpty()){
            response.put("status","error");
            response.put("message","no issue found for given book");
            return response;
        }

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

    public JsonNode returnBook(Long issueId, LocalDate returnDate) {

        ObjectNode response = objectMapper.createObjectNode();

        Optional<Issue>issueOptional=issueRepository.findById(issueId);
        if(issueOptional.isEmpty()){
            response.put("status","error");
            response.put("message","Issue record not found with ID: "+issueId);

            return response;
        }

        Issue issue = issueOptional.get();
        issue.setReturnDate(returnDate);

        LocalDate dueDate = issue.getDueDate();
        Long lateDays = ChronoUnit.DAYS.between(dueDate,returnDate);

        // Calculating the fine amount for each day ie: 10Rs fine for each day
        double fineAmount=0.0;
        if(lateDays>0){
             fineAmount = lateDays*10.0;
            issue.setFineAmount(fineAmount);
        }

        // Update Book DB increase the count of available
        Books books = issue.getBooks();
        books.setAvailableCopies(books.getAvailableCopies()+1);

        booksRepository.save(books);
        issueRepository.save(issue);

        // if fine exist , then create fine entry

        if(fineAmount>0){
            Fine fine =new Fine();
            fine.setAmount(fineAmount);
            fine.setMember(issue.getMember());
            fine.setFineDate(returnDate);
            fine.setFinePaid(false);

            fineRepository.save(fine);
        }

        response.put("status","success");
        response.put("message","Book returned successfully");
        response.put("memberName",issue.getMember().getName());
        response.put("fineAmount",fineAmount);
        response.put("returnDate",returnDate.toString());

        return response;
    }

    //


}
