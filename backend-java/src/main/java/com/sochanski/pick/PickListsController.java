package com.sochanski.pick;


import com.sochanski.ApiUtils;
import com.sochanski.pick.data.PickList;
import com.sochanski.pick.data.PickTask;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize(value ="hasAuthority('PICKING')")
@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH + "/pick-lists")
@CrossOrigin
public class PickListsController {

    private final PickListService service;

    public PickListsController(PickListService service) {
        this.service = service;
    }

    @GetMapping
    public List<PickList> getPickLists() {
        return service.getPickLists();
    }

    @GetMapping(path = "/{pickListId}/pick-tasks")
    public List<PickTask> getPickTasks(@PathVariable long pickListId) {
        return service.getPickTasks(pickListId);
    }

}
