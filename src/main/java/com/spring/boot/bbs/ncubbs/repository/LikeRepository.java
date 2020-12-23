package com.spring.boot.bbs.ncubbs.repository;

import com.spring.boot.bbs.ncubbs.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * 点赞管理仓库
 * @Author:yangjiao
 */
public interface LikeRepository extends JpaRepository<Like ,Long> {

}
