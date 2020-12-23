package com.spring.boot.bbs.ncubbs.repository;

import com.spring.boot.bbs.ncubbs.domain.Demand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author:weiqian
 * @Date: 2018/6/18 22:45
 */
public interface DemandRepository extends JpaRepository<Demand,Long>{
    Demand getDemandByDid(Long did);
    Demand findByDid(Long id);
    Demand findByDname(String name);
    List<Demand> findByDuid(Long duid);
    List<Demand> findByDsid(Long dsid);
}
