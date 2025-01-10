package com.duoc.cloud.back_duoc_cloud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.duoc.cloud.Model.AlertaMedica;

public interface AlertaMedicaRepository extends JpaRepository<AlertaMedica, Long> {
}