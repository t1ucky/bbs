package com.spring.boot.bbs.ncubbs.repository;

import com.spring.boot.bbs.ncubbs.domain.SuperMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperMasterRepository extends JpaRepository<SuperMaster,Long> {
    /**
     * 根据名称查询
     * @param name
     * @return
     */
    SuperMaster findBySmname(String name);
}
