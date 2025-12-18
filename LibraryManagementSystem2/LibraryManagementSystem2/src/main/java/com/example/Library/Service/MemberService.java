package com.example.Library.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Library.Entity.Member;
import com.example.Library.Repository.MemberRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    final private ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode createOrUpdateMember(JsonNode jsonNode) {

        ObjectNode response = objectMapper.createObjectNode();

        Member member;
        if(jsonNode.has("memberId")){
            Long memberId = jsonNode.get("memberId").asLong();
            Optional<Member> memberOptional=memberRepository.findById(memberId);
            if(memberOptional.isEmpty()){
                response.put("status","error");
                response.put("message","member is not found with ID: "+memberId);
                return response;
            }else{
                member = memberOptional.get();
            }
        }else{
            member = new Member();
        }

        member.setName(jsonNode.has("name")?jsonNode.get("name").asText():null);
        member.setEmail(jsonNode.has("email")?jsonNode.get("email").asText():null);
        member.setPhone(jsonNode.has("phone")?jsonNode.get("phone").asText():null);
        member.setAddress(jsonNode.has("address")?jsonNode.get("address").asText():null);
        member.setMembershipDate(jsonNode.has("membershipDate")?jsonNode.get("membershipDate").asText():null);

        memberRepository.save(member);

        response.put("status","success");
        response.put("message","Data createdOrUpdated successfully");

        return response;




    }

    public JsonNode getAllMember() {

        ObjectNode response = objectMapper.createObjectNode();

        List<Member> memberList= memberRepository.findAll();
        if(memberList.isEmpty()){
            response.put("status","error");
            response.put("message","No member is available");

            return response;
        }
        return objectMapper.valueToTree(memberList);
    }

    public JsonNode getMemberId(Long memberId) {

        ObjectNode response = objectMapper.createObjectNode();

        Optional<Member>memberOptional = memberRepository.findById(memberId);
        if(memberOptional.isEmpty()){
            response.put("status","error");
            response.put("message","member is not found with ID: "+memberId);

            return response;
        }

        return objectMapper.valueToTree(memberOptional.get());
    }

    public JsonNode deleteMemberById(Long memberId) {

        ObjectNode response = objectMapper.createObjectNode();

        Optional<Member>memberOptional = memberRepository.findById(memberId);
        if(memberOptional.isEmpty()){
            response.put("status","error");
            response.put("message","member is not found with ID: "+memberId);

            return response;
        }else{
            Member member = memberOptional.get();
            memberRepository.delete(member);

            response.put("status","success");
            response.put("message","member is deleted with ID: "+memberId);
        }
        return response;
    }
}
