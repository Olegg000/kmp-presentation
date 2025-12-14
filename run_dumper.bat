@echo off
REM Это аналог вашего .sh скрипта для Windows

REM Путь к вашему скрипту project_dumper.py
set "PYTHON_SCRIPT=./project_dumper.py"

REM Директория проекта, которую нужно обработать
set "PROJECT_ROOT=./"

REM Файл с дополнительным текстом
set "APPEND_TEXT_FILE="

REM Имя выходного файла
set "OUTPUT_FILENAME=RESULT.txt"

REM Директория для сохранения дампа
set "OUTPUT_DIR=./"

REM Аргументы для кодировки (если нужны)
REM set "ENCODING_ARGS=--encoding utf-8 --error-handling surrogateescape"
set "ENCODING_ARGS="

REM Файл игнорирования (если нужен)
REM set "IGNORE_FILE_ARG=--ignore-file ./dataGetter/.custom_ignore"
set "IGNORE_FILE_ARG="


REM Запуск скрипта python
echo Запуск создания дампа проекта...
python "%PYTHON_SCRIPT%" "%PROJECT_ROOT%" ^
    --append-text-file "%APPEND_TEXT_FILE%" ^
    --output-filename "%OUTPUT_FILENAME%" ^
    --output-dir "%OUTPUT_DIR%" ^
    %ENCODING_ARGS% ^
    %IGNORE_FILE_ARG%

REM Проверка кода завершения
if %errorlevel% equ 0 (
    echo Дамп успешно создан!
) else (
    echo Во время создания дампа произошла ошибка.
)

REM пауза, чтобы окно не закрылось сразу (опционально)
pause