package com.im_api.repository;

import com.im_api.model.Imovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long>, JpaSpecificationExecutor<Imovel> {
    @Query("SELECT new com.im_api.dto.ImovelResumoDTO(i.id, i.codigo, i.titulo, i.clienteId, i.corretorId, i.status) FROM Imovel i")
    List<com.im_api.dto.ImovelResumoDTO> findAllResumo();

    @Query("SELECT new com.im_api.dto.ImovelResumoDTO(i.id, i.codigo, i.titulo, i.clienteId, i.corretorId, i.status) FROM Imovel i WHERE i.corretorId = :corretorId")
    List<com.im_api.dto.ImovelResumoDTO> findResumoByCorretorId(@org.springframework.data.repository.query.Param("corretorId") Long corretorId);

    @Query("SELECT new com.im_api.dto.ImovelResumoDTO(i.id, i.codigo, i.titulo, i.clienteId, i.corretorId, i.status) FROM Imovel i WHERE i.corretorId IN :corretorIds")
    List<com.im_api.dto.ImovelResumoDTO> findResumoByCorretorIdIn(@org.springframework.data.repository.query.Param("corretorIds") List<Long> corretorIds);

    Page<Imovel> findByCorretorId(Long corretorId, Pageable pageable);

    Page<Imovel> findByCorretorIdIn(List<Long> corretorIds, Pageable pageable);

}
