#!/usr/bin/env bash
#   Use this script to test if a given TCP host/port are available

TIMEOUT=15
QUIET=0
HOST=
PORT=

echoerr() { if [[ "$QUIET" -ne 1 ]]; then echo "$@" 1>&2; fi }

usage()
{
    cat << USAGE >&2
Usage:
    $0 host:port [-t timeout] [-- command args]
    -q | --quiet                        Do not output any status messages
    -t TIMEOUT | --timeout=timeout      Timeout in seconds, zero for no timeout
    -- COMMAND ARGS                     Execute command with args after the test finishes
USAGE
    exit 1
}

wait_for()
{
    if ! command -v nc >/dev/null; then
        echoerr 'nc command is not available'
        exit 1
    fi

    while :; do
        nc -z "$HOST" "$PORT"
        result=$?
        if [[ $result -eq 0 ]]; then
            if [[ $# -gt 0 ]]; then
                exec "$@"
            fi
            exit 0
        fi
        sleep 1
    done
}

while [[ $# -gt 0 ]]; do
    case "$1" in
        *:* )
        HOST="$(cut -d: -f1 <<< "$1")"
        PORT="$(cut -d: -f2 <<< "$1")"
        shift 1
        ;;
        -q | --quiet)
        QUIET=1
        shift 1
        ;;
        -t)
        TIMEOUT="$2"
        if [[ "$TIMEOUT" == "" ]]; then break; fi
        shift 2
        ;;
        --timeout=*)
        TIMEOUT="${1#*=}"
        shift 1
        ;;
        --)
        shift
        break
        ;;
        -*)
        echoerr "Unknown flag: $1"
        usage
        ;;
        *)
        break
        ;;
    esac
done

if [[ "$HOST" == "" || "$PORT" == "" ]]; then
    echoerr "Error: you need to provide a host and port to test."
    usage
fi

wait_for "$@"