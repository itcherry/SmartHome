package com.smarthome.SmartHome.service.impl

import com.smarthome.SmartHome.service.MailSenderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.awt.Toolkit
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


@Service
class MailSenderServiceImpl @Autowired constructor(private val mailSender: JavaMailSender) : MailSenderService {
    private lateinit var toolkit: Toolkit
    private lateinit var timer: Timer

    private val fetchingPhotoFromCameraTask = object : TimerTask() {
        var numPhotos = 2
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
        Runtime.getRuntime().exec("ffmpeg -y -rtsp_transport tcp -i rtsp://admin:10madDev01@192.168.88.89:554 -vframes 1 $PATH_TO_PHOTO/$nameOfFile.jpg").waitFor()
        return nameOfFile
    }

    override fun sendEmailWithPhotoFromCamera() {
        toolkit = Toolkit.getDefaultToolkit()
        timer = Timer()
        timer.schedule(fetchingPhotoFromCameraTask,
                0, //initial delay
                1000//subsequent rate 1 second
        )
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

       /* val props = Properties()
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.host"] = SMTP_HOST_NAME
        props["mail.smtp.auth"] = "true"

        val auth: Authenticator = SMTPAuthenticator()
        val mailSession: Session = Session.getDefaultInstance(props, auth)
        val transport: Transport = mailSession.getTransport()

        val message = MimeMessage(mailSession)
        message.setFrom(InternetAddress(FROM_EMAIL))
        message.addRecipient(Message.RecipientType.TO, InternetAddress(TO_EMAIL1))
        message.addRecipient(Message.RecipientType.TO, InternetAddress(TO_EMAIL2))

        transport.connect()
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO))
        transport.close()*/
    }

    /*private class SMTPAuthenticator : Authenticator() {
        public override fun getPasswordAuthentication(): PasswordAuthentication {
            val username: String = SMTP_AUTH_USER
            val password: String = SMTP_AUTH_PWD
            return PasswordAuthentication(username, password)
        }
    }*/

    companion object {
        private const val TO_EMAIL1 = "itcherry97@gmail.com"
        private const val TO_EMAIL2 = "lida.chkpi@gmail.com"
        private const val FROM_EMAIL = "dynasty.sys@gmail.com"
        private const val SUBJECT = "Династія. ОХОРОНА. СИГНАЛІЗАЦІЯ!"
        private const val TEXT = "У вашому домі злодій!!! Переглянь фото в прикріплених файлах"
        private const val PHOTO_NAME_PATTERN = "dd/MM/yyyy__hh:mm:ss:SSS"
        private const val PATH_TO_PHOTO = "/home/pi/DynastyWebCam"

    }
}