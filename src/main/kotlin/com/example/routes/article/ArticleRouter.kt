package com.example.routes.article

import com.example.dao.article.dao
import com.example.plugins.AUTH_NAME
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.initRouterArticle() {
    authenticate(AUTH_NAME) {
        get<ArticleResources> {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim("username").asString()
            val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
            call.respond(status = HttpStatusCode.OK, dao.allArticles())
        }
    }
}

@Resource("/articles")
class ArticleResources(val query: String? = "") {
    @Resource("search")
    class Search(val parent: ArticleResources = ArticleResources())
}