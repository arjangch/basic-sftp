package com.basicsftp

import grails.plugins.*

class BasicSftpGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
       def grailsVersion = "7.0.0-SNAPSHOT  > *"
       // resources that are excluded from plugin packaging
       def pluginExcludes = [
           "grails-app/views/error.gsp"
       ]

       def title = "Basic-sftp" // Headline display name of the plugin
       def author = "Arjang Chinichian"
       def authorEmail = "arjangch@gmail.com"
       def description = '''\
   SFTP helper that can connect to multiple servers at the same time.
   '''
   // URL to the plugin's documentation
       def documentation = "https://grails.github.io/BasicSftp/"

       // Extra (optional) plugin metadata

       // License: one of 'APACHE', 'GPL2', 'GPL3'
       def license = "APACHE"

       // Details of company behind the plugin (if there is one)
   //    def organization = [ name: "My Company", url: "https://www.my-company.com/" ]

       // Any additional developers beyond the author specified above.
       def developers = [ [ name: "Arjang Chinichian", email: "arjangch@gmail.com" ]]

       // Location of the plugin's issue tracker.
   //    def issueManagement = [ system: "GitHub", url: "https://github.com/grails/BasicSftp/issues" ]

       // Online location of the plugin's browseable source code.
   //    def scm = [ url: "https://github.com/grails/BasicSftp" ]

       Closure doWithSpring() { {->
               // TODO Implement runtime spring config (optional)
           }
       }

       void doWithDynamicMethods() {
           // TODO Implement registering dynamic methods to classes (optional)
       }

       void doWithApplicationContext() {
           // TODO Implement post initialization spring config (optional)
       }

       void onChange(Map<String, Object> event) {
           // TODO Implement code that is executed when any artefact that this plugin is
           // watching is modified and reloaded. The event contains: event.source,
           // event.application, event.manager, event.ctx, and event.plugin.
       }

       void onConfigChange(Map<String, Object> event) {
           // TODO Implement code that is executed when the project configuration changes.
           // The event is the same as for 'onChange'.
       }

       void onShutdown(Map<String, Object> event) {
           // TODO Implement code that is executed when the application shuts down (optional)
       }
}