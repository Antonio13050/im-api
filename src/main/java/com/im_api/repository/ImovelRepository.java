package com.im_api.repository;

import com.im_api.model.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {
    List<Imovel> findByCorretorId(Long corretorId);
    List<Imovel> findByCorretorIdIn(List<Long> corretorIds);
}
