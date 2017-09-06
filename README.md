# InvitationService

This is very simple HTTP Rest Service for Invitations. 

To get all possible invitations use GET request with `/invitation` endpoint.

To add new invitation use POST request with `/invitation` endpoint and following json body : 
```
{invitee:[InviteeName], email:[EmailAddress]}
```
Please mind that server has check for email address format, so it will refuse all badly formatted requests.

You can also check if server is alive using `/healthcheck` endpoint, if server is running it will return `Alive & Healthy` with status code `200`.

# How To Run

To run the service simply use run.bat script that will run fat-jar in project folder.

To specify host and port on which to run the server use : 
` run.bat [host] [port]
`
If no values will be specified default host `localhost` and port `8080` will be used.

This server is using `sbt-assembly` plugin for fat-jars creation to ease execution.
