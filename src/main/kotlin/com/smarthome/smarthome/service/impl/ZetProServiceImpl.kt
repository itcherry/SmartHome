package com.smarthome.smarthome.service.impl

import com.burgstaller.okhttp.AuthenticationCacheInterceptor
import com.burgstaller.okhttp.CachingAuthenticatorDecorator
import com.burgstaller.okhttp.digest.CachingAuthenticator
import com.burgstaller.okhttp.digest.Credentials
import com.burgstaller.okhttp.digest.DigestAuthenticator
import com.smarthome.smarthome.error.ZetProError
import com.smarthome.smarthome.exception.ZetProException
import com.smarthome.smarthome.service.ZetProService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class ZetProServiceImpl @Autowired constructor() : ZetProService {
    private val log = LoggerFactory.getLogger("ZetProService")

    @Value("\${zetpro.server.username}")
    private val USERNAME: String? = null

    @Value("\${zetpro.server.password}")
    private val REALM: String? = null

    @Value("\${zetpro.server.security.url}")
    private val URL: String? = null

    override fun enableZetProSecurity(doEnable: Boolean): Boolean {
        val body = "{\"Enable\":${if (doEnable) 1 else 0}}"

        val authenticator = DigestAuthenticator(Credentials(USERNAME, REALM))

        val authCache = ConcurrentHashMap<String, CachingAuthenticator>()
        val client = OkHttpClient.Builder()
                .authenticator(CachingAuthenticatorDecorator(authenticator, authCache))
                .addInterceptor(AuthenticationCacheInterceptor(authCache))
                .build()

        val requestNew = okhttp3.Request.Builder()
                .url(URL!!)
                .put(body.toRequestBody(MediaType.APPLICATION_JSON_VALUE.toMediaType()))
                .build()

        try {
            val response = client.newCall(requestNew).execute()

            return if(response.isSuccessful) {
                response.close()
                doEnable
            } else {
                log.error("Can't enable/disable ZetPro security.Code from server: ${response.code}. Body: ${response.body}")
                if(response.code == 401) {
                    throw ZetProException(ZetProError.UNAUTHORIZED)
                } else {
                    throw ZetProException(ZetProError.NOT_SUCCESFUL)
                }
            }
        } catch (e: Exception) {
            log.error("Can't enable/disable ZetPro security due to error: ", e)
            throw ZetProException(ZetProError.NOT_SUCCESFUL)
        }
    }
}