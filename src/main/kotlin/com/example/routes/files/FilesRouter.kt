package com.example.routes.files

import com.example.domain.request.LoginRequest
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.Route
import io.ktor.server.resources.post
import io.ktor.server.response.*
import java.io.File

@Resource("files")
class FilesResource {
    @Resource("upload")
    class Upload
}

fun Route.initializeFilesRouter() {
    var fileDescription = ""
    var fileName = ""
    post<FilesResource.Upload> {
        val multipartData = call.receiveMultipart()
        val contentLength = call.request.header(HttpHeaders.ContentLength)

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    fileDescription = part.value
                }

                is PartData.FileItem -> {
                    fileName = part.originalFileName as String
                    val fileBytes = part.streamProvider().readBytes()
                    File("uploads/$fileName").writeBytes(fileBytes)
                }

                else -> {}
            }
            part.dispose()
        }

        call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
    }
}