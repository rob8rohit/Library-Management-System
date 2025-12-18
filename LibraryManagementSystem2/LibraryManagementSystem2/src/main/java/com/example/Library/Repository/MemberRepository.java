package com.example.Library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Library.Entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
}
