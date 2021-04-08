package com.envolti.teste.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface SourceControllerDocs {

    @ApiOperation(value = "Returns a source of gitHub")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a source of gitHub"),
    })
    String getSource();
}
