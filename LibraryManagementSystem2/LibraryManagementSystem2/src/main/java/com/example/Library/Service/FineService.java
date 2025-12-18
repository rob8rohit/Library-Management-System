package com.example.Library.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Library.Entity.Fine;
import com.example.Library.Repository.FineRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Optional;

@Service
public class FineService {

    @Autowired
    private FineRepository fineRepository;

    final private ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode getAllFines() {
        ObjectNode response = objectMapper.createObjectNode();

        List<Fine>fineList = fineRepository.findAll();
        if(fineList.isEmpty()){
            response.put("status","success");
            response.put("message","No fine found");

            return response;
        }

        ArrayNode arrayNode = objectMapper.createArrayNode();
        for(Fine fine :fineList){
            ObjectNode resp = objectMapper.createObjectNode();
            resp.put("fineId",fine.getFineId());
            resp.put("amount",fine.getAmount());
            resp.put("fineDate",fine.getFineDate().toString());
            resp.put("finePaid",fine.isFinePaid());
            resp.put("memberName",fine.getMember().getName());

            arrayNode.add(resp);
        }

        response.put("status","success");
        response.put("message","Fine data fetched successfully");
        response.set("Fine",arrayNode);
        return response;
    }

    public JsonNode getFineById(Long fineId) {

        ObjectNode response = objectMapper.createObjectNode();

        Optional<Fine> fineOptional=fineRepository.findById(fineId);
        if(fineOptional.isEmpty()){
            response.put("status","error");
            response.put("message","No fine found with ID: "+fineId);

            return response;
        }
        Fine fine = fineOptional.get();

        response.put("fineId",fine.getFineId());
        response.put("amount",fine.getAmount());
        response.put("fineDate",fine.getFineDate().toString());
        response.put("finePaid",fine.isFinePaid());
        response.put("memberName",fine.getMember().getName());

        response.put("status","success");
        return response;
    }

    public JsonNode getFinesByMember(Long memberId) {

        ObjectNode response = objectMapper.createObjectNode();
        List<Fine>fineList = fineRepository.findByMember_MemberId(memberId);

        if(fineList.isEmpty()){
            response.put("status","success");
            response.put("message","No fine found with member ID: "+memberId);

            return response;
        }

        ArrayNode arrayNode = objectMapper.createArrayNode();
        for(Fine fine :fineList){
            ObjectNode resp = objectMapper.createObjectNode();
            resp.put("fineId",fine.getFineId());
            resp.put("amount",fine.getAmount());
            resp.put("fineDate",fine.getFineDate().toString());
            resp.put("finePaid",fine.isFinePaid());
            resp.put("memberName",fine.getMember().getName());

            arrayNode.add(resp);
        }

        response.put("status","success");
        response.put("message","Fine data fetched successfully");
        response.set("Fine",arrayNode);
        return response;

    }

    public JsonNode getUnpaidFinesByMember(Long memberId) {

        ObjectNode response = objectMapper.createObjectNode();

        List<Fine>fineList = fineRepository.findByMember_MemberId(memberId)
                .stream()
                .filter(f->!f.isFinePaid())
                .toList();

        if(fineList.isEmpty()){
            response.put("status","success");
            response.put("message","No Fine found with member ID: "+memberId);

            return response;
        }

        ArrayNode arrayNode = objectMapper.createArrayNode();
        for(Fine fine :fineList){
            ObjectNode resp = objectMapper.createObjectNode();
            resp.put("fineId",fine.getFineId());
            resp.put("amount",fine.getAmount());
            resp.put("fineDate",fine.getFineDate().toString());
            resp.put("finePaid",fine.isFinePaid());
            resp.put("memberName",fine.getMember().getName());

            arrayNode.add(resp);
        }

        response.put("status","success");
        response.put("message","Fine data fetched successfully");
        response.set("Fine",arrayNode);
        return response;

    }

    public JsonNode payFine(Long fineId) {

        ObjectNode response = objectMapper.createObjectNode();
        Optional<Fine>optionalFine = fineRepository.findById(fineId);
        if(optionalFine.isEmpty()){
            response.put("status","success");
            response.put("message","No fine found with ID: "+fineId);
            return response;
        }

        Fine fine = optionalFine.get();
        fine.setFinePaid(true);
        fineRepository.save(fine);

        response.put("status","success");
        response.put("message","Fine paid");

        return response;
    }
}
