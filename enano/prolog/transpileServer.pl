:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_dispatch)).
:- use_module(library(http/http_json)).
:- use_module(library(http/http_log)).
:- use_module(library(http/http_client)).
:- use_module(library(http/http_cors)).
:- use_module(client, [runClass/2,compileFile/2, readTranspileFile/2]).
:-use_module(parser,[transpile/2]).

:- set_setting(http:cors, [*]).
% URL handlers.
:- http_handler('/newFile', writeFileHandler, [method(post)]).
:- http_handler('/renameFile', renameFileHandler, [method(post)]).
:- http_handler('/checkForFile', checkFileHandler, [method(post)]).
:- http_handler('/compile', compilerCall, [method(post)]).
:- http_handler('/run', evalHandler, [method(post)]).
:- http_handler('/getTranspileCode', getTranspileCode, [method(post)]).

%main response
writeFileHandler(Request) :- http_read_data(Request, Data,[]), writeBody(Data),cors_enable,reply_json_dict('DSDS').
renameFileHandler(Request) :- http_read_data(Request, Data,[]), renameFile(Data,R),cors_enable,reply_json_dict(R).
checkFileHandler(Request) :- http_read_data(Request, Data,[]), checkForFile(Data,R),cors_enable,reply_json_dict(R).
evalHandler(Request) :- http_read_data(Request, Data,[]), sendToRun(Data,R),cors_enable,reply_json_dict(R).
compilerCall(Request):-http_read_data(Request, Data,[]), compileFile(Data,R),cors_enable,reply_json_dict(R).
getTranspileCode(Request) :- http_read_data(Request, Data,[]), readTranspileFile(Data,R),cors_enable,reply_json_dict(R).


%Utils
writeBody(B):- open('nanoFiles\\new-File.no',write,Out),write(Out,B),close(Out).

renameFile(N,R):-atom_concat('nanoFiles\\',N,NN),atom_concat(NN,'.no',F), rename_file('nanoFiles\\new-File.no',F),
startTranspile(F,N,R).

checkForFile(F,R):-getFileName(F,FF),exists_file(FF)->R = 'existe';R = 'no existe'.
sendToRun(F, R) :- runClass(R, F).
startTranspile(File,Name,R):-catch((transpile(File,Name),compileFile(Name,R2),R=['Se ha transpilado correctamente',R2]),EX,R=EX).
getFileName(N,F):-atom_concat('nanoFiles\\',N,NN),atom_concat(NN,'.no',F).


server(Port) :- http_server(http_dispatch, [port(Port)]).

%set_setting(http:logfile, 'service_log_file.log').

:- initialization
    format('*** Starting Server ***~n', []),
    (current_prolog_flag(argv, [SPort | _]) -> true ; SPort='8000'),
    atom_number(SPort, Port),
    format('*** Serving on port ~d *** ~n', [Port]),
    server(Port).