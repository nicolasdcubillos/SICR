@echo off
for /r %%i in (*.yaml, *.yml) do (
    echo Ejecutando: kubectl apply -f "%%i"
    kubectl apply -f "%%i"
)