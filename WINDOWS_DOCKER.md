# Windows

This page holds some specifics for Windows and using docker.

# Getting Started

Install [Chocolatey](https://chocolatey.org/install) first and then the following packages: `docker, docker-compose, docker-for-windows`. Afterwards follow the following instructions for [Windows Subsytem for Linux](https://docs.microsoft.com/en-us/windows/wsl/install-win10) to get [Linux Containers](https://docs.microsoft.com/en-us/virtualization/windowscontainers/quick-start/quick-start-windows-10-linux). 

Now the system is ready to run docker. 

# Mounting volumes

Volumes in `docker-compose.yml` can only be directories to directories in the so called [short syntax](https://docs.docker.com/compose/compose-file/compose-file-v3/#short-syntax-3).

If files need to be mounted directly then the following [long syntax](https://docs.docker.com/compose/compose-file/compose-file-v3/#long-syntax-3) needs to be used:

```
 volumes:
   - type: bind
     source: ./application.yml
     target: /srv/http/application.yml
```

## I/O intensive

If folders like `node_modules` need to be mounted that will be I/O intensive as there will be a lot of writing and reading to install packages for example, then this needs to be a [named volume](https://docs.docker.com/compose/compose-file/compose-file-v3/#volume-configuration-reference).

> The reason for this is that there will otherwise be a feedback loop that will cause issues. The container will write a file and inform the host system, the host system sees the new file and will tell the container and so on. 

## Scripts in the .boot dirs

Scripts cannot be mounted as is due to line ending problems. 

> Windows uses \CRLF and Unix uses \LF. This means that the shell scripts get mangled and cannot be executed.

To workaround this, create a zip of everything inside `.boot` and place it inside `.boot`. The entrypoint script will take this zip file and unpack it over the files and then run them.