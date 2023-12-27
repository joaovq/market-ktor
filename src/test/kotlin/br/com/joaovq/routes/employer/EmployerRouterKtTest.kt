package br.com.joaovq.routes.employer

import com.market.core.utils.AppVersion
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class EmployerRouterKtTest {

    @Test
    fun `should return get employers status Unauthorized`() = testApplication {
        val response = client.get(AppVersion.ACTUAL_VERSION + "/employers") {
            header("Authorization", "Bearer $")
        }
        assertEquals(
            HttpStatusCode.Unauthorized,
            response.status
        )
    }
}