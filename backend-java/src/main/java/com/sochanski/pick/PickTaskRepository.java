package com.sochanski.pick;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PickTaskRepository extends JpaRepository<PickTask, Long> {
}
