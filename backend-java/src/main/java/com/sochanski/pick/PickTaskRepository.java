package com.sochanski.pick;

import com.sochanski.pick.data.PickTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickTaskRepository extends JpaRepository<PickTask, Long> {
}
