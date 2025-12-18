package com.example.Library.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Library.Service.FineService;

import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/fine")
public class FineController {

    @Autowired
    private FineService fineService;

    // Get All fine
    @GetMapping("/getAllFines")
    public JsonNode getAllFines(){
        return fineService.getAllFines();
    }

    // Get fine by id

    @GetMapping("/{fineId}")
    public JsonNode getFineById(@PathVariable Long fineId){
        return fineService.getFineById(fineId);
    }

    // Get fine by member
    @GetMapping("/member/{memberId}")
    public JsonNode getFinesByMember(@PathVariable Long memberId){
        return fineService.getFinesByMember(memberId);
    }

    // Get unpaid fines by member

    @GetMapping("/member/{memberId}/unpaid")
    public JsonNode getUnpaidFinesByMember(@PathVariable Long memberId){
        return fineService.getUnpaidFinesByMember(memberId);
    }

    //mark fine as paid
    @PostMapping("/pay/{fineId}")
    public JsonNode payFine(@PathVariable Long fineId){
        return fineService.payFine(fineId);
    }
}
