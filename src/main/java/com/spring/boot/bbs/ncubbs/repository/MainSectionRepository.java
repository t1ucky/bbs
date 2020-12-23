package com.spring.boot.bbs.ncubbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.spring.boot.bbs.ncubbs.domain.MainSection;

/**
 * @Author:weiqian
 * @Date: 2018/6/14 19:12
 */
public interface MainSectionRepository extends JpaRepository<MainSection,Long> {
    MainSection findByMsid(Long id);
}
