package com.ideia.projetoideia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideia.projetoideia.model.LeanCanvas;

@Repository
public interface LeanCanvasRepositorio extends JpaRepository<LeanCanvas, Integer> {

}
