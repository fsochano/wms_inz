package com.sochanski.pick;


import com.sochanski.ApiUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH + "/pick-tasks/{pickTaskId}")
@CrossOrigin
public class PickTaskController {

    private final PickTaskService service;

    public PickTaskController(PickTaskService service) {
        this.service = service;
    }

    @PostMapping(path = "/status")
    public PickTask updatePickTaskState(@PathVariable long pickTaskId, @RequestBody @Valid PickTaskStatusParam params) {
        return service.changePickTaskStatus(pickTaskId, params.status);
    }
}
