package com.example.Library.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Library.Entity.Fine;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine,Long> {
    List<Fine> findByMember_MemberId(Long memberId);
}
