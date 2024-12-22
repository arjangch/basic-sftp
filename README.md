## Grails Basic SFTP Plugin () 
Basic file actions for an SFTP server.

## Description
Allows application to upload, download, list, delete, rename, create directory to an SFTP Server with one method call. The connections and boilerplate codes are all handled by the plugin. The plugin uses JCraft JSch (http://www.jcraft.com/jsch/) for its SSH2 implementation. The client can be authenticated via private key or password.

This plugin allows connection to multiple SFTP server at the same time.

## Installation
Add plugin to ```build.gradle```
```groovy
dependencies {
    implementation 'org.grails.plugins:basic-sftp:<latest-version>'
}
```

## Dependency
Add dependency to ```grails-app/conf/BuildConfig.groovy```
```groovy
dependencies {
    implementation 'com.jcraft:jsch:0.1.55'
}
```
## Config
In your application add sftp configuration ```grails-app/conf/application.yml```
```yaml
sftpserver1:
    server='qwerty.houston.com'
    username='helloworld'
    password='' // Leave empty string if you are using a private key, if password has a value it will overwrite the private key.
    remoteDir='/path_to_remote_dir/my_dir'
    port=22
    keyFilePath='/path_to_pk/my_pk.ppk'
    throwException=false // set to true if you want to handle the exceptions manually.

sftpserver2:
    server='qwerty.houston.com'
    username='helloworld'
    password='' // Leave empty string if you are using a private key, if password has a value it will overwrite the private key.
    remoteDir='/path_to_remote_dir/my_dir'
    port=22
    keyFilePath='/path_to_pk/my_pk.ppk'
    throwException=false // set to true if you want to handle the exceptions manually.
```

## Usage
In code open connection to SFTP server and pass ChannelSftp when invoking method.  
```groovy
ChannelSftp sftpChannel1 = basicSftpService.connect(grailsApplication.config.sftpserver1)
ChannelSftp sftpChannel2 = basicSftpService.connect(grailsApplication.config.sftpserver2)

basicSftpService.createDir(sftpChannel1, 'temp1')
basicSftpService.createDir(sftpChannel2, 'temp2')
```

## Sample code
Inject the service class, from there you can call the uploadFile(), downloadFile(), etc.
```groovy
import com.jcraft.jsch.ChannelSftp

class MyController {
	// inject the service class.
    BasicSftpService basicSftpService

    def sftpOperations() {
        ChannelSftp sftpChannel12 = basicSftpService.connect(grailsApplication.config.sftpserver12)
        
        def instrm = IOUtils.toInputStream("hellow world", StandardCharsets.UTF_8)
        basicSftpService.uploadFile(sftpChannel12, instrm, "tempfile.txt")
        basicSftpService.uploadFile(sftpChannel12, instrm, "temp1/tempfile1.txt")

        def fileList = basicSftpService.listDir(sftpChannel12, 'temp1/')
        log.info "---fileList="+fileList

        InputStream targetStream = new FileInputStream(new File("/Downloads/test3.json"))
        basicSftpService.uploadFile(sftpChannel12, targetStream, "test3.json")
        basicSftpService.uploadFile(sftpChannel12, targetStream, "temp1/test3.json")

        basicSftpService.renameFile(sftpChannel12, "test3.json", "test3X.json")
        basicSftpService.renameFile(sftpChannel12, "temp1/test3.json", "temp1/test3X.json")

        basicSftpService.downloadFile(sftpChannel12, "/Downloads/test3X_local.json", "test3X.json")
        basicSftpService.downloadFile(sftpChannel12, "/Downloads/test3X_temp1_local.json", "temp1/test3X.json")

        basicSftpService.removeFile(sftpChannel12,"test3X.json")
        basicSftpService.removeFile(sftpChannel12,"temp1/test3X.json")

        basicSftpService.disconnect(sftpChannel12)
    }
}
```
