package com.example.Library.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Library.Service.MemberService;

import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/add")
    public JsonNode createOrUpdateMember(@RequestBody JsonNode jsonNode){
        return memberService.createOrUpdateMember(jsonNode);
    }

    @GetMapping("/getAll")
    public JsonNode getAllMember(){
        return memberService.getAllMember();
    }

    @GetMapping("/{memberId}")
    public JsonNode getMemberId(@PathVariable Long memberId){
        return memberService.getMemberId(memberId);
    }

    @DeleteMapping("{memberId}")
    public JsonNode deleteMemberById(@PathVariable Long memberId){
        return memberService.deleteMemberById(memberId);
    }
}
