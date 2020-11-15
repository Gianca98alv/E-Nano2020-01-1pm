:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_dispatch)).
:- use_module(library(http/http_json)).
:- use_module(library(http/http_log)).
:- use_module(library(http/http_client)).
:- use_module(library(http/http_cors)).
:- use_module(client, [postClient/2]).
:- set_setting(http:cors, [*]).
% URL handlers.
:- http_handler('/newFile', writeFileHandler, [method(post)]).
:- http_handler('/renameFile', renameFileHandler, [method(post)]).
:- http_handler('/checkForFile', checkFileHandler, [method(post)]).
:- http_handler('/run', evalHandler, [method(post)]).

%main response
writeFileHandler(Request) :- http_read_data(Request, Data,[]), writeBody(Data),cors_enable,reply_json_dict([]).
renameFileHandler(Request) :- http_read_data(Request, Data,[]), renameFile(Data),cors_enable,reply_json_dict([]).
checkFileHandler(Request) :- http_read_data(Request, Data,[]), checkForFile(Data,R),cors_enable,reply_json_dict(R).
evalHandler(Request) :- http_read_data(Request, Data,[]), sendToRun(Data,R),cors_enable,reply_json_dict(R).


%Utils
writeBody(B):- open('nanoFiles\\new-File.no',write,Out),write(Out,B),close(Out).
renameFile(N):-atom_concat('nanoFiles\\',N,NN), rename_file('nanoFiles\\new-File.no',NN).
checkForFile(F,R):-atom_concat('nanoFiles\\',F,FF),exists_file(FF)->R = 'existe';R = 'no existe'.
sendToRun(F, R) :- postClient(R, F).



server(Port) :- http_server(http_dispatch, [port(Port)]).

%set_setting(http:logfile, 'service_log_file.log').

:- initialization
    format('*** Starting Server ***~n', []),
    (current_prolog_flag(argv, [SPort | _]) -> true ; SPort='8000'),
    atom_number(SPort, Port),
    format('*** Serving on port ~d *** ~n', [Port]),
    server(Port).