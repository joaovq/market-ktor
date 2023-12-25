package com.example.routes.files

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.routing.Route
import io.ktor.server.resources.post
import io.ktor.server.response.*

@Resource("files")
class FilesResource {
    @Resource("upload")
    class Upload
}

fun Route.initializeFilesRouter() {
    post<FilesResource.Upload> {
        call.respondText {
            "Downloading file"
        }
    }
}