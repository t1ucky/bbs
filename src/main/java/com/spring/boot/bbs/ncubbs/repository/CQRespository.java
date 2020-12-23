package com.spring.boot.bbs.ncubbs.repository;

import com.spring.boot.bbs.ncubbs.domain.CommentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 问答评论管理仓库
 * @Author:yangjiao
 */
public interface CQRespository extends JpaRepository<CommentQuestion,Long> {
    List<CommentQuestion> findByCqid(Long id);

}
