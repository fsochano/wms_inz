package com.sochanski.pick;


import com.sochanski.ApiUtils;
import com.sochanski.pick.data.PickTask;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@PreAuthorize(value ="hasAuthority('PICKING')")
@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH + "/pick-tasks/{pickTaskId}")
@CrossOrigin
public class PickTaskController {

    private final PickListService service;

    public PickTaskController(PickListService service) {
        this.service = service;
    }

    @PostMapping(path = "/pick")
    public PickTask pickPickTask(@PathVariable long pickTaskId) {
        return service.pick(pickTaskId);
    }

    @PostMapping(path = "/complete")
    public PickTask completePickTask(@PathVariable long pickTaskId) {
        return service.complete(pickTaskId);
    }

}
