package com.smarthome.smarthome.service.impl

import com.smarthome.smarthome.service.MailSenderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.awt.Toolkit
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@Service
class MailSenderServiceImpl @Autowired constructor(private val mailSender: JavaMailSender) : MailSenderService {
    override fun sendEmailWithPhotoFromCamera() {
        val toolkit = Toolkit.getDefaultToolkit()
        val timer = Timer()
        timer.schedule(getFetchingPhotoFromCameraTask(toolkit, timer),
                0, //initial delay
                1000//subsequent rate 1 second
        )
    }

    fun getFetchingPhotoFromCameraTask(toolkit: Toolkit, timer: Timer) = object : TimerTask() {
        var numPhotos = PHOTOS_QUANTITY
        val photoNames = mutableListOf<String>()

        override fun run() {
            if (numPhotos > 0) {
                toolkit.beep()
                photoNames.add(fetchPhotoFromCamera())
                numPhotos--
            } else {
                toolkit.beep()
                sendEmailWithAttachments(photoNames)
                photoNames.clear()
                timer.cancel()
            }
        }
    }

    private fun fetchPhotoFromCamera(): String {
        val nameOfFile = SimpleDateFormat(PHOTO_NAME_PATTERN).format(Date())

        try {
            val process = Runtime.getRuntime().exec("ffmpeg -y -rtsp_transport tcp -i rtsp://admin:10madDev01@192.168.88.89:554 -vframes 1 $PATH_TO_PHOTO/$nameOfFile.jpg")
            val exitVal: Int = process.waitFor()
            if (exitVal == 0) {
                println("Success fetching photo!")
            }
        } catch (e: IOException) {
            e.printStackTrace();
        } catch (e: InterruptedException) {
            e.printStackTrace();
        }

        return nameOfFile
    }

    private fun sendEmailWithAttachments(attachmentNames: List<String>) {
        val msg = mailSender.createMimeMessage()

        MimeMessageHelper(msg, true).apply {
            setFrom(FROM_EMAIL)
            setTo(arrayOf(TO_EMAIL1, TO_EMAIL2))
            setSubject(SUBJECT)
            setText(TEXT)

            attachmentNames.forEach {
                addAttachment("$it.jpg", FileSystemResource(File("$PATH_TO_PHOTO/$it.jpg")))
            }
        }

        mailSender.send(msg)
    }

    companion object {
        private const val TO_EMAIL1 = "itcherry97@gmail.com"
        private const val TO_EMAIL2 = "lida.chkpi@gmail.com"
        private const val FROM_EMAIL = "dynasty.sys@gmail.com"
        private const val SUBJECT = "Династія. ОХОРОНА. СИГНАЛІЗАЦІЯ!"
        private const val TEXT = "У вашому домі злодій!!! Переглянь фото в прикріплених файлах"
        private const val PHOTO_NAME_PATTERN = "dd_MM_yyyy__hh:mm:ss:SSS"
        private const val PATH_TO_PHOTO = "DynastyWebCam"
        private const val PHOTOS_QUANTITY = 5
    }
}