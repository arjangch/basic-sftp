package com.basicftp

import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import grails.gorm.transactions.Transactional

@Transactional
class BasicSftpService {
    Boolean throwException

    def uploadFile(ChannelSftp sftp, InputStream inputStream, String fileName) {
        try {
            sftp.put inputStream, fileName
        } catch (Exception e) {
            log.error e.message
            if (throwException) {
                throw e
            }
        }
    }
    
    def downloadFile(ChannelSftp sftp, String localFile, String remoteFile) {
        try {
            File outputFile = new File(localFile)
            outputFile?.newOutputStream() << sftp.get(remoteFile)
            outputFile
        } catch (Exception e) {
            log.error e.message
            if (throwException) {
                throw e
            }
        }
    }

    def renameFile(ChannelSftp sftp, String oldPath, String newPath) {
        try {
            sftp.rename oldPath, newPath
        } catch (Exception e) {
            log.error e.message
            if (throwException) {
                throw e
            }
        }
    }

    def createDir(ChannelSftp sftp, String dirName) {
        try {
            sftp.mkdir dirName
        } catch (Exception e) {
            log.error e.message
            if (throwException) {
                throw e
            }
        }
    }

    def listDir(ChannelSftp sftp, String dirName) {
        try {
            def fileLineList
            def fileList = []
            fileLineList = sftp.ls(dirName)
            fileLineList.each { it ->
                fileList.add(it.getFilename())
            }
            return fileList
        } catch (Exception e) {
            log.error e.message
            if (throwException) {
                throw e
            }
        }
    }

    def removeFile(ChannelSftp sftp, String fileName) throws Throwable {
        try {
            sftp.rm fileName
        } catch (Exception e) {
            log.error e.message
            if (throwException) {
                throw e
            }
        }
    }

    private def connect(def sftpConfig) {
        Session session = null
        ChannelSftp sftp = null
        String server = sftpConfig.get("server")
        String username = sftpConfig.get("username")
        String password = sftpConfig.get("password")
        String remoteDir = sftpConfig.get("remoteDir")
        int port = sftpConfig.get("port").toInteger()
        String keyFilePath = sftpConfig.get("keyFilePath")
        throwException = sftpConfig.get("throwException")
        try {
            JSch jSch = new JSch()
            session = jSch.getSession username, server, port
            session.setConfig "StrictHostKeyChecking", "no"

            if (password) {
                session.password = password
            } else {
                File keyFile = new File(keyFilePath)
                jSch.addIdentity(keyFile?.absolutePath)
            }

            session.connect()
            Channel channel = session.openChannel "sftp"
            channel.connect()
            sftp = channel as ChannelSftp
            sftp.cd remoteDir
            return sftp
        } catch (Exception e) {
            log.error e.message
            if (throwException) {
                throw e
            }
        }
    }

    def disconnect(ChannelSftp sftp) {
        Session session = sftp?.getSession()
        sftp?.exit()
        session?.disconnect()
    }
}
