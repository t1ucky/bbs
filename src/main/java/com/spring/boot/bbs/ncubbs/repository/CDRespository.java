package com.spring.boot.bbs.ncubbs.repository;

import com.spring.boot.bbs.ncubbs.domain.CommentDemand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 需求评论管理仓库
 * @Author:yangjiao
 */
public interface CDRespository extends JpaRepository<CommentDemand,Long> {
    List<CommentDemand> findByCdid(Long id);
}
