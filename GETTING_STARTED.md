# Technologies

| Name                      | Version   |
|---                        |---        |
| docker                    | 20.10.7   |
| docker-compose            | 1.29.2    |
| Gradle                    | 7.2       |
| OpenJDK                   | 11.0.11   |
|                           |           |

# Docker

Before running the docker commands, a environment variable `$DOCKER_UID` needs to be set. It should be set accordingly:

### Linux

`export DOCKER_UID=$(id -u)`

### MacOS

`export DOCKER_UID=$(id -u)`

First time you will run the project, using `docker-compose up`, the images will be built automatically.

Afterwards if the images need to be changed or have been changed due to renovate, run `docker-compose build` to rebuild the images. 

> The reason why `docker-compose up --build` is **not** suggested is due to history and autocomplete in the shells `up` and `up --build` will be too similar and an **_accidental_** rebuild might be triggered.

If logging is not needed, then `docker-compose up -d` will daemonize the docker containers. Logging can be acquired again using `docker-compose logs -f --tail 1 api` for the backend, `docker-compose logs -f --tail 1 angular` for the frontend and `docker-compose logs -f --tail 1 reverse-proxy` for the reverse proxy. 

> The `--tail 1` makes it so you start at the last line of the container. Otherwise you will see all that the container printed to stdout.

Windows specifics are located [here](WINDOWS_DOCKER.md).

## Shell

The shell on all images is always **Bash**. Run the following to get a correct shell to interact with the backend container for example:

`docker-compose exec app bash -l` 