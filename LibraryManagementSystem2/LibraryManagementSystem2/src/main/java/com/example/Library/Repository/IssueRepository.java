package com.example.Library.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Library.Entity.Issue;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {
    List<Issue> findByMember_MemberId(Long memberId);

    List<Issue> findByBooks_BookId(Long bookId);
}
