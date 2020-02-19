package com.sochanski.pick;

import com.sochanski.pick.data.PickList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickListRepository extends JpaRepository<PickList, Long> {
}
