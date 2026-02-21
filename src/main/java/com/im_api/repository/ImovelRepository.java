package com.im_api.repository;

import com.im_api.model.Imovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long>, JpaSpecificationExecutor<Imovel> {
//    List<Imovel> findByCorretorId(Long corretorId);
//
//    List<Imovel> findByCorretorIdIn(List<Long> corretorIds);

    Page<Imovel> findByCorretorId(Long corretorId, Pageable pageable);

    Page<Imovel> findByCorretorIdIn(List<Long> corretorIds, Pageable pageable);

}
