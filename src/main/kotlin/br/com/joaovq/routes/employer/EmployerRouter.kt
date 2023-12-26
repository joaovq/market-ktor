package br.com.joaovq.routes.employer

import br.com.joaovq.dao.employer.EmployerRepository
import br.com.joaovq.domain.request.CreateEmployerRequest
import br.com.joaovq.plugins.AUTH_NAME
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.resources.get
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.initializeEmployerRouter() {
    val employerDao by inject<EmployerRepository>()
    authenticate(AUTH_NAME) {
        get<Employer> {
            val employers = employerDao.findAllEmployers()
            call.respond(
                HttpStatusCode.OK,
                employers
            )
        }
        post<Employer> { _ ->
            val employerRequest = call.receive<CreateEmployerRequest>()
            val newEmployer = employerDao.addNewEmployer(employerRequest)
            call.response.header(HttpHeaders.Location, "v1/employers/${newEmployer.id}")
            call.respond(
                HttpStatusCode.Created,
                newEmployer
            )
        }
    }
}

@Resource("/employers")
class Employer(val query: String? = "")