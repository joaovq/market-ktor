package br.com.joaovq.routes.files

import com.market.core.utils.models.EmployerTableValues
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import java.io.File
import java.util.UUID

@Resource("files")
class FilesResource {
    @Resource("upload")
    class Upload(val parent: FilesResource = FilesResource())

    @Resource("stream/{id}")
    class Stream(val parent: FilesResource = FilesResource(), val id: String)
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

    get<FilesResource.Stream> { fileResource ->
        val id = fileResource.id
        call.respondOutputStream(
            status = HttpStatusCode.PartialContent,
            contentType = ContentType.Video.Any,
            contentLength = null
        ) {
            headers {
              //  append()
            }
            TODO("Implements response stream video")
        }
    }
}